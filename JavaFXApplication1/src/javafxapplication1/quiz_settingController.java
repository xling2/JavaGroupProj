/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	@FXML
	private void backAction(ActionEvent event) {
		goPage.goPage("student_panel.fxml", tips, 0);
	}

	@FXML
	private void start(ActionEvent event) {
		
		goPage.quizDifficultyOfStringSelect = difficulty.getSelectionModel().selectedIndexProperty().intValue();
		if (goPage.quizDifficultyOfStringSelect == -1 || number.getText().length() == 0) {
			tips.setText("Please select a difficulty and input a number first.");
			tips.setVisible(true);
		} else if (Integer.parseInt(number.getText()) > 50) {
			tips.setText("The number must less than 50.");
			tips.setVisible(true);
		} else {
			goPage.numberOfCurrentQuiz = 1;
			goPage.questionNumberFromQuizSetting = Integer.parseInt(number.getText());
			goPage.getRandomQuiz();
			goPage.goPage("question.fxml", tips);
		}
	}
	
	private GoPage goPage;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		goPage = GoPage.getGoPage();
		difficulty.setItems(FXCollections.observableArrayList("Easy", "Mediem ", "Hard", "Mixed"));
		goPage.quizDifficultyOfStringSelect = -1;
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

}
