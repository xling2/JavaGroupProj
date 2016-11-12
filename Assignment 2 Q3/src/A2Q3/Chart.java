package A2Q3;

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
public class Chart {

    public static void main(String[] args) {
        System.out.print("How many times do you wanna roll two dice?: ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        Dice dice = new Dice();
        int i, j;

        //total[] store the sum of two dice every time they are rolled
        int total[] = new int[n];
        for (i = 0; i < n; i++) {
            total[i] = dice.Throw();
            System.out.print("Try " + (i + 1) + "\t");
            dice.Show();
        }
        //
        int result[] = new int[n];

        //unrepeated counts how many sums are exclusive in total[]
        int unrepeated = -1;

        //repeat[i] counts how many times the total=i appears
        int repeat[] = new int[13];
        for (i = 0; i < n; i++) {
            if (total[i] > 0) {
                repeat[total[i]] = 1;
                for (j = i + 1; j < n; j++) {
                    //When meets the duplicated result, make it become -1
                    if (total[i] == total[j]) {
                        total[j] = -1;
                        repeat[total[i]]++;
                    }
                }
            } else {
                //Go to the next sum
                continue;
            }
            unrepeated++;
            //Store the exclusive sum into result[]
            result[unrepeated] = total[i];
        }

        //Sort the result[] from small to big
        int temp;
        for (i = 0; i <= unrepeated; i++) {
            for (j = i + 1; j <= unrepeated; j++) {
                if (result[i] > result[j]) {
                    temp = result[i];
                    result[i] = result[j];
                    result[j] = temp;
                }
            }
        }

        System.out.println("******* Here is the chart of frequency ********\n");

        //Prints the scale        
        System.out.printf("%s\t%26s%25s%51s\n", "Total", "25%", "50%", "100%");
        System.out.printf("\t%25s%25s%50s\n", "|", "|", "|");
        for (i = 0; i <= unrepeated; i++) {
            System.out.print(result[i] + "\t");

            //The maximum width of the chart is 100 "*", which represents 100%
            int starNumber = repeat[result[i]] * 100 / n;
            while (starNumber != 0) {
                System.out.print("*");
                starNumber--;
            }
            System.out.print("\n");
        }

    }

}
