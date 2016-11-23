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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

/**
 *
 * @author mica
 */
public class add_studentController implements Initializable {

	@FXML
	private Button add;
	@FXML
	private TextArea text;
	@FXML
	private Button back;
	@FXML
	private Label tips;

	@FXML
	private void addAction(ActionEvent event) {
		if (GoPage.getGoPage().communicateWithServe.addStudent(text.getText())) {
			tips.setTextFill(Color.GREEN);
			tips.setText("Add Successed");
			tips.setVisible(true);
		} else {
			tips.setTextFill(Color.RED);
			tips.setText("Add Failed");
			tips.setVisible(true);
		}
	}

	@FXML
	private void backAction(ActionEvent event) {
		GoPage.getGoPage().goPage("Instructer_panel.fxml", back, 0);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
