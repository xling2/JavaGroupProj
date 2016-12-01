/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

/**
 *
 * @author lingxingyu
 */
public class IntToString {
     
    // convert difficulty from E M H Mixed to 1 2 3 4
    public String toStringDiff(int questionDiff) {
        String diff = null;

        if (questionDiff == 0) {
            diff = "E";
        } else if (questionDiff == 1) {
            diff = "M";
        } else if (questionDiff == 2) {
            diff = "H";
        } else {
            diff = "Mixed";
        }

        return diff;
    }
}
