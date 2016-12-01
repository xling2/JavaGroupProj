/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilclass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import com.opencsv.CSVReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Collections;

/**
 *
 * @author lingxingyu
 *
 * Communicate with database, save data from serve, and provide data for serve
 */
public class Communicate {

    private String quizUrl = "jdbc:derby:QuizDB;"
            + "create=true";
    private String quizUsername = "quiz";
    private String quizPassword = "quiz";
    private String userUrl = "jdbc:derby:UserDB; create=true";
    private String userUsername = "user";
    private String userPassword = "user";
    private String passW;

    TimeRecorder recorder = new TimeRecorder();

    public Question[] getRandomQuestion(int quizDifficulty, int questionNumber) {

        IntToString its = new IntToString();
        Question[] question = new Question[questionNumber];
        ArrayList<Question> questionBank = new ArrayList();
        StringToInt sti = new StringToInt();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "";
            if (quizDifficulty < 3) {// select E\M\H
                // convert difficulty to int
                String diff = its.toStringDiff(quizDifficulty);
                // randomly select questions in specific difficulty levels
                sql = "Select * from Question where difficulty = '" + diff
                        + "' order by Random() fetch first " + questionNumber + " rows only";
            } else {// Select mixed
                sql = "Select * from Question order by Random() fetch first " + questionNumber + " rows only";
            }

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ArrayList<Choice> temp = new ArrayList();
                String[] key = {"A", "B", "C", "D"};
                String answer = "";
                if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                    for (int i = 0; i < 4; i++) {
                        // Choice(String content, String correct)
                        Choice choice = new Choice(rs.getString(3 + 2 * (i + 1)),
                                rs.getString(2 * (i + 1) + 4));
                        temp.add(choice);
                    }
                    // randomly sort the choices
                    Collections.shuffle(temp);
                    String[] choices = new String[4];
                    for (int i = 0; i < 4; i++) {
                        choices[i] = temp.get(i).getContent();
                        // record correct answer key
                        if (temp.get(i).getCorrect().equals("correct")) {
                            answer = answer + key[i];
                        }
                    }
                    // construct objects questions 
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choices, answer);
                    questionBank.add(ques);
                } else {
                    String[] choices = {"", "", "", "", ""};
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choices, rs.getString("answer"));
                    questionBank.add(ques);
                }
                answer = "";

            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);

        }
        // change Arraylist to array
        question = questionBank.toArray(new Question[questionBank.size()]);

        return question;
    }

    /*
    check if the questions that users choosed are more than the questions in
    database
     */
    public boolean checkQuestions(int quizDifficulty, int questionNumber) {
        boolean exception = false;
        int count = 0;
        IntToString its = new IntToString();
        String diff = its.toStringDiff(quizDifficulty);
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            // for questions in specific difficulty level
            if (quizDifficulty < 3) {
                String sql = "Select * from Question where difficulty = '" + diff + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    count++;
                }
                if (count < questionNumber) {
                    exception = true;
                }
            } else {
                // for questions mixed 
                String sql = "Select * from Question";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    count++;
                }
                if (count < questionNumber) {
                    exception = true;
                }
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);

        }
        return exception;
    }

    public void createTableQuiz(QuizOfStudent quizResult) {
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            // check if already have table 
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, quizResult.studentName.toUpperCase(), null);
            // if not, then create one
            if (!rs.next()) {
                //stmt.execute("drop table " + quizResult.studentName);
                String sql = "create table " + quizResult.studentName
                        + "(quizID varchar(40), difficulty varchar(40), score int, "
                        + "number int, startTime varchar(40), endTime varchar(40)";
                for (int i = 1; i < 51; i++) {

                    sql = sql + " , no" + i + " int, answer" + i + " varchar(40), correct" + i + " varchar(40)";
                }
                sql = sql + ")";

                stmt.execute(sql);
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }

    public void insertTableQuiz(QuizOfStudent quizResult) {
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            IntToString its = new IntToString();
            String sql = "Select quizID from " + quizResult.studentName;
            ResultSet rs = stmt.executeQuery(sql);
            int count = 1;
            // check the quizID to caculate the number of total quizzes
            while (rs.next()) {
                count++;
            }
            // quizID = andrewID + times (to make it unique)
            String quizID = quizResult.studentName + count;
            quizResult.quizId = quizID;
            // convert time to String
            String startDate = recorder.convertTimeToString(quizResult.startDate);
            String endDate = recorder.convertTimeToString(quizResult.finishDate);
            sql = "insert into " + quizResult.studentName + " values ('"
                    + quizResult.quizId + "', '"
                    + its.toStringDiff(quizResult.quizDifficulty) + "', " + quizResult.totalScore
                    + ", " + quizResult.questionsOfQuiz.length + ", '" + startDate
                    + "', '" + endDate + "', ";

            for (int i = 0; i < quizResult.answerOfStudent.length; i++) {
                String correct = "";

                if (quizResult.questionsOfQuiz[i].questionType == 2) {// TF
                    // save correct answer into database
                    correct = quizResult.questionsOfQuiz[i].correctAnswer.toLowerCase();

                } else {
                    correct = quizResult.questionsOfQuiz[i].correctAnswer;
                }
                sql = sql + quizResult.questionsOfQuiz[i].questionID
                        + ", '" + quizResult.answerOfStudent[i].toString() + "', '"
                        + correct + "', ";
            }

            for (int i = quizResult.answerOfStudent.length; i < 49; i++) {
                sql = sql + 0 + ", '', '', ";
            }

            sql = sql + 0 + ", '', '')";

            stmt.execute(sql);

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

    }

    public void createTableQuestion() {

        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            //check if the table exsit
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Question".toUpperCase(), null);
            if (!rs.next()) {
                // create the table question
                stmt.execute("create table Question(number int, type varchar(40), "
                        + "difficulty varchar(40), description long varchar, "
                        + "choice1 varchar(255), correct1 varchar(40), "
                        + "choice2 varchar(255), correct2 varchar(40),"
                        + "choice3 varchar(255), correct3 varchar(40),"
                        + "choice4 varchar(255), correct4 varchar(40),"
                        + "answer varchar(255))");
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }

    public Question[] insertQuestion(File csvFile) {
        ArrayList<Question> ques = new ArrayList();
        StringToInt sti = new StringToInt();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            // read questions from csv file
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            int count = 0;
            Question[] questionBank = getAllQuestions();
            boolean exist = false;
            String sql = "select * from Question";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                count = rs.getInt("number");
            }
            count++;
            while ((line = reader.readNext()) != null) {

                // check if the questions exist in database      
                for (Question q : questionBank) {
                    if (line[2].equals(q.content)) {
                        exist = true;
                    }
                }

                if (!exist) {

                    // if not,import data from file into database
                    if (line[0].equals("MA") | line[0].equals("MC")) {
                        sql = "insert into Question values (" + count + ", '";
                        for (int i = 0; i < line.length - 1; i++) {
                            sql = sql + line[i] + "', '";
                        }
                        sql = sql + line[line.length - 1] + "', '')";
                        stmt.execute(sql);
                        String[] choice = {line[3], line[5], line[7], line[9]};
                        String answer = "";
                        for (int i = 0; i < line.length; i++) {
                            if (line[i].equals("correct")) {
                                answer = line[i - 1];
                            }
                        }
                        // construct question object
                        Question question = new Question(sti.toIntType(line[0]),
                                count, sti.toIntDiff(line[1]),
                                line[2], choice, answer);
                        ques.add(question);
                    } else {
                        // import FIB and TF
                        if (line[0].equals("TF")) {
                            line[3] = line[3].toLowerCase();
                        }
                        sql = "insert into Question values (" + count + ", '"
                                + line[0] + "', '" + line[1] + "', '" + line[2] + "', '";
                        for (int i = 3; i < 11; i++) {
                            sql = sql + "" + "', '";
                        }
                        sql = sql + line[3] + "')";
                        String[] choice = {"", "", "", ""};
                        stmt.execute(sql);
                        // construct question object
                        Question question = new Question(sti.toIntType(line[0]),
                                count, sti.toIntDiff(line[1]),
                                line[2], choice, line[3]);
                        ques.add(question);
                    }
                    count++;
                }
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Question[] questions = new Question[ques.size()];
        // change Questions Arraylist to array
        questions = ques.toArray(new Question[ques.size()]);
        return questions;
    }

    /*
    get all current questions in database for questionbank
    */
    public Question[] getAllQuestions() {
        
        ArrayList<Question> allQuestion = new ArrayList();

        String answer = "";
        StringToInt sti = new StringToInt();
        try (Connection con = DriverManager.getConnection(quizUrl, quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "select * from Question";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String[] choice = new String[4];
                String[] key = {"A", "B", "C", "D"};
                if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                    for (int i = 0; i < 4; i++) {
                       // get choices from database
                        choice[i] = rs.getString(3 + 2 * (i + 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        if (rs.getString(2 * (i + 1) + 4).equals("correct")) {
                           // compare to get answers from database
                            answer = answer + key[i];
                        }
                    }
                    // construct Question Object
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choice, answer);
                    allQuestion.add(ques);
                } else {
                    String[] choices = {"", "", "", "", ""};
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choices, rs.getString("answer"));
                    allQuestion.add(ques);
                }
                // resset answer
                answer = "";

            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        Question[] question = new Question[allQuestion.size()];
        question = allQuestion.toArray(new Question[allQuestion.size()]);
        return question;
    }

    public HistoryRecord[] getHistoryRecord(String studentName) {
        ArrayList quizID = new ArrayList();
        ArrayList<Date> date = new ArrayList();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select quizID, endTime from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                quizID.add(rs.getString(1));
                date.add(recorder.convertStringToTime(rs.getString(2)));
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        
        HistoryRecord[] record = new HistoryRecord[quizID.size()];
        for (int i = 0; i < quizID.size(); i++) {
            record[i] = new HistoryRecord(quizID.get(i).toString(), date.get(i));
        }

        return record;
    }

    public QuizOfStudent getQuiz(String quizId, String studentName) {
        int num;// the number of questions of this quiz
        ArrayList<Integer> no = new ArrayList();// question ID in the Question table
        ArrayList answers = new ArrayList();
        ArrayList correctAnswers = new ArrayList();
        Date startDate = new Date();
        Date finishDate = new Date();
        StringToInt sti = new StringToInt();

        int totalDiff = 0;
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select * from " + studentName + " where quizID = '"
                    + quizId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                num = rs.getInt("number");
                // convert String to time
                startDate = recorder.convertStringToTime(rs.getString("startTime"));
                finishDate = recorder.convertStringToTime(rs.getString("endTime"));
                totalDiff = sti.toIntDiff(rs.getString("difficulty"));
               
                for (int i = 0; i < num; i++) {
                    
                    no.add(rs.getInt(4 + 3 * (i + 1)));
                    answers.add(rs.getString(5 + 3 * (i + 1)));
                    correctAnswers.add(rs.getString(6 + 3 * (i + 1)));
                }
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

        Question[] allQuestions = getAllQuestions();
        ArrayList<Question> ques = new ArrayList<Question>();
        // check if questions have been deleted
        boolean delete = true;
        for (int j = 0; j < no.size(); j++) {
            for (int i = 0; i < allQuestions.length; i++) {
                if (allQuestions[i].questionID == no.get(j)) {
                    // if ID matches, then construct Question Object for quiz
                    Question question = new Question(allQuestions[i].questionType,
                            allQuestions[i].questionID, allQuestions[i].questionDifficult,
                            allQuestions[i].content, allQuestions[i].choices, correctAnswers.get(j).toString());
                    ques.add(question);
                    delete = false;
                }
            }
            if (delete) {
                // if questions have been deleted, then warn users
                String[] choices = {"", "", "", ""};
                Question deletedQuestion = new Question(0, 0, 0,
                        "This question was deleted.", choices, "");
                ques.add(deletedQuestion);
            }
        }
        Question[] questionsOfQuiz = ques.toArray(new Question[ques.size()]);

        Answer[] answerOfStudent = new Answer[questionsOfQuiz.length];
        for (int i = 0; i < questionsOfQuiz.length; i++) {

            switch (questionsOfQuiz[i].questionType) {
                case 0:// for MA
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType,
                            sti.toIntMultipleAnswer(answers.get(i).toString()));
                    // if the question is answered, set the boolean true
                    answerOfStudent[i].isAnswer = true;
                    break;
                case 1:// for MC
                    answerOfStudent[i] = new Answer(no.get(i), questionsOfQuiz[i].questionType,
                            sti.toIntSingleAnswer(answers.get(i).toString()));
                    answerOfStudent[i].isAnswer = true;

                    break;
                case 2:// for TF
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType, Boolean.valueOf(answers.get(i).toString()));
                    answerOfStudent[i].isAnswer = true;
                    break;
                case 3:// for FIB
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType, answers.get(i).toString());
                    answerOfStudent[i].isAnswer = true;
                    break;
                default: // for questions not answered by users
                    answerOfStudent[i] = new Answer();
                    break;
            }

        }
        // construct the object quiz
        QuizOfStudent quizOfStudent = new QuizOfStudent(studentName, questionsOfQuiz,
                answerOfStudent, startDate, finishDate);
        quizOfStudent.quizId = quizId;
        quizOfStudent.quizDifficulty = totalDiff;

        return quizOfStudent;
    }

    public int[] getStudentAllRecordScore(String studentName) {

        ArrayList<Integer> score = new ArrayList();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select score from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                score.add(rs.getInt(1));
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        int[] scores = new int[score.size()];
        for (int i = 0; i < score.size(); i++) {
            scores[i] = score.get(i);
        }
        return scores;
    }

    public String[] getStudentRecordDate(String studentName) {
        ArrayList date = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select endTime from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                date.add(rs.getString(1));
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        String[] endDate = new String[date.size()];
        for (int i = 0; i < date.size(); i++) {
            endDate[i] = sdf.format(recorder.convertStringToTime(date.get(i).toString()).getTime());
        }
        return endDate;

    }

    public int[] getStudentAverageScoreOfThreeDifficulty(String studentName) {
        int[] avgScore = new int[3];
        double[] diff = new double[3];
        double[] diffCorrect = new double[3];
        ArrayList<Integer> numCorrect = new ArrayList();// question number in question bank
        ArrayList<Integer> num = new ArrayList();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select * from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                num.add(rs.getInt("number"));
                for (int i = 0; i < 50; i++) {
                   // compare to define if the answer is correct
                    if (rs.getString(6 + 3 * (i + 1)).equals(rs.getString(5 + 3 * (i + 1)))) {
                        numCorrect.add(rs.getInt("number"));
                    }
                }
            }
            // get all questions from database
            Question[] allQuestions = getAllQuestions();
            // check if there are questions deleted
            boolean delete = true;
            for (int j = 0; j < num.size(); j++) {
                for (int i = 0; i < allQuestions.length; i++) {
                    if (allQuestions[i].questionID == num.get(j)) {
                        delete = false;
                    }
                }
                // remove the ID of deleted questions
                if (delete) {
                    num.remove(j);
                }
            }
           // total number of questions in three difficulty
            for (int i = 0; i < num.size(); i++) {
                sql = "Select difficulty from Question where number = " + num.get(i);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getString(1).equals("E")) {
                        diff[0]++;
                    } else if (rs.getString(1).equals("M")) {
                        diff[1]++;
                    } else {
                        diff[2]++;
                    }
                }
            }
            // correct questions in three difficulty
            for (int i = 0; i < numCorrect.size(); i++) {
                sql = "Select difficulty from Question where number = " + numCorrect.get(i);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (rs.getString(1).equals("E")) {
                        diffCorrect[0]++;
                    } else if (rs.getString(1).equals("M")) {
                        diffCorrect[1]++;
                    } else {
                        diffCorrect[2]++;
                    }
                }
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

        for (int i = 0; i < 3; i++) {
            avgScore[i] = (int) ((diffCorrect[i] / diff[i]) * 100);
        }

        return avgScore;
    }

    public void createTable() {
        try (Connection con = DriverManager.getConnection(userUrl, userUsername,
                userPassword)) {
            // check if the table Students exist
            Statement stmt = con.createStatement();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Students".toUpperCase(), null);
            if (!rs.next()) {
                // if not, create the table Students
                stmt.execute("create table Students(andrewID varchar(40), "
                        + "password varchar(40))");
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }

    public boolean checkID(String id) {
        // check if the students exsit in database
        ArrayList allStudent = getAllStudent();
        boolean exist = false;
        for (int i = 0; i < allStudent.size(); i++) {
            if (allStudent.get(i).toString().equals(id)) {
                exist = true;
            }
        }
        return exist;
    }

    public void createPassword() {
         // create password
        Random rand = new Random();
        int num = rand.nextInt(8999) + 1000;
        String letters = "";
        for (int i = 0; i < 3; i++) {
            int index = 97 + rand.nextInt(25);
            letters = letters + (char) index;
        }
        passW = letters + num;
    }

     /*
    Send e-mail to students to inform them their password
    */
    public void sendMail(String text) {
       
        String from = "lxy125@gmail.com";
        String pass = "lingxingyu125";
        String[] to = {text + "@andrew.cmu.edu"}; // list of recipient email addresses
        String subject = "Your Exam Password";
        String body = "Dear Student,\n\nHere is your exam password:\n"
                + passW + "\n\nRegards\nCMU";

        sendFromGMail(from, pass, to, subject, body);

    }

    public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    
    /*encrypt the password using MD5
    
    */
    public String encryptPassW() {
        String pass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(passW.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            pass = bigInt.toString(16);

        } catch (Exception e) {

            System.out.println("Exception: " + e);

        }
        return pass;
    }

    public void insertStudent(String pass, String ID) {

        try (Connection con = DriverManager.getConnection(userUrl, userUsername, userPassword)) {
            Statement stmt = con.createStatement();
            String sql = "insert into Students values ('" + ID + "', '" + pass + "')";
            stmt.execute(sql);
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }

    public ArrayList<String> getAllStudent() {
        ArrayList<String> allStudent = new ArrayList();
        try (Connection con = DriverManager.getConnection(userUrl, userUsername, userPassword)) {
            Statement stmt = con.createStatement();
            String sql = "select andrewID from Students";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                allStudent.add(rs.getString(1));
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        return allStudent;
    }

    public void deleteStudent(String andrewID) {

        try (Connection con = DriverManager.getConnection(userUrl, userUsername, userPassword)) {
            Statement stmt = con.createStatement();
            stmt.execute("delete from Students where andrewID = '" + andrewID + "'");

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

    }

}
