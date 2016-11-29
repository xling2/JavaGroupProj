package implcommunicate;

import interfacewithserve.ICommunicate1;
import java.util.ArrayList;
import utilclass.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Communicate1 implements ICommunicate1 {
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    DBConnection con = new DBConnection();

    @Override
    public String[] getStudentFailedListOfLastMouth() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentFailLM();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray; 
    }

    @Override
    public String[] getStudentFailedListOfLastQuarter() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentFailLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    @Override
    public String[] getStudentFailedListOfLastYear() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentFailLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    @Override
    public String[] getStudentPassedListOfLastMouth() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentPassLM();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    @Override
    public String[] getStudentPassedListOfLastQuarter() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentPassLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    @Override
    public String[] getStudentPassedListOfLastYear() {
        con.getAndrewID();
        con.getResult();
        ArrayList <String> list = con.getStudentPassLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    @Override
    public int[] getLastNumberOfALLQuizzes() {
        con.getAndrewID();
        con.getResult();
        int numberTestLM = con.getNumberTestLM();
        int numberTestLQ = con.getNumberTestLQ();
        int numberTestLY = con.getNumberTestLY();
        int [] numberOfTest = {numberTestLM, numberTestLQ, numberTestLY};
        return numberOfTest;
    }

    @Override
    public int[] getlastAverageOfALLQuizzes() {
        con.getAndrewID();
        con.getResult();
        int avgScoreLM = con.getAvgScoreLM();
        int avgScoreLQ = con.getAvgScoreLQ();
        int avgScoreLY = con.getAvgScoreLY();
        int [] avgAll = {avgScoreLM, avgScoreLQ, avgScoreLY};
        return avgAll;
    }

    @Override
    public int[] geteasyAverageScoreOfThreeLastTime() {
        con.getAndrewID();
        con.getResult();
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreELY = con.getAvgScoreELY();
        int [] scoreEasy = {avgScoreELM, avgScoreELQ, avgScoreELY};
        return scoreEasy;
    }

    @Override
    public int[] getMediumAverageScoreOfThreeLastTime() {
        con.getAndrewID();
        con.getResult();
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreMLY = con.getAvgScoreMLY();
        int [] scoreMedium = {avgScoreMLM, avgScoreMLQ, avgScoreMLY};
        return scoreMedium;
    }

    @Override
    public int[] getHardAverageScoreOfThreeLastTime() {
        con.getAndrewID();
        con.getResult();
        int avgScoreHLM = con.getAvgScoreHLM();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreHard = {avgScoreHLM, avgScoreHLQ, avgScoreHLY};
        return scoreHard;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
        con.getAndrewID();
        con.getResult();
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreHLM = con.getAvgScoreHLM();
        int [] scoreDiffLM = {avgScoreELM, avgScoreMLM, avgScoreHLM};
        return scoreDiffLM;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
        con.getAndrewID();
        con.getResult();
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int [] scoreDiffLQ = {avgScoreELQ, avgScoreMLQ, avgScoreHLQ};
        return scoreDiffLQ;
    }

    @Override
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
        con.getAndrewID();
        con.getResult();
        int avgScoreELY = con.getAvgScoreELY();
        int avgScoreMLY = con.getAvgScoreMLY();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreDiffLY = {avgScoreELY, avgScoreMLY, avgScoreHLY};
        return scoreDiffLY;
    }

    @Override
    public void deleteById(int questionID) {
       String sql = "DELETE FROM QUESTIONS WHERE NUMBER = '"+questionID+"'";  
       try (Connection con = DriverManager.getConnection(resUrl)){
           Statement stmt = con.createStatement();
           stmt.executeUpdate(sql);
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
            System.exit(0);
        }

    }

}
