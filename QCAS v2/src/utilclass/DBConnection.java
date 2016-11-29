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
import java.sql.Date;
import java.sql.DatabaseMetaData;


public class DBConnection {

    // URL needs to be consistent with Xingyu
    private String stuUrl = "jdbc:derby:UserDB;create = true"; 
    private String userUsername = "user";
    private String userPassword = "user";
    private String resUrl = "jdbc:derby:QuizDB;create = true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private ArrayList <String> AndrewIDs = new ArrayList <String> ();
    private int numberTestLM, numberTestLQ, numberTestLY,avgScoreLM, avgScoreLQ,
            avgScoreLY, avgScoreE, avgScoreM, avgScoreH, avgScoreELM, avgScoreELQ,
            avgScoreELY, avgScoreMLM, avgScoreMLQ, avgScoreMLY, avgScoreHLM, 
            avgScoreHLQ, avgScoreHLY, numberQE, numberQM, numberQH;
    private ArrayList <String> studentPassLM = new ArrayList();
    private ArrayList <String> studentPassLQ = new ArrayList();
    private ArrayList <String> studentPassLY = new ArrayList();
    private ArrayList <String> studentFailLM = new ArrayList();
    private ArrayList <String> studentFailLQ = new ArrayList();
    private ArrayList <String> studentFailLY = new ArrayList();
    
    
    public ArrayList<String> getAndrewID(){
        try (java.sql.Connection con = DriverManager.getConnection(stuUrl, userUsername,
                userPassword)){
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM STUDENTS";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String andrewID = rs.getString(1);
                AndrewIDs.add(andrewID);
            }
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
            System.exit(0);
        }
        return AndrewIDs;
    
    }
    
    public void getResult(){
        // get today's date
        LocalDate today = LocalDate.now();
        int thisYear = today.getYear();
        int thisMonth = today.getMonth().getValue();
        String lastYear = String.valueOf(thisYear - 1);
        String lastMonth = String.valueOf(thisMonth - 1);
        String lastQuarter = String.valueOf(thisMonth - 3);
        double tsLY = 0;
        double tsLM = 0;
        double tsLQ = 0;
        double tsE = 0;
        double tsM = 0;
        double tsH = 0;
        double tsELM = 0;
        double tsELQ = 0;
        double tsELY = 0;
        double tsMLM = 0;
        double tsMLQ = 0;
        double tsMLY = 0;
        double tsHLM = 0;
        double tsHLQ = 0;
        double tsHLY = 0;
        int numberQELM = 0;
        int numberQELQ = 0;
        int numberQELY = 0;
        int numberQMLM = 0;
        int numberQMLQ = 0;
        int numberQMLY = 0;
        int numberQHLM = 0;
        int numberQHLQ = 0;
        int numberQHLY = 0;
        
        
        try (java.sql.Connection con = DriverManager.getConnection(resUrl, quizUsername, quizPassword)){
            
            for (String a : AndrewIDs){
                // check whether this andrewID table exists
                DatabaseMetaData meta = con.getMetaData();
                ResultSet res = meta.getTables(null, null, "QuizDB", 
                    new String[] {a});
                
                while (res.next()){
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM" + " " + a;
                    ResultSet rs = stmt.executeQuery(query);

                    double avgScoreLM_P = 0;
                    double avgScoreLQ_P = 0;
                    double avgScoreLY_P = 0;
                    int numberTestPLM = 0;
                    int numberTestPLQ = 0;
                    int numberTestPLY = 0;
                    double tsPLM = 0;
                    double tsPLQ = 0;
                    int tsPLY = 0;
                    // use endTime to get year and month
                    while(rs.next()){
                        // convert Date to String
                        Date endTime = rs.getDate(6); 
                        String entimeS = endTime.toString(); //  yyyy-mm-dd
                        String examYear = entimeS.substring(0,4);
                        String examMonth = entimeS.substring(5,7);
                        String diffLevel = rs.getString(2);   
                        int score = rs.getInt(3);
                        int numberQ = rs.getInt(4);


                        // check for number of Tests and Scores by quiz time
                        if (examYear.equals(lastYear)){
                            numberTestLY++;
                            numberTestPLY++;
                            tsLY += score;  
                            tsPLY += score;
                        }
                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth)){
                            numberTestLM++;
                            numberTestPLM++;
                            tsLM += score;
                            tsPLM += score;
                        }
                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter)) {
                            numberTestLQ++;
                            numberTestPLQ++;
                            tsLQ += score;
                            tsPLQ += score;
                        }

                        // check for Scores by level of difficulty. AVG SCORE FOR EACH QUESTION!!!
                        if (diffLevel.equals("E")){
                            numberQE += numberQ;
                            tsE += score;
                            if (examYear.equals(thisYear) && examMonth.equals(lastMonth)){
                                numberQELM += numberQ;
                                tsELM += score;
                            }
                            if (examYear.equals(thisYear) && examMonth.equals(lastQuarter)){
                                numberQELQ += numberQ;
                                tsELQ += score;
                            }
                            if (examYear.equals(lastYear)){
                                numberQELY += numberQ;
                                tsELY += score;  
                            }
                        }
                        else if (diffLevel.equals("M")){
                            numberQM += numberQ;
                            tsM += score;
                            // add
                            if (examYear.equals(thisYear) && examMonth.equals(lastMonth)){
                                numberQMLM += numberQ;
                                tsMLM += score;
                            }
                            if (examYear.equals(thisYear) && examMonth.equals(lastQuarter)){
                                numberQMLQ += numberQ;
                                tsMLQ += score;
                            }
                            if (examYear.equals(lastYear)){
                                numberQMLY += numberQ;
                                tsMLY += score;  
                            }

                        }
                        else if (diffLevel.equals("H")){
                            numberQH += numberQ;
                            tsH += score;
                            //add
                            if (examYear.equals(thisYear) && examMonth.equals(lastMonth)){
                                numberQHLM += numberQ;
                                tsHLM += score;
                            }
                            if (examYear.equals(thisYear) && examMonth.equals(lastQuarter)){
                                numberQHLQ += numberQ;
                                tsHLQ += score;
                            }
                            if (examYear.equals(lastYear)){
                                numberQHLY += numberQ;
                                tsHLY += score;  
                            }
                        }
                        else{
                            // mixed exam
                            int originalNumber;
                            String answer;
                            String diffQ;
                            for (int i = 0; i < numberQ; i++){
                                originalNumber = rs.getInt(7 + 2 * i);
                                answer = rs.getString(9 + 2 * i);
                                String getDiff = "SELECT DIFFICULTY FROM QUESTION WHERE NUMBER = '"+originalNumber+"'";
                                ResultSet rsn = stmt.executeQuery(getDiff);
                                //************* need check
                                while (rsn.next()){
                                    diffQ = rsn.getString(1);
                                    // check if Easy question
                                    if (diffQ.equals("E")){
                                        numberQE ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            numberQELM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            numberQELQ ++;
                                        if (examYear.equals(lastYear))
                                            numberQELY ++;
                                    }

                                    if (answer.equals("correct")){
                                        tsE ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            tsELM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            tsELQ ++;
                                        if (examYear.equals(lastYear))
                                            tsELY ++;     
                                    }

                                    // check if Medium question
                                    if (diffQ.equals("M")){
                                        numberQM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            numberQMLM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            numberQMLQ ++;
                                        if (examYear.equals(lastYear))
                                            numberQMLY ++;
                                    }

                                    if (answer.equals("correct")){
                                        tsM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            tsMLM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            tsMLQ ++;
                                        if (examYear.equals(lastYear))
                                            tsMLY ++;     
                                    }
                                    // check if Hard question
                                    if (diffQ.equals("H")){
                                        numberQH ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            numberQHLM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            numberQHLQ ++;
                                        if (examYear.equals(lastYear))
                                            numberQHLY ++;
                                    }

                                    if (answer.equals("correct")){
                                        tsH ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastMonth))
                                            tsHLM ++;
                                        if (examYear.equals(thisYear) && examMonth.equals(lastQuarter))
                                            tsHLQ ++;
                                        if (examYear.equals(lastYear))
                                            tsHLY ++;     
                                    }
                                }

                            }

                        }
                    }
                    // students passing and failing over different periods
                    avgScoreLM_P = tsPLM/numberTestPLM;
                    avgScoreLQ_P = tsPLQ/numberTestPLQ;
                    avgScoreLY_P = tsPLY/numberTestPLY;
                    if (avgScoreLM_P >= 0.6)
                        getStudentPassLM().add(a);
                    else
                        getStudentFailLM().add(a);

                    if (avgScoreLQ_P >= 0.6)
                        getStudentPassLQ().add(a);
                    else
                        getStudentFailLQ().add(a);

                    if (avgScoreLY_P >= 0.6)
                        getStudentPassLY().add(a);
                    else
                        getStudentFailLY().add(a);        
                    }
                 
            }
                
                      
        }catch (SQLException e) {
            System.out.println("Exception creating connection: " + e);
            e.printStackTrace();
            System.exit(0);
        }
        
        avgScoreLM = (int)(tsLM/getNumberTestLM()*100);
        avgScoreLQ = (int)(tsLQ/getNumberTestLQ()*100);
        avgScoreLY = (int)(tsLY/getNumberTestLY()*100);
        
        avgScoreE = (int)(tsE/getNumberQE()*100);
        avgScoreM = (int)(tsM/getNumberQM()*100);
        avgScoreH = (int)(tsH/getNumberQH()*100);
        
        avgScoreELM = (int)(tsELM/numberQELM*100);
        avgScoreELQ = (int)(tsELQ/numberQELQ*100);
        avgScoreELY = (int)(tsELY/numberQELY*100);
        avgScoreMLM = (int)(tsMLM/numberQMLM*100);
        avgScoreMLQ = (int)(tsMLQ/numberQMLQ*100);
        avgScoreMLY = (int)(tsMLY/numberQMLY*100);
        avgScoreHLM = (int)(tsHLM/numberQHLM*100);
        avgScoreHLQ = (int)(tsHLQ/numberQHLQ*100);
        avgScoreHLY = (int)(tsHLY/numberQHLY*100);
    }


    /**
     * @return the studentPassLM
     */
    public ArrayList <String> getStudentPassLM() {
        return studentPassLM;
    }

    /**
     * @return the studentPassLQ
     */
    public ArrayList <String> getStudentPassLQ() {
        return studentPassLQ;
    }

    /**
     * @return the studentPassLY
     */
    public ArrayList <String> getStudentPassLY() {
        return studentPassLY;
    }

    /**
     * @return the studentFailLM
     */
    public ArrayList <String> getStudentFailLM() {
        return studentFailLM;
    }

    /**
     * @return the studentFailLQ
     */
    public ArrayList <String> getStudentFailLQ() {
        return studentFailLQ;
    }

    /**
     * @return the studentFailLY
     */
    public ArrayList <String> getStudentFailLY() {
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
