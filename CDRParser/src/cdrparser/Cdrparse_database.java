/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdrparser;

/**
 *
 * @author Acer
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cdrparse_database {
      public String jdbcURL = "jdbc:postgresql://localhost:5432/billing";
      public  String username = "postgres";
      public  String password = "1121";
      public  Connection connection = null;
   public Connection getconnection ()  throws SQLException {
       
        try{
            Class.forName("org.postgresql.Driver"); 
            connection = DriverManager.getConnection(jdbcURL, username, password);
        
     } catch (ClassNotFoundException ex) {
          Logger.getLogger(Cdrparse_database.class.getName()).log(Level.SEVERE, null, ex);
        
        
    }
            return connection;

    
}}