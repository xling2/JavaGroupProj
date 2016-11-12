/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q1;

/**
 *
 * @author Yixin1
 */
public class Controller {

    // rightFlag[][] indicating whether the user's answer is correct, set all 
    // false by default
    private boolean[][] rightFlag = {
        {false, false, false, false},
        {false, false, false, false},
        {false, false, false, false},
        {false, false, false, false}
    };
    // rightTimes indicating how many times the user get a correct answer
    // There are 8 pairs of cards, so making 7 correct pair will win the game
    private int rightTimes = 0;
    // winFlag indicating whether the user win or not
    public static boolean winFlag = false;

    public void isPair(CardGrid theCardGrid, int i, int j, int s, int t) {
        // Compare the string every card got
        // and make sure they are not the same card
        if (theCardGrid.getCard(i, j).equals(theCardGrid.getCard(s, t)) 
                && (i != s || j != t)) {
            // Set both cards' rightFlag  true
            rightFlag[i][j] = true;
            rightFlag[s][t] = true;
            // Count rightTimes plus 1
            rightTimes++;
        } else {
            rightFlag[i][j] = false;
            rightFlag[i][j] = false;
        }
    }

    public void showResult(CardGrid theCardGrid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // If one card's rightFlag is true
                // which means it is correctly paired
                // then show its string value, else show the $ sign
                if (rightFlag[i][j] == true) {
                    System.out.print(theCardGrid.getCard(i, j) + "\t");
                } else {
                    System.out.print("$" + "\t");
                }
            }
            System.out.print("\n");
        }
        // If guess correctly for 7 times, then win
        if (rightTimes == 7) {
            winFlag = true;
        }
    }
}
