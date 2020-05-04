
package billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
        Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public Connection connect() throws ClassNotFoundException {

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/billing", "postgres", "1234");
            System.out.println("connection success");
        } catch (SQLException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return con;

    }
        public List<String> getAllCustomers() throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select msisdn from customer";
        List<String> customers = new ArrayList<String>();
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            customers.add(rs.getString("msisdn"));
        }

        return customers;

    }
        public List<Double> getRatingChargesFromUDR( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select rating_charges from udr where dial_a= ?";
        List<Double> ratingCharges = new ArrayList<Double>();
        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn );
        rs = pst.executeQuery();
        while (rs.next()) {
            ratingCharges.add(rs.getDouble("rating_charges"));
        }

        return ratingCharges;

    }
      public int getProfileIdOfCustomer( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select profile_id from customer where msisdn= ?";
int profileId=0;
        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn );
        rs = pst.executeQuery();
        while (rs.next()) {
            profileId=rs.getInt("profile_id");
        }

        return profileId;

    }
            public int getCustomerId( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select customer_id from customer where msisdn= ?";
int customerId=0;
        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn );
        rs = pst.executeQuery();
        while (rs.next()) {
            customerId=rs.getInt("customer_id");
        }

        return customerId;

    }
            public int getMonthlyFees( Integer profileId) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select monthly_fees from rate_plane where profile_id= ?";
        int monthlyFees=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,profileId );
        rs = pst.executeQuery();
        while (rs.next()) {
            monthlyFees =rs.getInt("monthly_fees");
        }

        return monthlyFees;

    }
              public int getRecurringFees( Integer profileId ,Integer serviceId) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select unitcost from rating_package where profile_id= ? and service_id=?";
        int recurringFees=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,profileId );
             pst.setInt(2,serviceId );
        rs = pst.executeQuery();
        while (rs.next()) {
            recurringFees =rs.getInt("unitcost");
        }

        return recurringFees;

    }
      public int getOnTimeFees( Integer profileId ,Integer serviceId) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select unitcost from rating_package where profile_id= ? and service_id=?";
        int onTimeFees=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,profileId );
             pst.setInt(2,serviceId );
        rs = pst.executeQuery();
        while (rs.next()) {
            onTimeFees =rs.getInt("unitcost");
        }

        return onTimeFees;

    }
          public void insertBillTable(Double totalCost, int customer_id, int profile_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "insert into bill (total_cost,customer_id, profile_id,time)  Values (?,?,?,now()) ";
        pst = con.prepareStatement(sql);
        pst.setDouble(1, totalCost);
        pst.setInt(2,  customer_id);
        pst.setInt(3, profile_id);
        
        pst.executeUpdate();
    }
    
}
