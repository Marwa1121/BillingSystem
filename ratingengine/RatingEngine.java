/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratingengine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Date.from;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.sql.PreparedStatement;

/**
 *
 * @author Acer
 */
public class RatingEngine {

    /**
     * @param args the command line arguments
     *
     *
     */
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        } // If there was an Exception 
        // while creating URL object 
        catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        String jdbcURL = "jdbc:postgresql://localhost:5432/billing";
        String username = "postgres";
        String password = "1121";
        Connection connection = null;

        int destination_id = 0;

        //information from rating_package table
        float unit_cost = 0;
        float quantity;
        int ratingpkg_id = 0;

        float rating_charges = 0;


        Cdr cdr = new Cdr();
        Udr udr =new Udr(); 
        customer customer = new customer();
      float rest_units=customer.getRest_units();

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
            String sql = "select  *from cdr where israting = false";

            Statement st = connection.createStatement();
            PreparedStatement pstmt ;
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {

//                //to get service_id from cdr
                int service_id = rs.getInt(4);
                udr.setService_id(service_id);
                
                udr.setUdr_id(rs.getInt(1));
                udr.setStart_date(rs.getDate(6));
                udr.setStart_time(rs.getTime(7));

                switch (service_id) {
                    case 4:
                        udr.setRating_charges(0);
                        break;
                    case 5:
                        udr.setRating_charges(0);
                        break; 
                    default:
                      cdr.setDur_vol_msg(rs.getFloat(5));
                      udr.setDur_vol_msg(rs.getFloat(5));
                            if (service_id ==3)
                            {
                            udr.setExtenal_charges(rs.getFloat(8));
                            }
                        break;

                }
//
//                // to get destination_id from cdr
                String url = rs.getString(3);
                udr.setDial_b(url);
//                System.out.println(isValid(url));
                if (isValid(url) == true) {
                    destination_id = 5;
                } else {
                    char check_operator = (char) rs.getString(3).charAt(1);
                    switch (check_operator) {
                        case '2':
                            destination_id = 1;
                            break;
                        case '1':
                            destination_id = 2;
                            break;
                        case '5':
                            destination_id = 3;
                            break;
                        case '0':
                            destination_id = 4;
                            break;
                    }
                }

//                //to get customer_id,profile_id from cdr
                cdr.setDial_a(rs.getString(2));
                udr.setDial_a(rs.getString(2));

                String sql1 = "select customer_id,profile_id from customer where msisdn =" + "'" + cdr.getDial_a() + "'";
                ResultSet rs1 = st.executeQuery(sql1);
                if (rs1.next()) {
                    customer.setCustomer_id(rs1.getInt(1));
                    customer.setProfile_id((rs1.getInt(2)));
                    udr.setProfile_id(rs1.getInt(2));
                }
                //select ratingpkg_id from rating_package
                String sql2 = "select * from rating_package where service_id =" + "'" + service_id + "'"
                        + "and profile_id =" + "'" + customer.getProfile_id() + "'"
                        + "and desination_id =" + "'" + destination_id + "'";

                ResultSet rs2 = st.executeQuery(sql2);
                if (rs2.next()) {
                    unit_cost = rs2.getFloat(4);
                    quantity = rs2.getFloat(5);
                    ratingpkg_id = rs2.getInt(6);
//                    System.out.println(unit_cost);
//                    System.out.println(quantity);
//                    System.out.println(ratingpkg_id);

                }
                //rating process
                String sql3 = "select rest_units from  customer_freeunits where customer_id=" + "'" + customer.getCustomer_id() + "'"
                        + "and freeunit_id =" + "'" + ratingpkg_id + "'";
                ResultSet rs3 = st.executeQuery(sql3);
                if (rs3.next()) {
                customer.setRest_units(rs3.getFloat(1));
                }

                if (rest_units >= cdr.getDur_vol_msg()) {
                    rest_units =rest_units - cdr.getDur_vol_msg();
                    //change in customer_freeunits table
                } else {
                    rest_units = 0;
                    System.out.println(rest_units);

//                    //change in customer_freeunits table
                    rating_charges = cdr.getDur_vol_msg() * unit_cost;
                    udr.setRating_charges(rating_charges);

                }
          
          String sql5 = "update cdr  set israting =" + true;
          String sql4 = "insert into udr values (?,?,?,?,?,?,?,?,?,?)"  ;
          pstmt=connection.prepareStatement(sql4);
          pstmt.setInt(1,udr.getUdr_id());
          pstmt.setString(2,udr.getDial_a());
          pstmt.setString(3,udr.getDial_b());
          pstmt.setInt(4,udr.getService_id());
          pstmt.setFloat(5,udr.getDur_vol_msg());
          pstmt.setFloat(8,udr.getExtenal_charges());
          pstmt.setDate(6,udr.getStart_date());
          pstmt.setTime(7,udr.getStart_time());
          pstmt.setInt(9,udr.getProfile_id());
          pstmt.setFloat(10,udr.getRating_charges());


          pstmt.executeUpdate();
          st.executeUpdate(sql5);

                

            }
            
       else
            {
                System.out.println("this cdr id rated before");
            }
            
            

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RatingEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
