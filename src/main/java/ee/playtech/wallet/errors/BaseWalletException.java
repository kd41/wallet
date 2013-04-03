package ee.playtech.wallet.errors;

public abstract class BaseWalletException extends Exception {
  private static final long serialVersionUID = 1L;

  public BaseWalletException(String message) {
    super(message);
  }

  public abstract long getErrorCode();

}
