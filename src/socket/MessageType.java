package socket;

/**
 * The Enum MessageType.
 */
public enum MessageType {

  /** The read. */
  READ("read"),
  /** The find. */
  FIND("find"),
  /** The get. */
  GET("get"),
  /** The create. */
  CREATE("create"),
  /** The update. */
  UPDATE("update"),
  /** The delete. */
  DELETE("delete"),
  /** The lock. */
  LOCK("lock"),
  /** The unlock. */
  UNLOCK("unlock"),
  /** The error no record. */
  ERROR_NO_RECORD("e_no_rec"),
  /** The error security. */
  ERROR_SECURITY("e_security"),
  /** The error duplicate. */
  ERROR_DUPLICATE("e_duplicate"),
  /** The error connection. */
  ERROR_CONNECTION("e_connection");

  private String name;

  private MessageType(String name) {
    this.name = name;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return this.name;
  }
}
