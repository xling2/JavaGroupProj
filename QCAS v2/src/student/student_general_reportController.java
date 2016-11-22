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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import qcas.GoPage;
import pdfutil.PDFGeneral;

/**
 *
 * @author mica
 */
public class student_general_reportController implements Initializable {
    
	@FXML
	private Label tips;
	@FXML
	private Label studentID;
    @FXML
    private Label quizzesNumber;
    @FXML
    private Label averageScore;  
    @FXML
    private Label successedTips;
    @FXML
    private BarChart<Number, String> difficultyChart;
    @FXML
    private LineChart<String, Number> quizRecord;
    
    private GoPage goPage;
    
    @FXML
    private void backAction(ActionEvent event){
        GoPage.getGoPage().goPage("/view_history.fxml", tips);
    }
    
    @FXML
    private void export(ActionEvent event){
		File folder = new File("./Ouptput");
		if(!folder.exists())folder.mkdirs();
		Document document = new Document();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
		String documentName = goPage.student.id + df.format(new Date()) + "_general_report" + ".PDF";
		File exportPDF = new File(folder+"/"+documentName);
		if(exportPDF.exists()){
			successedTips.setVisible(false);
			tips.setText("Export failed, report already exist.");
			tips.setVisible(true);
		}else{
			try {
				PdfWriter.getInstance(document, new FileOutputStream(folder+"/"+documentName));
				document.open();
				PDFGeneral.addTitleLine(document, "Quiz Report");
				df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				PDFGeneral.addContentLine(document, String.format("%s%s", 
						"StudentID:", goPage.student.id));
				PDFGeneral.addContentLine(document, String.format("%s%s", 
						"StudentName:", goPage.student.name));
				PDFGeneral.addContentLine(document, String.format("%s%s", 
						"Number of quizzes taken:", quizzesNumber.getText()));
				PDFGeneral.addContentLine(document, String.format("%s%s", 
						"Average score:", averageScore.getText()));
				document.close();
				successedTips.setText("Export successed, file in " + folder.getAbsolutePath() + "/" + documentName);
				tips.setVisible(false);
				successedTips.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				tips.setText("Export failed, Please check your config is right.");
				successedTips.setVisible(false);
				tips.setVisible(true);
			}
		}
    }
    
	private void initialBarChart() {
		Series<Number, String> series = new XYChart.Series<Number, String>();
		String[] difficulty = new String[]{"Easy", "Medium", "Hard"};
		int[] scores = goPage.getStudentAverageScoreOfAllDifficulty();
		for (int i = 0; i < 3; i++) {
			series.getData().add(new Data<Number, String>(scores[i], difficulty[i]));
		}
		difficultyChart.getData().add(series);
	}
    
    private void initialLineChart() {
    	Series<String, Number> series = new XYChart.Series<String, Number>();
    	String[] date = goPage.getOneStudentRecordDate();
    	int[] scoresOfAllRecord = goPage.getOneStudentScoresOfAllRecord();
    	for (int i = 0; i < scoresOfAllRecord.length; i++) {
			series.getData().add(new Data<String, Number>(date[i], scoresOfAllRecord[i]));
		}
    	quizRecord.getData().add(series);
	}
    
    @FXML
    private Label quizzesNumer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tips.setVisible(false);
        successedTips.setVisible(false);
        goPage = GoPage.getGoPage(); 
        studentID.setText(goPage.student.name);
        quizzesNumber.setText(goPage.getOneStudentScoresOfAllRecord().length+"");
        int totalScore = 0;
        for (int i = 0; i < goPage.getOneStudentScoresOfAllRecord().length; i++) {
			totalScore += goPage.getOneStudentScoresOfAllRecord()[i];
		}
        averageScore.setText(totalScore/goPage.getOneStudentScoresOfAllRecord().length+"");
        initialBarChart();
        initialLineChart();
    }
}
