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
import qcas.SceneSwitcher;

/**
 *
 * @author mica
 */
public class import_questionController implements Initializable {
    
    @FXML
    private Button back;
    
    @FXML
    private void backAction(ActionEvent event) throws IOException{
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/Instructer_panel.fxml")), back.getScene());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
