/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortPack;

import ShipPack.Ship;

/**
 *
 * @author Yixin1
 */
public class Port {

    String name;
    String country;
    int population;
    boolean passNeeded;

    Port(String name, String country, int pop, boolean passNeeded) {
        this.name = name;
        this.country = country;
        population = pop;
        this.passNeeded = passNeeded;
    }

    public String toString() {
        return "Port Name:  " + name + "\n"
                + "Country: " + country + "\n"
                + "Population:  " + population + "\n"
                + "Passport needed when embark?  " + passNeeded + "\n";
    }

}
