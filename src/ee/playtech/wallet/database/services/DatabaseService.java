package ee.playtech.wallet.database.services;

import java.math.BigDecimal;

public interface DatabaseService {
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request);

  public BigDecimal getBalanceByUserName(String userName);

  public int getBalanceVersionByUserName(String userName);
}
