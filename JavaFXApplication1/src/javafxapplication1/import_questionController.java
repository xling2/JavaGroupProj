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
public class import_questionController implements Initializable {
    
    @FXML
    private Button back;
    
    @FXML
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("Instructer_panel.fxml", back, 0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
