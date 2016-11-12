package A2Q4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;

/**
 *
 * @author Yixin1
 */
public class Tray {

    private String name;
    private String pickDate;
    private String[] pickDateCatalog = {"03/09", "01/09", "07/09", "31/08",
        "22/08"};
    private String[] nameCatalog = {"Banana", "Apple", "Cauliflower", "Potato",
        "Capsicum"};

    public Tray(int i) {
        name = nameCatalog[i];
        pickDate = pickDateCatalog[i];
    }

    public Tray() {
        Random rand = new Random();
        int i = rand.nextInt(5);
        name = nameCatalog[i];
        pickDate = pickDateCatalog[i];
    }

    public String getName() {
        return name;
    }

    public String getPickDate() {
        return pickDate;
    }

    //Get the catalog of fruits and vegetables
    public String[] getNameCatalog() {
        return nameCatalog;
    }
}
