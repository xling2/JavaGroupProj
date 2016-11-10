/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortPack;

import CruisePack.Cruise;
import ShipPack.Ship;

/**
 *
 * @author Yixin1
 */
public class DockingCost {

    private int cost = 0;

    public DockingCost(Cruise theCruise) {
        cost += (theCruise.getPOC().size() + 1) * 
                getDockingFee(theCruise.getShip());
    }

    private int getDockingFee(Ship theShip) {
        if (theShip.getWeight() < 30) {
            return 20000;
        } else if (theShip.getWeight() < 50) {
            return 50000;
        } else {
            return 100000;
        }
    }

    public int getDockingCost() {
        return cost;
    }

    public void showDockingCost() {
        System.out.println("--------------Docking Cost--------------");
        System.out.println("Total cost of docking is: " + cost + "\n");
    }
}
