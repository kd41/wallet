package ee.playtech.wallet.program;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {

  private static PropertiesLoader instance;
  private static final String RESOURCE_FILE_NAME = "wallet.properties";
  private static final String DB_ALONE_LOCATION_KEY = "database.alone.location";
  private static final String DB_SERVER_HOST_KEY = "database.server.host";
  private static final String DB_SERVER_PORT_KEY = "database.server.port";
  private static final String DB_DEFAULT_LOCATION = "db-2x3.db";
  private static final String DB_DEFAULT_PORT = "14444";
  private static Properties properties;
  private String dbLocation;
  private String dbHost;
  private String dbPort;

  private PropertiesLoader() {
  }

  public static synchronized PropertiesLoader getInstance() {
    if (instance == null) {
      instance = new PropertiesLoader();
      properties = new Properties();
      FileInputStream is = null;
      try {
        is = new FileInputStream(RESOURCE_FILE_NAME);
        properties.load(is);
      } catch (FileNotFoundException e) {
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        if (null != is) {
          try {
            is.close();
          } catch (IOException e) {
          }
        }
      }
    }
    return instance;
  }

  public String getDbLocation() {
    dbLocation = properties.containsKey(DB_ALONE_LOCATION_KEY) ? properties.getProperty(DB_ALONE_LOCATION_KEY) : DB_DEFAULT_LOCATION;
    return dbLocation;
  }

  public String getDbHost() {
    dbHost = properties.containsKey(DB_SERVER_HOST_KEY) ? properties.getProperty(DB_SERVER_HOST_KEY) : "";
    return dbHost;
  }

  public String getDbPort() {
    dbPort = properties.containsKey(DB_SERVER_PORT_KEY) ? properties.getProperty(DB_SERVER_PORT_KEY) : DB_DEFAULT_PORT;
    return dbPort;
  }

  public void saveProperties(String location, String host, String port) {
    try {
      properties.put(DB_ALONE_LOCATION_KEY, location);
      properties.put(DB_SERVER_HOST_KEY, host);
      properties.put(DB_SERVER_PORT_KEY, port);
      FileOutputStream fos = new FileOutputStream(RESOURCE_FILE_NAME);
      properties.store(fos, null);
      fos.close();
      dbLocation = location;
    } catch (IOException e) {
    }
  }
}
