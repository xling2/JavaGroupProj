/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;


import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import qcas.GoPage;
import utilclass.Question;


/**
 *
 * @author mica
 */
public class questionController implements Initializable {
    GoPage goPage;
    
    @FXML
    private Button nextQuestion;
    @FXML
    private ToggleGroup select;
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
    private Slider numberSlider;
    @FXML
    private Label question;
    @FXML
    private Label currentNumber;
    @FXML
    private Label title;
    @FXML
    private TextArea blank;
    @FXML
    private Button preview;

    @FXML
    private void preview(ActionEvent event){
        if(goPage.numberOfCurrentQuiz==1){
        }else{
            goPage.numberOfCurrentQuiz -= 1;
            goPage.goPage("/question.fxml", nextQuestion);
        } 
    }
    
    @FXML
    private void nextQuestion(ActionEvent event){
        preview.setVisible(true);
        if(goPage.numberOfCurrentQuiz==goPage.questionNumberFromQuizSetting){
        	goPage.quizOfCurrentCheck.finishQuiz();
            goPage.recordQuizResultToServe();
            goPage.back = "/student_panel.fxml";
            goPage.backX = 600;
            goPage.backY = 400;
            goPage.goPage("/student_quiz_report_onetime.fxml", nextQuestion, 438, 800);
        }else{
            goPage.numberOfCurrentQuiz += 1;
            goPage.goPage("/question.fxml", nextQuestion);
        }   
    }
    
    @FXML
    private void radioAction(ActionEvent event){
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].singleChoice = (int)select.getSelectedToggle().getUserData();
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].trueOrFalse = (int)select.getSelectedToggle().getUserData()==1;
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].isAnswer = true;
    }
    @FXML
    private void checkAction(ActionEvent event){
        CheckBox[] temp = new CheckBox[]{choice5, choice6, choice7, choice8};
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].multipleChoices.clear();
        for (int i = 0; i < temp.length; i++) {
            if(temp[i].isSelected()){
                goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].multipleChoices.add((int)temp[i].getUserData());
            }
        }
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].isAnswer = true;
    }

    @FXML
    private void goNumber(ActionEvent event){
        goPage.numberOfCurrentQuiz = (int)numberSlider.getValue();
        goPage.goPage("/question.fxml", nextQuestion);    
    }
    
    private Question currentQuestion;
    
    public void setVisible(){
    	
        switch(goPage.quizOfCurrentCheck.questionsOfQuiz[goPage.numberOfCurrentQuiz-1].questionType){
            case 0:
                choice5.setVisible(true);
                choice6.setVisible(true);
                choice7.setVisible(true);
                choice8.setVisible(true);
                choice5.setText(currentQuestion.choices[0]);
                choice6.setText(currentQuestion.choices[1]);
                choice7.setText(currentQuestion.choices[2]);
                choice8.setText(currentQuestion.choices[3]);
                break;
            case 1:
                choice1.setVisible(true);
                choice2.setVisible(true);
                choice3.setVisible(true);
                choice4.setVisible(true);
                choice1.setText(currentQuestion.choices[0]);
                choice2.setText(currentQuestion.choices[1]);
                choice3.setText(currentQuestion.choices[2]);
                choice4.setText(currentQuestion.choices[3]);
                break;
            case 2:
                choice1.setText("True");
                choice2.setText("False");
                choice1.setVisible(true);
                choice2.setVisible(true);
                break;
            case 3:
                blank.setVisible(true);
                break;
            default:
                System.out.println("error");
                break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.goPage = GoPage.getGoPage();
        currentQuestion = goPage.quizOfCurrentCheck.questionsOfQuiz[goPage.numberOfCurrentQuiz-1];
        numberSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numberSlider.setValue((int)numberSlider.getValue());
            currentNumber.setText((int)numberSlider.getValue()+"/"+goPage.questionNumberFromQuizSetting);
        });
        
        blank.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].blank = blank.getText();
                goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].isAnswer = true;
            }
        });
        
        choice1.setUserData(1);
        choice2.setUserData(2);
        choice3.setUserData(3);
        choice4.setUserData(4);
        choice5.setUserData(1);
        choice6.setUserData(2);
        choice7.setUserData(3);
        choice8.setUserData(4);
        setVisible();
        
        numberSlider.setMax(goPage.questionNumberFromQuizSetting);
        numberSlider.setValue(goPage.numberOfCurrentQuiz);
        currentNumber.setText(goPage.numberOfCurrentQuiz+"/"+goPage.questionNumberFromQuizSetting);

        question.setText(currentQuestion.content);
        
        //set question height
        int questionLengh = currentQuestion.content.split("\\n").length;
        question.setStyle("-fx-padding: 5px 30px 5px 5px;"
            + "-fx-text-alignment:justify;");
        question.setPrefHeight(questionLengh*20<198?198:questionLengh*20);
        
        title.setText("No." + goPage.numberOfCurrentQuiz + " " + Question.TYPENAME[currentQuestion.questionType]);
        
        if(goPage.numberOfCurrentQuiz==goPage.questionNumberFromQuizSetting){
            nextQuestion.setText("submit");
        }
        if(goPage.numberOfCurrentQuiz==1){
            preview.setVisible(false);
        }
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].questionID = currentQuestion.questionID;
        goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].questionType = currentQuestion.questionType;
        
        
        if(goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].isAnswer){
            HashSet<Integer> temp = new HashSet<Integer>();
            switch(currentQuestion.questionType){
                case 0:
                    temp.addAll(goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].multipleChoices);
                    choice5.setSelected(temp.contains(1));
                    choice6.setSelected(temp.contains(2));
                    choice7.setSelected(temp.contains(3));
                    choice8.setSelected(temp.contains(4));
                case 1:case 2:
                    temp.add(goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].singleChoice);
                    choice1.setSelected(temp.contains(1));
                    choice2.setSelected(temp.contains(2));
                    choice3.setSelected(temp.contains(3));
                    choice4.setSelected(temp.contains(4));
                    break;
                case 3:
                    blank.setText(goPage.quizOfCurrentCheck.answerOfStudent[goPage.numberOfCurrentQuiz-1].blank);
                    break;
                default:
                    System.out.println("error");
                    break;
            }
        }
    }    
    
}
