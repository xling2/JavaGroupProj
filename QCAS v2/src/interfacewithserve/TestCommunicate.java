package interfacewithserve;

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
import utilclass.Answer;
import utilclass.HistoryRecord;
import utilclass.Question;
import utilclass.QuizOfStudent;
import utilclass.User;
import utilclass.StringToInt;
import java.util.ArrayList;

public class TestCommunicate implements CommunicateWithServe {
        private String questionUrl = "jdbc:derby:Question;"
                + "create=true";
        private String questionUsername = "question";
        private String questionPassword = "question";
        private File csvFile;
        private String studentUrl = "jdbc:derby:Student; create=true";
        private String studentUsername = "student";
        private String studentPassword = "student";
        private String passW;
	
        @Override
	public Question[] getRandomQuestionListOfQuiz(int quizDifficulty, int questionNumber) {
		// TODO Auto-generated method stub
		 Question[] questions = new Question[questionNumber];
                 String[] choice = new String[4];
                 String  answer = null;
                 ArrayList<Question> question = new ArrayList();
                 StringToInt sti = new StringToInt();
                 try (Connection con = DriverManager.getConnection(questionUrl,
                        questionUsername, questionPassword)){
            Statement stmt = con.createStatement();
            String sql = "Select * from Question where difficulty = '" + quizDifficulty +
                    "' order by Random() fetch first " +  questionNumber + " rows only";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
               for(int i = 0; i < 4; i++){
                   if(rs.getString(2*(i+1) + 3).equals("correct")){
                       answer = answer + rs.getString(2 + 2*(i+1));
                   }
               }
               
               for(int i = 0; i < 4; i++){
                   
                    choice[i] = rs.getString(2 + 2*(i+1));
                   }
               
            Question ques = new Question(sti.toIntType(rs.getString("type")), 
                    rs.getInt("number"), sti.toIntDiff(rs.getString("difficulty")),
                    rs.getString("description"), choice, rs.getString(answer));  
		question.add(ques);
            }
            
                }catch (SQLException se) {
            System.out.println("Exception: " + se);
            
        }
		for(int i = 0; i < questionNumber; i++){
                questions[i] = question.get(i);
                }
                 return questions;
	}

	@Override
	public void recordQuizResultToServe(QuizOfStudent quizResult) {
		// TODO Auto-generated method stub
		System.out.println("TestCommunicate.recordQuizResultToServe");
	}

	@Override
	public boolean login(int loginType, int id, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUserNameById(int loginType, int userId) {
		// TODO Auto-generated method stub
		return "Shelly";
	}

        @Override
	public void deleteById(int questionID) {
		// TODO Auto-generated method stub
		System.out.println("delete");
	}
        


	@Override
	public boolean importQuestionFromCSV(File CSVFile) {
		 csvFile = CSVFile;
                 createTableQuestion();
                 insertQuestion();
                 return true;
	}
        
        public void createTableQuestion(){
            
            
            try (Connection con = DriverManager.getConnection(questionUrl, 
                    questionUsername, questionPassword)) {
            Statement stmt = con.createStatement();
            // check if the table exsit
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Question".toUpperCase(),null);
             if(!rs.next()){
            // create the table question
            stmt.execute("create table Question(number int, type varchar(40), "
                    + "difficulty varchar(40), description long varchar, "
                    + "choice1 varchar(255), correct1 varchar(255), "
                    + "choice2 varchar(255), correct2 varchar(255),"
                    + "choice3 varchar(255), correct3 varchar(255),"
                    + "choice4 varchar(255), correct4 varchar(255),"
                    + "answer varchar(255))");
             }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
    }
        
        
        public void insertQuestion(){
            
            try (Connection con = DriverManager.getConnection(questionUrl, 
                    questionUsername, questionPassword)) {
            Statement stmt = con.createStatement();
             CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            int count = 1;
            while ((line = reader.readNext()) != null) {
                // import data from file into database
                // import MA and MC
                if(line[0].equals("MA") | line[0].equals("MC")){
                String sql = "insert into Question values (" + count + ", '";
                for(int i = 0; i < line.length-1; i++)  {     
                    sql =  sql + line[i] + "', '";    
                }
                sql = sql + line[line.length-1] + "', '')";
                stmt.execute(sql);
        }
                else {
                // import FIB and TF
                String sql = "insert into Question values (" + count + ", '" +
                        line[0] +"', '" + line[1] + "', '" + line[2] + "', '"; 
                for(int i = 3; i < 11; i++) {       
                sql = sql + "" + "', '" ;
                }    
                sql = sql + line[3] +"')";
                //System.out.println(sql);
                stmt.execute(sql);
                }
                 count ++; 
            }
            }catch (SQLException se) {
            System.out.println("Exception: " + se);
            } catch (FileNotFoundException e) {
            e.printStackTrace();
}
            catch (IOException e) {
            e.printStackTrace();
        }
        }

	@Override
	public HistoryRecord[] getHistoryRecordFromServeByStudentID(int studentUserId) {
		// TODO Auto-generated method stub
		int x = 20;
		Date now = new Date();
		HistoryRecord[] historyRecord = new HistoryRecord[x];
		for (int i = 0; i < x; i++) {
			historyRecord[i] = new HistoryRecord(x, new Date(now.getTime() + 100000 * x));
		}
		return historyRecord;
	}

	@Override
	public QuizOfStudent getQuizByQuizId(int quizId) {
		// TODO Auto-generated method stub
		User student = new User("Shelly", 123456);
		int questionNumber = quizId;
		int[] questionsIdOfQuiz = new int[questionNumber];
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			questionsIdOfQuiz[i] = i;
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, questionsIdOfQuiz, answerOfStudent, startDate, finishDate);
		quizOfStudent.getQuestionFromServe(this);
		for (int i = 0; i < questionNumber; i++) {
			int questionType = quizOfStudent.questionsOfQuiz[i].questionType;
			switch (questionType) {
			case 0:
				answerOfStudent[i] = new Answer(1, questionType, new ArrayList<>());
				break;
			case 1:
				answerOfStudent[i] = new Answer(1, questionType, 1);
				break;
			case 2:
				answerOfStudent[i] = new Answer(1, questionType, true);
				break;
			case 3:
				answerOfStudent[i] = new Answer(1, questionType, "A");
				break;
			default:
				break;
			}
		}
		return quizOfStudent;
	}

	@Override
	public int[] getStudentAllRecordScoreById(int id) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public String[] getStudentRecordDateById(int id) {
		// TODO Auto-generated method stub
		return new String[] { "2011-10-16", "2011-10-17", "2011-10-18" };
	}

	@Override
	public int[] getStudentAverageScoreOfThreeDifficultyById(int id) {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public User[] getStudentFailedListOfLastMouth() {
		// TODO Auto-generated method stub
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|Mouth" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentFailedListOfLastQuarter() {
		// TODO Auto-generated method stub
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|quarter" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentFailedListOfLastYear() {
		User[] studentFailedList = new User[4];
		for (int i = 0; i < studentFailedList.length; i++) {
			studentFailedList[i] = new User("YangLiu|Year" + i, i);
		}
		return studentFailedList;
	}

	@Override
	public User[] getStudentPassedListOfLastMouth() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|Mouth" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public User[] getStudentPassedListOfLastQuarter() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|quarter" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public User[] getStudentPassedListOfLastYear() {
		User[] studentPassedList = new User[4];
		for (int i = 0; i < studentPassedList.length; i++) {
			studentPassedList[i] = new User("Shelly|year" + i, i);
		}
		return studentPassedList;
	}

	@Override
	public QuizOfStudent getQuizByStudentIdAndTimeType(int id, int timeType) {
		// TODO Auto-generated method stub
		String name = this.getUserNameById(1, id);
		User student = new User(name, id);
		int questionNumber = 20;
		int[] questionsIdOfQuiz = new int[questionNumber];
		Date startDate = new Date();
		Date finishDate = new Date(startDate.getTime() + 100000);
		Answer[] answerOfStudent = new Answer[questionNumber];
		for (int i = 0; i < questionNumber; i++) {
			questionsIdOfQuiz[i] = i;
			answerOfStudent[i] = new Answer();
		}
		QuizOfStudent quizOfStudent = new QuizOfStudent(student, questionsIdOfQuiz, answerOfStudent, startDate, finishDate);
		quizOfStudent.getQuestionFromServe(this);
		for (int i = 0; i < questionNumber; i++) {
			int questionType = quizOfStudent.questionsOfQuiz[i].questionType;
			switch (questionType) {
			case 0:
				answerOfStudent[i] = new Answer(1, questionType, new ArrayList<>());
				break;
			case 1:
				answerOfStudent[i] = new Answer(1, questionType, 1);
				break;
			case 2:
				answerOfStudent[i] = new Answer(1, questionType, true);
				break;
			case 3:
				answerOfStudent[i] = new Answer(1, questionType, "A");
				break;
			default:
				break;
			}
		}
		return quizOfStudent;
	}

	@Override
	public boolean addStudent(String text) {
                 createTable();
                 createPassword();
                 insertStudent(encryptPassW(), text);
                 boolean success = true;
                 return success;
                
 
	}
        
         public void createTable(){
            try (Connection con = DriverManager.getConnection(studentUrl, studentUsername, 
                    studentPassword)) {
            Statement stmt = con.createStatement(); 
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "Students".toUpperCase(),null);
            if(!rs.next()){
            // create the table question
            stmt.execute("create table Students(andrewID varchar(40), "
                    + "password varchar(40))");
            }
        } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
        }
         
        public void createPassword(){
            
            Random rand = new Random();
            int num = rand.nextInt(8999) + 1000;    
            String letters = "";
            for (int i = 0; i < 3; i++) {
            int index = 97 + rand.nextInt(25);
            letters = letters + (char)index;
        } 
            passW = letters + num;
        }
        
        public String encryptPassW(){
            
            String pass = null;
            try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(passW.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            pass = bigInt.toString();
        
        }catch(Exception e) {
           
            System.out.println("Exception: " + e);
        
}       return pass;
        }

        public void insertStudent(String pass, String ID){
            
            
            try (Connection con = DriverManager.getConnection(studentUrl, studentUsername, studentPassword)) {
            Statement stmt = con.createStatement(); 
             String sql = "insert into Students values ('" + ID  +  "', '" + pass + "')";
             stmt.execute(sql);
             } catch (SQLException se) {
            System.out.println("Exception: " + se);
        }
         }

	@Override
	public int[] getLastNumberOfALLQuizzes() {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public int[] getlastAverageOfALLQuizzes() {
		// TODO Auto-generated method stub
		return new int[] { 20, 30, 40 };
	}

	@Override
	public int[] geteasyAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 40, 80, 90 };
	}

	@Override
	public int[] getMediumAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 20, 40, 70 };
	}

	@Override
	public int[] getHardAverageScoreOfThreeLastTime() {
		// TODO Auto-generated method stub
		return new int[] { 20, 20, 10 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastMouth() {
		// TODO Auto-generated method stub
		return new int[] { 10, 20, 60 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastQuater() {
		// TODO Auto-generated method stub
		return new int[] { 30, 50, 90 };
	}

	@Override
	public int[] getAllQuizAverageScoreOfEachDifficultyInLastYear() {
		// TODO Auto-generated method stub
		return new int[] { 40, 90, 100 };
	}

}
