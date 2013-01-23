package socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.Server;

import database.services.WalletRequest;
import database.services.WalletResponse;

public class Client {
  private static final Logger log = LoggerFactory.getLogger(Server.class);

  private String host;
  private int port;

  private WalletRequest message;
  private WalletResponse response;

  public Client(String host, int port, WalletRequest message) {
    this.host = host;
    this.port = port;
    this.message = message;
    start();
  }

  public void start() {
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
          log.info("Client sended: " + message);
          // receive
          response = (WalletResponse) in.readObject();
          log.info("Client received: " + response);
        } catch (ClassNotFoundException e) {
          log.error(e.getMessage(), e);
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public WalletResponse getResponse() {
    return response;
  }

  protected void setMessage(WalletRequest message) {
    this.message = message;
  }

}
