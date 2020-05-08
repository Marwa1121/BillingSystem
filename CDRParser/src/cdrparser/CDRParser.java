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
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

public class CDRParser {

    /**
     * @param args the command line arguments
     */
    public int j = 0;

    public static class Filter {

        public File[] finder(String dirName) {
            File dir = new File(dirName);

            return dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".cvs");

                }
            });

        }

    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
        Cdrparse_database db = new Cdrparse_database();
        Connection connection = db.getconnection();
        CDRParser object = new CDRParser();
        object.parser(connection);
    }

    private synchronized void parser(Connection connection) throws SQLException, IOException {

        Random rand = new Random();
        Filter f = new Filter();
        String dirName = "C:\\Users\\Acer\\Desktop\\Billing\\BillingSystem\\CDR";
        String dirtarget = "C:\\Users\\Acer\\Desktop\\Billing\\BillingSystem\\CDR archive";
        File[] newcdrs;
        int i = 0;

        while (true) {
            newcdrs = f.finder(dirName);
            i = 0;
            j = rand.nextInt();

            for (i = 0; i < newcdrs.length; i++) {
                String sql = "INSERT INTO cdr (dial_a,dial_b,service_id,duration_volume_msg,start_date,start_time,external_charges,israting)" + " VALUES" + " (?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                Scanner scanner = new Scanner(newcdrs[i]);
                Scanner valueScanner = null;
                int index = 0;
                while (scanner.hasNextLine()) {
                    valueScanner = new Scanner(scanner.nextLine());
                    valueScanner.useDelimiter(",");

                    while (valueScanner.hasNext()) {
                        String data = valueScanner.next();

//                        if (index == 0) {
//                            int cdr_id = Integer.parseInt(data);
//                            statement.setInt(1, cdr_id);
//                            System.out.println(cdr_id);

                         if (index == 0) {
                            String dial_a = data;
                            statement.setString(1, dial_a);
                            System.out.println(dial_a);

                        } else if (index == 1) {
                            String dial_b = data;
                            statement.setString(2, dial_b);
                            System.out.println(dial_b);

                        } else if (index == 2) {
                            int service_id = Integer.parseInt(data);
                            statement.setInt(3, service_id);
                            System.out.println(service_id);

                        } else if (index == 3) {
                            float dur_vol_msg = Float.parseFloat(data);
                            statement.setFloat(4, dur_vol_msg);

                        } else if (index == 4) {
                            Date start_date = Date.valueOf(data);
                            statement.setDate(5, start_date);

                        } else if (index == 5) {
                            Time start_time = Time.valueOf(data);
                            statement.setTime(6, start_time);

                        } else if (index == 6) {
                            float external_charges = Float.parseFloat(data);
                            statement.setFloat(7, external_charges);

                        }
                         
                          else if (index == 7) {
                            boolean israting = Boolean.parseBoolean(data);
                            statement.setBoolean(8,israting );

                        }
                         
                        index++;
                    }
                }
                scanner.close();
                Files.copy(Paths.get(newcdrs[i].toString()), Paths.get(dirtarget.toString() + "//" + "cdr" + j + ".cvs"));
                statement.executeUpdate();
                newcdrs[i].delete();
            }

            if (i >= newcdrs.length) {

                System.out.print("no new cdr \n");
            }

////                connection.close();
            try {
                this.wait(2000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

    }
}
