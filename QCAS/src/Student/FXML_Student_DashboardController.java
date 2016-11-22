/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import qcas.SceneSwitcher;

/**
 * FXML Controller class
 *
 * @author Yixin1
 */
public class FXML_Student_DashboardController implements Initializable {

    protected static Scene currentScene;

    @FXML
    private Button logoutButton;

    @FXML
    private Button takeQuizButton;
    
    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Login.fxml")), logoutButton.getScene());
    }

    @FXML
    private void takeQuizButtonAction(ActionEvent event) throws IOException {
        currentScene = takeQuizButton.getScene();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_Student_Quiz_Setting.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentScene.getWindow());
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void viewHistoryButtonAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Student_View_History.fxml")), logoutButton.getScene());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
