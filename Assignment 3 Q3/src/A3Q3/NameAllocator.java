/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q3;


/**
 *
 * @author Yixin1
 */
public class NameAllocator {

    public NameAllocator(long intPart) {
        int i = 0;
        // section[i] stores the (3*i+1)-th to (3*i+3)-th digits of intPart, 
        // the whole section array can store up to 15 digits
        int section[] = new int[5];
        // intTemp is used to tell whether the whole long integer 
        // is all stored in the section[] array
        long intTemp = intPart;
        // The units are used for words when number are splitted in 
        // 3 digits a section from the lowest order
        String[] unit = {"thousand ", "million ", "billion ", "trillion "};
        // Record the number of each section
        do {
            section[i] = (int) (intTemp % 1000);
            intTemp = intTemp / 1000;
            i++;
        } while (intTemp != 0); // when intTemp == 0, 
                                //all the digits are seperated and stored
        int numberOfSection = --i;
        //Except for the lowest three digits, (the units are only used for 
        //thousand and above) output the number stored in each section and units
        for (i = numberOfSection; i > 0; i--) {
            asWord(section[i]);
            System.out.print(unit[i - 1]);
        }
        // Then output the lowest three digits
        asWord(section[0]);
    }
    
    // Method asWord used for convert integers under 1000 to words
    private void asWord(int i) {
        // Build the 0~19's names
        if (i <= 19) {
            switch (i) {
                case 0: {
                    break;
                }
                case 1: {
                    System.out.print("ONE ");
                    break;
                }
                case 2: {
                    System.out.print("TWO ");
                    break;
                }
                case 3: {
                    System.out.print("THREE ");
                    break;
                }
                case 4: {
                    System.out.print("FOUR ");
                    break;
                }
                case 5: {
                    System.out.print("FIVE ");
                    break;
                }
                case 6: {
                    System.out.print("SIX ");
                    break;
                }
                case 7: {
                    System.out.print("SEVEN ");
                    break;
                }
                case 8: {
                    System.out.print("EIGHT ");
                    break;
                }
                case 9: {
                    System.out.print("NINE ");
                    break;
                }
                case 10: {
                    System.out.print("TEN ");
                    break;
                }
                case 11: {
                    System.out.print("ELEVEN ");
                    break;
                }
                case 12: {
                    System.out.print("TWELVE ");
                    break;
                }
                case 13: {
                    System.out.print("THIRTEEN ");
                    break;
                }
                case 14: {
                    System.out.print("FOURTEEN ");
                    break;
                }
                case 15: {
                    System.out.print("FIFTEEN ");
                    break;
                }
                case 16: {
                    System.out.print("SIXTEEN ");
                    break;
                }
                case 17: {
                    System.out.print("SEVENTEEN ");
                    break;
                }
                case 18: {
                    System.out.print("EIGHTEEN ");
                    break;
                }
                case 19: {
                    System.out.print("NINETEEN ");
                    break;
                }
            }
        } else if (i < 100) {
            // Build the unit of ten's place
            switch (i / 10) {
                case 2: {
                    System.out.print("TWENTY ");
                    // Use the iteration method to output the lowest one digit
                    asWord(i % 10);
                    break;
                }
                case 3: {
                    System.out.print("THIRTY ");
                    asWord(i % 10);
                    break;
                }
                case 4: {
                    System.out.print("FORTY ");
                    asWord(i % 10);
                    break;
                }
                case 5: {
                    System.out.print("FIFTY ");
                    asWord(i % 10);
                    break;
                }
                case 6: {
                    System.out.print("SIXTY ");
                    asWord(i % 10);
                    break;
                }
                case 7: {
                    System.out.print("SEVENTY ");
                    asWord(i % 10);
                    break;
                }
                case 8: {
                    System.out.print("EIGHTY ");
                    asWord(i % 10);
                    break;
                }
                case 9: {
                    System.out.print("NINETY ");
                    asWord(i % 10);
                    break;
                }
            }
        } else {
            // Output the unit of hundred and its digit
            asWord(i / 100);
            System.out.print("hundred ");
            // Output the remaining lowest two digits by a iteration method
            asWord(i % 100);
        }
    }
}
