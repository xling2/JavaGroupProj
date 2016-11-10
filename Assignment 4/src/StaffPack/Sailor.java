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
public class Sailor extends Staff {

    SeniorSailor supervisor = null;

    protected Sailor(String ID, String name, String DOB, String nationality) {
        super(ID, name, DOB, nationality);
    }

    @Override
    public String toString() {
        if (supervisor == null) {
            return super.toString();
        } else {
            return super.toString() + "Supervisor: " + 
                    supervisor.getName() + "\n";
        }
    }

    protected void setSupervisor(SeniorSailor ss) {
        supervisor = ss;
    }
}
