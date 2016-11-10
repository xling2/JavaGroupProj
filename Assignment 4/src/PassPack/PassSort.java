/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import CruisePack.Cruise;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Yixin1
 */
public class PassSort {

    private ArrayList<Passenger> sortedPass;

    public PassSort(Cruise theCruise) {

        sortedPass = theCruise.getPass();
        for (int i = 0; i < sortedPass.size(); i++) {
            for (int j = i; j < sortedPass.size(); j++) {
                if (sortedPass.get(i).getMSOC()
                        < sortedPass.get(j).getMSOC()) {
                    Collections.swap(sortedPass, i, j);
                }
            }
        }
    }

    public void printSortedPass() {
        System.out.println("--------------Passenger Sorted by Money "
                + "Spent on Cruise--------------");
        for (Passenger p : sortedPass) {

            System.out.println(p.toString());
        }
    }

}
