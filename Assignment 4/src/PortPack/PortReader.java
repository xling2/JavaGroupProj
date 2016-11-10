/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortPack;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
/**
 *
 * @author Yixin1
 */
public class PortReader {
    private Path filePath = null;
    private File theFile = null;
    private ArrayList port = null;

    private final String FIELD_SEP = ",";

    public PortReader() {

        filePath = Paths.get("Port.txt");
        theFile = filePath.toFile();
        port = getContent();
    }

    private ArrayList<Port> getContent() {
        if (port != null) {
            return port;
        }
        port = new ArrayList<Port>();
        if (Files.exists(filePath)) {
            try (BufferedReader in
                    = new BufferedReader(
                            new FileReader(theFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] columns = line.split(FIELD_SEP);
                    String name = columns[0];
                    String country = columns[1];
                    String population = columns[2];
                    String passNeeded = columns[3];

                    Port p = new Port(
                            name, country,
                            Integer.parseInt(population), 
                            (passNeeded.equals("Yes")));

                    port.add(p);

                    line = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        }
        return port;
    }
    public ArrayList<Port> getPort(){
        return port;
    }
}
