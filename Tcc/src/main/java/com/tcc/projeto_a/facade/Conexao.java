package com.tcc.projeto_a.facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class Conexao {
 
         private static Connection connection;
 
         public static Connection getConnection() {
                   if (connection == null) {
                            try {
                                      Class.forName("org.postgresql.Driver");
 
                                      String host = "localhost";
                                      String port = "5432";
                                      String database = "tcc";
                                      String user = "postgres";
                                      String pass = "postgres";
                                      String url = "jdbc:postgresql://" + host + ":" + port + "/"
                                                        + database;
                                      connection = DriverManager.getConnection(url, user, pass);
                            } catch (ClassNotFoundException e) {
                                      // TODO Auto-generated catch block
                                      e.printStackTrace();
                            } catch (SQLException e) {
                                      // TODO Auto-generated catch block
                                      e.printStackTrace();
                            }
                   }
 
                   return connection;
         }
          
         
         
         public static void close() {
                   if (connection == null){
                            throw new RuntimeException("Nenhum conexao aberta");
                   }else{
                            try {
                                      connection.close();
                            } catch (SQLException e) {
                                      // TODO Auto-generated catch block
                                      e.printStackTrace();
                            }
                   }
         }
 
}  
