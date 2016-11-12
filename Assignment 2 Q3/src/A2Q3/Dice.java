package A2Q3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;

/**
 *
 * @author Yixin1
 */
public class Dice {

    int diceOne;
    int diceTwo;
    int total;

    public int Throw() {
        Random rand = new Random();
        diceOne = rand.nextInt(6) + 1;
        diceTwo = rand.nextInt(6) + 1;
        total = diceOne + diceTwo;
        return total;
    }

    //Show the result of rolling dice
    public void Show() {
        System.out.print("First dice is: ");

        System.out.print(" " + diceOne + "\t");
        System.out.print("Second dice is: ");

        System.out.print(" " + diceTwo + "\t\tin total: ");
        System.out.printf("%d\n\n", diceOne + diceTwo);
    }

}
