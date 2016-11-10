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
public class StaffInfo {

    public StaffInfo(ArrayList staff) {
        System.out.println("--------------Staff Information--------------");
        System.out.println("* Staff List *");

        for (int i = 0; i < staff.size() - 1; i++) {
            ArrayList job = (ArrayList) staff.get(i);
            for (int j = 0; j < job.size(); j++) {
                System.out.println(job.get(j).toString());
            }
        }
        System.out.println(staff.get(staff.size() - 1).toString());
    }
}
