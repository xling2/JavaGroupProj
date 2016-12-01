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
    private Label tips;

    @FXML
    private Button backButton;

    @FXML
    private ListView<HistoryRecord> historyListView;

    private popUpPage pup = new popUpPage();

    @FXML
    private void backAction(ActionEvent event) {
        goPage.goPage("/student_panel.fxml", tips);

    }

    @FXML
    private void viewGenerateReport(ActionEvent event) {
        popUpPage.setParentScene(tips);
        pup.open("/student_general_report.fxml");
    }

    @FXML
    private void viewReport(ActionEvent event) {
        if (historyListView.getSelectionModel().getSelectedIndex() == -1) {
            tips.setVisible(true);
        } else {
            tips.setVisible(false);
            goPage.getQuizFromServeById(historyListView.getSelectionModel().getSelectedItem().quizId);
            popUpPage.setParentScene(tips);
            pup.open("/student_quiz_report_onetime.fxml");
        }
    }

    private GoPage goPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        goPage.getStudentHistoryRecordFromServe();
        // get history record of this student and set them in the list view
        ObservableList<HistoryRecord> historyItems
                = FXCollections.observableArrayList(goPage.historyRecordItems);
        for (HistoryRecord h : historyItems) {
            // add from the bottom
            historyListView.getItems().add(0, h);
        }

        tips.setVisible(false);

        // when instructor check student, no back button needed.
        if (goPage.userName.equals("instructor")) {
            backButton.setVisible(false);
        }
    }
}
