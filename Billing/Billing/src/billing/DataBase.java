
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
                    "jdbc:postgresql://localhost:5432/billing", "postgres", "1121");
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
        public List<Float> getRatingChargesFromUDR( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select rating_charges from udr where dial_a= ?";
        List<Float> ratingCharges = new ArrayList<Float>();
        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn );
        rs = pst.executeQuery();
        while (rs.next()) {
            ratingCharges.add(rs.getFloat("rating_charges"));
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
              public float getRecurringFees( Integer profileId ,Integer serviceId) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select unitcost from rating_package where profile_id= ? and service_id=?";
        float recurringFees=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,profileId );
             pst.setInt(2,serviceId );
        rs = pst.executeQuery();
        while (rs.next()) {
            recurringFees =rs.getFloat("unitcost");
        }

        return recurringFees;

    }
      public float getOnTimeFees( Integer profileId ,Integer serviceId) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select unitcost from rating_package where profile_id= ? and service_id=?";
        float onTimeFees=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,profileId );
             pst.setInt(2,serviceId );
        rs = pst.executeQuery();
        while (rs.next()) {
            onTimeFees =rs.getFloat("unitcost");
        }

        return onTimeFees;

    }
          public void insertBillTable(float totalCost, int customer_id, int profile_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "insert into bill (total_cost,customer_id, profile_id,bill_cycle)  Values (?,?,?,now()) ";
        pst = con.prepareStatement(sql);
        pst.setFloat(1, totalCost);
        pst.setInt(2,  customer_id);
        pst.setInt(3, profile_id);
        
        pst.executeUpdate();
    }
    
}
