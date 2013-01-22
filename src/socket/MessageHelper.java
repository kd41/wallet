package socket;

import static constants.Variables.TERMINATOR;

/**
 * The Class MessageHelper.
 */
public class MessageHelper {

  /**
   * Instantiates a new message helper.
   */
  public MessageHelper() {
  }

  /**
   * Gets the read request message.
   * 
   * @param recNo the number of record
   * @return the read request message
   */
  public static String getReadRequestMessage(long recNo) {
    return MessageType.READ.getName() + TERMINATOR + recNo;
  }

  /**
   * Gets the read response message.
   * 
   * @param data the data
   * @return the read response message
   */
  public static String getReadResponseMessage(String[] data) {
    return MessageType.READ.getName() + TERMINATOR + getMessage(data);
  }

  /**
   * Gets the find by criteria request message.
   * 
   * @param criteria the criteria
   * @return the find by criteria request message
   */
  public static String getFindByCriteriaRequestMessage(String[] criteria) {
    return MessageType.FIND.getName() + TERMINATOR + getFindMessage(criteria);
  }

  /**
   * Gets the find by criteria response message.
   * 
   * @param recNos the number of records
   * @return the find by criteria response message
   */
  public static String getFindByCriteriaResponseMessage(long[] recNos) {
    return MessageType.FIND.getName() + TERMINATOR + getMessage(recNos);
  }

  /**
   * Gets the update request message.
   * 
   * @param recNo the number of record
   * @param data the data
   * @param lockCookie the lock cookie
   * @return the update request message
   */
  public static String getUpdateRequestMessage(long recNo, String[] data, long lockCookie) {
    return MessageType.UPDATE.getName() + TERMINATOR + recNo + TERMINATOR + getMessage(data) + TERMINATOR + lockCookie;
  }

  /**
   * Gets the update response message.
   * 
   * @return the update response message
   */
  public static String getUpdateResponseMessage() {
    return MessageType.UPDATE.getName();
  }

  /**
   * Gets the delete request message.
   * 
   * @param recNo the number of record
   * @param lockCookie the lock cookie
   * @return the delete request message
   */
  public static String getDeleteRequestMessage(long recNo, long lockCookie) {
    return MessageType.DELETE.getName() + TERMINATOR + recNo + TERMINATOR + lockCookie;
  }

  /**
   * Gets the delete response message.
   * 
   * @return the delete response message
   */
  public static String getDeleteResponseMessage() {
    return MessageType.DELETE.getName();
  }

  /**
   * Gets the creates the request message.
   * 
   * @param data the data
   * @return the creates the request message
   */
  public static String getCreateRequestMessage(String[] data) {
    return MessageType.CREATE.getName() + TERMINATOR + getMessage(data);
  }

  /**
   * Gets the creates the response message.
   * 
   * @param recNo the number of record
   * @return the creates the response message
   */
  public static String getCreateResponseMessage(long recNo) {
    return MessageType.CREATE.getName() + TERMINATOR + recNo;
  }

  /**
   * Gets the lock request message.
   * 
   * @param recNo the number of record
   * @return the lock request message
   */
  public static String getLockRequestMessage(long recNo) {
    return MessageType.LOCK.getName() + TERMINATOR + recNo;
  }

  /**
   * Gets the lock response message.
   * 
   * @param cookie the cookie
   * @return the lock response message
   */
  public static String getLockResponseMessage(long cookie) {
    return MessageType.LOCK.getName() + TERMINATOR + cookie;
  }

  /**
   * Gets the unlock request message.
   * 
   * @param recNo the number of record
   * @param cookie the cookie
   * @return the unlock request message
   */
  public static String getUnlockRequestMessage(long recNo, long cookie) {
    return MessageType.UNLOCK.getName() + TERMINATOR + recNo + TERMINATOR + cookie;
  }

  /**
   * Gets the unlock response message.
   * 
   * @return the unlock response message
   */
  public static String getUnlockResponseMessage() {
    return MessageType.UNLOCK.getName();
  }

  /**
   * Gets the security error.
   * 
   * @return the security error
   */
  public static String getSecurityError() {
    return MessageType.ERROR_SECURITY.getName();
  }

  /**
   * Gets the no record error.
   * 
   * @return the no record error
   */
  public static String getNoRecordError() {
    return MessageType.ERROR_NO_RECORD.getName();
  }

  /**
   * Gets the duplicate error.
   * 
   * @return the duplicate error
   */
  public static String getDuplicateError() {
    return MessageType.ERROR_DUPLICATE.getName();
  }

  private static String getMessage(String[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (i < data.length - 1) {
        sb.append(data[i]).append(TERMINATOR);
      } else {
        sb.append(data[i]);
      }
    }
    return sb.toString();
  }

  private static String getFindMessage(String[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (data[i] == null) {
        sb.append(TERMINATOR);
      } else {
        if (i < data.length - 1) {
          sb.append(data[i]).append(TERMINATOR);
        } else {
          sb.append(data[i]);
        }
      }
    }
    return sb.toString();
  }

  private static String getMessage(long[] data) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (i < data.length - 1) {
        sb.append(data[i]).append(TERMINATOR);
      } else {
        sb.append(data[i]);
      }
    }
    return sb.toString();
  }
}
