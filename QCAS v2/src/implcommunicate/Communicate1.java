package implcommunicate;


import java.util.ArrayList;
import utilclass.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Yuhao HE
 */
public class Communicate1 {
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private DBConnection con = new DBConnection(); // to do the calculation
    private Set <String> SandrewID = new HashSet(); // to get the andrewID of users
    
    /**
     * This constructor is to get the HashSet of andrewID, and do the calculation
     * at the time we have an object of Communicate1
     */
    public Communicate1(){
        SandrewID = con.getAndrewID();
        con.getResult(SandrewID);
    }
 
    /**
     *
     * @return an array of andrewID of students who failed last month
     */
    public String[] getStudentFailedListOfLastMouth() {
        ArrayList <String> list = con.getStudentFailLM();
        String[] stringArray = list.toArray(new String[list.size()]);
       
        return stringArray; 
    }

    /**
     *
     * @return an array of andrewID of students who failed last quarter
     */
    public String[] getStudentFailedListOfLastQuarter() {
        ArrayList <String> list = con.getStudentFailLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
           
        return stringArray;
    }

    /**
     *
     * @return an array of andrewID of students who failed last year
     */
    public String[] getStudentFailedListOfLastYear() {
        ArrayList <String> list = con.getStudentFailLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    /**
     *
     * @return an array of andrewID of students who passed last month
     */
    public String[] getStudentPassedListOfLastMouth() {
        
        ArrayList <String> list = con.getStudentPassLM();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    /**
     *
     * @return an array of andrewID of students who passed last quarter
     */
    public String[] getStudentPassedListOfLastQuarter() {
        
        ArrayList <String> list = con.getStudentPassLQ();
        String[] stringArray = list.toArray(new String[list.size()]);
        return stringArray;
    }

    /**
     *
     * @return an array of andrewID of students who passed last year
     */
    public String[] getStudentPassedListOfLastYear() {
        
        ArrayList <String> list = con.getStudentPassLY();
        String[] stringArray = list.toArray(new String[list.size()]);
        System.out.println("Student Passed Last Year");
        return stringArray;
    }

    /**
     *
     * @return an array of the number of quizzes over three periods
     */
    public int[] getLastNumberOfALLQuizzes() {
       
        int numberTestLM = con.getNumberTestLM();
        int numberTestLQ = con.getNumberTestLQ();
        int numberTestLY = con.getNumberTestLY();
        int [] numberOfTest = {numberTestLM, numberTestLQ, numberTestLY};
        return numberOfTest;
    }

    /**
     *
     * @return the average scores of quizzes over three periods
     */
    public int[] getlastAverageOfALLQuizzes() {     
      
        int avgScoreLM = con.getAvgScoreLM();
        int avgScoreLQ = con.getAvgScoreLQ();
        int avgScoreLY = con.getAvgScoreLY();
        int [] avgAll = {avgScoreLM, avgScoreLQ, avgScoreLY};
        return avgAll;
    }

    /**
     *
     * @return average scores of easy level questions over three periods
     */
    public int[] geteasyAverageScoreOfThreeLastTime() {
        
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreELY = con.getAvgScoreELY();
        int [] scoreEasy = {avgScoreELM, avgScoreELQ, avgScoreELY};
        return scoreEasy;
    }

    /**
     *
     * @return  average scores of medium level questions over three periods
     */
    public int[] getMediumAverageScoreOfThreeLastTime() {
        
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreMLY = con.getAvgScoreMLY();
        int [] scoreMedium = {avgScoreMLM, avgScoreMLQ, avgScoreMLY};

        return scoreMedium;
    }

    /**
     *
     * @return  average scores of hard level questions over three periods
     */
    public int[] getHardAverageScoreOfThreeLastTime() {
        
        int avgScoreHLM = con.getAvgScoreHLM();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreHard = {avgScoreHLM, avgScoreHLQ, avgScoreHLY};

        return scoreHard;
    }

    /**
     *
     * @return  average scores of three levels questions in last month
     */
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
        
        int avgScoreELM = con.getAvgScoreELM();
        int avgScoreMLM = con.getAvgScoreMLM();
        int avgScoreHLM = con.getAvgScoreHLM();
        int [] scoreDiffLM = {avgScoreELM, avgScoreMLM, avgScoreHLM};

        return scoreDiffLM;
    }

    /**
     *
     * @return average scores of three levels questions in last quarter
     */
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
    
        int avgScoreELQ = con.getAvgScoreELQ();
        int avgScoreMLQ = con.getAvgScoreMLQ();
        int avgScoreHLQ = con.getAvgScoreHLQ();
        int [] scoreDiffLQ = {avgScoreELQ, avgScoreMLQ, avgScoreHLQ};

        return scoreDiffLQ;
    }

    /**
     *
     * @return average scores of three levels questions in last year
     */
    public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
      
        int avgScoreELY = con.getAvgScoreELY();
        int avgScoreMLY = con.getAvgScoreMLY();
        int avgScoreHLY = con.getAvgScoreHLY();
        int [] scoreDiffLY = {avgScoreELY, avgScoreMLY, avgScoreHLY};

        return scoreDiffLY;
    }

    /**
     *This method is to delete questions in question bank
     * @param questionID is the unique ID of each question in question bank
     */
    public void deleteById(int questionID) {
       String sql = "DELETE FROM QUESTION WHERE NUMBER = " + questionID;  
       try (Connection con = DriverManager.getConnection(resUrl, quizUsername, quizPassword)){
           Statement stmt = con.createStatement();
           int i = stmt.executeUpdate(sql);
           //check if successfully delete a question
           if (i != 1) {
            System.out.println ("Failed to delete a question record");
           } 
           
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
        }

    }

   

}
