package database.services;

public interface DatabaseService {
  public WalletResponse getWalletResponse(WalletRequest request) throws Exception;
}
