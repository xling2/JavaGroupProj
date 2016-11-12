/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q1;

import java.util.Random;

/**
 *
 * @author Yixin1
 */
public class CardGrid {

    //Create a 4x4 grid
    private String[][] cards = new String[4][4];
    private String[] cardCatalog = {"A", "A", "Q", "Q", "K", "K", "J", "J",
        "2", "2", "5", "5", "6", "6", "9", "9"};
    Random rand = new Random();

    public CardGrid() {
        // select == i indicating cardCatalog[i] will be put in the grid
        int select = rand.nextInt(16);;
        // selectFlag[i] indicates whether cardCatalog[i] is selected
        boolean[] selectFlag = new boolean[16];
        // Set selectFlag[] be all false by default.
        for (int i = 0; i < 16; i++) {
            selectFlag[i] = false;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // If selectFlag[i] indicates cardCatalog[i] has been already 
                // selected, then generate another random number
                while (selectFlag[select]) {
                    select = rand.nextInt(16);
                }
                cards[i][j] = cardCatalog[select];
                // Set selectFlag to be true
                selectFlag[select] = true;
                // Generate another random number
                select = rand.nextInt(16);
            }
        }
    }

    public String getCard(int i, int j) {
        return cards[i][j];
    }

    // show() prints out the whole grid 
    public void show() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(this.getCard(i, j) + "\t");
            }
            System.out.print("\n");
        }
    }
}
