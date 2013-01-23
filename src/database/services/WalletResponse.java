package database.services;

import java.math.BigDecimal;

public class WalletResponse {

  private long transactionID;
  private long erroCode;
  private int balanceVersion;
  private BigDecimal balanceChange;
  private BigDecimal balanceAmount;

  public WalletResponse(WalletRequest request) {
    this.transactionID = request.getTransactionID();
    this.balanceChange = request.getBalanceChange();
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

  public BigDecimal getBalanceChange() {
    return balanceChange;
  }

  public void setBalanceChange(BigDecimal balanceChange) {
    this.balanceChange = balanceChange;
  }

  public BigDecimal getBalanceAmount() {
    return balanceAmount;
  }

  public void setBalanceAmount(BigDecimal balanceAmount) {
    this.balanceAmount = balanceAmount;
  }

  @Override
  public String toString() {
    return "WalletResponse [transactionID=" + transactionID + ", erroCode=" + erroCode + ", balanceVersion=" + balanceVersion + ", balanceChange=" + balanceChange
           + ", balanceAmount=" + balanceAmount + "]";
  }
}
