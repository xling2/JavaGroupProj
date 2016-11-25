package implcommunicate;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import interfacewithserve.ICommunicate2;
import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;

public class TestCommunicate extends Communicate1 implements ICommunicate2{

	@Override
	public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recordQuizResultToServe(QuizOfStudent quizResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean login(int loginType, String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Question[] importQuestionFromCSV(File CSVFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuizOfStudent getQuizByQuizId(String quizId, String StudentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getStudentAllRecordScoreByStudentName(String studentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getStudentRecordDateByStudentName(String studentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String studentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addStudent(String text) {
		// TODO Auto-generated method stub
		return false;
	}

}
