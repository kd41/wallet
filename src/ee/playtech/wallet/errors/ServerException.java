package ee.playtech.wallet.errors;

public class ServerException extends Exception {
  private static final long serialVersionUID = 1L;

  public static final long ERROR_CODE = 666;

  public ServerException(String message) {
    super(message);
  }
}
