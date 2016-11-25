/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import implcommunicate.TestCommunicate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.QuizOfStudent;

/**
 *
 * @author mica
 */

public class GoPage {
	private static GoPage gopage = new GoPage();
	// some global date
	public String studentName;
	public String userName;
	public QuizOfStudent quizOfCurrentCheck;
	public int numberOfCurrentQuiz = 1;
	public TestCommunicate communicateWithServe = new TestCommunicate();
	// some select date between pages
	public int quizzsReviewSelectOfInstructor = -1;
	public int quizDifficultyOfStudentSelect = -1;// 0-easy 1-Mediem 2-Hard 3-Mixed
												// -1-nochoice
	public int questionNumberFromQuizSetting = 10;
	public HistoryRecord[] historyRecordItems;
	public String[] studentViewListForInstructor;//instructor_reviw_quizzs_detail,student passed and failed
	//instructor_review_quizzes
	public int[] numberOfQuizzesTakenOfAll;
	public int[] averageScoreOfAll;

	public String back = "student_panel.fxml";
	public double backX = 600;
	public double backY = 400;

	private GoPage() {
	}

	public static GoPage getGoPage() {
		return gopage;
	}

	// goPage
	public void goPage(String FXMLName, Node node, int... args) {
		Scene scene;
		int x = 600;
		int y = 400;
		if (args.length >= 2) {
			x = args[0];
			y = args[1];
		}
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource(FXMLName)), x, y);
			// scene.getStylesheets().add("./src/javafxapplication1/no-divider.css");
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			Logger.getLogger(GoPage.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	// quiz_setting.
	public void getRandomQuiz() {
		Answer[] answerOfStudent = new Answer[questionNumberFromQuizSetting];
		for (int i = 0; i < answerOfStudent.length; i++) {
			answerOfStudent[i] = new Answer();
		}
		Date startDate = new Date();
		quizOfCurrentCheck = new QuizOfStudent(studentName, communicateWithServe.getRandomQuestionListOfQuiz(
				quizDifficultyOfStudentSelect, questionNumberFromQuizSetting), answerOfStudent, startDate, startDate);
		quizOfCurrentCheck.quizDifficulty = quizDifficultyOfStudentSelect;
	}
	public void recordQuizResultToServe() {
		communicateWithServe.recordQuizResultToServe(quizOfCurrentCheck);
	}
	
	// Student_view_history
	public void getStudentHistoryRecordFromServe() {
		historyRecordItems = communicateWithServe.getHistoryRecordFromServeByStudentName(studentName);
	}

	public void getQuizFromServeById(String quizId) {
		quizOfCurrentCheck = communicateWithServe.getQuizByQuizId(quizId, studentName);
	}
	
	//student_general_report
	public int[] getOneStudentScoresOfAllRecord() {
		return communicateWithServe.getStudentAllRecordScoreByStudentName(studentName);
	}

	public String[] getOneStudentRecordDate() {
		return communicateWithServe.getStudentRecordDateByStudentName(studentName);
	}
	
	public int[] getStudentAverageScoreOfAllDifficulty() {
		//three difficulty average score
		return communicateWithServe.getStudentAverageScoreOfThreeDifficultyByStudentName(studentName);
	}
	
	//Instructor Quizzes review
	public int[] getAllQuizAverageScoreOfEachDifficultyLastMouth() {
		return communicateWithServe.getAllQuizAverageScoreOfEachDifficultyInLastMouth();
	}
	
	public int[] getAllQuizAverageScoreOfEachDifficultyLastQuater() {
		return communicateWithServe.getAllQuizAverageScoreOfEachDifficultyInLastQuater();
	}
	
	public int[] getAllQuizAverageScoreOfEachDifficultyLastYear() {
		return communicateWithServe.getAllQuizAverageScoreOfEachDifficultyInLastYear();
	}
	
	public void getStudentFailedListLastMouth() {
		this.studentViewListForInstructor = communicateWithServe.getStudentFailedListOfLastMouth();
	}

	public void getStudentFailedListLastQuarter() {
		this.studentViewListForInstructor = communicateWithServe.getStudentFailedListOfLastQuarter();
	}

	public void getStudentFailedListLastYear() {
		this.studentViewListForInstructor = communicateWithServe.getStudentFailedListOfLastYear();
	}

	public void getStudentPassedListOfLastMouth() {
		this.studentViewListForInstructor = communicateWithServe.getStudentPassedListOfLastMouth();
	}

	public void getStudentPassedListOfLastQuarter() {
		this.studentViewListForInstructor = communicateWithServe.getStudentPassedListOfLastQuarter();
	}

	public void getStudentPassedListOfLastYear() {
		this.studentViewListForInstructor = communicateWithServe.getStudentPassedListOfLastYear();
	}

	public void getPassedList(int indexOfDuration) {
		switch (indexOfDuration) {
		case 0:
			getStudentPassedListOfLastMouth();
			break;
		case 1:
			getStudentPassedListOfLastQuarter();
			break;
		case 2:
			getStudentPassedListOfLastYear();
			break;
		default:
			break;
		}
	}

	public void getFailedList(int indexOfDuration) {
		switch (indexOfDuration) {
		case 0:
			getStudentFailedListLastMouth();
			break;
		case 1:
			getStudentFailedListLastQuarter();
			break;
		case 2:
			getStudentFailedListLastYear();
			break;
		default:
			break;
		}
	}

	// instructor student quiz report check
	public void checkStudentQuizReport(String studentName, int indexOfDuration) {
		quizOfCurrentCheck = communicateWithServe.getQuizByStudentNameAndTimeType(studentName, indexOfDuration);
	}
}
