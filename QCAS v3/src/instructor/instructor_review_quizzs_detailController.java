/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.animation.PauseTransition;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static pdfutil.ChartManipulation.*;
import qcas.GoPage;
import pdfutil.PDFGeneral;
import qcas.popUpPage;

/**
 *
 * @author mica
 */
public class instructor_review_quizzs_detailController implements Initializable {

    private final static String[] TIMESTRING
            = new String[]{
                "Last month record",
                "Last quarter record",
                "Last year record",
                "General record"};
    @FXML
    private ScrollPane wholeScrollPane;

    @FXML
    private Pane pane;

    @FXML
    private Label successTip;

    @FXML
    private Label failTip;

    @FXML
    private Label existLabel;

    @FXML
    private Label checkTips;

    @FXML
    private ListView<String> studentList;

    @FXML
    private ChoiceBox<String> passOrFail;

    @FXML
    private Label quizzesNumber;

    @FXML
    private Label averageScore;

    @FXML
    private Label reviewTimeLabel;

    @FXML
    private BarChart<Number, String> difficultyChart;

    private popUpPage pup = new popUpPage();

    private GoPage goPage;

    @FXML
    private void backAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/Instructer_panel.fxml", pane);
    }

    @FXML
    private void export(ActionEvent event) {
        File folder = new File(System.getProperty("user.home"), "Desktop");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Document document = new Document();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String documentName = goPage.userName + df.format(new Date()) + "quizzes_review "
                + TIMESTRING[GoPage.getGoPage().quizzsReviewSelectOfInstructor] + ".PDF";

        String chartName = goPage.userName + "quizzes_review "
                + TIMESTRING[GoPage.getGoPage().quizzsReviewSelectOfInstructor] + ".png";

        File chartFile = new File(folder + "/" + chartName);
        
        File exportPDF = new File(folder + "/" + documentName);
        
        if (exportPDF.exists()) {
            failTip.setVisible(true);
            existLabel.setVisible(true);
            PauseTransition visiblePause
                    = new PauseTransition(Duration.seconds(3));
            visiblePause.setOnFinished(e -> {
                failTip.setVisible(false);
                existLabel.setVisible(false);
            });
            visiblePause.play();
        } else {
            try {
                PdfWriter.getInstance(document, new FileOutputStream(folder + "/" + documentName));
                document.open();
                PDFGeneral.addTitleLine(document, TIMESTRING[GoPage.getGoPage().quizzsReviewSelectOfInstructor]);
                PDFGeneral.addContentLine(document, "Quizzes taken : " + quizzesNumber.getText());
                PDFGeneral.addContentLine(document, "Average score : " + averageScore.getText());
                PDFGeneral.addSubtitleLine(document, "Score vs. Difficulty");
                PDFGeneral.addChartGraph(document, difficultyChart, chartFile);
                PDFGeneral.addSubtitleLine(document, "Student Passed");
                goPage.getPassedList(GoPage.getGoPage().quizzsReviewSelectOfInstructor);
                for(String s : goPage.studentViewListForInstructor){
                    PDFGeneral.addContentLine(document, s);
                }
                PDFGeneral.addSubtitleLine(document, "Student failed");
                goPage.getFailedList(GoPage.getGoPage().quizzsReviewSelectOfInstructor);
                for(String s : goPage.studentViewListForInstructor){
                    PDFGeneral.addContentLine(document, s);
                }
                document.close();
                successTip.setVisible(true);
                PauseTransition visiblePause
                        = new PauseTransition(Duration.seconds(3));
                visiblePause.setOnFinished(e -> {
                    successTip.setVisible(false);
                });
                visiblePause.play();
            } catch (Exception e) {
                e.printStackTrace();
                failTip.setVisible(true);
                PauseTransition visiblePause
                        = new PauseTransition(Duration.seconds(3));
                visiblePause.setOnFinished(E -> {
                    failTip.setVisible(false);
                });
                visiblePause.play();
            }
        }
    }

    @FXML
    private void checkStudent(ActionEvent event) {
        if (studentList.getSelectionModel().getSelectedIndex() != -1) {
            popUpPage.setParentScene(pane);
            goPage.studentName = studentList.getSelectionModel().getSelectedItem();
            pup.open("/view_history.fxml");
        } else {
            checkTips.setTextFill(Color.RED);
            checkTips.setText("Check failed, Please select a student first");
            checkTips.setVisible(true);
        }
    }

    private void initialBarChart() {
        difficultyChart.setAnimated(false);
        Series<Number, String> series = new XYChart.Series<Number, String>();
        String[] difficulty = new String[]{"Easy", "Medium", "Hard"};
        int[][] scores = new int[][]{goPage.getAllQuizAverageScoreOfEachDifficultyLastMouth(),
            goPage.getAllQuizAverageScoreOfEachDifficultyLastQuater(),
            goPage.getAllQuizAverageScoreOfEachDifficultyLastYear()};
        int[] score = scores[goPage.quizzsReviewSelectOfInstructor];
        for (int i = 0; i < 3; i++) {
            series.getData().add(new Data<Number, String>(score[i], difficulty[i]));
        }
        difficultyChart.getData().add(series);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        goPage = GoPage.getGoPage();
        //get some data from serve
        String period = (GoPage.getGoPage().quizzsReviewSelectOfInstructor == 0)
                ? "-last month"
                : (GoPage.getGoPage().quizzsReviewSelectOfInstructor == 1)
                        ? "-last quarter" : "-last year";

        reviewTimeLabel.setText(period);

        goPage.numberOfQuizzesTakenOfAll = goPage.communicateWithServe.getLastNumberOfALLQuizzes();
        goPage.averageScoreOfAll = goPage.communicateWithServe.getlastAverageOfALLQuizzes();
        //some initial
        quizzesNumber.setText(goPage.numberOfQuizzesTakenOfAll[goPage.quizzsReviewSelectOfInstructor] + "");
        averageScore.setText(goPage.averageScoreOfAll[goPage.quizzsReviewSelectOfInstructor] + "");

        passOrFail.setItems(FXCollections.observableArrayList("Students Passed", "Students Failed"));

        // select initial
        initialBarChart();
        // ListView default
        passOrFail.getSelectionModel().select(0);
        goPage.getPassedList(GoPage.getGoPage().quizzsReviewSelectOfInstructor);

        studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));

        // Fail or Pass list initial
        passOrFail.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value) {
                checkTips.setText("Select to check student's quiz report.");
                checkTips.setTextFill(Color.WHITE);
                if (new_value.intValue() == 0) {
                    goPage.getPassedList(GoPage.getGoPage().quizzsReviewSelectOfInstructor);
                    System.out.println("goPage.studentViewListForInstructor.length: " + goPage.studentViewListForInstructor.length);
                    studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));
                } else if (new_value.intValue() == 1) {
                    goPage.getFailedList(GoPage.getGoPage().quizzsReviewSelectOfInstructor);
                    System.out.println("goPage.studentViewListForInstructor.length: " + goPage.studentViewListForInstructor.length);
                    studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));
                }
            }
        });
//        successTip.requestFocus();
        wholeScrollPane.setVvalue(0.0);
    }

}
