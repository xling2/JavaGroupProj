package implcommunicate;


import java.util.ArrayList;
import utilclass.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.HashSet;

public class Communicate1 {
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    private DBConnection con = new DBConnection();
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private Set <String> SandrewID = new HashSet();
    
    public Communicate1(){
        SandrewID = con.getAndrewID();
        con.getResult(SandrewID);
    }
 
    public String[] getStudentFailedListOfLastMouth() {
        
        /*System.out.println("SAndreID 1: ");
        for (String a : SandrewID)
            System.out.println(a);*/
        ArrayList <String> list = con.getStudentFailLM();
        String[] stringArray = list.toArray(new String[list.size()]);
       
        return stringArray; 
    }


    public String[] getStudentFailedListOfLastQuarter() {
        
        /*System.out.println("SAndreID 2: ");
        for (String a : SandrewID)
            System.out.println(a);*/
        ArrayList <String> list = con.getStudentFailLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
           
        return stringArray;
    }


    public String[] getStudentFailedListOfLastYear() {
   
        /*System.out.println("SAndreID 3: ");
        for (String a : SandrewID)
            System.out.println(a);*/
        ArrayList <String> list = con.getStudentFailLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

  
    public String[] getStudentPassedListOfLastMouth() {
        
        ArrayList <String> list = con.getStudentPassLM();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

   
    public String[] getStudentPassedListOfLastQuarter() {
        
        ArrayList <String> list = con.getStudentPassLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    public String[] getStudentPassedListOfLastYear() {
        
        ArrayList <String> list = con.getStudentPassLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        System.out.println("Student Passed Last Year");
        return stringArray;
    }


    public int[] getLastNumberOfALLQuizzes() {
       
        int numberTestLM = con.getNumberTestLM();
        int numberTestLQ = con.getNumberTestLQ();
        int numberTestLY = con.getNumberTestLY();
        int [] numberOfTest = {numberTestLM, numberTestLQ, numberTestLY};
        return numberOfTest;
    }

    public int[] getlastAverageOfALLQuizzes() {     
      
        int avgScoreLM = con.getAvgScoreLM();
        int avgScoreLQ = con.getAvgScoreLQ();
        int avgScoreLY = con.getAvgScoreLY();
        int [] avgAll = {avgScoreLM, avgScoreLQ, avgScoreLY};
        return avgAll;
    }

    public int[] geteasyAverageScoreOfThreeLastTime() {
        
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreELY = con.getAvgScoreELY();
        int [] scoreEasy = {avgScoreELM, avgScoreELQ, avgScoreELY};
        return scoreEasy;
    }


    public int[] getMediumAverageScoreOfThreeLastTime() {
        
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreMLY = con.getAvgScoreMLY();
        int [] scoreMedium = {avgScoreMLM, avgScoreMLQ, avgScoreMLY};

        return scoreMedium;
    }


    public int[] getHardAverageScoreOfThreeLastTime() {
        
        int avgScoreHLM = con.getAvgScoreHLM();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreHard = {avgScoreHLM, avgScoreHLQ, avgScoreHLY};

        return scoreHard;
    }

    public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
        
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreHLM = con.getAvgScoreHLM();
        int [] scoreDiffLM = {avgScoreELM, avgScoreMLM, avgScoreHLM};

        return scoreDiffLM;
    }

 
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
    
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int [] scoreDiffLQ = {avgScoreELQ, avgScoreMLQ, avgScoreHLQ};

        return scoreDiffLQ;
    }

    public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
      
        int avgScoreELY = con.getAvgScoreELY();
        int avgScoreMLY = con.getAvgScoreMLY();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreDiffLY = {avgScoreELY, avgScoreMLY, avgScoreHLY};

        return scoreDiffLY;
    }


    public void deleteById(int questionID) {
       String sql = "DELETE FROM QUESTION WHERE NUMBER = " + questionID;  
       try (Connection con = DriverManager.getConnection(resUrl, quizUsername, quizPassword)){
           Statement stmt = con.createStatement();
           int i = stmt.executeUpdate(sql);
           //check
           if (i != 1) {
            System.out.println ("Failed to delete a question record");
           } 
           
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
        }

    }

   

}
