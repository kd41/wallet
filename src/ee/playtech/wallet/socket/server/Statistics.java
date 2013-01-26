package ee.playtech.wallet.socket.server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.program.PropertiesLoaderUtil;

public class Statistics {
  private static final Logger log = LoggerFactory.getLogger("statistics");
  private int tick;
  private int requestsCount;
  private int requestsCountPerTick;
  private int maxDuration;
  private int minDuration;
  private int averageDuration;
  private List<Integer> durationsList = Collections.synchronizedList(new LinkedList<Integer>());

  public Statistics() {
    tick = PropertiesLoaderUtil.getStatisticInterval();
    if (isEnabled()) {
      StatisticsThread thread = new StatisticsThread(tick);
      thread.start();
    }
  }

  public boolean isEnabled() {
    return tick > 0;
  }

  public synchronized void incrementRequestsCount() {
    requestsCount++;
    requestsCountPerTick++;
  }

  public void storeStatistic(long transactionID, int duration) {
    durationsList.add(duration);
    log.debug("Transaction {} has duration: {}ms", transactionID, duration);
  }

  private synchronized void calculateAndLogStatics() {
    int count = durationsList.size();
    if (count == 0) {
      log.info("No requests was during last interval");
      return;
    }
    int totalDuration = 0;
    for (int i = 0; i < count; i++) {
      int duration = durationsList.get(i);
      if (maxDuration < duration) {
        maxDuration = duration;
      }
      if (minDuration > duration) {
        minDuration = duration;
      }
      totalDuration += duration;
    }
    averageDuration = totalDuration / count;
    logStatiscs();
    requestsCountPerTick = 0;
    maxDuration = Integer.MIN_VALUE;
    minDuration = Integer.MAX_VALUE;
    averageDuration = 0;
    durationsList.clear();
  }

  private void logStatiscs() {
    if (log.isInfoEnabled()) {
      log.info("Requests: total count={}, per collected interval={}. Durations: max={}ms, min={}ms, average={}ms.", new Object[] { requestsCount, requestsCountPerTick,
                                                                                                                                  maxDuration, minDuration, averageDuration });
    }
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
        calculateAndLogStatics();
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          log.error(e.getMessage(), e);
        }
      } while (true);
    }
  }
}
