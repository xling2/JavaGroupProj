/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPack;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class StaffReader {

    private Path filePath;
    private File theFile;
    private ArrayList staff;
    private ArrayList<Sailor> sailorList;
    private ArrayList<Cook> cookList;
    private ArrayList<Doctor> doctorList;
    private ArrayList<Engineer> engineerList;
    private ArrayList<SeniorSailor> seniorList;
    private Captain theCaptain;

    private final String FIELD_SEP = ",";

    protected StaffReader() {

        staff = new ArrayList<>();
        sailorList = new ArrayList<Sailor>();
        cookList = new ArrayList<Cook>();
        doctorList = new ArrayList<Doctor>();
        engineerList = new ArrayList<Engineer>();
        seniorList = new ArrayList<SeniorSailor>();
        filePath = Paths.get("Staff.txt");
        theFile = filePath.toFile();
        staff = this.getContent();
    }

    private ArrayList getContent() {

        ArrayList staffList = new ArrayList<>();
        if (Files.exists(filePath)) {
            try (BufferedReader in
                    = new BufferedReader(
                            new FileReader(theFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] columns = line.split(FIELD_SEP);
                    String ID = columns[0];
                    String name = columns[1];
                    String DOB = columns[2];
                    String nationality = columns[3];
                    String job = columns[4];

                    switch (job) {
                        case "Captain": {
                            theCaptain = new Captain(ID, name,
                                    DOB, nationality);
                            break;
                        }
                        case "Cook": {
                            Cook c = new Cook(ID, name, DOB, nationality);
                            cookList.add(c);
                            break;
                        }
                        case "Sailor": {
                            Sailor s = new Sailor(ID, name, DOB, nationality);
                            sailorList.add(s);
                            break;
                        }
                        case "Senior Sailor": {
                            SeniorSailor ss = new SeniorSailor(ID, name, 
                                    DOB, nationality);
                            seniorList.add(ss);
                            break;
                        }
                        case "Doctor": {
                            Doctor d = new Doctor(ID, name, DOB, nationality);
                            doctorList.add(d);
                            break;
                        }
                        case "Engineer": {
                            Engineer e = new Engineer(ID, name, 
                                    DOB, nationality);
                            engineerList.add(e);
                            break;
                        }
                    }
                    line = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }

        }
        staffList.add(sailorList);
        staffList.add(seniorList);
        staffList.add(cookList);
        staffList.add(engineerList);
        staffList.add(doctorList);
        staffList.add(theCaptain);
        return staffList;
    }

    protected ArrayList getList() {

        return staff;
    }
}
