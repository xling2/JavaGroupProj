/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import qcas.GoPage;
import qcas.popUpPage;
import utilclass.HistoryRecord;

/**
 *
 * @author mica
 */
public class view_historyController implements Initializable {

    @FXML
    private ListView<HistoryRecord> historyList;

    @FXML
    private Label tips;

    @FXML
    private Button generateReportButton;

    @FXML
    private Button viewReportButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView<?> historyListView;

    private popUpPage pup = new popUpPage();

    @FXML
    private void backAction(ActionEvent event) {

        goPage.goPage("/student_panel.fxml", tips);

    }

    @FXML
    private void viewGenerateReport(ActionEvent event) {
        //System.out.println("view_historyController.viewGenerateReport");
        popUpPage.setParentScene(tips);
        pup.open("/student_general_report.fxml");
    }

    @FXML
    private void viewReport(ActionEvent event) {
        if (historyList.getSelectionModel().getSelectedIndex() == -1) {
            tips.setVisible(true);
        } else {
            tips.setVisible(false);
            goPage.getQuizFromServeById(historyList.getSelectionModel().getSelectedItem().quizId);
            goPage.back = "/view_history.fxml";
            goPage.backX = tips.getScene().getWidth();
            goPage.backY = tips.getScene().getHeight();
            goPage.goPage("/student_quiz_report_onetime.fxml", tips, 438, 1000);
        }
    }

    private GoPage goPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        goPage.getStudentHistoryRecordFromServe();
        ObservableList<HistoryRecord> historyItems = FXCollections.observableArrayList(goPage.historyRecordItems);
        historyList.setItems(historyItems);
        tips.setVisible(false);
        
        if(GoPage.getGoPage().studentName.equals("instructor")){
            backButton.setVisible(false);
        }
    }
}
