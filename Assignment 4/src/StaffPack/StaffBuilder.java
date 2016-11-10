/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPack;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Yixin1
 */
public class StaffBuilder {

    private ArrayList staff;

    public StaffBuilder() {
        StaffReader reader = new StaffReader();
        staff = reader.getList();
        genSupervision();
        
    }

    void genSupervision() {
        int numOfSailor = ((ArrayList) staff.get(0)).size();
        int numOfSenior = ((ArrayList) staff.get(1)).size();
        int[] supervisedBy = new int[numOfSailor];
        boolean[] supervised = new boolean[numOfSailor];
        Random rand = new Random();
        int j = rand.nextInt(numOfSailor);

        for (int i = 0; i < numOfSenior; i++) {
            while (supervised[j]) {
                j = rand.nextInt(numOfSailor);
            }
            supervisedBy[j] = i;
            supervised[j] = true;
            ((SeniorSailor) (((ArrayList) staff.get(1)).get(i))).setSupervisee(
                    ((Sailor) (((ArrayList) staff.get(0)).get(j))));
        }
        for (int i = 0; i < numOfSailor; i++) {
            while (supervised[i] == false) {
                j = rand.nextInt(numOfSenior);
                supervisedBy[i] = j;
                supervised[i] = true;
                ((SeniorSailor) (((ArrayList) staff.get(1)).get(j))).setSupervisee(
                        ((Sailor) (((ArrayList) staff.get(0)).get(i))));
            }
        }

        for (int i = 0; i < numOfSailor; i++) {
            ((Sailor) (((ArrayList) staff.get(0)).get(i))).setSupervisor(
                    (SeniorSailor) ((ArrayList) staff.get(1)).get(supervisedBy[i]));
        }
    }

    public ArrayList getStaff() {
        return staff;
    }

}
