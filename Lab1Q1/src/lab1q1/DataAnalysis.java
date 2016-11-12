/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1q1;

/**
 *
 * @author Yixin1
 */
import java.util.Scanner;
public class DataAnalysis {
    public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    int d1,d2,d3,sum,average,product,smallest,largest;
    System.out.print("Please Enter 3 integers:");
    d1 = input.nextInt();
    d2 = input.nextInt();
    d3 = input.nextInt();
    
    sum = d1 + d2 + d3;
    average = sum / 3;
    product = d1 * d2 * d3;
    
    smallest = d1; 
    largest = d1;
    if (d1 < d2){
        largest = d2;
        if(largest < d3){
            largest = d3;
        }
    }
    if (d1 > d2){
        smallest = d2;
        if (smallest < d3){
            smallest = d3;
        }
    }
    System.out.printf("The sum of the three integers is: %d\nThe average of the three integers is: %d\nThe product of the three integers is: %d\nThe smallest of the three integers is: %d\nThe largest of the three integers is: %d",sum,average,product,smallest,largest);

    /**
     * @param args the command line arguments
     */
    }
    
}
