/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import qcas.GoPage;
import utilclass.Question;

/**
 *
 * @author mica
 */
public class import_questionController implements Initializable {

	@FXML
	private Button importButton;
	@FXML
	private TextField filePath;
	@FXML
	private Label successedTips;
	@FXML
	private Label failedTips;
	@FXML
	private Label noFileTips;
//	@FXML
//	private VBox showQuestion;
	
	
//	@FXML
//	private void backAction(ActionEvent event) {
//		GoPage.getGoPage().goPage("Instructer_panel.fxml", importButton, 0);
//	}

	@FXML
	private void selectCSV(ActionEvent event) {
		selectCSV();
	}

	private File CSVFile;

	private void selectCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.CSV"));
		CSVFile = fileChooser.showOpenDialog(successedTips.getScene().getWindow());
		noFileTips.setVisible(false);
		successedTips.setVisible(false);
		failedTips.setVisible(false);
		if (CSVFile != null) {
			filePath.setText(CSVFile.getAbsolutePath());
		} else {
			filePath.setText("No File Choosed");
		}

	}

	@FXML
	private void importCSV(ActionEvent event) {
		if (CSVFile != null) {
			filePath.setText(CSVFile.getAbsolutePath());
			Question[] questions = goPage.communicateWithServe.importQuestionFromCSV(CSVFile);
			if (questions != null) {
				failedTips.setVisible(false);
				noFileTips.setVisible(false);
				successedTips.setVisible(true);
//				for (int i = 0; i < questions.length; i++) {
//					Question question =  questions[i];
//					HBox hBox = new HBox();
//					hBox.setMinSize(332, 300);
//					hBox.setPadding(new Insets(10));
//					hBox.setPadding(new Insets(15, 12, 15, 12));
//					hBox.setStyle("-fx-border-width: 10;");
//					hBox.setStyle("-fx-border-color: black;");
//					Button button = new Button("delete");
//					Label label = new Label("question" + (i+1) + "." + questions[i]);
//					label.setMinSize(200, 200);
//					
//					label.setAlignment(Pos.TOP_LEFT);
//					hBox.getChildren().add(label);
//					hBox.getChildren().add(button);
//					showQuestion.getChildren().add(hBox);
//					button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//						@Override
//						public void handle(MouseEvent event) {
//							// TODO Auto-generated method stub
//							goPage.communicateWithServe.deleteById(question.questionID);
//							System.out.println(question.questionID);
//							showQuestion.getChildren().remove(hBox);
//						}
//					});
//					
//				}
//				showQuestion.setVisible(true);
			} else {
				noFileTips.setVisible(false);
				successedTips.setVisible(false);
				failedTips.setVisible(true);
			}

		} else {
			successedTips.setVisible(false);
			failedTips.setVisible(false);
			noFileTips.setVisible(true);
		}
	}

	private GoPage goPage;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		goPage = GoPage.getGoPage();
		filePath.setOnMouseClicked(ev -> {
			selectCSV();
		});
	}

}
