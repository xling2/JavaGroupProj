/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2Q1;

import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class IsFiveLetter {

    static int wordLength;
    static String originalWord;

    public static String isFiveLetter() {
        //Check if it is a five letter word
        do {
            System.out.print("Input the five-letter word: ");
            Scanner input = new Scanner(System.in);
            originalWord = input.next();
            wordLength = originalWord.length();
            if (wordLength != 5) {
                System.out.println("The word you input has wrong number of "
                        + "letters, please input a 5-letter word.");
            }
        } while (wordLength != 5);
        return originalWord;
    }

}
