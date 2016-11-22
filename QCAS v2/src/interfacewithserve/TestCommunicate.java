package interfacewithserve;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.User;

public class TestCommunicate implements CommunicateWithServe {

	@Override
	public Question[] questionsOfQuizByIds(int[] questionsId) {
		// TODO Auto-generated method stub
		Question[] questions = new Question[questionsId.length];
		Random rd = new Random();
		for (int i = 0; i < questions.length; i++) {
			questions[i] = new Question(rd.nextInt(4), questionsId[i], rd.nextInt(3), "this is a question",
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
	public boolean login(int loginType, int id, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUserNameById(int loginType, int userId) {
		// TODO Auto-generated method stub
		return "Shelly";
	}

        @Override
	public void deleteById(int questionID) {
		// TODO Auto-generated method stub
		System.out.println("delete");
	}
        
	@Override
	public int[] getRandomQuestionIdListOfQuiz(int quizDifficultyOfUserSelect, int questionNumber) {
		// TODO Auto-generated method stub
		Random rd = new Random();
		int[] questionIds = new int[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			questionIds[i] = rd.nextInt(20);
		}
		return questionIds;
	}

	@Override
	public boolean importQuestionFromCSV(File CSVFile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HistoryRecord[] getHistoryRecordFromServeByStudentID(int studentUserId) {
		// TODO Auto-generated method stub
		int x = 20;
		Date now = new Date();
		HistoryRecord[] historyRecord = new HistoryRecord[x];
		for (int i = 0; i < x; i++) {
			historyRecord[i] = new HistoryRecord(x, new Date(now.getTime() + 100000 * x));
		}
		return historyRecord;
	}

	@Override
	public QuizOfStudent getQuizByQuizId(int quizId) {
		// TODO Auto-generated method stub
		User student = new User("Shelly", 123456);
		int questionNumber = quizId;
		int[] questionsIdOfQuiz = new int[questionNumber];
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			questionsIdOfQuiz[i] = i;
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, questionsIdOfQuiz, answerOfStudent, startDate, finishDate);
		quizOfStudent.getQuestionFromServe(this);
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
	public int[] getStudentAllRecordScoreById(int id) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public String[] getStudentRecordDateById(int id) {
		// TODO Auto-generated method stub
		return new String[] { "2011-10-16", "2011-10-17", "2011-10-18" };
	}

	@Override
	public int[] getStudentAverageScoreOfThreeDifficultyById(int id) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public User[] getStudentFailedListOfLastMouth() {
		// TODO Auto-generated method stub
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|Mouth" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentFailedListOfLastQuarter() {
		// TODO Auto-generated method stub
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|quarter" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentFailedListOfLastYear() {
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|Year" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentPassedListOfLastMouth() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|Mouth" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public User[] getStudentPassedListOfLastQuarter() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|quarter" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public User[] getStudentPassedListOfLastYear() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|year" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public QuizOfStudent getQuizByStudentIdAndTimeType(int id, int timeType) {
		// TODO Auto-generated method stub
		String name = this.getUserNameById(1, id);
		User student = new User(name, id);
		int questionNumber = 20;
		int[] questionsIdOfQuiz = new int[questionNumber];
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			questionsIdOfQuiz[i] = i;
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, questionsIdOfQuiz, answerOfStudent, startDate, finishDate);
		quizOfStudent.getQuestionFromServe(this);
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

}
