package ee.playtech.wallet.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.services.DatabaseService;
import ee.playtech.wallet.database.services.DatabaseServiceImpl;
import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;

public class ServerServiceImpl implements ServerService {
  private static final Logger _log = LoggerFactory.getLogger("server");

  private DatabaseService service = new DatabaseServiceImpl();

  private Statistics statistics = new Statistics();

  @Override
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request) {
    long start = 0;
    if (statistics.isEnabled()) {
      statistics.incrementRequestsCount();
      start = System.currentTimeMillis();
    }

    _log.debug(request.toString());
    WalletChangeResponse response = service.getWalletResponse(request);
    _log.debug(response.toString());

    if (statistics.isEnabled()) {
      long stop = System.currentTimeMillis();
      long duration = stop - start;
      statistics.storeStatistic(request.getTransactionID(), (int) duration);
    }
    return response;
  }
}
