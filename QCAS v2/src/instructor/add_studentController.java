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
    private TextArea text;

    @FXML
    private Label tips;

    @FXML
    private Button backButton;

    @FXML
    private TableView<studentInTable> studentTable;

    private ObservableList<studentInTable> studentData
            = FXCollections.observableArrayList();

    @FXML
    private TableColumn<studentInTable, String> andrewIDColumn;

    private popUpPage pup = new popUpPage();

    @FXML
    private void addAction(ActionEvent event) {
        if (GoPage.getGoPage().communicateWithServe.addStudent(text.getText())) {
            tips.setTextFill(Color.GREEN);
            tips.setText("Add Successed");
            tips.setVisible(true);
        } else {
            tips.setTextFill(Color.RED);
            tips.setText("Add Failed");
            tips.setVisible(true);
        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", backButton);
    }

    @FXML
    private void deleteAction(ActionEvent event) {

        ObservableList<studentInTable> studentListSelected;

        studentListSelected = studentTable.getSelectionModel().getSelectedItems();
        if (studentListSelected != null) {
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
