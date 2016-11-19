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
    
    @FXML
    private void logoutAction(ActionEvent event) {
        GoPage.getGoPage().goPage("index.fxml", logout, 0);
    }
    
    @FXML
    private void viewHistory(ActionEvent event) {
        GoPage.getGoPage().goPage("view_history.fxml", logout, 0);
    }
    
    @FXML
    private void quiz(ActionEvent event) {
        GoPage.getGoPage().goPage("quiz_setting.fxml", logout, 600, 308);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
