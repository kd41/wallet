package ee.playtech.wallet.server.services;

import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;

public interface ServerService {
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request);
}
