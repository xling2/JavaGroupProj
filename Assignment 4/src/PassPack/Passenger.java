/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import CruisePack.*;
import java.util.*;

/**
 *
 * @author Yixin1
 */
public class Passenger {

    private final int yearNow = 2016;
    private String passNumber;
    private String name;
    private String homeAdd;
    private String nationality;
    private String DOB;
    private int joinFee;
    private int moneySpentOnCruise;
    private int[] evaluation = new int[5];
    private ArrayList<CruiseInfo> cruiseInPast;
    private ArrayList<CruiseInfo> cruiseInFuture;

    Passenger(String num, String name, String add, String nationality,
            String DOB, int joinFee, int spendOnCruise) {

        passNumber = num;
        this.name = name;
        homeAdd = add;
        this.nationality = nationality;
        this.DOB = DOB;
        this.joinFee = joinFee;
        moneySpentOnCruise = spendOnCruise;

        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            evaluation[i] = rand.nextInt(5) + 1;
        }

        boolean flagPast = rand.nextBoolean();
        boolean flagFuture = rand.nextBoolean();

        if (flagPast) {
            cruiseInPast = new ArrayList<CruiseInfo>();
            int numPast = rand.nextInt(3) + 1;
            Random r = new Random();
            ArrayList<CruiseInfo> infoMap = CruiseCreator.cruiseReader.getInfo();
            int indexOfList = r.nextInt(infoMap.size());
            for (int i = 0; i < numPast; i++) {
                if ((indexOfList + i) < infoMap.size()) {
                    cruiseInPast.add(infoMap.get(indexOfList + i));
                } else {
                    cruiseInPast.add(infoMap.get(indexOfList - i));
                }
            }
        }
        if (flagFuture) {
            cruiseInFuture = new ArrayList<CruiseInfo>();
            int numFuture = rand.nextInt(2) + 1;
            Random r = new Random();
            ArrayList<CruiseInfo> infoMap = CruiseCreator.cruiseReader.getInfo();
            int indexOfList = r.nextInt(infoMap.size());
            for (int i = 0; i < numFuture; i++) {
                if ((indexOfList + i) < infoMap.size()) {
                    cruiseInFuture.add(infoMap.get(indexOfList + i));
                } else {
                    cruiseInFuture.add(infoMap.get(indexOfList - i));
                }
            }

        }

    }

    public int getMSOC() {
        return moneySpentOnCruise;
    }

    public String getCountry() {
        return nationality;
    }

    public int getAge() {
        return 2016 - Integer.parseInt(DOB.substring(DOB.length() - 4));
    }

    protected int[] getEva(){
        return evaluation;
    }
    
    protected int getJoinFee(){
        return joinFee;
    }
    
    @Override
    public String toString() {
        String history = new String();
        String booking = new String();
        if (cruiseInPast != null) {
            for (CruiseInfo i : cruiseInPast) {
                history += i.getSerial() + " ";
            }
        }else{
            history = "NONE";
        }

        if (cruiseInFuture != null) {
            for (CruiseInfo i : cruiseInFuture) {
                booking += i.getSerial() + " ";
            }
        }else{
            booking = "NONE";
        }
        return "Passenger Number: " + passNumber + "\n"
                + "Passenger Name: " + name + "\n"
                + "Home Address: " + homeAdd + "\n"
                + "Nationality: " + nationality + "\n"
                + "DOB: " + DOB + "\n"
                + "Join Fee:" + joinFee + "\n"
                + "Spend on Cruise: " + moneySpentOnCruise + "\n"
                + "Serial Number of Past Cruise: " + history + "\n"
                + "Serial Number of Future Cruise: " + booking + "\n";
    }
}
