/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q1;

import java.sql.*;

/**
 *
 * @author Yixin1
 */
public class DBReader {

    private int questionNumber;
    private String description;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String Answer;

    public DBReader(int rowNum) {

        // Create a prepared statement to read one row from the database
        String query = "SELECT * FROM Quiz WHERE QUESTIONNUMBER = " + rowNum;
        try (Connection con = DriverManager.getConnection(DBCreator.url);
                PreparedStatement pstmt = con.prepareStatement(query);) {

            // rs saves the set of one row that requests
            ResultSet rs = pstmt.executeQuery();

            // Move the cursor to the first row
            if (rs.next()) {
                questionNumber = rs.getInt("questionNumber");
                description = rs.getString("description");
                choice1 = rs.getString("choice1");
                choice2 = rs.getString("choice2");
                choice3 = rs.getString("choice3");
                choice4 = rs.getString("choice4");
                Answer = rs.getString("Answer");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception in DBReader: " + e);
        }
    }

    @Override
    public String toString() {

        return "\n" + questionNumber + ". " + description + "\n"
                + choice1 + "\n"
                + choice2 + "\n"
                + choice3 + "\n"
                + choice4 + "\n";
    }

    public String getAnswer() {

        return Answer;
    }

    // Build a static method to return the size of the database
    public static int getSize() {
        // Get the number of rows from the result set
        String query = "SELECT COUNT(*) FROM QUIZ";
        int rowCount = 0;
        try (Connection con = DriverManager.getConnection(DBCreator.url);
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery(query);) {

            // Move the cursor to the first row
            rs1.next();

            // The first column has the COUNT result
            rowCount = rs1.getInt(1);

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e);
        }
        return rowCount;
    }

}
