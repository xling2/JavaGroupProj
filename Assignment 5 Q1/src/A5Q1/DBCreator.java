/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q1;

import java.io.*;
import java.sql.*;

/**
 *
 * @author Yixin1
 */
public class DBCreator {

    private int questionNumber;
    private String description;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String Answer;

    public static String url = "jdbc:derby:/Users/Yixin1/"
            + "Library/Application Support/NetBeans/8.1/derby/ExamDB";

    // The data in csv file is seperated by comma
    private final String FIELD_SEP = ",";

    public DBCreator(File exam) {

        // Create a prepared statement used to insert data
        String insertStmt = "INSERT INTO Quiz (questionNumber,description,"
                + "choice1,choice2,choice3,choice4,Answer)"
                + "VALUES (?,?,?,?,?,?,?)";

        // Read in data from file
        try (BufferedReader bIn = new BufferedReader(new FileReader(exam));) {

            // Readin one line per time
            String line = bIn.readLine();
            while (line != null) {
                // Use String array to store different field of data
                String[] columns = line.split(FIELD_SEP);

                // Store data in according vairables
                questionNumber = Integer.parseInt(columns[0]);
                description = columns[1];
                choice1 = columns[2];
                choice2 = columns[3];
                choice3 = columns[4];
                choice4 = columns[5];
                Answer = columns[6];

                // Connect the database
                try (Connection con = DriverManager.getConnection(url);
                        PreparedStatement pstmt
                        = con.prepareStatement(insertStmt);) {

                    // Set every variable in the prepared statement
                    pstmt.setInt(1, questionNumber);
                    pstmt.setString(2, description);
                    pstmt.setString(3, choice1);
                    pstmt.setString(4, choice2);
                    pstmt.setString(5, choice3);
                    pstmt.setString(6, choice4);
                    pstmt.setString(7, Answer);

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
