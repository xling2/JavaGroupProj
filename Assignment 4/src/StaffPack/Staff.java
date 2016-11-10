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
public abstract class Staff {

    private String sailorID;
    private String sailorName;
    private String DOB;
    private int salary;
    private String nationality;

    protected Staff(String ID, String name, String DOB, String nationality) {

        sailorID = ID;
        sailorName = name;
        this.DOB = DOB;
        this.nationality = nationality;
        setSalary(50000);

    }

    protected void setSalary(int salary) {

        this.salary = salary;

    }

    @Override
    public String toString() {

        return "\n" + "Sailor ID: " + sailorID + "\n"
                + "Name: " + sailorName + "\n"
                + "DOB: " + DOB + "\n"
                + "Nationality: " + nationality + "\n"
                + "Salary: " + salary + "\n";
                
    }

    protected String getName() {

        return sailorName;
    }
    
    protected int getSalary(){
        return salary;
    }
}
