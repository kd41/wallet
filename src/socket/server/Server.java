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
  private static final Logger _log = LoggerFactory.getLogger("server");

  protected boolean isRunning = true;
  private ServerSocket serverSocket;
  private ServerService service = new ServerServiceImpl();

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
    private WalletRequest message;

    private ServerThread(Socket clientSocket, int count) {
      this.clientSocket = clientSocket;
      this.count = count;
    }

    @Override
    public void run() {
      try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
        out.flush();
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
          do {
            try {
              message = (WalletRequest) in.readObject();
              out.writeObject(service.getWalletResponse(message));
              out.flush();
            } catch (ClassNotFoundException e) {
              log.error(e.getMessage(), e);
            }
          } while (true);
        }
      } catch (EOFException e) {
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
  }
}
