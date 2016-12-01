/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.util.ArrayList;

/**
 *
 * @author lingxingyu
 */
public class StringToInt {
    // convert the answer of MA from String to int
    public ArrayList<Integer> toIntMultipleAnswer(String answer) {
        String[] list = answer.split("");
        ArrayList<Integer> multipleChoices = new ArrayList();
        for (String a : list) {
            if (a.equals("A")) {
                multipleChoices.add(1);
            } else if (a.equals("B")) {
                multipleChoices.add(2);
            } else if (a.equals("C")) {
                multipleChoices.add(3);
            } else {
                multipleChoices.add(4);
            }
        }
        return multipleChoices;
    }
    // convert the answer of Single Choice from String to int
    public int toIntSingleAnswer(String answer) {
        if (answer.equals("A")) {
            return 1;
        }
        if (answer.equals("B")) {
            return 2;
        }
        if (answer.equals("C")) {
            return 3;
        } else {
            return 4;
        }
    }
    // convert the question type from String to int
    public int toIntType(String questionType) {
        int type = 0;

        if (questionType.equals("MA")) {
            type = 0;
        } else if (questionType.equals("MC")) {
            type = 1;
        } else if (questionType.equals("TF")) {
            type = 2;
        } else {
            type = 3;
        }

        return type;
    }
     // convert the difficulty level from String to int
    public int toIntDiff(String questionDiff) {
        int diff = 0;

        if (questionDiff.equals("E")) {
            diff = 0;
        } else if (questionDiff.equals("M")) {
            diff = 1;
        } else if (questionDiff.equals("H")) {
            diff = 2;
        } else {
            diff = 3;
        }

        return diff;
    }
}
