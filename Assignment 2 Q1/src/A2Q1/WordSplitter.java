/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2Q1;

/**
 *
 * @author Yixin1
 */
public class WordSplitter {

    static char letter[] = new char[5];

    public static char[] wordSplitter(String originalWord) {
        //Split the word
        int i;
        for (i = 0; i < 5; i++) {
            letter[i] = originalWord.charAt(i);
        }
        return letter;
    }

}
