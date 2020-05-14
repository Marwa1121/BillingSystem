package billing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

public class Billing {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, JRException {
        List<String> customers = new ArrayList<String>();
        List<Float> ratingCharges = new ArrayList<Float>();

        DataBase db = new DataBase();
        Connection con = db.connect();
        customers = db.getAllCustomers();
        ResultSet rs;
        int monthlyFees;
        float recurringFees;
      
        float onTimeFees;
   
        int customerId;
        float totalBill = 0;
        float totalDataFees = 0;
        float totalSMSFees = 0;
        float totalCallFees = 0;

        float totalBillWithoutTax = 0;
        float tax = 0;
        int profileId;
        String customer_name = null;
        int nid = 0;
        int bill_id = 0;
        String address = null;
        float billCost = 0;
        int sizeOfCustomers = customers.size();
        for (int i = 0; i < sizeOfCustomers; i++) {
            float totalRatingCharges = 0;
            ratingCharges = db.getRatingChargesFromUDR(customers.get(i));
            int sizeOfRatingCharges = ratingCharges.size();

            for (int y = 0; y < sizeOfRatingCharges; y++) {
                totalRatingCharges = totalRatingCharges + ratingCharges.get(y);
            }
            profileId = db.getProfileIdOfCustomer(customers.get(i));
            monthlyFees = db.getMonthlyFees(profileId);
            customerId = db.getCustomerId(customers.get(i));
            rs=db.getRecIds(customerId);
               float totalrecurringFees=0;
            while(rs.next()){
             recurringFees=db.getRecCost(rs.getInt(1));
             totalrecurringFees=totalrecurringFees+recurringFees;
             
            }
            rs=db.getOntimeIds(customerId);
           float totalonTimeFees=0;
            while(rs.next()){
                if(rs.getBoolean(2)==false){
                 onTimeFees=db.getOntimeCost(rs.getInt(1));
                 totalonTimeFees=totalonTimeFees+onTimeFees;
                 db.updateOntimeFlag(customerId,rs.getInt(1));
                }
            }
            totalBillWithoutTax = totalRatingCharges + monthlyFees + totalrecurringFees+  totalonTimeFees;
            tax = (float) ((totalBillWithoutTax) * 0.1);
            totalBill = totalBillWithoutTax + tax;
            
            db.insertBillTable(totalBill, customerId, profileId);
            bill_id = db.getBillId(customerId);
            db.updateIs_Billing(customers.get(i));
            db.updatebill_id(bill_id, customers.get(i));
            totalCallFees = db.getTotalCallFees(customers.get(i));
            totalSMSFees = db.getTotalSMSFees(customers.get(i));
            totalDataFees = db.getTotalDataFees(customers.get(i));

            JasperDesign Jd = JRXmlLoader.load("//home//aya//Downloads//Billing//BillingSystem//Billing//Billing//src//billing//report1.jrxml");
            HashedMap param = new HashedMap();
            param.put("p1", customerId);
            param.put("pCallFees", totalCallFees);
            param.put("pSMSFees", totalSMSFees);
            param.put("pDataFees", totalDataFees);
            param.put("pbill_id", bill_id);

            param.put("pmsisdn", customers.get(i));

            rs = db.getInfoOfCustomer(customerId);
            while (rs.next()) {
                customer_name = rs.getString("customer_name");
            }
            JasperReport Jr = JasperCompileManager.compileReport(Jd);

            JasperPrint Jp = JasperFillManager.fillReport(Jr, param, con);

            JasperViewer Jv = new JasperViewer(Jp, false);
            Jv.setVisible(true);
            JasperExportManager.exportReportToPdfFile(Jp, "/home//aya//Downloads//Billing//BillingSystem//bills//bill number " + bill_id + " of " + customer_name + ".pdf");
            db.updateBillTable(bill_id);

        }

    }

}
