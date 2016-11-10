/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaffPack;

/**
 *
 * @author Yixin1
 */
public class Cook extends Staff {

    protected Cook(String ID, String name, String DOB, String nationality) {

        super(ID, name, DOB, nationality);
        setSalary(80000);
    }
}
