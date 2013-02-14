package ee.playtech.wallet.database.services;

import java.math.BigDecimal;

import ee.playtech.wallet.errors.ServerException;

public interface DatabaseService {
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request);

  public BigDecimal getBalanceByUserName(String userName) throws ServerException;

  public int getBalanceVersionByUserName(String userName) throws ServerException;
}
