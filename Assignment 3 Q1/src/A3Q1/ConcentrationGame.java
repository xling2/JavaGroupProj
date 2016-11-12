/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q1;

import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class ConcentrationGame {

    public static void main(String[] args) {
        CardGrid theCardGrid = new CardGrid();
        // At the beginning, show the grid
        theCardGrid.show();
        Controller theController = new Controller();
        int i;
        Scanner input = new Scanner(System.in);
        System.out.println("Remember the cards in 10s.");

        // Pause the program for 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print 1000 rows of asterisk to force the old board off the screen
        for (i = 0; i < 1000; i++) {
            System.out.println("*********************************************");
        }
        while (!Controller.winFlag) {
            // c1d1 means the first dimension of card 1, so on and so forth
            int c1d1, c1d2, c2d1, c2d2;
            // To read which cards user want to pair
            System.out.println("\nInput the first card you choose "
                    + "in row number and column number, seprated by space: ");
            c1d1 = input.nextInt();
            c1d2 = input.nextInt();
            System.out.println("\nInput the second card you choose "
                    + "in row number and column number, seprated by space: ");
            c2d1 = input.nextInt();
            c2d2 = input.nextInt();
            // To compare two selected cards and count correct times
            theController.isPair(theCardGrid, c1d1 - 1, c1d2 - 1,
                    c2d1 - 1, c2d2 - 1);
            // Then reveal any pair that user paired correctly
            theController.showResult(theCardGrid);
        }
        System.out.println("\nCongratulations! You win!");
        // After user winning, show the whole grid directly
        theCardGrid.show();

    }
}
