/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A2Q2;

import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class Output {

    public static void main(String[] args) {
        System.out.print("Please input integer n: ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        System.out.println("The result is: " + Iterative.thisIsIterative(n));

    }
}
