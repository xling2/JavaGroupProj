package implcommunicate;

import interfacewithserve.ICommunicate2;
import java.io.File;
import java.util.ArrayList;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.Communicate;


public class TestCommunicate extends Communicate1 implements ICommunicate2 {

    private String quizUrl = "jdbc:derby:QuizDB;"
            + "create=true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private File csvFile;
    private String userUrl = "jdbc:derby:UserDB; create=true";
    private String userUsername = "user";
    private String userPassword = "user";
    private String passW;
    Communicate comm = new Communicate();

    @Override
    public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
 
              
        return comm.getRandomQuestion(quizDifficulty, questionNumber);
    }

    @Override
    public void recordQuizResultToServe(QuizOfStudent quizResult) {

        comm.createTableQuiz(quizResult);
        comm.insertTableQuiz(quizResult);
    }

    

    @Override
    public boolean login(int loginType, String userName, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Question[] importQuestionFromCSV(File CSVFile) {
        
        comm.createTableQuestion();
        return comm.insertQuestion(CSVFile);

    }

    
    
    @Override
    public Question[] getAllQuestions(){
        
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
        comm.createTable();
        if (!comm.checkID(text)) {
            comm.createPassword();
            comm.sendMail(text, passW);
            comm.insertStudent(comm.encryptPassW(), text);
        }
        boolean success = true;
        return success;

    }

    

    public ArrayList<String> getAllStudent() {
       
        return comm.getAllStudent();
    }

    public boolean deleteStudent(String andrewID) {
        
        comm.deleteStudent(andrewID);
        return true;

    }

}