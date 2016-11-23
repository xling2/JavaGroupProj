/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private void backAction(ActionEvent event){
        goPage.goPage("student_panel.fxml", tips, 0);
        
    }
    @FXML
    private void viewGenerateReport(ActionEvent event){
        //System.out.println("view_historyController.viewGenerateReport");
    	goPage.goPage("student_general_report.fxml", tips, 438, 1000);
    }
    @FXML
    private void viewReport(ActionEvent event){
        if(historyList.getSelectionModel().getSelectedIndex()==-1){
            tips.setVisible(true);
        }else{
            tips.setVisible(false);
            goPage.getQuizFromServeById(historyList.getSelectionModel().getSelectedItem().quizId);
            goPage.back = "view_history.fxml";
            goPage.backX = tips.getScene().getWidth();
            goPage.backY = tips.getScene().getHeight();
            goPage.goPage("student_quiz_report_onetime.fxml", tips, 438, 1000);
        }
    }
    
    private GoPage goPage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	goPage = GoPage.getGoPage();
        goPage.getStudentHistoryRecordFromServe();
        ObservableList<HistoryRecord> historyItems=FXCollections.observableArrayList(goPage.historyRecordItems);
        historyList.setItems(historyItems); 
        tips.setVisible(false);
    }    
    
}
