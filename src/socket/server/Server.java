package socket.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.services.WalletRequest;
import database.services.WalletResponse;

public class Server {
  private static final Logger log = LoggerFactory.getLogger(Server.class);
  private ServerSocket serverSocket;
  protected boolean isRunning = true;

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    int count = 0;
    while (isRunning) {
      Socket clientSocket = serverSocket.accept();
      ServerThread thread = new ServerThread(clientSocket, count++);
      thread.run();
    }
  }

  protected void stop() {
    this.isRunning = false;
  }

  private class ServerThread extends Thread {
    private Socket clientSocket;
    private int count = -1;
    private ObjectOutputStream out;
    private WalletRequest message;

    private ServerThread(Socket clientSocket, int count) {
      this.clientSocket = clientSocket;
      this.count = count;
    }

    @Override
    public void run() {
      try {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
          do {
            try {
              message = (WalletRequest) in.readObject();
              log.info("Server received: " + message);
              sendMessage(new WalletResponse(message));
            } catch (ClassNotFoundException e) {
              System.out.println("Data received in unknown format: " + e);
            }
          } while (true);
        }
      } catch (EOFException e) {
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        try {
          out.close();
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    }

    private void sendMessage(WalletResponse msg) {
      log.info("Server sended: " + msg);
      try {
        out.writeObject(msg);
        out.flush();
      } catch (IOException e) {
        System.out.println(e);
      }
    }

  }
}
