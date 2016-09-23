/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A3Q2;

import java.util.Random;

/**
 *
 * @author Yixin1
 */
public class Fighter {

    String name;
    double accuracy;
    boolean alive = true;

    public void shootAtTarget(Fighter target) {
        Random rand = new Random();
        int i = rand.nextInt(6) + 1;
        //When the random number divided by 6 is within the accuracy, 
        //then the shot is on target 
        if (i / 6.0 <= this.accuracy) {
            target.alive = false;
        }
    }

    public Fighter(String name, double accuracy) {
        this.name = name;
        this.accuracy = accuracy;
    }
}
