/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import javafx.animation.PauseTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class instructor_review_quizzs_general_detailController implements Initializable {

    @FXML
    private Label numberOfLastMouth;
    @FXML
    private Label numberOfLastQuater;
    @FXML
    private Label numberOfLastYear;
    @FXML
    private Label averageScoreOfLastMouth;
    @FXML
    private Label averageScoreOfLastQuater;
    @FXML
    private Label averageScoreOfLastYear;

    @FXML
    private Label successTip;
    @FXML
    private Label failTip;
    @FXML
    private Label existLabel;

    private GoPage goPage;

    @FXML
    private void export(ActionEvent event) {
        File folder = new File(System.getProperty("user.home"), "Desktop");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String documentName = goPage.userName
                + " " + df.format(new Date()) + "_general_report" + ".PDF";
        File exportPDF = new File(folder + "/" + documentName);
        String chartName = goPage.userName + "quizzes_review " + ".png";

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
                PDFGeneral.addTitleLine(document, "Quizzes review - General record");
                PDFGeneral.addSubtitleLine(document, "Number of quizzes taken in -");
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last month", 20)
                        + numberOfLastMouth.getText());
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last quater", 21)
                        + numberOfLastQuater.getText());
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last year", 22)
                        + numberOfLastYear.getText());
                PDFGeneral.addSubtitleLine(document, "Average score over -");
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last month", 20)
                        + averageScoreOfLastMouth.getText());
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last quater", 21)
                        + averageScoreOfLastQuater.getText());
                PDFGeneral.addContentLine(document, PDFGeneral.getFormatString("Last year", 22)
                        + averageScoreOfLastYear.getText());
                PDFGeneral.addSubtitleLine(document, "Score vs. Difficulty");
                PDFGeneral.addChartGraph(document, lineChart, chartFile);
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
    private LineChart<String, Number> lineChart;

    private void initialLineChart() {
        Series<String, Number> series1 = new XYChart.Series<String, Number>();
        Series<String, Number> series2 = new XYChart.Series<String, Number>();
        Series<String, Number> series3 = new XYChart.Series<String, Number>();
        series1.setName("Easy");
        series2.setName("Medium");
        series3.setName("Hard");
        String[] diff = {"Last Month", "Last Quarter", "Last Year"};
        //String[] date = goPage.getOneStudentRecordDate();
        int[] scoreEasy = goPage.geteasyAverageScoreOfThreeLastTime();
        int[] scoreMedium = goPage.getMediumAverageScoreOfThreeLastTime();
        int[] scoreHard = goPage.getHardAverageScoreOfThreeLastTime();
        for (int i = 0; i < scoreEasy.length; i++) {
            series1.getData().add(new Data<String, Number>(diff[i], scoreEasy[i]));
        }
        lineChart.getData().add(series1);
        for (int i = 0; i < scoreMedium.length; i++) {
            series2.getData().add(new Data<String, Number>(diff[i], scoreMedium[i]));
        }
        lineChart.getData().add(series2);
        for (int i = 0; i < scoreHard.length; i++) {
            series3.getData().add(new Data<String, Number>(diff[i], scoreHard[i]));
        }
        lineChart.getData().add(series3);

    }

    @SuppressWarnings("unchecked")
    private void initialBarChart() {
        lineChart.setAnimated(false);
        Series<String, Number> seriesEasy = new XYChart.Series<>();
        Series<String, Number> seriesMedium = new XYChart.Series<>();
        Series<String, Number> seriesHard = new XYChart.Series<>();
        seriesEasy.setName("Easy");
        seriesMedium.setName("Medium");
        seriesHard.setName("Hard");
        String[] lastTimeType = new String[]{"Last month", "Last Quater", "Last Year"};
        for (int i = 0; i < 3; i++) {
            seriesEasy.getData().add(new Data<>(lastTimeType[i], easyAverageScoreOfThreeLastTime[i]));
            seriesMedium.getData().add(new Data<>(lastTimeType[i], mediumAverageScoreOfThreeLastTime[i]));
            seriesHard.getData().add(new Data<>(lastTimeType[i], hardAverageScoreOfThreeLastTime[i]));
        }
        lineChart.getData().addAll(seriesEasy, seriesMedium, seriesHard);
    }

    private int[] easyAverageScoreOfThreeLastTime;
    private int[] mediumAverageScoreOfThreeLastTime;
    private int[] hardAverageScoreOfThreeLastTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();

        // getSomeData from serve
        goPage.numberOfQuizzesTakenOfAll = goPage.communicateWithServe.getLastNumberOfALLQuizzes();
        goPage.averageScoreOfAll = goPage.communicateWithServe.getlastAverageOfALLQuizzes();
        easyAverageScoreOfThreeLastTime = goPage.communicateWithServe.geteasyAverageScoreOfThreeLastTime();
        mediumAverageScoreOfThreeLastTime = goPage.communicateWithServe.getMediumAverageScoreOfThreeLastTime();
        hardAverageScoreOfThreeLastTime = goPage.communicateWithServe.getHardAverageScoreOfThreeLastTime();
        //initial some widget rely on serve data
        initialBarChart();
        //initialLineChart();
        numberOfLastMouth.setText(goPage.numberOfQuizzesTakenOfAll[0] + "");
        numberOfLastQuater.setText(goPage.numberOfQuizzesTakenOfAll[1] + "");
        numberOfLastYear.setText(goPage.numberOfQuizzesTakenOfAll[2] + "");
        averageScoreOfLastMouth.setText(goPage.averageScoreOfAll[0] + "");
        averageScoreOfLastQuater.setText(goPage.averageScoreOfAll[1] + "");
        averageScoreOfLastYear.setText(goPage.averageScoreOfAll[2] + "");
        //change lineChart legend style
    }

}
