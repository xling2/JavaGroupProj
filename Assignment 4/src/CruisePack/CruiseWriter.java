/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

import java.io.*;
import StaffPack.*;
import PassPack.*;
import PortPack.*;
import ShipPack.*;
import java.util.*;

/**
 *
 * @author Yixin1
 */
public class CruiseWriter {

    private File cruiseFile;

    public CruiseWriter(String fileName, Cruise theCruise) {
        cruiseFile = new File(fileName);
        ArrayList staff = theCruise.getStaff();
        ArrayList<Passenger> pass = theCruise.getPass();
        try {
            FileWriter fw = new FileWriter(this.cruiseFile, true);
            fw.write("-------------- Cruise Information --------------");
            fw.write("\n\n");
            fw.write(theCruise.getInfo().toString());
            fw.write("\n\n");

            fw.write("-------------- Ship Information ---------------");
            fw.write("\n\n");
            fw.write(theCruise.getShip().toString());
            fw.write("\n\n");

            fw.write("-------------- Staff Information ---------------");
            fw.write("\n\n");
            for (int i = 0; i < staff.size() - 1; i++) {
                ArrayList job = (ArrayList) staff.get(i);
                for (int j = 0; j < job.size(); j++) {
                    fw.write(job.get(j).toString());
                }
            }
            fw.write(System.lineSeparator());

            fw.write(staff.get(staff.size() - 1).toString());

            fw.write("\n\n");

            fw.write("-------------- Port Information ---------------");
            fw.write("\n\n");
            fw.write("* Departure Port *");
            fw.write(System.lineSeparator());

            fw.write(theCruise.getDePort().toString());

            fw.write(System.lineSeparator());
            fw.write("* Ports-of-call *");
            fw.write(System.lineSeparator());

            for (int i = 1; i < theCruise.getPOC().size(); i++) {
                fw.write(theCruise.getPOC().get(i).toString());
                fw.write(System.lineSeparator());
            }
            fw.write("\n\n");

            fw.write("-------------- Passenger Information ---------------");
            fw.write("\n\n");
            for (Passenger p : pass) {
                fw.write(p.toString());
                fw.write(System.lineSeparator());

            }

            fw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
