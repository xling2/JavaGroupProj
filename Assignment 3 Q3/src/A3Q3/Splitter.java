/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q3;

import java.util.StringTokenizer;

/**
 *
 * @author Yixin1
 */
public class Splitter {
    
    // Use a long type to store the integer part of the amount
    // Use a String type to store the decimal part of the amount
    long intPart;
    String deciPart;

    public Splitter(String amount) {

        // twoParts[] to store the parts before and after dot
        String[] twoParts = new String[2];
        int i = 0;

        StringTokenizer amountTokenizer
                = new StringTokenizer(amount, ".");
        // Use nextToken() to store two parts seperately
        while (amountTokenizer.hasMoreTokens()) {
            twoParts[i] = amountTokenizer.nextToken();
            i++;
        }

        // Convert String of twoParts[0] to long integer
        intPart = Long.parseLong(twoParts[0]);
        //If the decimal part is missing, make it "00" by default
        if (twoParts[1] == null) {
            deciPart = "00";
        }// If only tenths is input, make up the percentile 
        else if(twoParts[1].toCharArray().length == 1){
            char[] charPartTwo = {twoParts[1].toCharArray()[0], '0'};
            deciPart = new String(charPartTwo);
        }// If decimal part is complete, then store it in deciPart
        else{
            deciPart = twoParts[1];
        }
        
    }
}
