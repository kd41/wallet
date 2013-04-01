package ee.playtech.wallet.socket.server;

import ee.playtech.wallet.database.services.DatabaseService;
import ee.playtech.wallet.database.services.DatabaseServiceImpl;
import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;

public class ServerServiceImpl implements ServerService {

  private DatabaseService service = new DatabaseServiceImpl();

  private Statistics statistics = new Statistics();

  @Override
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request) {
    long start = 0;
    if (statistics.isEnabled()) {
      statistics.incrementRequestsCount();
      start = System.currentTimeMillis();
    }

    WalletChangeResponse response = service.getWalletResponse(request);

    if (statistics.isEnabled()) {
      long stop = System.currentTimeMillis();
      long duration = stop - start;
      statistics.storeStatistic(request.getTransactionID(), (int) duration);
    }
    return response;
  }
}
