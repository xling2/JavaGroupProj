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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


/**
 *
 * @author mica
 */
public class view_historyController implements Initializable {
    
    @FXML
    private Button back;
    @FXML
    private Button viewGenerateReport;
    @FXML
    private Button viewReport;
    @FXML
    private Button getHistory;    
    @FXML
    private ListView historyList;
    @FXML
    private Label tips;
    
    @FXML
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("student_panel.fxml", back, 0);
    }
    @FXML
    private void viewGenerateReport(ActionEvent event){
        GoPage.getGoPage().goPage("quiz_report.fxml", back, 600, 800);
    }
    @FXML
    private void viewReport(ActionEvent event){
        GoPage.getGoPage().historySelectIndex = historyList.getSelectionModel().getSelectedIndex();
        if(GoPage.getGoPage().historySelectIndex==-1){
            tips.setVisible(true);
        }else{
            tips.setVisible(false);
            GoPage.getGoPage().goPage("quiz_report.fxml", back, 600, 800);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> historyItems=FXCollections.observableArrayList(GoPage.getGoPage().historyItems);
        historyList.setItems(historyItems); 
        tips.setVisible(false);
    }    
    
}
