package ee.playtech.wallet.socket.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.WalletChangeRequest;

public class Server {
  private static final Logger log = LoggerFactory.getLogger(Server.class);

  protected boolean isRunning = true;
  private ServerSocket serverSocket;
  private ServerService service = new ServerServiceImpl();

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    while (isRunning) {
      Socket clientSocket = serverSocket.accept();
      ServerThread thread = new ServerThread(clientSocket);
      thread.run();
    }
  }

  protected void stop() {
    this.isRunning = false;
  }

  private class ServerThread extends Thread {
    private Socket clientSocket;
    private WalletChangeRequest message;

    private ServerThread(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
        out.flush();
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
          do {
            try {
              message = (WalletChangeRequest) in.readObject();
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