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
import java.sql.PreparedStatement;
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

    public void createTableQuiz(QuizOfStudent quizResult) {
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, quizResult.studentName.toUpperCase(), null);
            if (!rs.next()) {
                String sql = "create table " + quizResult.studentName
                        + "(quizID varchar(40), difficulty varchar(40), score int, "
                        + "number int, startTime date, endTime date";
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
            quizResult.quizId = quizID;
            java.sql.Date startDate = new java.sql.Date(quizResult.startDate.getTime());
            java.sql.Date endDate = new java.sql.Date(quizResult.finishDate.getTime());
            sql = "insert into " + quizResult.studentName + " values ('"
                    + quizResult.quizId + "', '"
                    + its.toStringDiff(quizResult.quizDifficulty) + "', " + quizResult.totalScore
                    + ", " + quizResult.questionsOfQuiz.length + ", ?, ?, ";

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
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            pstmt.executeUpdate();

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
            int count = 1;
            Question[] questionBank = getAllQuestions();
            boolean exist = false;
            while ((line = reader.readNext()) != null) {
                // import data from file into database
                // import MA and MC
                for (Question q : questionBank) {
                    count++;
                    if (line[2].equals(q.content)) {
                        exist = true;
                    }
                }

                if (!exist) {
                    if (line[0].equals("MA") | line[0].equals("MC")) {
                        String sql = "insert into Question values (" + count + ", '";
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
                        String sql = "insert into Question values (" + count + ", '"
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
                date.add(rs.getDate(2));

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
        ArrayList<Integer> no = new ArrayList();// question number in question bank
        ArrayList answers = new ArrayList();
        ArrayList<Question> questions = new ArrayList();

        boolean tf = false;
        Date startDate = new Date();
        Date finishDate = new Date();
        StringToInt sti = new StringToInt();
        ArrayList<Answer> answerTemp = new ArrayList();
        int totalDiff = 0;
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select * from " + studentName + " where quizID = '"
                    + quizId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                num = rs.getInt("number");
                for (int i = 0; i < num; i++) {
                    no.add(rs.getInt(4 + 3 * (i + 1)));
                    answers.add(rs.getString(5 + 3 * (i + 1)));
                    startDate = rs.getDate("startTime");
                    finishDate = rs.getDate("endTime");
                    totalDiff = sti.toIntDiff(rs.getString("difficulty"));

                }
            }
            for (int i = 0; i < no.size(); i++) {
                sql = "Select * from Question where number = " + no.get(i);
                rs = stmt.executeQuery(sql);
                String[] choice = new String[4];
                while (rs.next()) {
                    if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                        for (int j = 0; j < 4; j++) {

                            choice[j] = rs.getString(2 + 2 * (j + 1));
                        }

                    } else if (rs.getString("type").equals("TF")) {
                        if (answers.get(i).equals("true")) {
                            tf = true;
                        }
                    }
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choice, answers.get(i).toString());
                    questions.add(ques);
                    int questionType = 100;
                    if (answers.get(i) != "") {
                        questionType = sti.toIntType(rs.getString("type"));
                    }

                    switch (questionType) {
                        case 0:
                            answerTemp.add(new Answer(no.get(i),
                                    questionType, sti.toIntMultipleAnswer(answers.get(i).toString())));
                            break;
                        case 1:
                            answerTemp.add(new Answer(no.get(i), questionType,
                                    sti.toIntSingleAnswer(answers.get(i).toString())));
                            break;
                        case 2:
                            answerTemp.add(new Answer(no.get(i), questionType, tf));
                            break;
                        case 3:
                            answerTemp.add(new Answer(no.get(i), questionType, answers.get(i).toString()));
                            break;
                        default:
                            break;
                    }

                }
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

        Answer[] answerOfStudent = answerTemp.toArray(new Answer[answerTemp.size()]);
        Question[] questionOfQuiz = questions.toArray(new Question[questions.size()]);
        QuizOfStudent quizOfStudent = new QuizOfStudent(studentName, questionOfQuiz,
                answerOfStudent, startDate, finishDate);
        quizOfStudent.quizId = quizId;
        quizOfStudent.quizDifficulty = totalDiff;

        return quizOfStudent;
    }

    public int[] getStudentAllRecordScore(String studentName) {

        String ID = studentName;
        ArrayList<Integer> score = new ArrayList();
        ArrayList<Integer> num = new ArrayList();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select score,　number from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                score.add(rs.getInt(1));
                num.add(rs.getInt(2));
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        int[] scores = new int[score.size()];
        for (int i = 0; i < score.size(); i++) {
            scores[i] = (int) ((((double) score.get(i) / num.get(i))) * 100);
        }
        return scores;
    }

    public String[] getStudentRecordDate(String studentName) {
        ArrayList date = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select endDate from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                date.add(sdf.format(rs.getDate(1)));
            }

        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        String[] endDate = new String[date.size()];
        for (int i = 0; i < date.size(); i++) {
            endDate[i] = date.get(i).toString();
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