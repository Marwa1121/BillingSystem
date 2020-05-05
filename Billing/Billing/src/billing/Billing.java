package billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Billing {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<String> customers = new ArrayList<String>();
        List<Float> ratingCharges = new ArrayList<Float>();
        DataBase db = new DataBase();
        customers = db.getAllCustomers();
        
        int monthlyFees;
        float recurringFees;
        float onTimeFees;
        int customerId;
        float totalBill = 0;
        float totalBillWithoutTax = 0;
        float tax = 0;
        int profileId;
        int sizeOfCustomers = customers.size();
        for (int i = 0; i < sizeOfCustomers; i++) {
            float totalRatingCharges = 0;
            ratingCharges = db.getRatingChargesFromUDR(customers.get(i));
                    int sizeOfRatingCharges = ratingCharges.size();

            for (int y = 0; i < sizeOfRatingCharges; y++) {
                totalRatingCharges = totalRatingCharges + ratingCharges.get(y);
            }
                profileId = db.getProfileIdOfCustomer(customers.get(i));
                monthlyFees = db.getMonthlyFees(profileId);
                recurringFees = db.getRecurringFees(profileId, 4);
                onTimeFees = db.getOnTimeFees(profileId, 5);
                totalBillWithoutTax = totalRatingCharges + monthlyFees + recurringFees + onTimeFees;
                tax =  (float)((totalBillWithoutTax) * 0.1);
                totalBill = totalBillWithoutTax + tax;
                customerId = db.getCustomerId(customers.get(i));
                db.insertBillTable(totalBill, customerId, profileId);
            }

        }

    }

