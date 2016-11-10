/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPack;

import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class SeniorSailor extends Staff {

    protected ArrayList<Sailor> supervisee;

    protected SeniorSailor(String ID, String name, String DOB, String nationality) {
        super(ID, name, DOB, nationality);
        supervisee = new ArrayList();
        setSalary(60000);
    }

    @Override
    public String toString() {

        if (supervisee.size() == 0) {
            return super.toString();
        } else {
            String superviseeNames = "";
            for (Sailor s : supervisee) {
                superviseeNames += s.getName() + ", ";
            }
            superviseeNames
                   = superviseeNames.substring(0, superviseeNames.length() - 2);
            return super.toString() + "Supervisee: " + superviseeNames + "\n";
        }

    }
    
    protected void setSupervisee(Sailor s){
        supervisee.add(s);
    }

}
