/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import qcas.SceneSwitcher;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Yixin1
 */
public class FXML_Student_Quiz_SettingController {

    @FXML
    private Button startQuizButton;

    @FXML
    private void startQuizButtonAction(ActionEvent event) throws IOException {
        Stage popUpWindow = (Stage) startQuizButton.getScene().getWindow();
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Student_Quiz.fxml")), FXML_Student_DashboardController.currentScene);
        popUpWindow.close();
    }

}
