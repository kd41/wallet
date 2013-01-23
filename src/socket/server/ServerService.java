package socket.server;

import database.services.WalletRequest;
import database.services.WalletResponse;

public interface ServerService {
  public WalletResponse getWalletResponse(WalletRequest request);
}
