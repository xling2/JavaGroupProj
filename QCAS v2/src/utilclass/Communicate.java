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
                String diff = its.toStringDiff(quizDifficulty);
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

    public boolean checkQuestions(int quizDifficulty, int questionNumber) {
        boolean exception = false;
        int count = 0;
        IntToString its = new IntToString();
        String diff = its.toStringDiff(quizDifficulty);
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
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
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, quizResult.studentName.toUpperCase(), null);
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
            while (rs.next()) {
                count++;
                //System.out.println(rs.getString(1));
            }
            String quizID = quizResult.studentName + count;
            //System.out.println(quizID);
            quizResult.quizId = quizID;
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
                    if (quizResult.answerOfStudent[i].toString().equals(quizResult.questionsOfQuiz[i].correctAnswer.toLowerCase())) {
                        correct = "correct";
                    } else {
                        correct = "incorrect";
                    }
                } else if (quizResult.answerOfStudent[i].toString().equals(quizResult.questionsOfQuiz[i].correctAnswer)) {
                    correct = "correct";
                } else {
                    correct = "incorrect";
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
                // stmt.execute("drop table Question");
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
            System.out.println(count);
            while ((line = reader.readNext()) != null) {
                // import data from file into database
                // import MA and MC
                for (Question q : questionBank) {
                    if (line[2].equals(q.content)) {
                        exist = true;
                    }
                }

                if (!exist) {
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
        questions = ques.toArray(new Question[ques.size()]);
        return questions;
    }

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

                        choice[i] = rs.getString(3 + 2 * (i + 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        if (rs.getString(2 * (i + 1) + 4).equals("correct")) {
                            answer = answer + key[i];
                        }
                    }

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
        int num;// the number of question of this quiz
        System.out.println(quizId);
        ArrayList<Integer> no = new ArrayList();// question number in question bank
        ArrayList answers = new ArrayList();
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
                //System.out.println(num);
                for (int i = 0; i < num; i++) {
                    //System.out.println(rs.getInt(4 + 3 * (i + 1)));
                    no.add(rs.getInt(4 + 3 * (i + 1)));
                    answers.add(rs.getString(5 + 3 * (i + 1)));
                    startDate = recorder.convertStringToTime(rs.getString("startTime"));
                    finishDate = recorder.convertStringToTime(rs.getString("endTime"));
                    totalDiff = sti.toIntDiff(rs.getString("difficulty"));
                }
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

        Question[] allQuestions = getAllQuestions();
        ArrayList<Question> ques = new ArrayList<Question>();
        int count = 0;
        boolean delete = true;
        for (int j = 0; j < no.size(); j++) {
            for (int i = 0; i < allQuestions.length; i++) {
                if (allQuestions[i].questionID == no.get(j)) {
                    ques.add(allQuestions[i]);
                    delete = false;
                }
              }
            if(delete){
                  count ++;
            }            
            }
        Question[] questionsOfQuiz = ques.toArray(new Question[ques.size()]);

        Answer[] answerOfStudent = new Answer[questionsOfQuiz.length];
        for (int i = 0; i < questionsOfQuiz.length; i++) {

            switch (questionsOfQuiz[i].questionType) {
                case 0:
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType,
                            sti.toIntMultipleAnswer(answers.get(i).toString()));
                    answerOfStudent[i].isAnswer = true;
                    break;
                case 1:
                    answerOfStudent[i] = new Answer(no.get(i), questionsOfQuiz[i].questionType,
                            sti.toIntSingleAnswer(answers.get(i).toString()));
                    answerOfStudent[i].isAnswer = true;

                    break;
                case 2:
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType, Boolean.valueOf(answers.get(i).toString()));
                    answerOfStudent[i].isAnswer = true;
                    break;
                case 3:
                    answerOfStudent[i] = new Answer(no.get(i),
                            questionsOfQuiz[i].questionType, answers.get(i).toString());
                    answerOfStudent[i].isAnswer = true;
                    break;
                default:
                    answerOfStudent[i] = new Answer();
                    break;
            }

        }
        QuizOfStudent quizOfStudent = new QuizOfStudent(studentName, questionsOfQuiz,
                answerOfStudent, startDate, finishDate);
        quizOfStudent.quizId = quizId;
        quizOfStudent.quizDifficulty = totalDiff;
        quizOfStudent.setDeletedQuestion(count);

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
                    if (rs.getString(6 + 3 * (i + 1)).equals("correct")) {
                        numCorrect.add(rs.getInt("number"));
                    }
                }
            }
            Question[] allQuestions = getAllQuestions();
            boolean delete = true;
            for (int j = 0; j < num.size(); j++) {
                for (int i = 0; i < allQuestions.length; i++) {
                    if (allQuestions[i].questionID == num.get(j)) {
                        delete = false;
                    }
                }
                if (delete) {
                    num.remove(j);
                }
            }

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
            Statement stmt = con.createStatement();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Students".toUpperCase(), null);
            if (!rs.next()) {
                // create the table question
                stmt.execute("create table Students(andrewID varchar(40), "
                        + "password varchar(40))");
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }

    public boolean checkID(String id) {
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

        Random rand = new Random();
        int num = rand.nextInt(8999) + 1000;
        String letters = "";
        for (int i = 0; i < 3; i++) {
            int index = 97 + rand.nextInt(25);
            letters = letters + (char) index;
        }
        passW = letters + num;
    }

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
