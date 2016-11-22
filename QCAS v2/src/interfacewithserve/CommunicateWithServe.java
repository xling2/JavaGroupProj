package interfacewithserve;

import java.io.File;

import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.User;

public interface CommunicateWithServe {
         // xingyu ok
       public abstract Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber);
        
        // xingyu
	public void recordQuizResultToServe(QuizOfStudent quizResult);
	
        // don't need
        public boolean login(int loginType, int id, String password);//loginType 1:Student 2:Instructor
	// 
        public abstract String getUserNameById(int loginType, int userId);
	//creat a quiz question list for student   quizDifficultyOfUserSelect:0-easy 1-Medium 2-Hard 3-Mixed
	
	
        // xingyu ok
        public abstract boolean importQuestionFromCSV(File CSVFile);//success->true  fail->false;
        
        public abstract void deleteById(int questionID);
        
        // xingyu
	public abstract HistoryRecord[] getHistoryRecordFromServeByStudentID(int studentId);
	
        // xingyu
        public abstract QuizOfStudent getQuizByQuizId(int quizId);// number of questions
	
        // xingyu
        public abstract int[] getStudentAllRecordScoreById(int id);//sort by finish time
	// xingyu
        public abstract String[] getStudentRecordDateById(int id);//sort by finish time
	// xingyu
        public abstract int[] getStudentAverageScoreOfThreeDifficultyById(int id);//[easy,medium,hard]
	
        
        public abstract User[] getStudentFailedListOfLastMouth();
	public abstract User[] getStudentFailedListOfLastQuarter();
	public abstract User[] getStudentFailedListOfLastYear();
	public abstract User[] getStudentPassedListOfLastMouth();
	public abstract User[] getStudentPassedListOfLastQuarter();
	public abstract User[] getStudentPassedListOfLastYear();
        
        // xingyu
        public abstract QuizOfStudent getQuizByStudentIdAndTimeType(int id, int timeType);//timeType:0-LastMouth,1:Last Quarter,2:Last Year
	

         // xingyu ok
        public abstract boolean addStudent(String text);
      
	public abstract int[] getLastNumberOfALLQuizzes();
	public abstract int[] getlastAverageOfALLQuizzes();
	public abstract int[] geteasyAverageScoreOfThreeLastTime();
	public abstract int[] getMediumAverageScoreOfThreeLastTime();
	public abstract int[] getHardAverageScoreOfThreeLastTime();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater();
	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastYear();
	
}
