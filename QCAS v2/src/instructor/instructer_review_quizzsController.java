/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import qcas.GoPage;
import qcas.popUpPage;

/**
 *
 * @author mica
 */
public class instructer_review_quizzsController implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label listDefaultLabel;
    @FXML
    private ChoiceBox<String> select;
    @FXML
    private Button enterButton;

    private popUpPage pup = new popUpPage();

    private GoPage goPage;

    @FXML
    private void enterButtonAction(ActionEvent ae) {

        popUpPage.setParentScene(pane);
        if (goPage.quizzsReviewSelectOfInstructor == 3) {
            pup.open("/instructor_review_quizzs_general.fxml");
        } else {
            pup.open("/instructor_review_quizzs_detail.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        select.setItems(FXCollections.observableArrayList(
                "Last month record",
                "Last quarter record",
                "Last year record",
                "General record"));
        goPage.quizzsReviewSelectOfInstructor = -1;
        //select initial
        select.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value) {
                listDefaultLabel.setVisible(false);
                goPage.quizzsReviewSelectOfInstructor = new_value.intValue();
                
            }
        });
    }

}
