/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

import PassPack.PassRevenue;
import PortPack.DockingCost;
import StaffPack.StaffCost;

/**
 *
 * @author Yixin1
 */
public class ProfitCalculator {
    private int profit;
    protected ProfitCalculator(Cruise theCruise){
        int dockingCost = (new DockingCost(theCruise)).getDockingCost();
        int staffCost = (new StaffCost(theCruise.getStaff())).getStaffCost();
        int joinRevenue = (new PassRevenue(theCruise).getJoinRevenue());
        int msocRevenue = (new PassRevenue(theCruise).getMSOCRevenue());
        
        profit = msocRevenue + joinRevenue - dockingCost - staffCost;
    }
    protected void getProfit(){
        System.out.println("--------------Cruise Profit--------------");
        System.out.println("The total profit of this cruise is: " + profit);
    }
}
