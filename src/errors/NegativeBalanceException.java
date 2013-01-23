package errors;

public class NegativeBalanceException extends Exception {
  private static final long serialVersionUID = 1L;

  public static final long ERROR_CODE = 1;

  public NegativeBalanceException(String message) {
    super(message);
  }
}
