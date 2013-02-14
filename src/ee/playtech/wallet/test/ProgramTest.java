package ee.playtech.wallet.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.playtech.wallet.database.services.DatabaseService;
import ee.playtech.wallet.database.services.DatabaseServiceImpl;
import ee.playtech.wallet.database.services.WalletChangeRequest;
import ee.playtech.wallet.database.services.WalletChangeResponse;

public class ProgramTest {
  private static String USER_NAME = "testusername2";
  private static BigDecimal DEFAULT_BALANCE = new BigDecimal(1);

  private DatabaseService service;

  @Before
  public void prepare() throws Exception {
    service = new DatabaseServiceImpl();
    service.getWalletResponse(new WalletChangeRequest(USER_NAME, DEFAULT_BALANCE, 123L));
  }

  @Test
  public void testWallet() {
    BigDecimal balance;
    try {
      balance = service.getBalanceByUserName(USER_NAME);
      Assert.assertTrue("Wrong balance for user", balance.compareTo(new BigDecimal(0)) > 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testBalanceVersionIncrement() {
    int oldVersion;
    try {
      oldVersion = service.getBalanceVersionByUserName(USER_NAME);
      WalletChangeResponse response = service.getWalletResponse(new WalletChangeRequest(USER_NAME, DEFAULT_BALANCE, 123L));
      Assert.assertTrue("Wrong version incrementing", (response.getBalanceVersion() - oldVersion == 1));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testZeroBalance() {
    BigDecimal userBalance;
    try {
      userBalance = service.getBalanceByUserName(USER_NAME);
      WalletChangeResponse response = service.getWalletResponse(new WalletChangeRequest(USER_NAME, userBalance.negate(), 123L));
      Assert.assertTrue("User balance can be 0", response.getBalanceAmount().compareTo(new BigDecimal(0)) == 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testNegativeBalance() {
    BigDecimal userBalance;
    try {
      userBalance = service.getBalanceByUserName(USER_NAME);
      WalletChangeResponse response = service.getWalletResponse(new WalletChangeRequest(USER_NAME, userBalance.add(new BigDecimal(2)).negate(), 123L));
      Assert.assertTrue("User balance can't be negative", (response.getBalanceAmount() == null && response.getErroCode() == 1));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
