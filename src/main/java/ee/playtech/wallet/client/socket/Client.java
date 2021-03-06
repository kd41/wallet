package ee.playtech.wallet.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;
import ee.playtech.wallet.server.socket.Server;

public class Client implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(Server.class);

  private String host;
  private int port;

  private WalletChangeRequest message;
  private WalletChangeResponse response;

  public Client(String host, int port, WalletChangeRequest message) {
    this.host = host;
    this.port = port;
    this.message = message;
  }

  @Override
  public void run() {
    try (Socket socket = new Socket(host, port)) {
      try {
        socket.setSoTimeout(3000);
      } catch (Exception e) {
        log.error("Client can't run on host: {} and port: {}", host, port);
        return;
      }
      try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
        out.flush();
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
          // send
          out.writeObject(message);
          out.flush();
          log.debug(":OUT {}", message);
          // receive
          response = (WalletChangeResponse) in.readObject();
          log.debug(":IN {}", response);
        } catch (ClassNotFoundException e) {
          log.error(e.getMessage(), e);
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}
