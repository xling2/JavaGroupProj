/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import qcas.SceneSwitcher;

/**
 *
 * @author mica
 */
public class quiz_reportController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button export;
    @FXML
    private Label name;
    @FXML
    private Label time;
    @FXML
    private Label duration;

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Student_View_History.fxml")), back.getScene());
    }

    @FXML
    private void export(ActionEvent event) throws IOException {
        // If exported successfully, open a popup window
        Scene currentScene = back.getScene();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML_Student_Quiz_Setting.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(currentScene.getWindow());
        stage.setResizable(false);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        ArrayList<Button> showList = new ArrayList<>();
//        for(int i =0;i<GoPage.getGoPage().questionCounts;i++){
//            showList.add(new Button("show ..."));
//        }
//        for(Button btn:showList){
//            ((StackPane)back.getScene().getRoot()).getChildren().add(btn);
//        }
        name.setText("AAA");
        time.setText("2016-11-18"/*GoPage.getGoPage().historyItems.get(GoPage.getGoPage().historySelectIndex)*/);

    }

}
