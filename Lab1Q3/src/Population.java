/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yixin1
 */
public class Population {
    public static void main(String[] args){
        final double CURRENT = 312032486.0;
        final int BIRTHSECOND = 7;
        final int DEATHSECOND = 13;
        final int IMMIGRANTSECOND = 45;
        final double SECONDSOFAYEAR = 365.0*24*3600;
        double popInFuture[] = new double[6];
        int numOfYear = 0;
        popInFuture[numOfYear] = CURRENT;
        while(numOfYear < 5){
            popInFuture[numOfYear + 1] = popInFuture[numOfYear] + SECONDSOFAYEAR/BIRTHSECOND + SECONDSOFAYEAR/IMMIGRANTSECOND - SECONDSOFAYEAR/DEATHSECOND;
            numOfYear++;
                    
            System.out.printf("%d year(s) after, the population of the United States will be: %f%n",numOfYear,popInFuture[numOfYear] );
        }
    }
}
