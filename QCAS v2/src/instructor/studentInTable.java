/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import javafx.beans.property.*;
import utilclass.QuizOfStudent;

/**
 *
 * @author Yixin1
 */
public class studentInTable {

    private SimpleStringProperty andrewID;
    
    // create studentInTable object from student's ID to store data for table view
    public studentInTable(String andrewID){
        this.andrewID = new SimpleStringProperty(andrewID);
    }
    
    public String getAndrewID(){
        return andrewID.get();
    }
    
    public void setAndrewID(String aID){
        andrewID.set(aID);
    }
    
    public Property andrewIDProperty(){
        return andrewID;
    }
}
