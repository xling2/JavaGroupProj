package A2Q2;

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
public class Iterative {

    static int thisIsIterative(int n) {
        if (n < 0) {
            return -10;
        }
        if (n == 0) {
            return 2;
        }
        if (n == 1) {
            return 5;
        }
        int i;
        int result[] = new int[n + 1];
        
        //Initialize the first three values
        result[0] = 2;
        result[1] = 5;
        for (i = 2; i <= n; i++) {
            result[i] = result[i - 1] + 3 * result[i - 2] + 2 * i;
        }
        return result[n];
    }

}
