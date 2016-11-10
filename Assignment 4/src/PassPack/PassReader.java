/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class PassReader {

    private Path filePath = null;
    private File theFile = null;
    private ArrayList pass = null;

    private final String FIELD_SEP = "\t";

    protected PassReader() {

        filePath = Paths.get("Passenger.txt");
        theFile = filePath.toFile();
        pass = getContent();

    }

    private ArrayList<Passenger> getContent() {
        if (pass != null) {
            return pass;
        }
        pass = new ArrayList<Passenger>();
        if (Files.exists(filePath)) {
            try (BufferedReader in
                    = new BufferedReader(
                            new FileReader(theFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] columns = line.split(FIELD_SEP);
                    String name = columns[0];
                    String ID = columns[1];
                    String add = columns[2];
                    String nationality = columns[3];
                    String DOB = columns[4];
                    String entrFee = columns[5];
                    String onCruiseSpend = columns[6];

                    Passenger pa = new Passenger(
                            ID, name, add, nationality, DOB,
                            Integer.parseInt(entrFee),
                            Integer.parseInt(onCruiseSpend));

                    pass.add(pa);

                    line = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        }
        return pass;
    }

    protected ArrayList<Passenger> getAutoPass() {
        return pass;
    }
}
