package ee.playtech.wallet.program;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.socket.client.Client;
import ee.playtech.wallet.socket.server.Server;

public class Program {
  private static final Logger log = LoggerFactory.getLogger(Program.class);

  public static void main(String... args) throws Exception {
    String userName = args.length > 0 ? args[0] : "alex1";
    if ("${userName}".equals(userName)) {
      userName = "server";
      System.out.println("run server");
      runServer(PropertiesLoaderUtil.getServerPort());
    } else if (args.length == 1) {
      System.out.println("run client: " + userName);
      runClient(userName, PropertiesLoaderUtil.getClientDelay());
    }

    System.out.println("username: " + userName);
    System.out.println("server.port= " + PropertiesLoaderUtil.getServerPort());
    System.out.println("statistic.interval: " + PropertiesLoaderUtil.getStatisticInterval());
    System.out.println("client.port: " + PropertiesLoaderUtil.getClientPort());
    System.out.println("server.host: " + PropertiesLoaderUtil.getServerHost());
    System.out.println("client.delay: " + PropertiesLoaderUtil.getClientDelay());
  }

  private static void runServer(final int port) {
    try {
      Executors.newSingleThreadExecutor().execute(new Server(port));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  private static void runClient(String userName, int period) {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(new Client(PropertiesLoaderUtil.getServerHost(), PropertiesLoaderUtil.getServerPort(), getTestRequest(userName)), 0, period,
                                 TimeUnit.MILLISECONDS);
  }

  private static WalletChangeRequest getTestRequest(final String userName) {
    int balanceChange = -100 + (int) (Math.random() * 200);
    return new WalletChangeRequest(userName, new BigDecimal(balanceChange), System.currentTimeMillis());
  }

}
