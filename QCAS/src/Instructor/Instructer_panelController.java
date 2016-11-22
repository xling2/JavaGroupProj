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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import qcas.SceneSwitcher;

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
    private void logoutAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Login.fxml")), logout.getScene());

    }

    @FXML
    private void reviewAciton(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/review_quizzs.fxml")), logout.getScene());
    }

    @FXML
    private void importAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/import_question.fxml")), logout.getScene());
    }

    @FXML
    private void addAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/add_student.fxml")), logout.getScene());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
