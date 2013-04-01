package ee.playtech.wallet.database.services;

import java.io.Serializable;

public class BalanceRequest implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userName;
  private long transactionID;

  public BalanceRequest(String userName, long transactionID) {
    this.userName = userName;
    this.transactionID = transactionID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public long getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(long transactionID) {
    this.transactionID = transactionID;
  }

  @Override
  public String toString() {
    return "BalanceRequest [userName=" + userName + ", transactionID=" + transactionID + "]";
  }
}
