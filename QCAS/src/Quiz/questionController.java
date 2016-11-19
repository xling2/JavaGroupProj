/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quiz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import qcas.SceneSwitcher;

/**
 *
 * @author mica
 */
public class questionController implements Initializable {

    GoPage util;

    private String[] FXMLForQuestion
            = new String[]{
                "/multiple_answers.fxml",
                "/multiple_choice.fxml",
                "/t_f.fxml",
                "/fill_in_blank.fxml"
            };

    private int questionType = 0;
    // 0 - multipleAnswers 
    // 1 - mulipleChoice 
    // 2 - trueOrFalse 
    // 3 - fill in blanks
    
    private int questionCounts = 4;
    ////////////// Only for test, needs a pass-in value
    
    private int currentNumber = 1;

    @FXML
    private Button nextQuestion;
    @FXML
    private RadioButton choice1;
    @FXML
    private RadioButton choice2;
    @FXML
    private RadioButton choice3;
    @FXML
    private RadioButton choice4;
    @FXML
    private CheckBox choice5;
    @FXML
    private CheckBox choice6;
    @FXML
    private CheckBox choice7;
    @FXML
    private CheckBox choice8;
    @FXML
    private Slider numberSlide;
    @FXML
    private Label question;
    @FXML
    private Label questionNumberLabel;

    @FXML
    private Button preview;

    @FXML
    private void dragDone(ActionEvent event) {
        System.out.println((int) numberSlide.getValue());
        questionNumberLabel.setText((int) numberSlide.getValue() + "/" + util.questionCounts);
    }

    @FXML
    private void preview(ActionEvent event) {
        if (currentNumber == 1) {
        } else {
            currentNumber -= 1;
            util.goPage(FXMLForQuestion[util.quiz.get(currentNumber - 1).questionType], question);
        }
    }

    @FXML
    private void nextQuestion(ActionEvent event) throws IOException {
        preview.setVisible(true);
        if (currentNumber == util.questionCounts) {
            SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                    "/quiz_report.fxml")), nextQuestion.getScene());
        } else {
            currentNumber += 1;
            SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                    "/quiz_report.fxml")), nextQuestion.getScene());
            util.goPage(FXMLForQuestion[util.quiz.get(currentNumber - 1).questionType], question);
        }
    }

    @FXML
    private void goNumber(ActionEvent event) {
        currentNumber = (int) numberSlide.getValue();
        if (currentNumber == 1) {
            preview.setVisible(true);
        }
        util.goPage(Question.FXMLForQuestion[util.quiz.get(currentNumber - 1).questionType], question);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.util = GoPage.getGoPage();
        numberSlide.setMax(util.questionCounts);
        numberSlide.setValue(currentNumber);
        questionNumberLabel.setText(currentNumber + "/" + util.questionCounts);
        //System.out.println(currentNumber);
        //System.out.println(util.quiz.get(currentNumber-1).choices[0]);
        choice1.setText(util.quiz.get(currentNumber - 1).choices[0]);
        choice2.setText(util.quiz.get(currentNumber - 1).choices[1]);
        choice3.setText(util.quiz.get(currentNumber - 1).choices[2]);
        choice4.setText(util.quiz.get(currentNumber - 1).choices[3]);
        choice5.setText(util.quiz.get(currentNumber - 1).choices[0]);
        choice6.setText(util.quiz.get(currentNumber - 1).choices[1]);
        choice7.setText(util.quiz.get(currentNumber - 1).choices[2]);
        choice8.setText(util.quiz.get(currentNumber - 1).choices[3]);
        question.setText(util.quiz.get(currentNumber - 1).title);

        if (util.quiz.get(currentNumber - 1).questionType == 2) {
            choice1.setText("True");
            choice2.setText("False");
        }

        if (currentNumber == util.questionCounts) {
            nextQuestion.setText("submit");
        }
        if (currentNumber == 1) {
            preview.setVisible(false);
        }

    }

}
