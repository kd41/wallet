package ee.playtech.wallet.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {
  private static final Logger log = LoggerFactory.getLogger(Database.class);

  private static final String DB_NAME = "db_wallet";
  private static final String DB_USER = "sa";
  private static final String DB_PASSWORD = "";
  private static final String DB_TABLE = "PLAYER";

  private static Database database;

  private Database() {
  }

  public static synchronized Database getInstance() {
    if (database == null) {
      database = new Database();
      try {
        database.create();
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        System.exit(0);
      }
    }
    return database;
  }

  public int save(String userName, BigDecimal amount) throws Exception {
    int balanceVesion = 0;
    try (Connection connection = getHSQLConnection()) {
      String sql1 = "SELECT BALANCE_VERSION FROM " + DB_TABLE + " WHERE USERNAME = ?";
      String sql2 = "insert into " + DB_TABLE + " (USERNAME,BALANCE_VERSION,BALANCE) values (?, 1, ?)";
      String sql3 = "UPDATE " + DB_TABLE + " SET BALANCE_VERSION = ?, BALANCE = ? WHERE USERNAME = ?";

      PreparedStatement statement1 = connection.prepareStatement(sql1);
      PreparedStatement statement2 = connection.prepareStatement(sql2);
      PreparedStatement statement3 = connection.prepareStatement(sql3);

      statement1.setString(1, userName);
      ResultSet resultSet = statement1.executeQuery();
      if (resultSet.next()) {
        balanceVesion = resultSet.getInt("BALANCE_VERSION");
        resultSet.close();

        statement3.setInt(1, ++balanceVesion);
        statement3.setBigDecimal(2, amount);
        statement3.setString(3, userName);
        statement3.executeUpdate();
      } else {
        statement2.setString(1, userName);
        statement2.setBigDecimal(2, amount);
        statement2.executeUpdate();
        balanceVesion = 1;
      }
    }
    return balanceVesion;
  }

  public int getVersionByUsername(String userName) {
    int version = 0;
    try (Connection connection = getHSQLConnection()) {
      String query = "SELECT BALANCE_VERSION FROM " + DB_TABLE + " WHERE USERNAME = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setNString(1, userName);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        version = resultSet.getInt("BALANCE_VERSION");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return version;
  }

  public BigDecimal getBalanceByUserName(String userName) {
    BigDecimal balance = new BigDecimal(0);
    try (Connection connection = getHSQLConnection()) {
      String query = "SELECT BALANCE FROM " + DB_TABLE + " WHERE USERNAME = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setNString(1, userName);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        balance = resultSet.getBigDecimal("BALANCE");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return balance;
  }

  private void create() throws Exception {
    try (Connection connection = getHSQLConnection()) {
      Statement statement = connection.createStatement();
      String query = "select count(*) from " + DB_TABLE;
      try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      } catch (SQLSyntaxErrorException e) {
        statement.executeUpdate("create table " + DB_TABLE + " (USERNAME VARCHAR(20) PRIMARY KEY, BALANCE_VERSION INTEGER, BALANCE DECIMAL(13,2));");
      }
    }
  }

  private static Connection getHSQLConnection() throws Exception {
    String url = "jdbc:hsqldb:data/" + DB_NAME;
    return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
  }
}
