package implcommunicate;

import interfacewithserve.ICommunicate1;
import java.util.ArrayList;
import utilclass.Question;
import utilclass.QuizOfStudent;

public class Communicate1 implements ICommunicate1 {

    @Override
    public String[] getStudentFailedListOfLastMouth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getStudentFailedListOfLastQuarter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getStudentFailedListOfLastYear() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getStudentPassedListOfLastMouth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getStudentPassedListOfLastQuarter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getStudentPassedListOfLastYear() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getLastNumberOfALLQuizzes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getlastAverageOfALLQuizzes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] geteasyAverageScoreOfThreeLastTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getMediumAverageScoreOfThreeLastTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getHardAverageScoreOfThreeLastTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(int questionID) {
        // TODO Auto-generated method stub

    }

    @Override
    public QuizOfStudent getQuizByStudentNameAndTimeType(String studentName, int timeType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Question[] getAllQuestion() {
        // For test purpose only
        String[] choices = new String[]{"a", "b", "c", "d"};
        Question q1 = new Question(1, 1, 1, "content here content here content here content here content here content here", choices, "a");
        Question q2 = new Question(0, 0, 2, "content here too", choices, "b");

        Question[] test = new Question[]{q1, q2};
        
        return test;
    }
}
