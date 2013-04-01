package ee.playtech.wallet.database.services;

public interface DatabaseService {
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request);

  public BalanceResponse getBalanceByUserName(BalanceRequest request);

}
