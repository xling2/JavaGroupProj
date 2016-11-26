/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import qcas.GoPage;
import qcas.popUpPage;
import utilclass.Question;
import javafx.beans.*;

/**
 * FXML Controller class
 *
 * @author Yixin1
 */
public class Question_bankController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button importButton;
    
    @FXML
    private TableView<Question> questionTable;
    
    private popUpPage pup = new popUpPage();

    @FXML
    private void importButtonAction(ActionEvent ae) {
        popUpPage.setParentScene(importButton);
        pup.open("/import_question.fxml");
    }

    @FXML
    private void deleteAction(ActionEvent ae) {

    }

    @FXML
    private void addAction(ActionEvent ae) {

    }

    @FXML
    private void backButtonAction(ActionEvent ae) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", backButton);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }

}
