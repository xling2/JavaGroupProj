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

/**
 *
 * @author mica
 */
public class review_quizzsController implements Initializable {
    
    @FXML
    private Button gotodash;
    @FXML
    private MenuButton select;
    
    @FXML
    private void gotodashAction(ActionEvent event) throws IOException {
        GoPage.getGoPage().goPage("Instructer_panel.fxml", select, 0);
    }
    
    @FXML
    private void selectAction(ActionEvent event){
        System.out.println(select.getId());
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
