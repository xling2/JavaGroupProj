/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import qcas.GoPage;
import qcas.popUpPage;

/**
 *
 * @author mica
 */
public class student_panelController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button viewHistory;
    @FXML
    private Button quiz;

    private popUpPage pup = new popUpPage();

    @FXML
    private void logoutAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/login.fxml", logout);
    }

    @FXML
    private void viewHistory(ActionEvent event) {
        GoPage.getGoPage().goPage("/view_history.fxml", logout, 0);
    }

    @FXML
    private void quiz(ActionEvent event) throws IOException {

        popUpPage.setParentScene(logout);
        pup.open("/quiz_setting.fxml");
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
