/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("view_history.fxml", back, 0);
    }
    @FXML
    private void export(ActionEvent event){
        GoPage.getGoPage().goPage("student_panel.fxml", back, 0);
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
        name.setText(GoPage.getGoPage().id+"");
        time.setText(GoPage.getGoPage().historyItems.get(GoPage.getGoPage().historySelectIndex));
        
    }    
    
}
