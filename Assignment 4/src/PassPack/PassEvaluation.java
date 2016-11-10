/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassPack;

import CruisePack.Cruise;

/**
 *
 * @author Yixin1
 */
public class PassEvaluation {

    private double meanScore[] = new double[5];

    public PassEvaluation(Cruise theCruise) {
        int totalScore[] = new int[5];
        for (Passenger p : theCruise.getPass()) {
            for (int i = 0; i < 5; i++) {
                totalScore[i] += p.getEva()[i];
            }
        }
        for (int i = 0; i < 5; i++) {
            meanScore[i] += (totalScore[i] + 0.0) / (theCruise.getPass().size());
        }
    }

    public void getEvaluation() {
        int totalMeanScore = 0;
        System.out.println("--------------Passenger Evaluation--------------");
        for (int i = 0; i < 5; i++) {
            System.out.printf("The mean score of Question " + "%d"
                    + " is: " + "%.2f" + " out of 5.\n", (i+1), meanScore[i]);
            totalMeanScore += meanScore[i];
        }
        System.out.println("Total Score is: " + totalMeanScore / 5.0 + " out of 5.");
    }
}
