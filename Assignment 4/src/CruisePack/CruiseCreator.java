/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

import java.util.*;
import StaffPack.*;
import PassPack.*;
import PortPack.*;
import ShipPack.*;

/**
 *
 * @author Yixin1
 */
public class CruiseCreator {

    public static CruiseReader cruiseReader;

    public static void main(String[] args) {
        cruiseReader = new CruiseReader();
        Random rand = new Random();
        int bound = cruiseReader.getInfo().size();
        int infoIndex = rand.nextInt(bound);
        CruiseInfo theInfo = cruiseReader.getInfo().get(infoIndex);

        StaffBuilder staffBuilder = new StaffBuilder();

        ShipReader shipReader = new ShipReader();

        PortReader portReader = new PortReader();

        Scanner input = new Scanner(System.in);

        System.out.println("Do you like to generate "
                + "random passenger information? (Y/N)");
        PassCreator passCreator = new PassCreator(input.next());

        Cruise theCruise = new Cruise(theInfo, portReader.getPort(),
                shipReader.getShip(), passCreator.getPassenger(),
                staffBuilder.getStaff());

        new RevAnalysis(theCruise);
        new StaffInfo(staffBuilder.getStaff());
        StaffCost staffCost = new StaffCost(staffBuilder.getStaff());
        staffCost.showStaffCost();
        DockingCost dockingCost = new DockingCost(theCruise);
        dockingCost.showDockingCost();
        PassSort passSort = new PassSort(theCruise);
        passSort.printSortedPass();
        PassEvaluation passEva = new PassEvaluation(theCruise);
        passEva.getEvaluation();
        ProfitCalculator cal = new ProfitCalculator(theCruise);
        cal.getProfit();
        new CruiseWriter("Cruise Detail.txt", theCruise);
        
    }
}
