
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yixin1
 */
public class Separator {
    public static void main(String[] args){
    System.out.println("Enter five digit integer:");
    Scanner input = new Scanner(System.in);
    int data = input.nextInt();
    System.out.printf("Digits in %d is:\n",data);
    int d[] = new int[5];
    for(int i=0; i<5; i++){
        d[i] = data % 10;
        data = data/10;
    }
    System.out.printf("%d   %d   %d   %d   %d.\n",d[4],d[3],d[2],d[1],d[0]);
    }
}
