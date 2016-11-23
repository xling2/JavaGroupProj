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
import qcas.popUpPage;

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

    private popUpPage pup = new popUpPage();

    @FXML
    private void logoutAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/login.fxml", logout);
    }

    @FXML
    private void reviewAciton(ActionEvent event) {
        popUpPage.setParentScene(review);
        pup.open("/instructer_review_quizzs.fxml");
    }

    @FXML
    private void importAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/import_question.fxml", add);
    }

    @FXML
    private void addAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/add_student.fxml", add);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
