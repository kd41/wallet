package ee.playtech.wallet.errors;

public class ServerException extends BaseWalletException {
  private static final long serialVersionUID = 1L;

  private static final long ERROR_CODE = 2;

  public ServerException(String message) {
    super(message);
  }

  @Override
  public long getErrorCode() {
    return ERROR_CODE;
  }
}
