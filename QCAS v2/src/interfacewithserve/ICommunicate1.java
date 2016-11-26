package interfacewithserve;

import java.util.ArrayList;
import utilclass.Question;
import utilclass.QuizOfStudent;

public interface ICommunicate1 {
	public abstract String[] getStudentFailedListOfLastMouth();// login id name

	public abstract String[] getStudentFailedListOfLastQuarter();

	public abstract String[] getStudentFailedListOfLastYear();

	public abstract String[] getStudentPassedListOfLastMouth();

	public abstract String[] getStudentPassedListOfLastQuarter();

	public abstract String[] getStudentPassedListOfLastYear();

	public abstract int[] getLastNumberOfALLQuizzes();

	public abstract int[] getlastAverageOfALLQuizzes();

	public abstract int[] geteasyAverageScoreOfThreeLastTime();

	public abstract int[] getMediumAverageScoreOfThreeLastTime();

	public abstract int[] getHardAverageScoreOfThreeLastTime();

	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth();

	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater();

	public abstract int[] getAllQuizAverageScoreOfEachDifficultyInLastYear();

	public abstract void deleteById(int questionID);

	public abstract QuizOfStudent getQuizByStudentNameAndTimeType(String studentName, int timeType);// timeType:0-LastMouth,1:Last
	// Quarter,2:Last
	// Year
        
        // Added by Ethan
        public abstract Question[] getAllQuestion();
}
