/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q2;

import java.sql.*;

/**
 *
 * @author Yixin1
 */
public class Report_RForRich {

    public Report_RForRich() {
        // Create a statement to get sailors with last names start with R
        // as well as salaries over 58543 
        String query = "SELECT * "
                + "FROM Sailor "
                + "WHERE (Salary > 58543 AND Lastname LIKE 'R%') ";
        // Connection to the database
        try (Connection con = DriverManager.getConnection(DBCreator.url);
                Statement stmt = con.createStatement();) {
            // Get the set as required
            ResultSet rsRich = stmt.executeQuery(query);
            System.out.println("\nThe sailors with last names start with ‘R’ "
                    + "and earn more than $58543: \n");
            // Loop through the set to print the data
            while (rsRich.next()) {
                System.out.println("Last Name: " + rsRich.getString(1) + "\n"
                        + "First Name: " + rsRich.getString(2) + "\n"
                        + "Position: " + rsRich.getString(3) + "\n"
                        + "Salary: " + rsRich.getInt(4) + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in DBReader: " + e);
        }
    }
}
