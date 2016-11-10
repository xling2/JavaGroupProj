/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPack;

import java.util.ArrayList;

/**
 *
 * @author Yixin1
 */
public class StaffCost {

    private int cost = 0;

    public StaffCost(ArrayList staff) {

        for (int i = 0; i < staff.size()-1; i++) {
            ArrayList job = (ArrayList) staff.get(i);
            for (int j = 0; j < job.size(); j++) {
                cost += ((Staff) (job.get(i))).getSalary();
            }
        }
        cost += ((Staff) staff.get(staff.size() - 1)).getSalary();
    }

    public int getStaffCost() {
        return cost;
    }

    public void showStaffCost() {
        System.out.println("--------------Staff Cost--------------");
        System.out.println("Total cost of sailors is: " + cost + "\n");

    }
}
