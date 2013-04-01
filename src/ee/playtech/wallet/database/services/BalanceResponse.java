package ee.playtech.wallet.database.services;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceResponse implements Serializable {
  private static final long serialVersionUID = 1L;
  private long transactionID;
  private long erroCode;
  private int balanceVersion;
  private BigDecimal balance;

  public BalanceResponse(BalanceRequest request) {
    this.transactionID = request.getTransactionID();
    this.erroCode = 0;
  }

  public long getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(long transactionID) {
    this.transactionID = transactionID;
  }

  public long getErroCode() {
    return erroCode;
  }

  public void setErroCode(long erroCode) {
    this.erroCode = erroCode;
  }

  public int getBalanceVersion() {
    return balanceVersion;
  }

  public void setBalanceVersion(int balanceVersion) {
    this.balanceVersion = balanceVersion;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "BalanceResponse [transactionID=" + transactionID + ", erroCode=" + erroCode + ", balanceVersion=" + balanceVersion + ", balance=" + balance + "]";
  }
}
