/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class PassCreator {

    private ArrayList<Passenger> pass;

    public PassCreator(String choice) {
        if (choice.equals("Y")) {
            PassReader reader = new PassReader();
            pass = reader.getAutoPass();
        } else {
            PassManualBuilder builder = new PassManualBuilder();
            pass = builder.getManPassenger();
        }
    }

    public ArrayList getPassenger() {
        return pass;
    }
}
