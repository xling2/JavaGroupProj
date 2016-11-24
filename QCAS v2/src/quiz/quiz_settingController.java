/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quiz;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import qcas.GoPage;
import qcas.popUpPage;
import student.student_panelController;
import utilclass.TimeRecorder;

/**
 *
 * @author mica
 */
public class quiz_settingController implements Initializable {

    @FXML
    private Label tips;
    @FXML
    private TextField number;
    @FXML
    private ChoiceBox<String> difficulty;

    private String startTime;

    private popUpPage pup = new popUpPage();

    @FXML
    private void backAction(ActionEvent event) {
        goPage.goPage("/student_panel.fxml", tips, 0);
    }

    @FXML
    private void start(ActionEvent event) {

        goPage.quizDifficultyOfStudentSelect = difficulty.getSelectionModel().selectedIndexProperty().intValue();
        if (!number.getText().isEmpty() && difficulty.getSelectionModel().getSelectedIndex() > -1) {
            if (Integer.parseInt(number.getText()) > 50) {
                tips.setText("Question number no more than 50");
                tips.setVisible(true);
                PauseTransition visiblePause
                        = new PauseTransition(Duration.seconds(3));
                visiblePause.setOnFinished(e -> {
                    tips.setVisible(false);
                });
                visiblePause.play();
            } else {
                goPage.numberOfCurrentQuiz = 1;
                goPage.questionNumberFromQuizSetting = Integer.parseInt(number.getText());
                goPage.getRandomQuiz();
                startTime = TimeRecorder.getTime();
                popUpPage.setpopUpScene(number);
                pup.closeToOpen("/question.fxml");

                startTime = TimeRecorder.getTime();
                

            }
        }
    }

    private GoPage goPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        difficulty.setItems(FXCollections.observableArrayList("Easy", "Mediem ", "Hard", "Mixed"));
		goPage.quizDifficultyOfStudentSelect = -1;
        tips.setVisible(false);
        // set only number
        number.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // TODO Auto-generated method stub
                if (!newValue.matches("\\d*")) {
                    number.setText(newValue.replaceAll("[^\\d]", ""));
                    tips.setText("Number available");
                    tips.setVisible(true);
                } else {
                    tips.setVisible(false);
                }
            }
        });
    }

    public String getStartTime() {
        return startTime;
    }

}
