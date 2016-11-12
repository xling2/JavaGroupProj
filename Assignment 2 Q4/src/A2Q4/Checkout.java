package A2Q4;

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
public class Checkout {

    public static void main(String[] args) {
        FreshBox theFreshBox = new FreshBox();
        String[] theCatalog;
        theCatalog = theFreshBox.theTray[0].getNameCatalog();

        Scanner input = new Scanner(System.in);

        /*whichToDitch indicates which tray in the FreshBox 
        customer doesn't want*/
        int whichToDitch;
        /*whichToSelect indicates which tray in the Catalog 
        customer wants*/
        int whichToSelect;

        System.out.println("This is your Fresh Box!");
        System.out.println(theFreshBox.toString());
        System.out.println("If you want to change one of the trays\n"
                + "Please select which one to change");
        System.out.println("Or press 0 to check out\n");
        System.out.print("Please input: ");
        whichToDitch = input.nextInt();

        while (whichToDitch != 0) {
            System.out.print("\n======================\n");
            for (int i = 0; i < 5; i++) {
                System.out.println((i + 1) + ". " + theCatalog[i]);
            }
            System.out.print("\nChoose which one to change for: ");
            whichToSelect = input.nextInt();
            //Change the old one with the new one
            theFreshBox.theTray[whichToDitch - 1] = new Tray(whichToSelect - 1);

            System.out.print("\n======================\n");
            System.out.println("Here is your new Fresh Box!");
            System.out.println(theFreshBox.toString());

            System.out.println("If you still want to change one of the trays\n"
                    + "Please select which one to change");
            System.out.println("Or press 0 to check out\n");
            System.out.print("Please input: ");
            whichToDitch = input.nextInt();
        }
        System.out.print("\n======================\n");
        System.out.println("Thank you for shopping.\n");
        
        //Generate the recipe
        theFreshBox.theRecipe = new Recipe(theFreshBox);

    }
}
