package implcommunicate;

import interfacewithserve.ICommunicate2;

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
import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.StringToInt;
import utilclass.IntToString;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;

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

    @Override
    public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
        IntToString its = new IntToString();
        Question[] question = new Question[questionNumber];
        
        String answer = "";
        ArrayList<Question> questionBank = new ArrayList();
        StringToInt sti = new StringToInt();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = null;
            if (quizDifficulty < 3) {
                String diff = its.toStringDiff(quizDifficulty);
                sql = "Select * from Question where difficulty = '" + diff
                        + "' order by Random() fetch first " + questionNumber + " rows only";
            } else {
                sql = "Select * from Question order by Random() fetch first " + questionNumber + " rows only";
            }

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String[] choice = new String[4];
                if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                    for (int i = 0; i < 4; i++) {

                        choice[i] = rs.getString(3 + 2 * (i + 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        if (rs.getString(2 * (i + 1) + 4).equals("correct")) {
                            answer = answer + rs.getString(3 + 2 * (i + 1));
                        }
                    }

                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choice, answer);
                    questionBank.add(ques);
                } else {
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), null, rs.getString("answer"));
                    questionBank.add(ques);
                }
                answer = "";

            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);

        }

        for (int i = 0; i < questionNumber; i++) {
            question[i] = questionBank.get(i);
        }
        return question;
    }

    @Override
    public void recordQuizResultToServe(QuizOfStudent quizResult) {

        createTableQuiz(quizResult);
        insertTableQuiz(quizResult);
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
                        + "number int, startTime date, endTime date ";
                for (int i = 0; i < 50; i++) {

                    sql = sql + " , " + i + "int, answer varchar(40), correct" + i + " varchar(40)";
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
            String sql = "insert into " + quizResult.studentName + " values ('"
                    + quizResult.quizId + "', '"
                    + its.toStringDiff(quizResult.quizDifficulty) + ", '" + quizResult.totalScore
                    + "', " + quizResult.questionsOfQuiz.length + ", "
                    + quizResult.startDate + ", " + quizResult.finishDate + ", ";

            for (int i = 0; i < quizResult.answerOfStudent.length; i++) {
                String correct = null;
                //if(!quizResult.answerOfStudent[i].isAnswer){
                //quizResult.answerOfStudent[i].questionType = 4; 
                //}
                if (quizResult.answerOfStudent[i].toString().equals(quizResult.questionsOfQuiz[i].correctAnswer)) {
                    correct = "correct";
                } else {
                    correct = "incorrect";
                }
                sql = sql + quizResult.questionsOfQuiz[i].questionID
                        + ", '" + quizResult.answerOfStudent[i].toString() + "', '"
                        + correct + "', ";
            }

            for (int i = quizResult.answerOfStudent.length; i < 49; i++) {
                sql = sql + "'', ";
            }

            sql = sql + "'')";

            stmt.execute(sql);
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

    }

    @Override
    public boolean login(int loginType, String userName, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Question[] importQuestionFromCSV(File CSVFile) {
        csvFile = CSVFile;
        createTableQuestion();
        Question[] questions = insertQuestion();
        return questions;

    }

    public void createTableQuestion() {

        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            // check if the table exsit
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Question".toUpperCase(), null);
            if (!rs.next()) {
                // create the table question
                //stmt.execute("drop table Question");
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

    public Question[] insertQuestion(){
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
                for(Question q: questionBank){
                    count ++;
                    if(line[2].equals(q.content))
                        exist = true;
                }
                if(!exist){
                if (line[0].equals("MA") | line[0].equals("MC")) {
                    String sql = "insert into Question values (" + count + ", '";
                    for (int i = 0; i < line.length - 1; i++) {
                        sql = sql + line[i] + "', '";
                    }
                    sql = sql + line[line.length - 1] + "', '')";
                    stmt.execute(sql);
                    String[] choice = {line[3], line[5], line[7], line[9]};
                    String answer = null;
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
                    String sql = "insert into Question values (" + count + ", '"
                            + line[0] + "', '" + line[1] + "', '" + line[2] + "', '";
                    for (int i = 3; i < 11; i++) {
                        sql = sql + "" + "', '";
                    }
                    sql = sql + line[3] + "')";
                    //System.out.println(sql);
                    stmt.execute(sql);
                    Question question = new Question(sti.toIntType(line[0]),
                            count, sti.toIntDiff(line[1]),
                            line[2], null, line[3]);
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
        for (int i = 0; i < ques.size(); i++) {
            questions[i] = ques.get(i);
        }
        return questions;
    }
    
    @Override
    public Question[] getAllQuestions(){
        ArrayList<Question> allQuestion = new ArrayList();
       
        String answer = "";
        StringToInt sti = new StringToInt();
        try (Connection con = DriverManager.getConnection(quizUrl, quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "select * from Question";
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                 String[] choice = new String[4];
                if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                    for (int i = 0; i < 4; i++) {

                        choice[i] = rs.getString(3 + 2 * (i + 1));
                    }
                    for (int i = 0; i < 4; i++) {
                        if (rs.getString(2 * (i + 1) + 4).equals("correct")) {
                            answer = answer + rs.getString(3 + 2 * (i + 1));
                        }
                    }

                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), choice, answer);
                    allQuestion.add(ques);
                } else {
                    Question ques = new Question(sti.toIntType(rs.getString("type")),
                            rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                            rs.getString("description"), null, rs.getString("answer"));
                    allQuestion.add(ques);
                }
                answer = "";
             
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        Question[] question = new Question[allQuestion.size()];
        for (int i = 0; i < allQuestion.size(); i++) {
            question[i] = allQuestion.get(i);
        }
        return question;
    }



    @Override
    public HistoryRecord[] getHistoryRecordFromServeByStudentName(String studentName) {

        ArrayList quizID = new ArrayList();
        ArrayList<Date> date = new ArrayList();
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select quizID, finishDate from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                quizID.add(rs.getString("quizID"));
                date.add(rs.getDate("finishDate"));
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

    @Override
    public QuizOfStudent getQuizByQuizId(String quizId, String studentName) {
        int num;// the number of question of this quiz
        ArrayList<Integer> no = new ArrayList();// question number in question bank
        ArrayList answers = new ArrayList();
        ArrayList<Question> questions = new ArrayList();
        ArrayList<Integer> ma = new ArrayList();
        int mc = 0;
        boolean tf = false;
        Date startDate = null, finishDate = null;
        StringToInt sti = new StringToInt();
        ArrayList<Answer> answerTemp = new ArrayList();
        int totalDiff = 0;
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select * from " + studentName + "where quizID = "
                    + quizId;
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
                String correctAnswer = null;
                if (rs.getString("type").equals("MC") | rs.getString("type").equals("MA")) {
                    for (String a : choice) {

                        choice[i] = rs.getString(2 + 2 * (i + 1));
                    }
                    for (int j = 0; j < 4; j++) {
                        if (rs.getString(2 * (i + 1) + 3).equals("correct")) {
                            correctAnswer = correctAnswer + rs.getString(2 + 2 * (i + 1));
                        }
                    }
                    if (rs.getString("type").equals("MA")) {
                        ma = sti.toIntMultipleAnswer(answers.get(i).toString());
                    } else {
                        mc = sti.toIntSingleAnswer(answers.get(i).toString());
                    }

                } else if (rs.getString("type").equals("TF")) {
                    if (answers.get(i).equals("true")) {
                        tf = true;
                    }
                }
                Question ques = new Question(sti.toIntType(rs.getString("type")),
                        rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                        rs.getString("description"), choice, correctAnswer);
                questions.add(ques);
                int questionType = 100;
                if (answers.get(i) != "") {
                    questionType = sti.toIntType(rs.getString("type"));
                }

                switch (questionType) {
                    case 0:
                        answerTemp.add(new Answer(no.get(i), questionType, ma));
                        break;
                    case 1:
                        answerTemp.add(new Answer(no.get(i), questionType, mc));
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
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }

        Answer[] answerOfStudent = new Answer[answerTemp.size()];
        for (int i = 0; i < answerTemp.size(); i++) {
            answerOfStudent[i] = answerTemp.get(i);
        }

        QuizOfStudent quizOfStudent = new QuizOfStudent(studentName, getRandomQuestionListOfQuiz(totalDiff, answerOfStudent.length),
                answerOfStudent, startDate, finishDate);

        return quizOfStudent;
    }

    @Override
    public int[] getStudentAllRecordScoreByStudentName(String studentName) {

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

    @Override
    public String[] getStudentRecordDateByStudentName(String studentName) {
        ArrayList date = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
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

    @Override
    public int[] getStudentAverageScoreOfThreeDifficultyByStudentName(String studentName) {
        int[] avgScore = new int[3];
        int num;// the number of question of this quiz
        int count = 0;
        int[] diff = new int[3];
        ArrayList<Integer> no = new ArrayList();// question number in question bank
        try (Connection con = DriverManager.getConnection(quizUrl,
                quizUsername, quizPassword)) {
            Statement stmt = con.createStatement();
            String sql = "Select * from " + studentName;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                count++;
                for (int i = 0; i < 50; i++) {
                    if (rs.getString(6 + 3 * (i + 1)).equals("correct")) {
                        no.add(rs.getInt("number"));
                    }
                }
                for (int i = 0; i < no.size(); i++) {
                    sql = "Select difficulty from Question where number = " + no.get(i);
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
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        for (int i = 0; i < 3; i++) {
            avgScore[i] = (int) (((double) diff[i] / count) * 100);
        }

        return avgScore;
    }

    @Override
    public boolean addStudent(String text) {
        createTable();
        if (!checkID(text)) {
            createPassword();
            sendMail(text, passW);
            insertStudent(encryptPassW(), text);
        }
        boolean success = true;
        return success;

    }

    private void createTable() {
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

    private boolean checkID(String id) {
        ArrayList allStudent = getAllStudent();
        boolean exist = false;
        for (int i = 0; i < allStudent.size(); i++) {
            if (allStudent.get(i).toString().equals(id)) {
                exist = true;
            }
        }
        return exist;
    }

    private void createPassword() {

        Random rand = new Random();
        int num = rand.nextInt(8999) + 1000;
        String letters = "";
        for (int i = 0; i < 3; i++) {
            int index = 97 + rand.nextInt(25);
            letters = letters + (char) index;
        }
        passW = letters + num;
    }

    private void sendMail(String text, String password) {
        String from = "lxy125@gmail.com";
        String pass = "lingxingyu125";
        String[] to = {text + "@andrew.cmu.edu"}; // list of recipient email addresses
        String subject = "Your Exam Password";
        String body = "Dear Student,\n\nHere is your exam password:\n" 
                + password + "\n\nRegards\nCMU";

        sendFromGMail(from, pass, to, subject, body);

    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
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

    private String encryptPassW() {

        String pass = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(passW.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            pass = bigInt.toString();

        } catch (Exception e) {

            System.out.println("Exception: " + e);

        }
        return pass;
    }

    private void insertStudent(String pass, String ID) {

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

    public boolean deleteStudent(String andrewID) {
        boolean delete = false;
        try (Connection con = DriverManager.getConnection(userUrl, userUsername, userPassword)) {
            Statement stmt = con.createStatement();
            stmt.execute("delete from Students where andrewID = '" + andrewID + "'");

            delete = true;
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        return delete;

    }

}
