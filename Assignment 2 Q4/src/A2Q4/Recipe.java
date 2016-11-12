package A2Q4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yixin1
 */
public class Recipe {

    //repeatFlag[] to indicate whether one tray is dupicated
    private int[] repeatFlag = {0, 0, 0, 0};

    public Recipe(FreshBox theFreshBox) {
        for (int i = 0; i < 4; i++) {
            if (repeatFlag[i] != -1) {
                for (int j = i + 1; j < 4; j++) {
                    if (theFreshBox.theTray[i].getName()
                            == theFreshBox.theTray[j].getName()) {
                        //Make the original one's repeatFlag 1                        
                        //Make the duplicated one's repeatFlag -1
                        repeatFlag[i] = 1;
                        repeatFlag[j] = -1;
                    }
                }
            } else {
                //Looking for the next tray
                continue;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (repeatFlag[i] == 1) {
                String theName = theFreshBox.theTray[i].getName();
                //Output the recipe if there are duplicated trays
                switch (theName) {
                    case "Potato": {
                        System.out.println("\"How to make mashed potato\" "
                                + "recipe added to your FreshBox.");
                        break;
                    }

                    case "Apple": {
                        System.out.println("\"How to make apple pie\" "
                                + "recipe added to your FreshBox.");
                        break;
                    }

                    case "Banana": {
                        System.out.println("\"How to make banana milkshake\" "
                                + "recipe added to your FreshBox.");
                        break;
                    }

                    case "Cauliflower": {
                        System.out.println("\"How to make cauliflower salad\" "
                                + "recipe added to your FreshBox.");
                        break;
                    }

                    case "Capsicum": {
                        System.out.println("\"How to make capsicum curry\" "
                                + "recipe added to your FreshBox.");
                        break;
                    }

                }
            }
        }

    }

}
