/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 *
 * @author mica
 */
public class quiz_settingController implements Initializable {
    
    @FXML
    private Button back;
    @FXML
    private Button start;
    @FXML
    private Label tips;
    @FXML
    private TextField number;
    @FXML
    private ChoiceBox difficulty;
    
    @FXML
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("student_panel.fxml", back, 0);
    }

    @FXML
    private void start(ActionEvent event){
        GoPage util = GoPage.getGoPage();
        System.out.println(util.quizDifficult = difficulty.getSelectionModel().selectedIndexProperty().intValue());
        if(util.questionCounts==-1||number.getText().length()==0){
            tips.setVisible(true);
        }else{
            try {
                util.questionCounts = Integer.parseInt(number.getText());
                tips.setVisible(false);
                util.getQuiz();
                util.currentNumber = 1;
                System.out.println(Question.FXMLForQuestion[util.quiz.get(util.currentNumber-1).questionType]);
                util.goPage(Question.FXMLForQuestion[util.quiz.get(util.currentNumber-1).questionType], back);
            }catch (Exception e) {
                System.out.println("General quiz Fail");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        difficulty.setItems(FXCollections.observableArrayList("Easy", "Mediem ", "Hard", "Mixed"));  
        tips.setVisible(false);
    }    
    
}
