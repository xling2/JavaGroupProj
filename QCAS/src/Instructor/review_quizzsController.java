/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Instructor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import qcas.SceneSwitcher;

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
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/Instructer_panel.fxml")), select.getScene());
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
