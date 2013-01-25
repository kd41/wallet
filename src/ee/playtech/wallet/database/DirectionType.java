package ee.playtech.wallet.database;

public enum DirectionType {
  IN(true), OUT(false);

  private boolean inServer;

  private DirectionType(boolean inServer) {
    this.inServer = inServer;
  }

  public boolean isInServer() {
    return inServer;
  }

}
