/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class Receipt {

    public static void main(String[] args) {
        System.out.printf("Enter Your Name:");
        Scanner input = new Scanner(System.in);
        //Use enter as the new separator instead of space to get the whole name
        input.useDelimiter("\n");
        String cName = input.next();

        //Create the receipt number
        Random rand = new Random();
        int rNumber = rand.nextInt(100000);

        //Obtain current time
        LocalDateTime dateTime = LocalDateTime.now();
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();

        //Input item information
        String itemName[] = new String[3];
        int itemQuantity[] = new int[3];
        double itemPrice[] = new double[3];
        double itemTotal[] = new double[3];
        double subTotal = 0.0;
        int i;
        //Calculate subtotal and total price
        for (i = 0; i < 3; i++) {
            System.out.printf("Input name of item %d:", i + 1);
            itemName[i] = input.next();
            System.out.printf("Input quantity of item %d:", i + 1);
            itemQuantity[i] = input.nextInt();
            System.out.printf("Input price of item %d:", i + 1);
            itemPrice[i] = input.nextDouble();
            itemTotal[i] = itemQuantity[i] * itemPrice[i];
            subTotal = subTotal + itemTotal[i];
        }
        double tax = subTotal * 0.0725;
        double total = subTotal + tax;
        //Print out name and receipt number
        System.out.println("\n\nInvoice for " + cName);
        System.out.printf("No:%05d", rNumber);

        //Easy to calculate that 37 spaces are needed to align the date display
        //with the edge of the receipt
        System.out.printf("%37s", "Date:");

        //To display the "0" in front of day and month
        System.out.printf("%02d-%02d-%d\n", day, month, year);

        //30 characters for the name
        System.out.printf("%-30s", "Item");

        //10 characters for the quantity
        System.out.printf("%-10s", "Quantity");

        //10 characters for the price
        System.out.printf("%-10s", "Price");

        //10 characters for the total
        System.out.printf("%-10s\n", "Total");

        //Print out the details
        for (i = 0; i < 3; i++) {
            System.out.printf("%-30s", itemName[i]);
            System.out.printf("%-10d", itemQuantity[i]);
            System.out.printf("%-10.2f", itemPrice[i]);
            System.out.printf("%-10.2f\n", itemTotal[i]);
        }

        //Two decimal output
        System.out.printf("%-50s%.2f\n", "Subtotal", subTotal);
        System.out.printf("%-50s%.2f\n", "7.25% sales tax", tax);
        System.out.printf("%-50s%.2f\n", "Total", total);

    }
}
