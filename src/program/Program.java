package program;

import database.Database;

public class Program {
  public static void main(String... args) throws Exception {
    Database database = Database.getInstance();
    database.insertMockData();
    database.getVersionByUsername("alex3");
    database.printData();
  }
}
