package ee.playtech.wallet.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.playtech.wallet.database.services.BalanceRequest;
import ee.playtech.wallet.database.services.BalanceResponse;
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
    BalanceResponse balance = service.getBalanceByUserName(new BalanceRequest(USER_NAME, 124L));
    Assert.assertTrue("Wrong balance for user", balance.getBalance().compareTo(new BigDecimal(0)) > 0);
  }

  @Test
  public void testZeroBalance() {
    BalanceResponse balance = service.getBalanceByUserName(new BalanceRequest(USER_NAME, 125L));
    WalletChangeResponse response = service.getWalletResponse(new WalletChangeRequest(USER_NAME, balance.getBalance().negate(), 126L));
    Assert.assertTrue("User balance can be 0", response.getBalanceAmount().compareTo(new BigDecimal(0)) == 0);
  }

  @Test
  public void testNegativeBalance() {
    BalanceResponse balance = service.getBalanceByUserName(new BalanceRequest(USER_NAME, 127L));
    WalletChangeResponse response = service.getWalletResponse(new WalletChangeRequest(USER_NAME, balance.getBalance().add(DEFAULT_BALANCE).negate(), 128L));
    Assert.assertTrue("User balance can't be negative", (response.getBalanceAmount() == null && response.getErroCode() == 1));
  }
}
