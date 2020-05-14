
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
                    "jdbc:postgresql://localhost:5432/billing", "postgres", "postgres");
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
        String sql = "select rating_charges from udr where dial_a= ? and is_billing=false";
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
  

          public void insertBillTable(float totalCost, int customer_id, int profile_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "insert into bill (total_cost,customer_id, profile_id,bill_cycle,Is_extracted)  Values (?,?,?,now(),false) ";
        pst = con.prepareStatement(sql);
        pst.setFloat(1, totalCost);
        pst.setInt(2,  customer_id);
        pst.setInt(3, profile_id);
        
        pst.executeUpdate();
    }
        public ResultSet getInfoOfCustomer( int customer_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select customer_name,nid,address from customer where customer_id=? ";
        pst = con.prepareStatement(sql);
       
        pst.setInt(1,  customer_id);
       
        
        rs = pst.executeQuery();
        return rs;
        }
      public float getBillCost(int customer_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select total_cost from bill where customer_id= ? and Is_extracted=false";
        float billCost=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,customer_id );
            
        rs = pst.executeQuery();
        while (rs.next()) {
            billCost =rs.getFloat("total_cost");
        }

        return billCost;

    }
         public int getBillId(int customer_id  ) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select bill_id from bill where customer_id= ? and is_extracted=false";
int billId=0;
        pst = con.prepareStatement(sql);
           pst.setInt(1,customer_id );
        rs = pst.executeQuery();
        while (rs.next()) {
            billId=rs.getInt("bill_id");
        }

        return billId;

    }
           public void updateIs_Billing( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "update udr set is_billing=true  where bill_id is null and dial_a=?";
        pst = con.prepareStatement(sql);
      
        pst.setString(1, msisdn);

       
        
        pst.executeUpdate();
    }
       public void updatebill_id( int bill_id,String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "update udr set bill_id=?  where bill_id is null and dial_a=?";
        pst = con.prepareStatement(sql);
       pst.setInt(1, bill_id);
        pst.setString(2, msisdn);

       
        
        pst.executeUpdate();
    }
        public void updateBillTable( int bill_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "update bill set is_extracted=true  where bill_id =? ";
        pst = con.prepareStatement(sql);
       pst.setInt(1, bill_id);
       

       
        
        pst.executeUpdate();
    }
    public float getTotalCallFees( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select sum(rating_charges) from udr where  service_id=1 and dial_a=?";
        float totalCallFees=0;

        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn);
          
        rs = pst.executeQuery();
        while (rs.next()) {
            totalCallFees =rs.getFloat(1);
        }

        return totalCallFees;

    }
        public float getTotalSMSFees( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select sum(rating_charges) from udr where  service_id=2 and dial_a=?";
        float totalSMSFees=0;

        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn);
          
        rs = pst.executeQuery();
        while (rs.next()) {
            totalSMSFees =rs.getFloat(1);
        }

        return totalSMSFees;

    }
         public float getTotalDataFees( String msisdn) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select sum(rating_charges) from udr where  service_id=3 and dial_a=?";
        float totalDataFees=0;

        pst = con.prepareStatement(sql);
           pst.setString(1,msisdn);
          
        rs = pst.executeQuery();
        while (rs.next()) {
            totalDataFees=rs.getFloat(1);
        }

        return totalDataFees;

    }
   public ResultSet getRecIds( int customer_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select  rec_id from recurring_customer where customer_id=? ";
        pst = con.prepareStatement(sql);
       
        pst.setInt(1, customer_id);
      
        
        rs = pst.executeQuery();
        return rs;
        }
      public float getRecCost( int rec_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select cost from recurring_service where  rec_id=?";
        float recFee=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,rec_id);
          
        rs = pst.executeQuery();
        while (rs.next()) {
           recFee=rs.getFloat(1);
        }

        return recFee;

    }
   
      public ResultSet getOntimeIds( int customer_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select  ontime_id, ontime_flag from ontime_customer where customer_id=? ";
        pst = con.prepareStatement(sql);
       
        pst.setInt(1, customer_id);
      
        
        rs = pst.executeQuery();
        return rs;
        }
            public float getOntimeCost( int ontime_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "select cost from ontime_service where  ontime_id=?";
        float ontimeFee=0;

        pst = con.prepareStatement(sql);
           pst.setInt(1,ontime_id);
          
        rs = pst.executeQuery();
        while (rs.next()) {
          ontimeFee=rs.getFloat(1);
        }

        return ontimeFee;

    }
         public void updateOntimeFlag( int customer_id,  int ontime_id) throws ClassNotFoundException, SQLException {
        connect();
        String sql = "update ontime_customer set ontime_flag=true  where customer_id =? and ontime_id=?";
        pst = con.prepareStatement(sql);
       pst.setInt(1, customer_id);
       pst.setInt(2, ontime_id);

       
        
        pst.executeUpdate();
    }
   
    }
    

