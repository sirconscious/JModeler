import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class DBConnection {
      Dotenv dotenv = Dotenv.load();
      private final String dbConnection = dotenv.get("BD_CONNECTION");
      private final String dbName = dotenv.get("DB_NAME");
      private final String dbUSER = dotenv.get("DB_USER");
      private final String dbHOST = dotenv.get("DB_HOST");
      private final String dbPort = dotenv.get("DB_PORT");
      private final String dbPassword = dotenv.get("DB_PASSWORD");
      private String connectionUrl ;
      private Connection connection = null;
      DBConnection(){
          this.connectionUrl = String.format("jdbc:%s://%s:%s/%s", dbConnection, dbHOST, dbPort, dbName);
      }
      public  Connection connect(){
          try {
              if (this.connection ==null){
                  this.connection = DriverManager.getConnection(this.connectionUrl, dbUSER, dbPassword);
              //System.out.println(" Connected to the database!");
              }
              //System.out.println("connected");
              return this.connection;
          } catch (SQLException e) {
              System.out.println("Failed to connect!");
              e.printStackTrace(); // ← helpful during development
              return null;
          }
      }
}
