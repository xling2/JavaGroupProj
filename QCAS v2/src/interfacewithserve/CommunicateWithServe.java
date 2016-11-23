package interfacewithserve;

import java.io.File;

import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;

public interface CommunicateWithServe {
       public abstract Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber);
	public void recordQuizResultToServe(QuizOfStudent quizResult);
	public boolean login(int loginType, String userName, String password);//loginType 1:Student 2:Instructor
	public abstract Question[] importQuestionFromCSV(File CSVFile);//success->true  fail->false;
	public abstract HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName);
	public abstract QuizOfStudent getQuizByQuizId(int quizId);
	public abstract int[] getStudentAllRecordScoreByStudentName(String studentName);//sort by finish time
	public abstract String[] getStudentRecordDateByStudentName(String studentName);//sort by finish time
	public abstract int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String studentName);//[easy,medium,hard]
	public abstract String[] getStudentFailedListOfLastMouth();//login id name
	public abstract String[] getStudentFailedListOfLastQuarter();
	public abstract String[] getStudentFailedListOfLastYear();
	public abstract String[] getStudentPassedListOfLastMouth();
	public abstract String[] getStudentPassedListOfLastQuarter();
	public abstract String[] getStudentPassedListOfLastYear();
	public abstract QuizOfStudent getQuizByStudentNameAndTimeType(String studentName, int timeType);//timeType:0-LastMouth,1:Last Quarter,2:Last Year
        public abstract boolean addStudent(String text);
	public abstract int[] getLastNumberOfALLQuizzes();
	public abstract int[] getlastAverageOfALLQuizzes();
	public abstract int[] geteasyAverageScoreOfThreeLastTime();
	public abstract int[] getMediumAverageScoreOfThreeLastTime();
	public abstract int[] getHardAverageScoreOfThreeLastTime();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastYear();
	public abstract void deleteById(int questionID);        

}
