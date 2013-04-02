package ee.playtech.wallet.database.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.playtech.wallet.database.Database;
import ee.playtech.wallet.errors.BaseWalletException;
import ee.playtech.wallet.errors.ExceptionConstatnts;
import ee.playtech.wallet.errors.NegativeBalanceException;
import ee.playtech.wallet.errors.ServerException;

public class DatabaseServiceImpl implements DatabaseService {
  private static final Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);
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
      int balanceVersion = getDatabase().saveAndGetBalanceVersion(request.getUserName(), newBalance);
      response.setBalanceAmount(newBalance);
      response.setBalanceVersion(balanceVersion);
    } catch (BaseWalletException e) {
      response.setErroCode(e.getErrorCode());
      log.debug(e.getMessage(), e);
    } catch (Exception e) {
      response.setErroCode(ExceptionConstatnts.INTERNAL_ERROR_CODE);
      log.error(e.getMessage(), e);
    }
    return response;
  }

  @Override
  public BalanceResponse getBalanceByUserName(BalanceRequest request) {
    BalanceResponse response = new BalanceResponse(request);
    try {
      response.setBalance(getDatabase().getBalanceByUserName(request.getUserName()));
      response.setBalanceVersion(getDatabase().getVersionByUsername(request.getUserName()));
    } catch (ServerException e) {
      response.setErroCode(e.getErrorCode());
      log.debug(e.getMessage(), e);
    } catch (Exception e) {
      response.setErroCode(ExceptionConstatnts.INTERNAL_ERROR_CODE);
      log.error(e.getMessage(), e);
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
