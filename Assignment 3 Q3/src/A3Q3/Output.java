/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q3;
import java.util.Scanner;
/**
 *
 * @author Yixin1
 */
public class Output {
    public static void main(String[] args){
        System.out.println("Please enter a dollar amount:");
        Scanner input = new Scanner(System.in);
        String amount = input.next();
        // Split the amount with the dot as token
        Splitter theSplitter = new Splitter(amount);
        System.out.print("Dollar amount in words: ");
        // Print the integer part first
        new NameAllocator(theSplitter.intPart);
        // Then print the decimal part
        System.out.println("and " + theSplitter.deciPart + "/100");
    }
}
