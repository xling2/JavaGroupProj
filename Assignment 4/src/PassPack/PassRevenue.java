/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import CruisePack.Cruise;

/**
 *
 * @author Yixin1
 */
public class PassRevenue {

    private int joinRevenue = 0;
    private int msocRevenue = 0;

    public PassRevenue(Cruise theCruise) {
        for (Passenger p : theCruise.getPass()) {
            joinRevenue += p.getJoinFee();
            msocRevenue += p.getMSOC();
        }
    }
    
    public int getJoinRevenue(){
        return joinRevenue;
    }
    
    public int getMSOCRevenue(){
        return msocRevenue;
    }
}
