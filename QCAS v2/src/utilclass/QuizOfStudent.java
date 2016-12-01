package utilclass;

import java.util.Date;

public class QuizOfStudent {

    public int quizDifficulty;
    public String quizId;
    public String studentName;
    public Question[] questionsOfQuiz;
    public Answer[] answerOfStudent;
    public Date startDate;
    public Date finishDate;
    public String duration;
    public int totalScore;
    public int[] scoreOfDifficulty = new int[3];
    private int deletedQuestions = 0;

    public QuizOfStudent(String student, Question[] questions, Answer[] answerOfStudent, Date startDate,
            Date finishDate) {
        // TODO Auto-generated constructor stub
        this.studentName = student;
        this.questionsOfQuiz = questions;
        this.startDate = startDate;
        this.answerOfStudent = answerOfStudent;
        this.finishDate = finishDate;
        getQuizScore();
        getDuration();
    }

    public void finishQuiz() {
        this.finishDate = new Date();
        getDuration();
        getQuizScore();
    }

    private void getQuizScore() {
        int[] totalNumber = new int[]{0, 0, 0};
        int[] correctNumber = new int[]{0, 0, 0};
        for (int i = 0; i < answerOfStudent.length; i++) {
            Answer answer = answerOfStudent[i];
            Question question = questionsOfQuiz[i];
            totalNumber[question.questionDifficult]++;
            if (question.correctAnswer.equals(answer.toString())) {
				correctNumber[question.questionDifficult]++;
			}
		}
        this.totalScore = (int) ((float) total(correctNumber) / (float) total(totalNumber) * 100.0);
        for (int i = 0; i < 3; i++) {
            double x = (float) correctNumber[i] / (float) (totalNumber[i] == 0 ? 1 : totalNumber[i]) * 100.0;
            this.scoreOfDifficulty[i] = (int) x;
        }
    }

    private void getDuration() {
        long ms = finishDate.getTime() - startDate.getTime();
        int hour = (int) ms / 1000 / 60 / 60;
        ms -= 60 * 60 * 1000 * hour;
        int mm = (int) ms / 1000 / 60;
        ms -= 60 * 1000 * mm;
        int ss = (int) (ms) / 1000;
        this.duration = String.format("%02d:%02d:%02d", hour, mm, ss);
    }

    public static int total(int[] x) {
        int j = 0;
        for (int i = 0; i < x.length; i++) {
            j += x[i];
        }
        return j;
    }
    
    public void setDeletedQuestion(int delete){
        this.deletedQuestions = delete;
    }
    
    public int getDeletedQuestion(){
        return this.deletedQuestions;
    }
            
    
}
