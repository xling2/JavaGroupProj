/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class CruiseReader {
    
    private Path filePath = null;
    private File theFile = null;
    private ArrayList<CruiseInfo> InfoList;

    private final String FIELD_SEP = ",";

    public CruiseReader() {

        filePath = Paths.get("Cruise.txt");
        theFile = filePath.toFile();
        InfoList = getContent();
    }

    private ArrayList getContent() {
        if (InfoList != null) {
            return InfoList;
        }
        InfoList = new ArrayList();
        if (Files.exists(filePath)) {
            try (BufferedReader in
                    = new BufferedReader(
                            new FileReader(theFile))) {
                String line = in.readLine();
                while (line != null) {
                    String[] columns = line.split(FIELD_SEP);
                    String ID = columns[0];
                    String deDate = columns[1];
                    String reDate = columns[2];
                    
                    CruiseInfo i = new CruiseInfo(ID, deDate, reDate);

                    InfoList.add(i);

                    line = in.readLine();
                }
            } catch (IOException e) {
                System.out.println(e);
                return null;
            }
        }
        return InfoList;
    }
    public ArrayList<CruiseInfo> getInfo(){
        return InfoList;
    }
}
