package A2Q1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class ThreeLetterWord {

    public static void main(String[] args) {
        String originalWord = IsFiveLetter.isFiveLetter();
        char character[] = new char[5];
        character = WordSplitter.wordSplitter(originalWord);
        System.out.println("Here are the three-letter words produced "
                + "from what you input:");
        int p, q, s;
        for (p = 0; p < 5; p++) {
            for (q = 0; q < 5; q++) {
                for (s = 0; s < 5; s++) {
                    if (p != q && p != s && q != s) {
                        System.out.printf("%c%c%c\n",
                                character[p], character[q], character[s]);
                    }
                }
            }
        }

    }

}
