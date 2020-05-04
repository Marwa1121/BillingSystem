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
        float totalRatingCharges = 0.0f;
        int monthlyFees;
        float recurringFees;
        float onTimeFees;
        int customerId;
        float totalBill = 0.0f;
        float totalBillWithoutTax = 0.0f;
        float tax = 0.0f;
        int profileId;
        int sizeOfCustomers = customers.size();
        int sizeOfRatingCharges = ratingCharges.size();
        for (int i = 0; i < sizeOfCustomers; i++) {
            ratingCharges = db.getRatingChargesFromUDR(customers.get(i));
            for (int y = 0; i < sizeOfRatingCharges; y++) {
                totalRatingCharges = totalRatingCharges + ratingCharges.get(y);
                profileId = db.getProfileIdOfCustomer(customers.get(i));
                monthlyFees = db.getMonthlyFees(profileId);
                recurringFees = db.getRecurringFees(profileId, 4);
                onTimeFees = db.getOnTimeFees(profileId, 5);
                totalBillWithoutTax = totalRatingCharges + monthlyFees + recurringFees + onTimeFees;
                tax = totalBillWithoutTax * 0.1f;
                totalBill = totalBillWithoutTax + tax;
                customerId = db.getCustomerId(customers.get(i));
                db.insertBillTable(totalBill, customerId, profileId);
            }

        }

    }
}
