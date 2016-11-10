/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q2;

import java.io.*;
import java.sql.*;

/**
 *
 * @author Yixin1
 */
public class DBCreator {

    private String first;
    private String last;
    private String position;
    private int salary;

    public static String url = "jdbc:derby://localhost:1527/EmployeeDB";

    // The data in txt file is seperated by comma
    private final String FIELD_SEP = ",";

    public DBCreator(File data) {

        // Create a prepared statement used to insert data
        String insertStmt = "INSERT INTO Sailor "
                + "(Lastname,Firstname,Position,Salary) "
                + "VALUES (?,?,?,?)";

        // Read in data from file
        try (BufferedReader bIn = new BufferedReader(new FileReader(data));) {

            // Readin one line per time
            String line = bIn.readLine();
            while (line != null) {
                // Use String array to store different field of data
                String[] columns = line.split(FIELD_SEP);

                // Store data in according vairables
                last = columns[0];
                first = columns[1];
                position = columns[2];
                salary = Integer.parseInt(columns[3]);

                // Connect the database
                try (Connection con = DriverManager.getConnection(url);
                        PreparedStatement pstmt
                        = con.prepareStatement(insertStmt);) {
                    // Set every variable in the prepared statement
                    pstmt.setString(1, last);
                    pstmt.setString(2, first);
                    pstmt.setString(3, position);
                    pstmt.setInt(4, salary);

                    // Execute the query 
                    pstmt.execute();
                    // Readin another line
                    line = bIn.readLine();

                } catch (SQLException e) {
                    System.out.println("SQL Exception in DBCreator: " + e);
                }
            }
            // Catch file exception first
        } catch (FileNotFoundException f) {
            System.out.println("File not found: " + f);
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

    }

}
