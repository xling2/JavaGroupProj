/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import qcas.GoPage;
import qcas.popUpPage;

/**
 *
 * @author mica
 */
public class add_studentController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button backButton;

    @FXML
    private TableView<studentInTable> studentTable;

    private ObservableList<studentInTable> studentData
            = FXCollections.observableArrayList();

    private popUpPage pup = new popUpPage();
    
    @FXML
    private TableColumn<studentInTable, String> andrewIDColumn;

    @FXML
    private void addAction(ActionEvent event) {
        popUpPage.setParentScene(backButton);
        pup.open("/add_student_input.fxml");
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", backButton);
    }

    @FXML
    private void deleteAction(ActionEvent event) {

        ObservableList<studentInTable> studentListSelected;

        studentListSelected = studentTable.getSelectionModel().getSelectedItems();
        if (!studentListSelected.isEmpty()) {
            popUpPage.setParentScene(backButton);
            pup.open("/delete_confirm.fxml");
            if (Delete_confirmController.enterPressed) {
                studentListSelected.forEach(studentData::remove);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentData = FXCollections.observableArrayList(
                new studentInTable("AAAA"), new studentInTable("BBBB"));

        andrewIDColumn.setCellValueFactory(
                new PropertyValueFactory<>("AndrewID"));
        //studentTable.setItems(studentData);
        studentTable.setItems(studentData);
    }

}
