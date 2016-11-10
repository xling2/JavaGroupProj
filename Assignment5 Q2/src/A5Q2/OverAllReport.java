/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q2;

import java.io.*;
import java.nio.file.*;

/**
 *
 * @author Yixin1
 */
public class OverAllReport {

    public static void main(String[] arg) {
        // Find the data file
        Path dataPath = Paths.get("SailorData.txt");
        File dataFile = dataPath.toFile();
        // Create the database
        new DBCreator(dataFile);
        // Create report one by one
        new Report_TopEarners();
        new Report_Highest_Lowest_Average();
        new Report_RForRich();
    }

}
