package database.services;

import java.math.BigDecimal;

import database.Database;
import errors.NegativeBalanceException;
import errors.ServerException;

public class DatabaseServiceImpl implements DatabaseService {
  private Database database;

  @Override
  public WalletChangeResponse getWalletResponse(WalletChangeRequest request) {
    WalletChangeResponse response = new WalletChangeResponse(request);
    try {
      BigDecimal oldBalance = getDatabase().getBalanceByUserName(request.getUserName());
      BigDecimal newBalance = oldBalance.add(request.getBalanceChange());
      if (newBalance.signum() < 0) {
        throw new NegativeBalanceException(request.getUserName() + " can't have negative balance");
      }
      int balanceVersion = getDatabase().save(request.getUserName(), newBalance);
      response.setBalanceAmount(newBalance);
      response.setBalanceVersion(balanceVersion);
    } catch (Exception e) {
      e.printStackTrace();
      if (e instanceof NegativeBalanceException) {
        response.setErroCode(NegativeBalanceException.ERROR_CODE);
      } else {
        response.setErroCode(ServerException.ERROR_CODE);
      }
    }

    return response;
  }

  @Override
  public BigDecimal getBalanceByUserName(String userName) {
    return getDatabase().getBalanceByUserName(userName);
  }

  @Override
  public int getBalanceVersionByUserName(String userName) {
    return getDatabase().getVersionByUsername(userName);
  }

  private synchronized Database getDatabase() {
    if (database == null) {
      database = Database.getInstance();
    }
    return database;
  }

}
