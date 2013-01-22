package database.services;

import database.Database;

public class DatabaseServiceImpl implements DatabaseService {

  @Override
  public WalletResponse getWalletResponse(WalletRequest request) throws Exception {
    Database.getInstance().save(request.getUserName(), request.getAmount());
    return null;
  }
}
