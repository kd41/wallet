package socket.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.services.DatabaseService;
import database.services.DatabaseServiceImpl;
import database.services.WalletChangeRequest;
import database.services.WalletChangeResponse;

public class ServerServiceImpl implements ServerService {
  private static final Logger _log = LoggerFactory.getLogger("server");
  private static final Logger log = LoggerFactory.getLogger(ServerServiceImpl.class);

  private DatabaseService service = new DatabaseServiceImpl();

  private List<Long> durationList = Collections.synchronizedList(new ArrayList<Long>());

  private AtomicInteger count = new AtomicInteger();

  @Override
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request) {
    log.info("Thread name: {}", Thread.currentThread().getName());
    count.incrementAndGet();
    long start = System.currentTimeMillis();
    _log.debug(request.toString());
    WalletChangeResponse response = service.getWalletResponse(request);
    _log.debug(response.toString());
    long stop = System.currentTimeMillis();
    long duration = stop - start;
    durationList.add(new Long(duration));
    log.info("Duration: {}ms", duration);

    return response;
  }

}
