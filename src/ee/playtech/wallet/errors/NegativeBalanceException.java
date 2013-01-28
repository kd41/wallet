package ee.playtech.wallet.errors;

public class NegativeBalanceException extends BaseWalletException {
  private static final long serialVersionUID = 1L;

  private static final long ERROR_CODE = 1;

  public NegativeBalanceException(String message) {
    super(message);
  }

  @Override
  public long getErrorCode() {
    return ERROR_CODE;
  }
}
