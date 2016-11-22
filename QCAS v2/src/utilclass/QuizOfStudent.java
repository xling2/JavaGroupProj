package utilclass;

import java.util.Date;
import interfacewithserve.CommunicateWithServe;

public class QuizOfStudent {
	public User student;
	public Question[] questionsOfQuiz;
	public Answer[] answerOfStudent;
	public Date startDate;
	public Date finishDate;
	public String duration;
	public int totalScore, diff, num;
	public int[] scoreOfDifficulty = new int[3];
        

	public QuizOfStudent(User student,  Answer[] answerOfStudent, Date startDate,
			Date finishDate, int difficulty, int number) {
		// TODO Auto-generated constructor stub
		this.student = student;
		this.startDate = startDate;
		this.answerOfStudent = answerOfStudent;
		this.finishDate = finishDate;
                this.diff = difficulty;
                this.num = number;
	}

	public void getQuestionFromServe(CommunicateWithServe communicateWithServe) {
		this.questionsOfQuiz = communicateWithServe.getRandomQuestionListOfQuiz(diff, num);
		getQuizScore();
		getDuration();
	}

	public void finishQuiz() {
		this.finishDate = new Date();
		getDuration();
		getQuizScore();
	}

	private void getQuizScore() {
		int[] totalNumber = new int[] { 0, 0, 0 };
		int[] correctNumber = new int[] { 0, 0, 0 };
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
}
