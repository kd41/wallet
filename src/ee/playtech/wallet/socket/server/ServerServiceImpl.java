package ee.playtech.wallet.socket.server;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.DatabaseService;
import ee.playtech.wallet.database.services.DatabaseServiceImpl;
import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;

public class ServerServiceImpl implements ServerService {
  private static final Logger _log = LoggerFactory.getLogger("server");
  private static final Logger log = LoggerFactory.getLogger(ServerServiceImpl.class);

  private DatabaseService service = new DatabaseServiceImpl();

  private Statistics statistics = new Statistics();

  private Map<Long, Long> durationList = Collections.synchronizedMap(new LinkedHashMap<Long, Long>());

  private AtomicInteger count = new AtomicInteger();

  private int count2;

  @Override
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request) {
    log.info("Thread name: {}", Thread.currentThread().getName());
    statistics.incrementRequestsCount();
    long start = System.currentTimeMillis();
    _log.debug(request.toString());
    WalletChangeResponse response = service.getWalletResponse(request);
    _log.debug(response.toString());
    long stop = System.currentTimeMillis();
    long duration = stop - start;
    durationList.put(request.getTransactionID(), duration);
    log.info("Transaction {} has duration: {}ms", request.getTransactionID(), duration);
    log.info("Transactions count: {}, count2: {}", count, count2);

    return response;
  }

}
