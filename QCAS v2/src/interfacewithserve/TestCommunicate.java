package interfacewithserve;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;

public class TestCommunicate implements CommunicateWithServe {

	@Override
	public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
		// TODO Auto-generated method stub
		Question[] questions = new Question[questionNumber];
		Random rd = new Random();
		for (int i = 0; i < questions.length; i++) {
			questions[i] = new Question(rd.nextInt(4), rd.nextInt(20) + 1, rd.nextInt(3), "this is a question",
					new String[] { "A.choices1", "B.choices2", "C.choices3", "D.choices4" }, "A");
		}
		return questions;
	}

	@Override
	public void recordQuizResultToServe(QuizOfStudent quizResult) {
		// TODO Auto-generated method stub
		System.out.println("TestCommunicate.recordQuizResultToServe");
	}

	@Override
	public boolean login(int loginType, String id, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Question[] importQuestionFromCSV(File CSVFile) {
		// TODO Auto-generated method stub
		return getRandomQuestionListOfQuiz(0, 20);
	}

	@Override
	public HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName) {
		// TODO Auto-generated method stub
		int x = 20;
		Date now = new Date();
		HistoryRecord[] historyRecord = new HistoryRecord[x];
		for (int i = 0; i < x; i++) {
			historyRecord[i] = new HistoryRecord(x+"", new Date(now.getTime() + 100000 * x));
		}
		return historyRecord;
	}

	@Override
	public QuizOfStudent getQuizByQuizId(String quizId, String studentName) {
		// TODO Auto-generated method stub
		String student = "Shelly";
		int questionNumber = 20;
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, getRandomQuestionListOfQuiz(0, answerOfStudent.length), 
				answerOfStudent, startDate, finishDate);
		for (int i = 0; i < questionNumber; i++) {
			int questionType = quizOfStudent.questionsOfQuiz[i].questionType;
			switch (questionType) {
			case 0:
				answerOfStudent[i] = new Answer(1, questionType, new ArrayList<>());
				break;
			case 1:
				answerOfStudent[i] = new Answer(1, questionType, 1);
				break;
			case 2:
				answerOfStudent[i] = new Answer(1, questionType, true);
				break;
			case 3:
				answerOfStudent[i] = new Answer(1, questionType, "A");
				break;
			default:
				break;
			}
		}
		return quizOfStudent;
	}

	@Override
	public int[] getStudentAllRecordScoreByStudentName(String name) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public String[] getStudentRecordDateByStudentName(String name) {
		// TODO Auto-generated method stub
		return new String[] { "2011-10-16", "2011-10-17", "2011-10-18" };
	}

	@Override
	public int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String name) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public String[] getStudentFailedListOfLastMouth() {
		// TODO Auto-generated method stub
		String[] studentFailedList = new String[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = "YangLiu|Mouth" + i;
		}
		return studentFailedList;
	}

	@Override
	public String[] getStudentFailedListOfLastQuarter() {
		// TODO Auto-generated method stub
		String[] studentFailedList = new String[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = "YangLiu|quarter" + i;
		}
		return studentFailedList;
	}

	@Override
	public String[] getStudentFailedListOfLastYear() {
		String[] studentFailedList = new String[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = "YangLiu|Year" + i;
		}
		return studentFailedList;
	}

	@Override
	public String[] getStudentPassedListOfLastMouth() {
		String[] studentPassedList = new String[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = "Shelly|Mouth" + i;
		}
		return studentPassedList;
	}

	@Override
	public String[] getStudentPassedListOfLastQuarter() {
		String[] studentPassedList = new String[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = "Shelly|quarter" + i;
		}
		return studentPassedList;
	}

	@Override
	public String[] getStudentPassedListOfLastYear() {
		String[] studentPassedList = new String[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = "Shelly|year" + i;
		}
		return studentPassedList;
	}

	@Override
	public QuizOfStudent getQuizByStudentNameAndTimeType(String name, int timeType) {
		// TODO Auto-generated method stub
		String student = name;
		int questionNumber = 20;
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, getRandomQuestionListOfQuiz(0, answerOfStudent.length), 
				answerOfStudent, startDate, finishDate);
		for (int i = 0; i < questionNumber; i++) {
			int questionType = quizOfStudent.questionsOfQuiz[i].questionType;
			switch (questionType) {
			case 0:
				answerOfStudent[i] = new Answer(1, questionType, new ArrayList<>());
				break;
			case 1:
				answerOfStudent[i] = new Answer(1, questionType, 1);
				break;
			case 2:
				answerOfStudent[i] = new Answer(1, questionType, true);
				break;
			case 3:
				answerOfStudent[i] = new Answer(1, questionType, "A");
				break;
			default:
				break;
			}
		}
		return quizOfStudent;
	}

	@Override
	public boolean addStudent(String text) {
		// TODO Auto-generated method stub
		System.out.println("addStudent");
		return false;
	}

	@Override
	public int[] getLastNumberOfALLQuizzes() {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public int[] getlastAverageOfALLQuizzes() {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public int[] geteasyAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 40, 80, 90 };
	}

	@Override
	public int[] getMediumAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 20, 40, 70 };
	}

	@Override
	public int[] getHardAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 20, 20, 10 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
		// TODO Auto-generated method stub
		return new int[] { 10, 20, 60 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
		// TODO Auto-generated method stub
		return new int[] { 30, 50, 90 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
		// TODO Auto-generated method stub
		return new int[] { 40, 90, 100 };
	}

	@Override
	public void deleteById(int questionID) {
		// TODO Auto-generated method stub
		System.out.println("delete");
	}

}
