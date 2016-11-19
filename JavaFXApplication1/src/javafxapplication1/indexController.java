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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 *
 * @author mica
 */
public class indexController implements Initializable {
    
    int status = 0;
    
    @FXML
    private Button login;
    
    @FXML
    private RadioButton student;
    
    @FXML
    private RadioButton instructor;
    
    @FXML
    private TextField id;
    @FXML
    private Button test;
    
    @FXML
    private void loginAction(ActionEvent event){
        try {
            GoPage.getGoPage().id = Integer.parseInt(id.getText());
        } catch (Exception e) {
        }
        
        if(student.isSelected())
            GoPage.getGoPage().goPage("student_panel.fxml", login, 0);
        else
            GoPage.getGoPage().goPage("Instructer_panel.fxml", login, 0);
        
    }
    @FXML
    private void test(ActionEvent event){
        
            //GoPage.getGoPage().goPage("multiple_choice.fxml", login, 0);
        
    }   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
