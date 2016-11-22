/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import qcas.GoPage;

/**
 *
 * @author mica
 */
public class import_questionController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private TextField filePath;
    @FXML
    private Label successedTips;
    @FXML
    private Label failedTips;
    @FXML
    private Label noFileTips;

    @FXML
    private void backAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", back, 0);
    }

    @FXML
    private void selectCSV(ActionEvent event) {
        selectCSV();
    }

    private File CSVFile;

    private void selectCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.CSV"));
        CSVFile = fileChooser.showOpenDialog(successedTips.getScene().getWindow());
        noFileTips.setVisible(false);
        successedTips.setVisible(false);
        failedTips.setVisible(false);
        if (CSVFile != null) {
            filePath.setText(CSVFile.getAbsolutePath());
        } else {
            filePath.setText("No File Choosed");
        }

    }

    @FXML
    private void importCSV(ActionEvent event) {
        if (CSVFile != null) {
            filePath.setText(CSVFile.getAbsolutePath());
            if (goPage.communicateWithServe.importQuestionFromCSV(CSVFile)) {
                failedTips.setVisible(false);
                noFileTips.setVisible(false);
                successedTips.setVisible(true);
            } else {
                noFileTips.setVisible(false);
                successedTips.setVisible(false);
                failedTips.setVisible(true);
            }

        } else {
            successedTips.setVisible(false);
            failedTips.setVisible(false);
            noFileTips.setVisible(true);
        }
    }

    private GoPage goPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        filePath.setOnMouseClicked(ev -> {
            selectCSV();
        });
    }

}
