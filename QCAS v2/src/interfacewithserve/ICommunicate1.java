package interfacewithserve;


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
      
}
