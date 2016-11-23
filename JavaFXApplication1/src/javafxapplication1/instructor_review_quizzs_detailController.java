/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pdfutil.PDFGeneral;

/**
 *
 * @author mica
 */
public class instructor_review_quizzs_detailController implements Initializable {

	private final static String[] TIMESTRING = new String[] { "Last month record", "Last quarter record",
			"Last year record", "General record" };
	@FXML
	private Pane pane;
	@FXML
	private ChoiceBox<String> selectDetail;
	@FXML
	private Button back;

	@FXML
	private void backAction(ActionEvent event) throws IOException {
		GoPage.getGoPage().goPage("Instructer_panel.fxml", pane);
	}

	@FXML
	private Label successedTips;
	@FXML
	private Label tips;

	private GoPage goPage;

	@FXML
	private void export(ActionEvent event) {
		File folder = new File("./Ouptput");
		if (!folder.exists())
			folder.mkdirs();
		Document document = new Document();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
		String documentName = goPage.userName + df.format(new Date()) + "quizzes_review"
				+ TIMESTRING[selectDetail.getSelectionModel().getSelectedIndex()] + ".PDF";
		File exportPDF = new File(folder + "/" + documentName);
		if (exportPDF.exists()) {
			successedTips.setVisible(false);
			tips.setText("Export failed, report already exist.");
			tips.setVisible(true);
		} else {
			try {
				PdfWriter.getInstance(document, new FileOutputStream(folder + "/" + documentName));
				document.open();
				PDFGeneral.addTitleLine(document, TIMESTRING[selectDetail.getSelectionModel().getSelectedIndex()]);
				PDFGeneral.addContentLine(document, "Number of quizzes taken : " + quizzesNumber.getText());
				PDFGeneral.addSubtitleLine(document, "Average Score : " + averageScore.getText());
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

	@FXML
	private Label checkTips;

	@FXML
	private void checkStudent(ActionEvent event) {
		if (studentList.getSelectionModel().getSelectedIndex() != -1) {
			goPage.checkStudentQuizReport(studentList.getSelectionModel().getSelectedItem(),
					selectDetail.getSelectionModel().getSelectedIndex());
			goPage.back = "instructor_review_quizzs_detail.fxml";
			goPage.backX = tips.getScene().getWidth();
			goPage.backY = tips.getScene().getHeight();
			goPage.goPage("student_quiz_report_onetime.fxml", tips, 438, 1000);
		} else {
			checkTips.setTextFill(Color.RED);
			checkTips.setText("Check failed, Please select a student first");
			checkTips.setVisible(true);
		}
	}

	@FXML
	private BarChart<Number, String> difficultyChart;

	private void initialBarChart() {
		Series<Number, String> series = new XYChart.Series<Number, String>();
		String[] difficulty = new String[] { "Easy", "Medium", "Hard" };
		int[][] scores = new int[][] { goPage.getAllQuizAverageScoreOfEachDifficultyLastMouth(),
				goPage.getAllQuizAverageScoreOfEachDifficultyLastQuater(),
				goPage.getAllQuizAverageScoreOfEachDifficultyLastYear() };
		int[] score = scores[goPage.quizzsReviewSelectOfInstructor];
		for (int i = 0; i < 3; i++) {
			series.getData().add(new Data<Number, String>(score[i], difficulty[i]));
		}
		difficultyChart.getData().add(series);
	}

	@FXML
	private ListView<String> studentList;

	@FXML
	private ChoiceBox<String> passOrFail;

	@FXML
	private Label quizzesNumber;
	@FXML
	private Label averageScore;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		goPage = GoPage.getGoPage();
		// get some data from serve
		goPage.numberOfQuizzesTakenOfAll = goPage.communicateWithServe.getLastNumberOfALLQuizzes();
		goPage.averageScoreOfAll = goPage.communicateWithServe.getlastAverageOfALLQuizzes();
		// some initial
		quizzesNumber.setText(goPage.numberOfQuizzesTakenOfAll[goPage.quizzsReviewSelectOfInstructor] + "");
		averageScore.setText(goPage.averageScoreOfAll[goPage.quizzsReviewSelectOfInstructor] + "");
		selectDetail.setItems(FXCollections.observableArrayList(TIMESTRING));
		passOrFail.setItems(FXCollections.observableArrayList("Students Passed", "Students Failed"));
		selectDetail.getSelectionModel().select(goPage.quizzsReviewSelectOfInstructor);
		// select initial
		selectDetail.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value) {
				goPage.quizzsReviewSelectOfInstructor = new_value.intValue();
				if (new_value.intValue() == 3) {
					goPage.goPage("instructor_review_quizzs_general.fxml", pane, 438, 1000);
				} else {
					goPage.goPage("instructor_review_quizzs_detail.fxml", pane, 438, 1000);
				}
			}
		});
		initialBarChart();
		// ListView default
		passOrFail.getSelectionModel().select(0);
		goPage.getPassedList(selectDetail.getSelectionModel().getSelectedIndex());
		studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));

		// Fail or Pass list initial
		passOrFail.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value) {
				checkTips.setText("Select to check student's quiz report.");
				checkTips.setTextFill(Color.WHITE);
				if (new_value.intValue() == 0) {
					System.out.println("0");
					goPage.getPassedList(selectDetail.getSelectionModel().getSelectedIndex());
					studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));
				} else if (new_value.intValue() == 1) {
					System.out.println("1");
					goPage.getFailedList(selectDetail.getSelectionModel().getSelectedIndex());
					studentList.setItems(FXCollections.observableArrayList(goPage.studentViewListForInstructor));
				}
			}
		});

	}

}
