/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import CruisePack.Cruise;
import PassPack.Passenger;
import java.util.*;

/**
 *
 * @author Yixin1
 */
public class RevAnalysis {
    
    private ArrayList<Passenger> cruisePass;
    
    protected ArrayList classify() {

        ArrayList analysis = new ArrayList();
        ArrayList<String> country = new ArrayList();
        ArrayList<Integer> revByCountry = new ArrayList();
        String[] age = {"<20", "20~29", "30~39", "40~49",
            "50-59", "60~69", "70~79", ">80"};
        int[] revByAge = new int[8];

        outer:
        for (Passenger p : cruisePass) {
            if (p.getAge() < 20) {
                revByAge[0] += p.getMSOC();
            } else if (p.getAge() >= 20 && p.getAge() < 30) {
                revByAge[1] += p.getMSOC();
            } else if (p.getAge() >= 30 && p.getAge() < 40) {
                revByAge[2] += p.getMSOC();
            } else if (p.getAge() >= 40 && p.getAge() < 50) {
                revByAge[3] += p.getMSOC();
            } else if (p.getAge() >= 50 && p.getAge() < 60) {
                revByAge[4] += p.getMSOC();
            } else if (p.getAge() >= 60 && p.getAge() < 70) {
                revByAge[5] += p.getMSOC();
            } else if (p.getAge() >= 70 && p.getAge() < 80) {
                revByAge[6] += p.getMSOC();
            } else if (p.getAge() >= 80) {
                revByAge[7] += p.getMSOC();
            }

            for (String s : country) {
                if (p.getCountry().equals(s)) {
                    revByCountry.set(
                            country.indexOf(s),
                            revByCountry.get(country.indexOf(s)) + p.getMSOC());
                    continue outer;
                }
            }
            country.add(p.getCountry());
            revByCountry.add(p.getMSOC());
        }

        analysis.add(country);
        analysis.add(revByCountry);
        analysis.add(age);
        analysis.add(revByAge);

        return analysis;

    }
    
    public RevAnalysis(Cruise theCruise){
        cruisePass = theCruise.getPass();
        ArrayList analysis = classify();
        System.out.println("--------------Revenue Analysis--------------");
        System.out.println("* Revenue by Nationality *");

        for (String s : (ArrayList<String>) analysis.get(0)) {
            int i = ((ArrayList<String>) analysis.get(0)).indexOf(s);
            System.out.print("Country: " + s + "\n");
            System.out.println("Revenue: "
                    + ((ArrayList<Integer>) analysis.get(1)).get(i) + "\n");
        }
        System.out.println("* Revenue by Age *");

        for (int i = 0; i < ((String[]) analysis.get(2)).length; i++) {
            String s = ((String[]) analysis.get(2))[i];
            int rev = ((int[]) analysis.get(3))[i];
            System.out.print("Age Group: " + s + "\n");
            System.out.println("Revenue: " + rev + "\n");
        }
    }
    
    
}
