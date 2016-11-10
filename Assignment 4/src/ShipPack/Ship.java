/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShipPack;

/**
 *
 * @author Yixin1
 */
public class Ship {

    private String shipNumber;
    private String shipName;
    private int shipWeight;
    private int yearBuilt;
    private int passCapacity;

    public Ship(String number, String name, int weight, int yearBuilt, int cap) {
        shipNumber = number;
        shipName = name;
        shipWeight = weight;
        this.yearBuilt = yearBuilt;
        passCapacity = cap;
    }

    public int getWeight() {
        return shipWeight;
    }

    @Override
    public String toString() {
        return "Ship Number " + shipNumber + "\n"
                + "Name: " + shipName + "\n"
                + "Weight: " + shipWeight + "\n"
                + "Year Built" + yearBuilt + "\n"
                + "Paseenger Capacity" + passCapacity + "\n";
    }
}
