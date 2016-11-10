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
public class Report_TopEarners {

    public Report_TopEarners() {
        // Create a statement to get the highest earners of each occupation
        // with a correlated subquery
        String query = "SELECT * FROM Sailor "
                + "WHERE Salary = (SELECT MAX(Salary) FROM Sailor AS s "
                + "WHERE s.POSITION LIKE Sailor.POSITION)";
        // Create connection to the database
        try (Connection con = DriverManager.getConnection(DBCreator.url);
                Statement stmt = con.createStatement();) {
            // Get the set as required
            ResultSet rsTop = stmt.executeQuery(query);
            System.out.println("\nThe list of top-earning sailors in "
                    + "each Position: \n");
            // Loop through the set to print the data
            while (rsTop.next()) {
                System.out.println("Last Name: " + rsTop.getString(1) + "\n"
                        + "First Name: " + rsTop.getString(2) + "\n"
                        + "Position: " + rsTop.getString(3) + "\n"
                        + "Salary: " + rsTop.getInt(4) + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Report_TopEarners: " + e);
        }
    }
}
