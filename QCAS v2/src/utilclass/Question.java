/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

/**
 *
 * @author mica
 */
public class Question {
    public static final String[] TYPENAME = new String[]{"Multiple Choice","Single Choice", "True Or False","Fill In Blank"};
    public final static String[] DIFFICULTY = new String[]{"easy","Mediem","Hard","Mixed"};
    public int questionType;//0-multipleAnswers 1-mulipleChoice 2-trueOrFalse 3-essayQuestion
    public int questionID;//id of question in database\
    public int questionDifficult;
    public String correctAnswer;
    public String content = "";
    public String[] choices = new String[]{"1","2","3","4"};

    public Question(int questionType, int questionID, int questionDifficult, String content, String[] choices, String correctAnswer) {
        this.questionType = questionType;
        this.questionDifficult = questionDifficult;
        this.questionID = questionID;
        this.content = content;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
        String s ="(" + TYPENAME[questionType] + ")" + "\n\n" + content + "\n\n";
        switch(questionType){
            case 0:case 1:
                for (int i = 0; i < choices.length; i++) {
					s += choices[i] + "\n";
				}
                break;
            case 2:case 3:
                break;
            default:
                break;
        }
        return s;
    }
}