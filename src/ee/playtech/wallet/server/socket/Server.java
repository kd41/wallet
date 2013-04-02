package ee.playtech.wallet.server.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;
import ee.playtech.wallet.server.services.ServerService;
import ee.playtech.wallet.server.services.ServerServiceImpl;

public class Server implements Runnable {
  private static final Logger log = LoggerFactory.getLogger("server");

  private ServerSocket serverSocket;
  private ServerService service = new ServerServiceImpl();
  private WalletChangeRequest message;

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }

  @Override
  public void run() {
    log.info("Server started");
    while (true) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
          do {
            try {
              // receive
              message = (WalletChangeRequest) in.readObject();
              log.debug(":IN {}", message);

              WalletChangeResponse response = service.getWalletResponse(message);

              // send
              out.writeObject(response);
              out.flush();
              log.debug(":OUT  {}", response);
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
