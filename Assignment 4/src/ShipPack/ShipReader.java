/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShipPack;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class ShipReader {

    private Path filePath = null;
    private File theFile = null;
    private ArrayList ship = null;

    private final String FIELD_SEP = ",";

    public ShipReader() {

        filePath = Paths.get("Ship.txt");
        theFile = filePath.toFile();
        ship = getContent();
    }

    private ArrayList<Ship> getContent() {
        if (ship != null) {
            return ship;
        }
        ship = new ArrayList<Ship>();
        if (Files.exists(filePath)) {
            try (BufferedReader in
                    = new BufferedReader(
                            new FileReader(theFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] columns = line.split(FIELD_SEP);
                    String name = columns[0];
                    String ID = columns[1];
                    String weight = columns[2];
                    String yearBuilt = columns[3];
                    String capacity = columns[4];

                    Ship sh = new Ship(
                            ID, name,
                            Integer.parseInt(weight),
                            Integer.parseInt(yearBuilt),
                            Integer.parseInt(capacity));

                    ship.add(sh);

                    line = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        }
        return ship;
    }
    public ArrayList getShip(){
        return ship;
    }

}




