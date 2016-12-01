/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.sql.Date;
import java.sql.DatabaseMetaData;

/**
 *
 * @author Yuhao HE
 */
public class DBConnection {
    private String stuUrl = "jdbc:derby:UserDB;create = true";
    private String userUsername = "user";
    private String userPassword = "user";
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private int numberTestLM, numberTestLQ, numberTestLY, avgScoreLM, avgScoreLQ,
            avgScoreLY, avgScoreE, avgScoreM, avgScoreH, avgScoreELM, avgScoreELQ,
            avgScoreELY, avgScoreMLM, avgScoreMLQ, avgScoreMLY, avgScoreHLM,
            avgScoreHLQ, avgScoreHLY, numberQE, numberQM, numberQH;
    private ArrayList<String> studentPassLM = new ArrayList();
    private ArrayList<String> studentPassLQ = new ArrayList();
    private ArrayList<String> studentPassLY = new ArrayList();
    private ArrayList<String> studentFailLM = new ArrayList();
    private ArrayList<String> studentFailLQ = new ArrayList();
    private ArrayList<String> studentFailLY = new ArrayList();

    /**
     *
     * @return a hashset which stores andrewID of all students
     */
    public Set<String> getAndrewID() {
        Set<String> AndrewIDs = new HashSet();
        try (java.sql.Connection con = DriverManager.getConnection(stuUrl, userUsername,
                userPassword)) {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM STUDENTS";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String andrewID = rs.getString(1);
                // check whether aleady contains this ID. if contains, add it into the hashset
                AndrewIDs.add(andrewID);
            }
        } catch (SQLException e) {
            System.out.println("Exception creating connection @DBConnection/getAndrewID " + e);
            e.printStackTrace();
        }
        return AndrewIDs;

    }

    /**
     * This method is to calculate all statistical figures
     * @param AndrewIDs is the hashset that stores andrewID of all students
     */
    public void getResult(Set<String> AndrewIDs) {
        // get today's date and related date data
        LocalDate today = LocalDate.now();
        int thisYear = today.getYear();
        int thisMonth = today.getMonth().getValue();
        int lastYear = thisYear - 1;
        int lastMonth = thisMonth - 1;
        int lastQuarter = thisMonth - 3;
        double tsLY = 0; // total score of quizzes taken in last year
        double tsLM = 0; // total score of quizzes taken in last month
        double tsLQ = 0; // total score of quizzes taken in last quarter
        double tsE = 0; // total score of quizzes of easy level
        double tsM = 0; // total score of quizzes of medium level
        double tsH = 0; // total score of quizzes of hard level
        double tsELM = 0; // total score of quizzes of easy level taken in last month
        double tsELQ = 0; // total score of quizzes of easy level taken in last quarter
        double tsELY = 0; // total score of quizzes of easy level taken in last year
        double tsMLM = 0; // total score of quizzes of meidum level taken in last month
        double tsMLQ = 0; // total score of quizzes of medium level taken in last month
        double tsMLY = 0; // total score of quizzes of medium level taken in last month
        double tsHLM = 0; // total score of quizzes of hard level taken in last month
        double tsHLQ = 0; // total score of quizzes of hard level taken in last month
        double tsHLY = 0; // total score of quizzes of hard level taken in last month
        int numberQELM = 0; // the number of questions of easy level taken in last month
        int numberQELQ = 0; // the number of questions of easy level taken in last quarter
        int numberQELY = 0; // the number of questions of easy level taken in last year
        int numberQMLM = 0; // the number of questions of medium level taken in last month
        int numberQMLQ = 0; // the number of questions of medium level taken in last quarter
        int numberQMLY = 0; // the number of questions of medium level taken in last year
        int numberQHLM = 0; // the number of questions of hard level taken in last month
        int numberQHLQ = 0; // the number of questions of hard level taken in last quarter
        int numberQHLY = 0; // the number of questions of hard level taken in last year
        int numberQLM = 0; // the number of questions taken in last month
        int numberQLQ = 0; // the number of questions taken in last quarter
        int numberQLY = 0; // the number of questions taken in last year

        try (java.sql.Connection con
                = DriverManager.getConnection(resUrl, quizUsername, quizPassword)) {
            // each student has a table named its AndrewID, this table is to store its quiz history
            for (String a : AndrewIDs) {
                // check whether this andrewID table exists
                DatabaseMetaData meta = con.getMetaData();
                ResultSet res = meta.getTables(null, null, a.toUpperCase(), null);
                // do calculation only when this table exists
                while (res.next()) {
                    // connect to the quiz history table of this student
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM" + " " + a;
                    ResultSet rs = stmt.executeQuery(query);

                    double avgScoreLM_P = 0; // average score in last month of this student 
                    double avgScoreLQ_P = 0; // average score in last quarter of this student 
                    double avgScoreLY_P = 0; // average score in last year of this student 
                    int numberTestPLM = 0; // the number of quizzes that this student took in last month
                    int numberTestPLQ = 0; // the number of quizzes that this student took in last quarter
                    int numberTestPLY = 0; // the number of quizzes that this student took in last year
                    double tsPLM = 0; // total score of quizzes took in last month by this student
                    double tsPLQ = 0; // total score of quizzes took in last quarter by this student
                    double tsPLY = 0; // total score of quizzes took in last year by this student
                    
                    // read the data in the quiz history table of this student
                    // each row in the table is a quiz record
                    while (rs.next()) {
                        String entimeS = rs.getString(6); //"YYYY/MM/DD HH:mm:ss"
                        String ey = entimeS.substring(0, 4); // get "YYYY"
                        int examYear = Integer.parseInt(ey); 
                        String em = entimeS.substring(5, 7); // get "MM"
                        int examMonth = Integer.parseInt(em);
                        String diffLevel = rs.getString(2); // the difficulty level of this quiz
                        int score = rs.getInt(3); // the score of this quiz(out of 100)
                        int numberQ = rs.getInt(4); // the number of questions in this quiz

                        // check for the number of quizzes and total scores in different periods
                        // assume each questin worths 1 score
                        // if this quiz was taken in last year
                        if (examYear == lastYear) {
                            numberTestLY++;
                            numberTestPLY++;
                            tsLY += score / 100.0 * numberQ; // get the number (or score) of correct questions
                            numberQLY += numberQ;
                            tsPLY += score; // use the score out of 100 to do calculation for a single students
                        }
                        // if this quiz was taken in last month
                        if (examYear == thisYear && examMonth == lastMonth) {
                            numberTestLM++;
                            numberTestPLM++;
                            tsLM += score / 100.0 * numberQ;
                            numberQLM += numberQ;
                            tsPLM += score;
                        }
                        // if this quiz was taken in last quarter
                        if (examYear == thisYear && examMonth == lastQuarter) {
                            numberTestLQ++;
                            numberTestPLQ++;
                            tsLQ += score / 100.0 * numberQ;
                            numberQLQ += numberQ;
                            tsPLQ += score;
                        }

                        // check for Scores by level of difficulty. 
                        // if this quiz is of easy level
                        if (diffLevel.equals("E")) {
                            numberQE += numberQ;
                            tsE += score / 100.0 * numberQ;
                            // if this quiz is of easy level and was taken in last month
                            if (examYear == thisYear && examMonth == lastMonth) {
                                numberQELM += numberQ;
                                tsELM += score / 100.0 * numberQ;
                            }
                            // if this quiz is of easy level and was taken in last quarter
                            if (examYear == thisYear && examMonth == lastQuarter) {
                                numberQELQ += numberQ;
                                tsELQ += score / 100.0 * numberQ;
                            }
                            // if this quiz is of easy level and was taken in last year
                            if (examYear == lastYear) {
                                numberQELY += numberQ;
                                tsELY += score / 100.0 * numberQ;
                            }
                        } 
                        // if this quiz is of medium level
                        else if (diffLevel.equals("M")) {
                            numberQM += numberQ;
                            tsM += score / 100.0 * numberQ;
                            // if this quiz is of medium level and was taken in last month
                            if (examYear == thisYear && examMonth == lastMonth) {
                                numberQMLM += numberQ;
                                tsMLM += score / 100.0 * numberQ;
                            }
                            // if this quiz is of medium level and was taken in last quarter
                            if (examYear == thisYear && examMonth == lastQuarter) {
                                numberQMLQ += numberQ;
                                tsMLQ += score / 100.0 * numberQ;
                            }
                            // if this quiz is of medium level and was taken in last year
                            if (examYear == lastYear) {
                                numberQMLY += numberQ;
                                tsMLY += score / 100.0 * numberQ;
                            }

                        }
                        // if this quiz is of hard level
                        else if (diffLevel.equals("H")) {
                            numberQH += numberQ;
                            tsH += score / 100.0 * numberQ;
                            // if this quiz is of hard level and was taken in last month
                            if (examYear == thisYear && examMonth == lastMonth) {
                                numberQHLM += numberQ;
                                tsHLM += score / 100.0 * numberQ;
                            }
                            // if this quiz is of hard level and was taken in last quarter
                            if (examYear == thisYear && examMonth == lastQuarter) {
                                numberQHLQ += numberQ;
                                tsHLQ += score / 100.0 * numberQ;
                            }
                            // if this quiz is of hard level and was taken in last year
                            if (examYear == lastYear) {
                                numberQHLY += numberQ;
                                tsHLY += score / 100.0 * numberQ;
                            }
                        }
                        // if this quiz is of mixed level
                        else {
                            int originalNumber; // the unique quizID of this quiz in quiz history table
                            String studentAnswer; // student's answer to a question in this quiz
                            String realAnswer;
                            String diffQ; // the difficulty level of a question in this quiz
                            // loop within all questions in this quiz
                            for (int i = 0; i < numberQ; i++) {
                                originalNumber = rs.getInt(7 + 3 * i);
                                studentAnswer = rs.getString(8 + 3 * i);
                                realAnswer = rs.getString(9 + 3 * i);
                                // connect to question table to get the difficulty level of a certain question in this quiz
                                String getDiff = "SELECT DIFFICULTY FROM QUESTION WHERE NUMBER = " + originalNumber;
                                ResultSet rsn;
                                Statement stmt1 = con.createStatement();
                                // allQuestion contains all current questions in question bank
                                Question[] allQuestion = new Communicate().getAllQuestions();
                                // check whether this question is deleted by the instructor
                                boolean qExist = false;
                                for (Question q: allQuestion){
                                    if (q.questionID == originalNumber){
                                        qExist = true;    
                                    }     
                                }     
                                rsn = stmt1.executeQuery(getDiff);
                                // do the calculation only when this question is not deleted
                                while (rsn.next() && qExist) {
                                    diffQ = rsn.getString(1); // the difficulty level of this question

                                    // if this question is of easy level 
                                    if (diffQ.equals("E")) {
                                        numberQE++;
                                        // if this question is of easy level and was contained in a quiz of last month
                                        if (examYear == thisYear&& examMonth == lastMonth) {
                                            numberQELM++;
                                        }
                                        // if this question is of easy level and was contained in a quiz of last quarter
                                        if (examYear == thisYear && examMonth == lastQuarter) {
                                            numberQELQ++;
                                        }
                                        // if this question is of easy level and was contained in a quiz of last year
                                        if (examYear == lastYear) {
                                            numberQELY++;
                                        }
                                        
                                        // if this student's answer to this question is correct
                                        if (studentAnswer.equals(realAnswer)) {
                                            tsE++;
                                            if (examYear == thisYear && examMonth==lastMonth) {
                                                tsELM++;
                                            }
                                            if (examYear == thisYear && examMonth==lastQuarter) {
                                                tsELQ++;
                                            }
                                            if (examYear==lastYear) {
                                                tsELY++;
                                            }
                                        }

                                    }

                                    // if this question is of medium level 
                                    if (diffQ.equals("M")) {
                                        numberQM++;
                                        // if this question is of medium level and was contained in a quiz of last month
                                        if (examYear==thisYear && examMonth==lastMonth) {
                                            numberQMLM++;
                                        }
                                        // if this question is of medium level and was contained in a quiz of last quarter
                                        if (examYear==thisYear && examMonth==lastQuarter) {
                                            numberQMLQ++;
                                        }
                                        // if this question is of medium level and was contained in a quiz of last year
                                        if (examYear==lastYear) {
                                            numberQMLY++;
                                        }
                                        // if this student's answer to this question is correct
                                        if (studentAnswer.equals(realAnswer)) {
                                            tsM++;
                                            if (examYear==thisYear && examMonth==lastMonth) {
                                                tsMLM++;
                                            }
                                            if (examYear==thisYear && examMonth==lastQuarter) {
                                                tsMLQ++;
                                            }
                                            if (examYear==lastYear) {
                                                tsMLY++;
                                            }
                                        }
                                    }

                                    // if this question is of hard level 
                                    if (diffQ.equals("H")) {
                                        numberQH++;
                                        // if this question is of medium level and was contained in a quiz of last month
                                        if (examYear==thisYear && examMonth==lastMonth) {
                                            numberQHLM++;
                                        }
                                        // if this question is of medium level and was contained in a quiz of last quarter
                                        if (examYear==thisYear&& examMonth==lastQuarter) {
                                            numberQHLQ++;
                                        }
                                        // if this question is of medium level and was contained in a quiz of last year
                                        if (examYear==lastYear) {
                                            numberQHLY++;
                                        }
                                        // if this student's answer to this question is correct
                                        if (studentAnswer.equals(realAnswer)) {
                                            tsH++;
                                            if (examYear==thisYear&& examMonth==lastMonth) {
                                                tsHLM++;
                                            }
                                            if (examYear==thisYear && examMonth==lastQuarter) {
                                                tsHLQ++;
                                            }
                                            if (examYear==lastYear) {
                                                tsHLY++;
                                            }
                                        }
                                    }

                                }

                            }

                        }

                    }
                    // students passing and failing over different periods
                    avgScoreLM_P = tsPLM / numberTestPLM;
                    avgScoreLQ_P = tsPLQ / numberTestPLQ;
                    avgScoreLY_P = tsPLY / numberTestPLY;
                    /* if this student took a quiz in last month, and the average score is more than 60,
                    ** include this student into the list of students passed last month
                    */
                    if (avgScoreLM_P >= 60 && numberTestPLM != 0) {
                        getStudentPassLM().add(a);
                    }
                    /* if this student took a quiz in last month, and the average score is more than 60,
                    ** include this student into the list of students failed last month
                    */
                    if (avgScoreLM_P < 60 && numberTestPLM != 0) {
                        getStudentFailLM().add(a);
                    }

                    if (avgScoreLQ_P >= 60 && numberTestPLQ != 0) {
                        getStudentPassLQ().add(a);
                    }
                    if (avgScoreLQ_P < 60 && numberTestPLQ != 0) {
                        getStudentFailLQ().add(a);
                    }

                    if (avgScoreLY_P >= 60 && numberTestPLY != 0) {
                        getStudentPassLY().add(a);
                    }
                    if (avgScoreLY_P < 60 && numberTestPLY != 0) {
                        getStudentFailLY().add(a);
                    }
                }

            }

        } catch (SQLException e) {
            System.out.println("Exception creating connection @ DBConnection/getResult: " + e);
            e.printStackTrace();
        }

        // all scores below are scores out of 100
        avgScoreLM = (int) (tsLM / numberQLM * 100);
        avgScoreLQ = (int) (tsLQ / numberQLQ * 100);
        avgScoreLY = (int) (tsLY / numberQLY * 100);

        avgScoreE = (int) (tsE / getNumberQE() * 100);
        avgScoreM = (int) (tsM / getNumberQM() * 100);
        avgScoreH = (int) (tsH / getNumberQH() * 100);

        avgScoreELM = (int) (tsELM / numberQELM * 100);
        avgScoreELQ = (int) (tsELQ / numberQELQ * 100);
        avgScoreELY = (int) (tsELY / numberQELY * 100);
        avgScoreMLM = (int) (tsMLM / numberQMLM * 100);
        avgScoreMLQ = (int) (tsMLQ / numberQMLQ * 100);
        avgScoreMLY = (int) (tsMLY / numberQMLY * 100);
        avgScoreHLM = (int) (tsHLM / numberQHLM * 100);
        avgScoreHLQ = (int) (tsHLQ / numberQHLQ * 100);
        avgScoreHLY = (int) (tsHLY / numberQHLY * 100);
    }

    /**
     * @return the studentPassLM
     */
    public ArrayList<String> getStudentPassLM() {
        return studentPassLM;
    }

    /**
     * @return the studentPassLQ
     */
    public ArrayList<String> getStudentPassLQ() {
        return studentPassLQ;
    }

    /**
     * @return the studentPassLY
     */
    public ArrayList<String> getStudentPassLY() {
        return studentPassLY;
    }

    /**
     * @return the studentFailLM
     */
    public ArrayList<String> getStudentFailLM() {
        return studentFailLM;
    }

    /**
     * @return the studentFailLQ
     */
    public ArrayList<String> getStudentFailLQ() {
        return studentFailLQ;
    }

    /**
     * @return the studentFailLY
     */
    public ArrayList<String> getStudentFailLY() {
        return studentFailLY;
    }

    /**
     * @return the numberTestLM
     */
    public int getNumberTestLM() {
        return numberTestLM;
    }

    /**
     * @return the numberTestLQ
     */
    public int getNumberTestLQ() {
        return numberTestLQ;
    }

    /**
     * @return the numberTestLY
     */
    public int getNumberTestLY() {
        return numberTestLY;
    }

    /**
     * @return the avgScoreLM
     */
    public int getAvgScoreLM() {
        return avgScoreLM;
    }

    /**
     * @return the avgScoreLQ
     */
    public int getAvgScoreLQ() {
        return avgScoreLQ;
    }

    /**
     * @return the avgScoreLY
     */
    public int getAvgScoreLY() {
        return avgScoreLY;
    }

    /**
     * @return the avgScoreE
     */
    public int getAvgScoreE() {
        return avgScoreE;
    }

    /**
     * @return the avgScoreM
     */
    public int getAvgScoreM() {
        return avgScoreM;
    }

    /**
     * @return the avgScoreH
     */
    public int getAvgScoreH() {
        return avgScoreH;
    }

    /**
     * @return the avgScoreELM
     */
    public int getAvgScoreELM() {
        return avgScoreELM;
    }

    /**
     * @return the avgScoreELQ
     */
    public int getAvgScoreELQ() {
        return avgScoreELQ;
    }

    /**
     * @return the avgScoreELY
     */
    public int getAvgScoreELY() {
        return avgScoreELY;
    }

    /**
     * @return the avgScoreMLM
     */
    public int getAvgScoreMLM() {
        return avgScoreMLM;
    }

    /**
     * @return the avgScoreMLQ
     */
    public int getAvgScoreMLQ() {
        return avgScoreMLQ;
    }

    /**
     * @return the avgScoreMLY
     */
    public int getAvgScoreMLY() {
        return avgScoreMLY;
    }

    /**
     * @return the avgScoreHLM
     */
    public int getAvgScoreHLM() {
        return avgScoreHLM;
    }

    /**
     * @return the avgScoreHLQ
     */
    public int getAvgScoreHLQ() {
        return avgScoreHLQ;
    }

    /**
     * @return the avgScoreHLY
     */
    public int getAvgScoreHLY() {
        return avgScoreHLY;
    }

    /**
     * @return the numberQE
     */
    public int getNumberQE() {
        return numberQE;
    }

    /**
     * @return the numberQM
     */
    public int getNumberQM() {
        return numberQM;
    }

    /**
     * @return the numberQH
     */
    public int getNumberQH() {
        return numberQH;
    }

}
