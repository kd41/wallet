package ee.playtech.wallet.program;

public class PropertiesLoaderUtil {

  private static PropertiesLoader properties;

  public static int getServerPort() {
    return getPropertiesLoader().getServerPort();
  }

  public static int getClientPort() {
    return getPropertiesLoader().getClientPort();
  }

  public static String getServerHost() {
    return getPropertiesLoader().getServerHost();
  }

  public static int getClientDelay() {
    return getPropertiesLoader().getClientDelay();
  }

  public static int getStatisticInterval() {
    return getPropertiesLoader().getStatisticInterval();
  }

  private static PropertiesLoader getPropertiesLoader() {
    if (properties == null) {
      properties = PropertiesLoader.getInstance();
    }
    return properties;
  }
}
