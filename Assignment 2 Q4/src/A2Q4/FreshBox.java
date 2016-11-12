package A2Q4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yixin1
 */
public class FreshBox {

    Tray theTray[] = new Tray[4];
    Recipe theRecipe;

    public FreshBox() {
        int i;
        for (i = 0; i < 4; i++) {
            theTray[i] = new Tray();
        }
    }

    //Return the complete contents of a FreshBox
    @Override 
    public String toString() {
        int i;
        String selected = new String("");
        for (i = 0; i < 4; i++) {
            selected = selected + (i + 1) + ". " + theTray[i].getName() + "\t" + "Pick Date: "
                    + theTray[i].getPickDate() + "\n";
        }
        return selected;
    }

}
