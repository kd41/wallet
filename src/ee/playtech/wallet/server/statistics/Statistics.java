package ee.playtech.wallet.server.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.program.PropertiesLoaderUtil;

public class Statistics {
  private static final Logger log = LoggerFactory.getLogger("statistics");
  private int delay;
  private AtomicInteger requestsCount = new AtomicInteger();
  private int maxDuration = Integer.MIN_VALUE;
  private int minDuration = Integer.MAX_VALUE;
  private int averageDuration;
  private List<Integer> durationsList = Collections.synchronizedList(new ArrayList<Integer>());

  public Statistics() {
    delay = PropertiesLoaderUtil.getStatisticInterval();
    if (isEnabled()) {
      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
          calculateAndLogStatics();
        }
      }, 0, delay, TimeUnit.SECONDS);
    }
  }

  public boolean isEnabled() {
    return delay > 0;
  }

  public void incrementRequestsCount() {
    requestsCount.incrementAndGet();
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
    logStatiscs(count);
    maxDuration = Integer.MIN_VALUE;
    minDuration = Integer.MAX_VALUE;
    averageDuration = 0;
    durationsList.subList(0, count).clear();
  }

  private void logStatiscs(int count) {
    if (log.isInfoEnabled()) {
      log.info("Requests: total count={}, per collected interval={}. Durations: max={}ms, min={}ms, average={}ms.", new Object[] { requestsCount, count, maxDuration,
                                                                                                                                  minDuration, averageDuration });
    }
  }
}
