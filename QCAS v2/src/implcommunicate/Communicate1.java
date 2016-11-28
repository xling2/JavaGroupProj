package implcommunicate;

import interfacewithserve.ICommunicate1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import utilclass.Answer;
import utilclass.DBConnection;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.StringToInt;

public class Communicate1 implements ICommunicate1 {
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    DBConnection con = new DBConnection();
    QuizOfStudent newQuiz;

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

    @Override
    public QuizOfStudent getQuizByQuizId(String quizId, String StudentName){
        Question[] questionsOfQuiz;
        Answer[] answerOfStudent;
        Question[] allQuestions = new TestCommunicate().getAllQuestions();
       
        try (Connection con = DriverManager.getConnection(resUrl)){
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM" + StudentName + "WHERE QUIZID = '"+quizId+"' ";
            ResultSet rs = stmt.executeQuery(query); // only one line
            while (rs.next()){
                int numberQ = rs.getInt(4);
                questionsOfQuiz = new Question[numberQ];
                answerOfStudent = new Answer[numberQ];

                for (int i = 0; i < numberQ; i++){ 
                    int originalNumber = rs.getInt(7 + 2 * i);
                    questionsOfQuiz[i] = allQuestions[originalNumber - 1];  
                    // get student's answer, check this Q's type
                    int type = allQuestions[originalNumber - 1].questionType;
                    String sAnswer = rs.getString(8 + 2 * i); // "ABC"
                    // check whether sAnswer is empty ("")
                    if (sAnswer.equals("")){
                        Answer newAnswer = new Answer();
                        answerOfStudent[i] = newAnswer;
                    }
                    else{
                        if (type == 0){
                            // convert "ABC" to "012"
                            ArrayList<Integer> multipleChoices = new StringToInt().toIntMultipleAnswer(sAnswer);
                            Answer newAnswer = new Answer(originalNumber, type, multipleChoices);
                            newAnswer.isAnswer = true;
                            answerOfStudent[i] = newAnswer;
                        }
                        else if (type == 1){
                            // convert "A" to "0"
                            int singleChoice = new StringToInt().toIntSingleAnswer(sAnswer);
                            Answer newAnswer = new Answer(originalNumber, type, singleChoice);
                            newAnswer.isAnswer = true;
                            answerOfStudent[i] = newAnswer;
                        }
                        else if (type == 2){
                            boolean trueOrFalse = Boolean.parseBoolean(sAnswer);
                            Answer newAnswer = new Answer(originalNumber, type, trueOrFalse);
                            newAnswer.isAnswer = true;
                            answerOfStudent[i] = newAnswer;
                        }   
                        else{
                            String blank = sAnswer;
                            Answer newAnswer = new Answer(originalNumber, type, blank);
                            newAnswer.isAnswer = true;
                            answerOfStudent[i] = newAnswer;
                        }
                    }
                    
                    
                    
                }
                
                
                Date startDate = rs.getDate(5);
                Date finishDate = rs.getDate(6);
                
                newQuiz = new QuizOfStudent(StudentName, 
                        questionsOfQuiz, answerOfStudent, startDate, finishDate);
                
            }
             
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
            System.exit(0);
        }
        
        return newQuiz; 
        
    }

}
