package database.services;

import java.io.Serializable;
import java.math.BigDecimal;

public class WalletRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  private String userName;
  private BigDecimal balanceChange;
  private long transactionID;

  public WalletRequest() {
  }

  public WalletRequest(String userName, BigDecimal balanceChange, long transactionID) {
    this.userName = userName;
    this.balanceChange = balanceChange;
    this.transactionID = transactionID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public BigDecimal getBalanceChange() {
    return balanceChange;
  }

  public void setBalanceChange(BigDecimal balanceChange) {
    this.balanceChange = balanceChange;
  }

  public long getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(long transactionID) {
    this.transactionID = transactionID;
  }

  @Override
  public String toString() {
    return "WalletRequest [userName=" + userName + ", balanceChange=" + balanceChange + ", transactionID=" + transactionID + "]";
  }

}
