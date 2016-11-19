/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;

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
    private void addAction(ActionEvent event){
        System.out.println("addStudentAction:\n"+text.getText());
    }
    
    @FXML
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("Instructer_panel.fxml", back, 0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
