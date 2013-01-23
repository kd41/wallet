package program;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Provider.Service;

import database.Database;
import database.services.DatabaseService;
import database.services.DatabaseServiceImpl;
import database.services.WalletRequest;
import database.services.WalletResponse;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import socket.client.Client;
import socket.server.Server;

public class Program {
  private static final Logger log = LoggerFactory.getLogger(Program.class);
  private static DatabaseService service = new DatabaseServiceImpl();
  private static Thread serverThread;

  public static void main(String... args) throws Exception {
    String userName = "alex1";

    Database database = Database.getInstance();
    // database.insertMockData();
    database.getVersionByUsername(userName);
    database.printData();

    WalletRequest request = new WalletRequest();
    request.setBalanceChange(new BigDecimal(1));
    request.setTransactionID(System.currentTimeMillis());
    request.setUserName(userName);
    System.out.println(request);
    WalletResponse response = service.getWalletResponse(request);
    System.out.println(response);

    database.printData();

    log.info(request.toString());
    log.info(response.toString());

    runServer(12345);

    Client client = new Client("localhost", 12345, new WalletRequest(userName, new BigDecimal(1), System.currentTimeMillis()));
    client.start();

    WalletResponse walletResponse = client.getResponse();

  }

  private static void runServer(final int port) {
    if (serverThread != null) {
      serverThread.interrupt();
      serverThread = null;
    }
    serverThread = new Thread() {
      @Override
      public void run() {
        try {
          new Server(port);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    serverThread.start();
  }
}
