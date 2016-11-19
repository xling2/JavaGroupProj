/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import qcas.SceneSwitcher;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import qcas.TimeRecorder;

/**
 *
 * @author Yixin1
 */
public class FXML_Student_Quiz_SettingController {

    private int numberOfQuestions = 0;
    private int difficultyLevel;
    private String startTime;

    ObservableList<String> difficultyList
            = FXCollections.observableArrayList("Easy", "Medium", "Hard", "Mixed");

    @FXML
    private TextField questionNumberTextField;

    @FXML
    private Button startQuizButton;

    @FXML
    private ChoiceBox difficultyBox;

    @FXML
    private void startQuizButtonAction(ActionEvent event) throws IOException {
        startTime = TimeRecorder.getTime();
        Stage popUpWindow = (Stage) startQuizButton.getScene().getWindow();
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Student_Quiz.fxml")),
                FXML_Student_DashboardController.currentScene);
        popUpWindow.close();
    }

    @FXML
    private void initialize() {
        difficultyBox.setItems(difficultyList);
    }

    public int getDifficulty() {
        difficultyLevel = difficultyBox.getSelectionModel().selectedIndexProperty().intValue();
        return difficultyLevel;
    }

    public int getNumberOfQuestions() {
        try {
            numberOfQuestions = Integer.parseInt(questionNumberTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException in "
                    + "FXML_Student_Quiz_SettingController: " + e);
        }
        return numberOfQuestions;
    }

    public String getStartTime() {
        return startTime;
    }
}
