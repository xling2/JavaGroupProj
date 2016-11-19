/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import qcas.SceneSwitcher;

/**
 *
 * @author mica
 */
public class FXML_Student_View_HistoryController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button generateReportButton;
    @FXML
    private Button viewReportButton;
    @FXML
    private ListView historyListView;

    @FXML
    private void backAction(ActionEvent e) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_Student_Dashboard.fxml")), historyListView.getScene()
        );
    }

    @FXML
    private void generalReportButtonAction(ActionEvent e) throws IOException {
        SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                "/FXML_General_Report.fxml")), historyListView.getScene());
    }

    @FXML
    private void viewReportButtonAction(ActionEvent e) throws IOException {
        if (historyListView.getSelectionModel().getSelectedIndex() > -1) {
            SceneSwitcher.goToScene(FXMLLoader.load(getClass().getResource(
                    "/FXML_View_Report.fxml")), historyListView.getScene());

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
        ArrayList<String> historyItems = new ArrayList<String>() {
            {
                add("8:00am");
                add("9:00pm");
                add("...");
            }
        };

        ObservableList<String> historyListViewItems = FXCollections.observableArrayList(historyItems);

        historyListView.setItems(historyListViewItems);
    }

}
