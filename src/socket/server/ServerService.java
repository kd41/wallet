package socket.server;

import database.services.WalletChangeRequest;
import database.services.WalletChangeResponse;

public interface ServerService {
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request);
}
