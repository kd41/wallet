package ee.playtech.wallet.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Statistics {
  private static final Logger log = LoggerFactory.getLogger("statistics");
  private long requestsCount;

  public Statistics() {
    StatisticsThread thread = new StatisticsThread(1);
    thread.start();
  }

  public synchronized void incrementRequestsCount() {
    requestsCount++;
  }

  public void logStatiscs() {
    if (log.isInfoEnabled()) {
      log.info("Requests total count: {}", requestsCount);
    }
  }

  public long getRequestsCount() {
    return requestsCount;
  }

  private class StatisticsThread extends Thread {
    private int delaySeconds;

    public StatisticsThread(int delaySeconds) {
      this.delaySeconds = delaySeconds;
    };

    @Override
    public void run() {
      long delay = delaySeconds * 1000;
      do {
        logStatiscs();
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          log.error(e.getMessage(), e);
        }
      } while (true);
    }

  }

}
