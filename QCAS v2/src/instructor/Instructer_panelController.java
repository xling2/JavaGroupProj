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
import qcas.GoPage;

/**
 *
 * @author mica
 */
public class Instructer_panelController implements Initializable {
    
    @FXML
    private Button logout;
    @FXML
    private Button review;
    @FXML
    private Button importquestions;
    @FXML
    private Button add;
    
    @FXML
    private void logoutAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/login.fxml", logout, 0);
    }
    
    @FXML
    private void reviewAciton(ActionEvent event) {
        GoPage.getGoPage().goPage("/instructer_review_quizzs.fxml", review, 350, 157);
    }
    
    @FXML
    private void importAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/import_question.fxml", importquestions, 406, 211);
    }
    
    @FXML
    private void addAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/add_student.fxml", add, 0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
