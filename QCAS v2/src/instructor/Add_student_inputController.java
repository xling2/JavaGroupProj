/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import qcas.GoPage;
import qcas.popUpPage;

/**
 * FXML Controller class
 *
 * @author Yixin1
 */
public class Add_student_inputController implements Initializable {

    @FXML
    private Label msgLabel;

    @FXML
    private Button addButton;

    @FXML
    private TextField andrewIDTextField;

    protected static boolean addSuccess;

    @FXML
    private void addButtonAction(ActionEvent ae) {
        msgLabel.setVisible(true);

        String andrewID = andrewIDTextField.getText();
        if (!andrewID.isEmpty()) {
            addSuccess = GoPage.getGoPage().communicateWithServe.addStudent(andrewID);
            if (addSuccess) {
                msgLabel.setText("Add success, email with password sent.");
                msgLabel.setVisible(true);
                PauseTransition visiblePause
                        = new PauseTransition(Duration.seconds(3));
                visiblePause.setOnFinished(e -> {
                    msgLabel.setVisible(false);
                });
                visiblePause.play();
            } else {
                msgLabel.setText("Add failed.");
                msgLabel.setTextFill(Color.rgb(187, 0, 0));
                msgLabel.setVisible(true);
                PauseTransition visiblePause
                        = new PauseTransition(Duration.seconds(3));
                visiblePause.setOnFinished(e -> {
                    msgLabel.setVisible(false);
                });
                visiblePause.play();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addSuccess = false;
        msgLabel.setVisible(false);
    }
}
