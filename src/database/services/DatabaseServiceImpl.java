package database.services;

import java.math.BigDecimal;

import database.Database;
import errors.NegativeBalanceException;

public class DatabaseServiceImpl implements DatabaseService {
  private Database database;

  @Override
  public WalletResponse getWalletResponse(WalletRequest request) {
    WalletResponse response = new WalletResponse(request);
    try {
      BigDecimal oldBalance = getDatabase().getBalanceByUserName(request.getUserName());
      System.out.println("user " + request.getUserName() + " had balance: " + oldBalance);
      BigDecimal newBalance = oldBalance.add(request.getBalanceChange());
      System.out.println("new balance: " + newBalance);
      if (newBalance.signum() < 0) {
        response.setErroCode(NegativeBalanceException.ERROR_CODE);
        throw new NegativeBalanceException(request.getUserName() + " can't have negative balance");
      }
      int balanceVersion = getDatabase().save(request.getUserName(), newBalance);
      response.setBalanceAmount(newBalance);
      response.setBalanceVersion(balanceVersion);
      System.out.println("user " + request.getUserName() + " saved new balance: " + newBalance);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return response;
  }

  private synchronized Database getDatabase() {
    if (database == null) {
      database = Database.getInstance();
    }
    return database;
  }
}
