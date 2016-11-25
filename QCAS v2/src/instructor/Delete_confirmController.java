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
import javafx.stage.Stage;
import qcas.popUpPage;

/**
 * FXML Controller class
 *
 * @author Yixin1
 */
public class Delete_confirmController implements Initializable {

    public static boolean enterPressed;

    @FXML
    private Button OKButton;

    @FXML
    private Button cancelButton;

    private popUpPage pup = new popUpPage();

    @FXML
    private void OKButtonAction(ActionEvent ae) {
        enterPressed = true;
        Stage popUpWindow = (Stage) OKButton.getScene().getWindow();
        popUpWindow.close();
    }

    @FXML
    private void cancelButtonAction(ActionEvent ae) {
        Stage popUpWindow = (Stage) OKButton.getScene().getWindow();
        popUpWindow.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enterPressed = false;
    }

}
