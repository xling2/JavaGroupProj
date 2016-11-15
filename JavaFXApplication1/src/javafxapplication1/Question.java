/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author mica
 */
public class Question {
    public static String[] FXMLForQuestion = new String[]{"multiple_answers.fxml","multiple_choice.fxml","t_f.fxml","fill_in_blank.fxml"};
    public int questionType = 0;//0-multipleAnswers 1-mulipleChoice 2-trueOrFalse 3-essayQuestion
    public int questionNumber = 1;
    public String title = "";
    public String[] choices = new String[]{"1","2","3","4"};

    public void Question() {
    }
    Question(int questionType, int questionNumber, String title, String[] choices) {
        this.questionNumber = questionNumber;
        this.questionType = questionType;
        this.title = title;
        this.choices = choices;
    }
}
