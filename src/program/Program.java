package program;

import java.math.BigDecimal;
import java.security.Provider.Service;

import database.Database;
import database.services.DatabaseService;
import database.services.DatabaseServiceImpl;
import database.services.WalletRequest;
import database.services.WalletResponse;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class Program {
  private static final Logger log = LoggerFactory.getLogger(Program.class);
  private static DatabaseService service = new DatabaseServiceImpl();

  public static void main(String... args) throws Exception {
    String userName = "alex3";

    Database database = Database.getInstance();
    // database.insertMockData();
    database.getVersionByUsername(userName);
    database.printData();

    WalletRequest request = new WalletRequest();
    request.setBalanceChange(new BigDecimal(-1));
    request.setTransactionID(System.currentTimeMillis());
    request.setUserName(userName);
    System.out.println(request);
    WalletResponse response = service.getWalletResponse(request);
    System.out.println(response);

    database.printData();

    log.info(request.toString());
    log.info(response.toString());
  }
}
