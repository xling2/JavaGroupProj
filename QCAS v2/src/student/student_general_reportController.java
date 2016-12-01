/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.animation.PauseTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.util.Duration;
import qcas.GoPage;
import pdfutil.PDFGeneral;

/**
 *
 * @author mica
 */
public class student_general_reportController implements Initializable {

    @FXML
    private Label successTip;
    @FXML
    private Label failTip;
    @FXML
    private Label existLabel;
    @FXML
    private Label studentID;
    @FXML
    private Label quizzesNumber;
    @FXML
    private Label averageScore;

    @FXML
    private BarChart<Number, String> difficultyChart;
    @FXML
    private LineChart<String, Number> quizRecord;

    private GoPage goPage;

    @FXML
    private void backAction(ActionEvent event) {
        GoPage.getGoPage().goPage("/view_history.fxml", studentID);
    }

    @FXML
    private void export(ActionEvent event) {
        File folder = new File(System.getProperty("user.home"), "Desktop");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String documentName = goPage.studentName + 
                " " + df.format(new Date()) + "_general_report" + ".PDF";
        File exportPDF = new File(folder + "/" + documentName);
        String chartName = goPage.studentName + "_chart" + ".png";
        File chartFile = new File(folder + "/" + chartName);
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
                PDFGeneral.addTitleLine(document, "Quiz Report");
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                
                PDFGeneral.addContentLine(document, String.format("%s%s",
                        "Student name:", goPage.studentName));
                PDFGeneral.addContentLine(document, String.format("%s%s",
                        "Number of quizzes taken:", quizzesNumber.getText()));
                PDFGeneral.addContentLine(document, String.format("%s%s",
                        "Average score:", averageScore.getText()));
                PDFGeneral.addSubtitleLine(document, "Score vs. Difficulty");
                PDFGeneral.addChartGraph(document, difficultyChart, chartFile);
                PDFGeneral.addSubtitleLine(document, "Quiz Record");
                PDFGeneral.addChartGraph(document, quizRecord, chartFile);
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

    private void initialBarChart() {
        difficultyChart.setAnimated(false);
        Series<Number, String> series = new XYChart.Series<Number, String>();
        String[] difficulty = new String[]{"Easy", "Medium", "Hard"};
        int[] scores = goPage.getStudentAverageScoreOfAllDifficulty();
        for (int i = 0; i < 3; i++) {
            series.getData().add(new Data<Number, String>(scores[i], difficulty[i]));
        }
        difficultyChart.getData().add(series);
    }

    private void initialLineChart() {
        quizRecord.setAnimated(false);
        Series<String, Number> series = new XYChart.Series<String, Number>();
        String[] date = goPage.getOneStudentRecordDate();
        int[] scoresOfAllRecord = goPage.getOneStudentScoresOfAllRecord();
        for (int i = 0; i < scoresOfAllRecord.length; i++) {
            series.getData().add(new Data<String, Number>(date[i], scoresOfAllRecord[i]));
        }
        quizRecord.getData().add(series);
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        studentID.setText(goPage.studentName);
        quizzesNumber.setText(goPage.getOneStudentScoresOfAllRecord().length + "");
        int totalScore = 0;
        for (int i = 0; i < goPage.getOneStudentScoresOfAllRecord().length; i++) {
            totalScore += goPage.getOneStudentScoresOfAllRecord()[i];
        }
        averageScore.setText(totalScore / goPage.getOneStudentScoresOfAllRecord().length + "");

        System.out.println("goPage.getOneStudentScoresOfAllRecord().length: "
                + goPage.getOneStudentScoresOfAllRecord().length);

        initialBarChart();
        initialLineChart();
    }
}
