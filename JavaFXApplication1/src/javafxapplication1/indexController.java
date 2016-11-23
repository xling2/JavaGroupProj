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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author mica
 */
public class indexController implements Initializable {

	private GoPage goPage;

	@FXML
	private Button login;

	@FXML
	private RadioButton student;

	@FXML
	private RadioButton instructor;

	@FXML
	private TextField id;

	@FXML
	private PasswordField pswd;
	@FXML
	private Label loginTip;

	@FXML
	private void login(ActionEvent event) {
		int loginType = student.isSelected() ? 1 : 2;
		if (id.getText().length() == 0) {
			loginTip.setText("Please input your ID first");
			loginTip.setVisible(true);
			id.requestFocus();
		} else {
			String userName = id.getText();
			if (goPage.communicateWithServe.login(loginType, userName, pswd.getText())) {
				goPage.userName = userName;
				if (loginType == 1){
					goPage.studentName = userName;
					goPage.goPage("student_panel.fxml", id, 0);
				}
				else
					goPage.goPage("Instructer_panel.fxml", id, 0);		
			} else {
				loginTip.setText("Wrong Id or Wrong Password");
				loginTip.setVisible(true);
			}
		}
	}

	@FXML
	private Label numberOnlyTips;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		goPage = GoPage.getGoPage();

		// set only number
		id.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				loginTip.setVisible(false);
				if (!newValue.matches("\\w*")) {
					id.setText(newValue.replaceAll("[^\\w]", ""));
					numberOnlyTips.setVisible(true);
				} else {
					numberOnlyTips.setVisible(false);
				}
			}
		});
	}

}
