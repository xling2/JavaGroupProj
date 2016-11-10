/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CruisePack;

/**
 *
 * @author Yixin1
 */
public class CruiseInfo {

    private String serialNumber;
    private String sailingDate;
    private String returnDate;

    protected CruiseInfo(String ID, String deDate, String reDate) {
        serialNumber = ID;
        sailingDate = deDate;
        returnDate = reDate;
    }
    
    public String getSerial(){
        return serialNumber;
    }
    
    public String getDepart(){
        return sailingDate;
    }
    
    public String getReturn(){
        return returnDate;
    }
    
    @Override
    public String toString(){
        return "Serial No.: " + serialNumber + "\n"
                + "Sailing Date: " + sailingDate + "\n"
                + "Return Date: " + returnDate + "\n";
    }
}
