/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import qcas.GoPage;
import qcas.popUpPage;
import utilclass.Question;
import javafx.beans.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import utilclass.TableUse;

/**
 * FXML Controller class
 *
 * @author Yixin1
 */
public class Question_bankController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;
    @FXML
    private Button importButton;

    @FXML
    private TableColumn<TableUse, String> difficultyColumn;

    @FXML
    private TableColumn<TableUse, String> typeColumn;

    @FXML
    private TableColumn<TableUse, String> descriptionColumn;

    @FXML
    private TableColumn<TableUse, String> answerColumn;

    @FXML
    private TableView<TableUse> questionTable;

    private ObservableList<TableUse> questionData
            = FXCollections.observableArrayList();

    private popUpPage pup = new popUpPage();

    @FXML
    private void importButtonAction(ActionEvent ae) {
        popUpPage.setParentScene(importButton);
        pup.open("/import_question.fxml");
        // If table doesn't renew, enable the following
        questionData = FXCollections.observableArrayList(
                createQuestionTableList(
                        GoPage.getGoPage().communicateWithServe.getAllQuestions()));
        questionTable.setItems(questionData);

    }

    @FXML
    private void deleteAction(ActionEvent ae) {
        ObservableList<TableUse> questionListSelected;

        questionListSelected = questionTable.getSelectionModel().getSelectedItems();
        if (!questionListSelected.isEmpty()) {
            popUpPage.setParentScene(backButton);
            pup.open("/delete_confirm.fxml");
            if (Delete_confirmController.enterPressed) {
                GoPage.getGoPage().communicateWithServe.deleteById(questionListSelected.get(0).getNumber());
                questionListSelected.forEach(questionData::remove);
            }
        }
    }

    @FXML
    private void backButtonAction(ActionEvent ae) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", backButton);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<TableUse> questionTableList = createQuestionTableList(
                GoPage.getGoPage().communicateWithServe.getAllQuestions());

        questionData = FXCollections.observableArrayList(questionTableList);

        difficultyColumn.setCellValueFactory(
                new PropertyValueFactory<>("difficulty"));
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("QuestionType"));
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        answerColumn.setCellValueFactory(
                new PropertyValueFactory<>("answer"));
        questionTable.setItems(questionData);

        descriptionColumn.setCellFactory(param -> {
            return new TableCell<TableUse, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;"
                                + "-fx-text-alignment:justify;");
                        text.setWrappingWidth(param.getPrefWidth() - 35);
                        setPrefHeight(117);
                        setGraphic(text);
                    }
                }
            };
        });

        answerColumn.setCellFactory(param -> {
            return new TableCell<TableUse, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;"
                                + "-fx-text-alignment:justify;");
                        setPrefHeight(117);
                        setGraphic(text);
                    }
                }
            };
        });

    }

    // Convert TableUse to ObservableArrayList
    private ArrayList<TableUse> createQuestionTableList(Question[] questionList) {
        ArrayList<TableUse> questionTableList = new ArrayList<>();
        for (Question q : questionList) {
            String description =  q.toString().split("\n\n")[1];

            if (q.questionType < 2) {
                description += "\n\n" + q.toString().split("\n\n")[2];
            }

            questionTableList.add(0,
                    new TableUse(q.questionID,
                            Question.TYPENAME[q.questionType],
                            Question.DIFFICULTY[q.questionDifficult],
                            q.correctAnswer,
                            q.toString()));
        }
        return questionTableList;
    }

}
