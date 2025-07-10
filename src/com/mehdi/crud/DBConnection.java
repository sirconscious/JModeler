package com.mehdi.crud;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        //to connect to the db
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
      //to check if a table exists in db
      public boolean tableExists(String tableName) throws SQLException {
          Connection conn = connect();
          DatabaseMetaData meta = conn.getMetaData();

          try (ResultSet tables = meta.getTables(null, null, tableName, new String[] { "TABLE" })) {
              return tables.next();  // returns true if table exists
          }
      }
    public List<String> getColumnNames(String tableName) throws SQLException {
          if (tableExists(tableName)){

        List<String> columnNames = new ArrayList<>();
        DatabaseMetaData meta = connect().getMetaData();

        try (ResultSet rs = meta.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String actualTable = rs.getString("TABLE_NAME");
                String catalog = rs.getString("TABLE_CAT");
                String columnName = rs.getString("COLUMN_NAME");

                if (actualTable != null && catalog != null) {
                    if (actualTable.equalsIgnoreCase(tableName) && catalog.equalsIgnoreCase(dbName)) {
                        columnNames.add(columnName);
                    }
                }
            }
        }
        return columnNames;
          } else{
              System.err.println("the table doesnt exist");
                return null;
          }
    }

}
