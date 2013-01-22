package errors;

public class NegativeBalanceException extends Exception {
  private static final long serialVersionUID = 1L;

  public NegativeBalanceException(String message) {
    super(message);
  }
}
