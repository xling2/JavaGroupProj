/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import qcas.GoPage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 *
 * @author mica
 */
public class loginController implements Initializable {

    private GoPage goPage;

    @FXML
    private Label incorrectMsg;

    @FXML
    private TextField examIDTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonAction(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        login();
    }

    // Action to enter key
    @FXML
    private void enterPressed(KeyEvent event) throws NoSuchAlgorithmException, IOException {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    private void login() {

        // get login information
        String username = examIDTextField.getText();
        String passwd = passwordField.getText();
        
        System.out.println(passwd);

        if (GoPage.getGoPage().communicateWithServe.login(username, passwd)) {
            // If instructor loged in, go to instructor panel
            if (username.equals("instructor")) {
                goPage.goPage("/Instructer_panel.fxml", loginButton);
            } else {
                goPage.goPage("/student_panel.fxml", loginButton);
            }
        } else {
            incorrectMsg.setVisible(true);
            PauseTransition visiblePause
                    = new PauseTransition(Duration.seconds(3));
            visiblePause.setOnFinished(e -> {
                incorrectMsg.setVisible(false);
            });
            visiblePause.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goPage = GoPage.getGoPage();

    }

}
