package ee.playtech.wallet.program;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.socket.client.Client;
import ee.playtech.wallet.socket.server.Server;

public class Program {
  private static final Logger log = LoggerFactory.getLogger(Program.class);

  private static Thread serverThread;

  public static void main(String... args) throws Exception {
    String userName = args.length > 0 ? args[0] : "alex1";
    if ("${userName}".equals(userName)) {
      userName = "server";
      System.out.println("run server");
      runServer(PropertiesLoaderUtil.getServerPort());
    } else if (args.length == 1) {
      System.out.println("run client");
      runClient(userName, PropertiesLoaderUtil.getClientDelay(), PropertiesLoaderUtil.getClientRequestCount());
    }

    System.out.println("username: " + userName);
    System.out.println("server.port= " + PropertiesLoaderUtil.getServerPort());
    System.out.println("statistic.interval: " + PropertiesLoaderUtil.getStatisticInterval());
    System.out.println("client.port: " + PropertiesLoaderUtil.getClientPort());
    System.out.println("server.host: " + PropertiesLoaderUtil.getServerHost());
    System.out.println("client.delay: " + PropertiesLoaderUtil.getClientDelay());
    System.out.println("client.request.count: " + PropertiesLoaderUtil.getClientRequestCount());
  }

  private static void runServer(final int port) {
    serverThread = new Thread() {
      @Override
      public void run() {
        try {
          new Server(port);
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    };
    serverThread.start();
  }

  private static void runClient(final String userName, final int sleepMilliseconds, final int count) {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        final String name = Thread.currentThread().getName();
        int i = 0;
        do {
          new Client(PropertiesLoaderUtil.getServerHost(), PropertiesLoaderUtil.getServerPort(), getTestRequest(name));
          try {
            Thread.sleep(sleepMilliseconds);
          } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
          }
          i++;
        } while (i < count);
      }
    }, userName);
    t.start();
  }

  private static WalletChangeRequest getTestRequest(final String userName) {
    int balanceChange = -100 + (int) (Math.random() * 200);
    return new WalletChangeRequest(userName, new BigDecimal(balanceChange), System.currentTimeMillis());
  }

}
