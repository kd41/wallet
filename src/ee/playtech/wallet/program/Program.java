package ee.playtech.wallet.program;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.client.socket.Client;
import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.server.socket.Server;

public class Program {
  private static final Logger log = LoggerFactory.getLogger("console");

  public static void main(String... args) throws Exception {
    String userName = args.length > 0 ? args[0] : "alex1";
    if ("${userName}".equals(userName)) {
      userName = "server";
      log.debug("run server");
      runServer(PropertiesLoaderUtil.getServerPort());
    } else if (args.length == 1) {
      log.debug("run client: {}", userName);
      runClient(userName, PropertiesLoaderUtil.getClientDelay());
    }

    log.debug("username: {}", userName);
    log.debug("server.port = {}", PropertiesLoaderUtil.getServerPort());
    log.debug("statistic.interval: {}", PropertiesLoaderUtil.getStatisticInterval());
    log.debug("client.port: {}", PropertiesLoaderUtil.getClientPort());
    log.debug("server.host: {}", PropertiesLoaderUtil.getServerHost());
    log.debug("client.delay: {}", PropertiesLoaderUtil.getClientDelay());
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
