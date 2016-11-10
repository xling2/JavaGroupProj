/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q1;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class QuestionPresenter {

    private Random rand = new Random();
    public static int nQuestion = DBReader.getSize();
    // asked array to store whether one question is already asked or not
    public static boolean[] asked = new boolean[nQuestion + 1];
    // correct array to store user's answer to three questions each time 
    // is correct or not
    private boolean[] correct = new boolean[3];
    private Scanner input = new Scanner(System.in);
    private String[] answer = new String[3];

    public QuestionPresenter() {

        // Initialize the asked array
        for (boolean b : asked) {
            b = false;
        }

        // The process of each time with 3 questions
        for (int i = 0; i < 3; i++) {
            
            // Genereate a random number as the question index
            int row = rand.nextInt(nQuestion) + 1;
            while (asked[row] == true) {
                row = rand.nextInt(nQuestion) + 1;
            }
            asked[row] = true;
            // Create a new DBReader instance
            DBReader theQuestion = new DBReader(row);
            // Print the question
            System.out.println(theQuestion.toString());

            System.out.print("Please input your answer: ");
            String s = input.next();
            // Get the correct answer
            answer[i] = theQuestion.getAnswer();
            // Compare the answers
            if (s.equals(answer[i])) {
                correct[i] = true;
            }
        }
        System.out.println("\nSolutions: ");
        for (int i = 0; i < 3; i++) {
            
            // Print correct or wrong instead
            System.out.printf("Answer to Question %d is %s, "
                    + "your answer is %s\n\n", (i + 1), answer[i],
                    (correct[i]) ? "correct" : "wrong");
        }

    }
}
