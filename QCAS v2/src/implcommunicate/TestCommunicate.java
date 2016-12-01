package implcommunicate;

import interfacewithserve.ICommunicate2;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import qcas.GoPage;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.Communicate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import qcas.GoPage;

/*
This class implements the interface ICommunicate2 and extends the Communicate1
*/

public class TestCommunicate extends Communicate1 implements ICommunicate2 {

    private String quizUrl = "jdbc:derby:QuizDB;"
            + "create=true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private String userUrl = "jdbc:derby:UserDB; create=true";
    private String userUsername = "user";
    private String userPassword = "user";
    Communicate comm = new Communicate();

    @Override
    public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
        return comm.getRandomQuestion(quizDifficulty, questionNumber);
    }
    
    @Override
    public boolean checkQuestions(int quizDifficulty, int questionNumber){
        return comm.checkQuestions(quizDifficulty, questionNumber);
    }
    @Override
    public void recordQuizResultToServe(QuizOfStudent quizResult) {
        
        comm.createTableQuiz(quizResult);
        comm.insertTableQuiz(quizResult);
    }

    @Override
    public boolean login(String userName, String password) {
        String encryptedPwd = new String();
        boolean loginSuccess = false;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(), 0, password.length());
            encryptedPwd = new BigInteger(1, md.digest()).toString(16);
            //System.out.println("Encrypted: " + encryptedPwd);
        } catch (NoSuchAlgorithmException nae) {
            System.out.println("NoSuchAlgorithmException @ login: " + nae);
        }

        String pQuery = "SELECT * FROM \"USER\".STUDENTS WHERE ANDREWID LIKE '"
                + userName + "' AND PASSWORD LIKE '" + encryptedPwd + "'";

        try (Connection con = DriverManager.getConnection(userUrl,
                userUsername, userPassword);
                PreparedStatement pStmt = con.prepareStatement(pQuery);) {
            ResultSet userRS = pStmt.executeQuery();
            if (userRS.next()) {
                GoPage.getGoPage().studentName = userName;
                GoPage.getGoPage().userName = 
                        (userName.equals("instructor")) ? "instructor" : "student";             
                //System.out.println("GoPage.getGoPage().userName: " + 
                       // GoPage.getGoPage().userName);
                loginSuccess = true;
               // System.out.println(loginSuccess);
               // System.out.println("GoPage.getGoPage().studentName: " +
                      //  GoPage.getGoPage().studentName);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception @ login: " + e);
        }
        return loginSuccess;
    }

    @Override
    public Question[] importQuestionFromCSV(File CSVFile) {

        comm.createTableQuestion();
        return comm.insertQuestion(CSVFile);

    }

    @Override
    public Question[] getAllQuestions() {

        return comm.getAllQuestions();
    }

    @Override
    public HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName) {

        return comm.getHistoryRecord(studentName);
    }

    @Override
    public QuizOfStudent getQuizByQuizId(String quizId, String studentName) {

        return comm.getQuiz(quizId, studentName);
    }

    @Override
    public int[] getStudentAllRecordScoreByStudentName(String studentName) {

        return comm.getStudentAllRecordScore(studentName);
    }

    @Override
    public String[] getStudentRecordDateByStudentName(String studentName) {

        return comm.getStudentRecordDate(studentName);
    }

    @Override
    public int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String studentName) {

        return comm.getStudentAverageScoreOfThreeDifficulty(studentName);
    }

    @Override
    public boolean addStudent(String text) {
        
        boolean success = false;
        comm.createTable();
        // check if the ID exsits in database
        if (!comm.checkID(text)) {
            // if not, then send main, and insert information.
            comm.createPassword();
            comm.sendMail(text);
            comm.insertStudent(comm.encryptPassW(), text);
            success = true;
        }

        return success;

    }

    @Override
    public ArrayList<String> getAllStudent() {

        return comm.getAllStudent();
    }

    @Override
    public boolean deleteStudent(String andrewID) {

        comm.deleteStudent(andrewID);
        return true;

    }
    @Override
    public void deleteById(int questionID) {
        // delete questions by question ID
        String sql = "DELETE FROM QUESTION WHERE NUMBER = " + questionID;
        try (Connection con = DriverManager.getConnection(quizUrl, quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);

        }

    }

}
