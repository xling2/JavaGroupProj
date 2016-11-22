/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;


/**
 *
 * @author mica
 */
public class questionController implements Initializable {
    GoPage util;
    
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
    private Label currentNumber;

    @FXML
    private Button preview;

    @FXML
    private void dragDone(ActionEvent event){
        System.out.println((int)numberSlide.getValue());
        currentNumber.setText((int)numberSlide.getValue()+"/"+util.questionCounts);
    }
    @FXML
    private void preview(ActionEvent event){
        if(util.currentNumber==1){
        }else{
            util.currentNumber -= 1;
            util.goPage(Question.FXMLForQuestion[util.quiz.get(util.currentNumber-1).questionType], question);
        } 
    }
    
    @FXML
    private void nextQuestion(ActionEvent event){
        preview.setVisible(true);
        if(util.currentNumber==util.questionCounts){
            util.goPage("quiz_setting.fxml", nextQuestion, 0);
        }else{
            util.currentNumber += 1;
            util.goPage(Question.FXMLForQuestion[util.quiz.get(util.currentNumber-1).questionType], question);
        }   
    }
    @FXML
    private void goNumber(ActionEvent event){
        util.currentNumber = (int)numberSlide.getValue();
        if(util.currentNumber==1)preview.setVisible(true);
        util.goPage(Question.FXMLForQuestion[util.quiz.get(util.currentNumber-1).questionType], question);     
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.util = GoPage.getGoPage();
        numberSlide.setMax(util.questionCounts);
        numberSlide.setValue(util.currentNumber);
        currentNumber.setText(util.currentNumber+"/"+util.questionCounts);
        //System.out.println(util.currentNumber);
        //System.out.println(util.quiz.get(util.currentNumber-1).choices[0]);
        choice1.setText(util.quiz.get(util.currentNumber-1).choices[0]);
        choice2.setText(util.quiz.get(util.currentNumber-1).choices[1]);
        choice3.setText(util.quiz.get(util.currentNumber-1).choices[2]);
        choice4.setText(util.quiz.get(util.currentNumber-1).choices[3]);
        choice5.setText(util.quiz.get(util.currentNumber-1).choices[0]);
        choice6.setText(util.quiz.get(util.currentNumber-1).choices[1]);
        choice7.setText(util.quiz.get(util.currentNumber-1).choices[2]);
        choice8.setText(util.quiz.get(util.currentNumber-1).choices[3]);
        question.setText(util.quiz.get(util.currentNumber-1).title);
        
        if(util.quiz.get(util.currentNumber-1).questionType==2){
            choice1.setText("True");
            choice2.setText("False");  
        }
        
        
        if(util.currentNumber==util.questionCounts){
            nextQuestion.setText("submit");
        }
        if(util.currentNumber==1){
            preview.setVisible(false);
        }   
        
        

    }    
    
}
