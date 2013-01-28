package ee.playtech.wallet.program;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {
  private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);

  private static Properties properties;
  private static PropertiesLoader instance;
  private static final String RESOURCE_FILE_NAME = "wallet.properties";

  private static final String SERVER_PORT_KEY = "server.port";
  private static final String CLIENT_PORT_KEY = "client.port";
  private static final String SERVER_HOST_KEY = "server.host";
  private static final String STATISTIC_INTERVAL_KEY = "statistic.interval";
  private static final String CLIENT_DELAY_KEY = "client.delay";
  private static final String CLIENT_REQUEST_COUNT_KEY = "client.request.count";

  private static final int DEFAULT_PORT = 14444;
  private static final String DEFAULT_SERVER_HOST = "localhost";
  private static final int DEFAULT_CLIENT_DELAY = 50;
  private static final int DEFAULT_STATISTIC_INTERVAL = 60;
  private static final int DEFAULT_CLIENT_REQUEST_COUNT = 1000;

  private PropertiesLoader() {
  }

  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      properties = new Properties();
      try (FileInputStream is = new FileInputStream(RESOURCE_FILE_NAME)) {
        properties.load(is);
      } catch (FileNotFoundException e) {
      } catch (IOException e) {
        System.out.println(e);
      }
    }
    return instance;
  }

  public int getServerPort() {
    return getIntProperty(SERVER_PORT_KEY, DEFAULT_PORT);
  }

  public int getClientPort() {
    return getIntProperty(CLIENT_PORT_KEY, DEFAULT_PORT);
  }

  public String getServerHost() {
    return getStringProperty(SERVER_HOST_KEY, DEFAULT_SERVER_HOST);
  }

  public int getClientDelay() {
    return getIntProperty(CLIENT_DELAY_KEY, DEFAULT_CLIENT_DELAY);
  }

  public int getStatisticInterval() {
    return getIntProperty(STATISTIC_INTERVAL_KEY, DEFAULT_STATISTIC_INTERVAL);
  }

  public int getClientRequestCount() {
    return getIntProperty(CLIENT_REQUEST_COUNT_KEY, DEFAULT_CLIENT_REQUEST_COUNT);
  }

  private int getIntProperty(String key, int defaultValue) {
    int value = defaultValue;
    if (properties.containsKey(key)) {
      try {
        value = Integer.parseInt(properties.getProperty(key));
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
    return value;
  }

  private String getStringProperty(String key, String defaultValue) {
    return properties.containsKey(key) ? properties.getProperty(key) : defaultValue;
  }

}
