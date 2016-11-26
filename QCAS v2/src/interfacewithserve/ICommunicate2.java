package interfacewithserve;

import java.io.File;
import java.util.ArrayList;

import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;

public interface ICommunicate2 {
	public abstract Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber);

	public abstract void recordQuizResultToServe(QuizOfStudent quizResult);

	public abstract boolean login(int loginType, String userName, String password);

	public abstract Question[] importQuestionFromCSV(File CSVFile);// success->true
																	// fail->false;

	public abstract HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName);

	public abstract QuizOfStudent getQuizByQuizId(String quizId, String StudentName);

	public abstract int[] getStudentAllRecordScoreByStudentName(String studentName);// sort
																					// by
																					// finish
																					// time

	public abstract String[] getStudentRecordDateByStudentName(String studentName);// sort
																					// by
																					// finish
																					// time

	public abstract int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String studentName);// [easy,medium,hard]

	public abstract boolean addStudent(String text);
        
        // Added by Ethan
        public abstract boolean deleteStudent(String andrewID);
        
        // Added by Ethan, return an ArrayList with all students' Andrew ID
        public abstract ArrayList<String> getAllStudent();

}
