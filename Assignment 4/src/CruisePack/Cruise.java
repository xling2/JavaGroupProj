/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

import StaffPack.*;
import PassPack.*;
import PortPack.*;
import ShipPack.*;
import java.util.*;

/**
 *
 * @author Yixin1
 */
public class Cruise {

    private CruiseInfo info;
    private String serialNumber;
    private String sailingDate;
    private String returnDate;
    private Port dePort;
    private ArrayList<Port> portsOfCall = new ArrayList();
    private Ship cruiseShip;
    private ArrayList<Passenger> cruisePass;
    private ArrayList<Sailor> cruiseSailor;
    Random rand = new Random();

    /**
     *
     * @param info
     * @param thePort
     * @param theShip
     * @param thePass
     * @param theSailor
     */
    protected Cruise(CruiseInfo info, ArrayList<Port> thePort, 
            ArrayList<Ship> theShip,
            ArrayList<Passenger> thePass, 
            ArrayList<Sailor> theSailor) {

        this.info = info;
        serialNumber = info.getSerial();
        sailingDate = info.getDepart();
        returnDate = info.getReturn();
        cruisePass = thePass;
        cruiseSailor = theSailor;

        int indexOfList = rand.nextInt(thePort.size());
        int numOfList = rand.nextInt(3) + 2;
        ArrayList<Port> POC = new ArrayList();
        dePort = thePort.get(indexOfList);
        for (int i = 1; i <= numOfList; i++) {
            if ((indexOfList + i) < thePort.size()) {
                POC.add(thePort.get(indexOfList + i));
            } else {
                POC.add(thePort.get(indexOfList - i));
            }
        }
        portsOfCall = POC;
        indexOfList = rand.nextInt(theShip.size());
        cruiseShip = theShip.get(indexOfList);
    }

    protected ArrayList<Sailor> getSailor() {
        return cruiseSailor;
    }


    public ArrayList<Passenger> getPass(){
        return cruisePass;
    }
    
    public Ship getShip(){
        return cruiseShip;
    }
    
    public ArrayList<Port> getPOC(){
        return portsOfCall;
    }
    
    public ArrayList getStaff(){
        return cruiseSailor;
    }
    
    public CruiseInfo getInfo(){
        return info;
    }
    
    public Port getDePort(){
        return dePort;
    }
}
