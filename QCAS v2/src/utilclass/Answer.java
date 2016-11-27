/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.util.ArrayList;

/**
 *
 * @author mica
 */
public class Answer {
	static final public String[] CHOICENAME = new String[] { "A", "B", "C", "D" };
	public int questionID;
	public int questionType = 100;
	public boolean isAnswer = false;
	public int singleChoice;
	public ArrayList<Integer> multipleChoices = new ArrayList<>();
	public boolean trueOrFalse;
	public String blank = "";

	public Answer() {
		// TODO Auto-generated constructor stub
	}

	public Answer(int quetionID, int quetionType, int singleChoice) {
		this.questionID = quetionID;
		this.questionType = quetionType;
		this.singleChoice = singleChoice;
	}

	public Answer(int quetionID, int quetionType, boolean trueOrFalse) {
		this.questionID = quetionID;
		this.questionType = quetionType;
		this.trueOrFalse = trueOrFalse;
	}

	public Answer(int quetionID, int quetionType, String blank) {
		this.questionID = quetionID;
		this.questionType = quetionType;
		this.blank = blank;
	}

	public Answer(int quetionID, int quetionType, ArrayList<Integer> multipleChoices) {
		this.questionID = quetionID;
		this.questionType = quetionType;
		this.multipleChoices = multipleChoices;
	}

	public Answer(int quetionID, int quetionType, ArrayList<Integer> multipleChoices, String blank, int singleChoice,
			boolean trueOrFalse) {
		this.questionID = quetionID;
		this.questionType = quetionType;
		this.multipleChoices = multipleChoices;
		this.blank = blank;
		this.singleChoice = singleChoice;
		this.trueOrFalse = trueOrFalse;
	}

	@Override
	public String toString() {
		String s = "";
        if(isAnswer){
            switch (questionType) {
            case 0:
                for (Integer multipleChoice : multipleChoices) {
                    s += CHOICENAME[multipleChoice - 1];
                }
                break;
            case 1:
                s += CHOICENAME[singleChoice - 1];
                break;
            case 2:
                s += trueOrFalse;
                break;
            case 3:
                s += blank;
                break;
            default:
                break;
            }
        }
        return s;
    }

}
