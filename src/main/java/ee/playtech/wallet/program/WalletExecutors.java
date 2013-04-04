package ee.playtech.wallet.program;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class WalletExecutors {
  private static ScheduledExecutorService clientExecutor;
  private static ExecutorService serverExecutor;
  private static ScheduledExecutorService statisticsExecutor;

  public static ScheduledExecutorService getClientExecutor() {
    if (clientExecutor == null) {
      clientExecutor = Executors.newScheduledThreadPool(PropertiesLoader.getInstance().getClientsCount());
    }
    return clientExecutor;
  }

  public static ExecutorService getSeverExecutor() {
    if (serverExecutor == null) {
      serverExecutor = Executors.newSingleThreadExecutor();
    }
    return serverExecutor;
  }

  public static ScheduledExecutorService getStatisticsExecutor() {
    if (statisticsExecutor == null) {
      statisticsExecutor = Executors.newSingleThreadScheduledExecutor();
    }
    return statisticsExecutor;
  }

  public static void shutdownClientExecutor() {
    shutdownExecutor(clientExecutor);
  }

  public static void shutdownServerExecutor() {
    shutdownExecutor(serverExecutor);
  }

  public static void shutdownStatisticsExecutor() {
    shutdownExecutor(statisticsExecutor);
  }

  public static void shutdownAllExecutors() {
    shutdownClientExecutor();
    shutdownServerExecutor();
    shutdownStatisticsExecutor();
  }

  private static void shutdownExecutor(ExecutorService executor) {
    if (executor != null) {
      executor.shutdown();
    }
  }
}
