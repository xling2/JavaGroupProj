/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import java.util.*;

/**
 *
 * @author Yixin1
 */
public class PassManualBuilder {

    private ArrayList<Passenger> pass;

    protected PassManualBuilder() {
        pass = new ArrayList<Passenger>();
        Scanner input = new Scanner(System.in);
        do {
            System.out.print("Please input passenger's  name: ");
            String name = input.next();

            System.out.print("Please input passenger's  ID: ");
            String ID = input.next();

            System.out.print("Please input passenger's  Home Address: ");
            String add = input.next();

            System.out.print("Please input passenger's  nationality: ");
            String nationality = input.next();

            System.out.print("Please input passenger's  DOB: ");
            String DOB = input.next();

            System.out.print("Please input passenger's  Entrance Fee: ");
            int entrFee = input.nextInt();

            System.out.print("Please input passenger's  Spend On Cruise: ");
            int onCruiseSpend = input.nextInt();

            Passenger pa = new Passenger(ID, name, add, nationality,
                    DOB, entrFee, onCruiseSpend);
            
            pass.add(pa);

            System.out.println("Input * to end, "
                    + "press any other key to continue.");
        } while (input.next().equals("*") == false);

    }
    
    protected ArrayList<Passenger> getManPassenger(){
        return pass;
    }
}
