package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public class Database {
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
        e.printStackTrace();
        System.exit(0);
      }
    }
    return database;
  }

  public void save(String userName, BigDecimal amount) throws Exception {
    try (Connection conn = getHSQLConnection()) {
      String sql1 = "SELECT BALANCE_VERSION FROM " + DB_TABLE + " WHERE USERNAME = ?";
      String sql2 = "insert into " + DB_TABLE + " (USERNAME,BALANCE_VERSION,BALANCE) values (?, 1, ?)";
      String sql3 = "UPDATE " + DB_TABLE + " SET BALANCE_VERSION = ?, BALANCE = ? WHERE USERNAME = ?";

      PreparedStatement pstmt1 = conn.prepareStatement(sql1);
      PreparedStatement pstmt2 = conn.prepareStatement(sql2);
      PreparedStatement pstmt3 = conn.prepareStatement(sql3);

      pstmt1.setString(1, userName);
      ResultSet rset = pstmt1.executeQuery();
      if (rset.next()) {
        System.out.println("The user with userName: " + userName + " already exists.");
        int balanceVesion = rset.getInt("BALANCE_VERSION");
        rset.close();

        pstmt3.setInt(1, ++balanceVesion);
        pstmt3.setBigDecimal(2, amount);
        pstmt3.setString(3, userName);
        pstmt3.executeUpdate();
      } else {
        pstmt2.setString(1, userName);
        pstmt2.setBigDecimal(2, amount);
        pstmt2.executeQuery();
      }
    }
  }

  public int getVersionByUsername(String userName) throws Exception {
    try (Connection conn = getHSQLConnection()) {
      String query = "SELECT BALANCE_VERSION FROM " + DB_TABLE + " WHERE USERNAME = ?";
      PreparedStatement pt = conn.prepareStatement(query);
      pt.setNString(1, userName);
      ResultSet rs = pt.executeQuery();
      int version = 0;
      while (rs.next()) {
        version = rs.getInt("BALANCE_VERSION");
        System.out.println(version);
      }
      return version;
    }
  }

  public void insertMockData() throws Exception {
    save("alex1", new BigDecimal(12.4));
    save("alex2", new BigDecimal(1.8));
    save("alex3", new BigDecimal(2.3));
  }

  public void printData() throws Exception {
    try (Connection conn = getHSQLConnection()) {
      String query = "SELECT * FROM " + DB_TABLE;
      PreparedStatement pt = conn.prepareStatement(query);
      ResultSet rs = pt.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getString("USERNAME") + ":" + rs.getInt("BALANCE_VERSION") + ":" + rs.getBigDecimal("BALANCE"));
      }
    }
  }

  private void create() throws Exception {
    try (Connection conn = getHSQLConnection()) {
      Statement st = conn.createStatement();
      String query = "select count(*) from " + DB_TABLE;
      try (PreparedStatement pstmt = conn.prepareStatement(query)) {
      } catch (SQLSyntaxErrorException e) {
        st.executeUpdate("create table " + DB_TABLE + " (USERNAME VARCHAR(20) PRIMARY KEY, BALANCE_VERSION INTEGER, BALANCE DECIMAL(13,2));");
      }
    }
  }

  private static Connection getHSQLConnection() throws Exception {
    String url = "jdbc:hsqldb:data/" + DB_NAME;
    return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
  }
}
