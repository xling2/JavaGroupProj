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
public class Report_Highest_Lowest_Average {

    public Report_Highest_Lowest_Average() {

        // Create statements to get the sailors with highest, lowest, 
        // and above average salaries
        String queryHighest = "SELECT * FROM Sailor "
                + "WHERE (Salary = (SELECT MAX(Salary) FROM Sailor))";

        String queryLowest = "SELECT * FROM Sailor "
                + "WHERE (Salary = (SELECT MIN(Salary) FROM Sailor))";

        String queryAvg = "SELECT * FROM Sailor "
                + "WHERE (Salary > (SELECT AVG(Salary) FROM Sailor))";

        // Connection to database
        try (Connection con = DriverManager.getConnection(DBCreator.url);
                Statement stmt = con.createStatement();) {
            // Get the set containing the sailor with the highest salary
            ResultSet rsHighest = stmt.executeQuery(queryHighest);
            System.out.println("\nThe sailors with the highest salaries: \n");
            while (rsHighest.next()) {
                System.out.println("Last Name: " + rsHighest.getString(1) + "\n"
                        + "First Name: " + rsHighest.getString(2) + "\n"
                        + "Position: " + rsHighest.getString(3) + "\n"
                        + "Salary: " + rsHighest.getInt(4) + "\n");
            }
            // Get the set containing the sailor with the lowest salary
            ResultSet rsLowest = stmt.executeQuery(queryLowest);
            System.out.println("\nThe sailors with the lowest salaries: \n");
            while (rsLowest.next()) {
                System.out.println("Last Name: " + rsLowest.getString(1) + "\n"
                        + "First Name: " + rsLowest.getString(2) + "\n"
                        + "Position: " + rsLowest.getString(3) + "\n"
                        + "Salary: " + rsLowest.getInt(4) + "\n");
            }

            // Get the set containing the sailor with above average salary
            ResultSet rsAvg = stmt.executeQuery(queryAvg);
            System.out.println("\nThe list of sailors above average salaries: \n");
            // Loop through the set to print the data
            while (rsAvg.next()) {
                System.out.println("Last Name: " + rsAvg.getString(1) + "\n"
                        + "First Name: " + rsAvg.getString(2) + "\n"
                        + "Position: " + rsAvg.getString(3) + "\n"
                        + "Salary: " + rsAvg.getInt(4) + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in Report_H_L_A: " + e);
        }
    }
}
