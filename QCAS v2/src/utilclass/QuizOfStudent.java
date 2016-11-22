package utilclass;

import java.util.Date;
import interfacewithserve.CommunicateWithServe;

public class QuizOfStudent {
	public User student;
	public int[] questionsIdOfQuiz;
	public Question[] questionsOfQuiz;
	public Answer[] answerOfStudent;
        public int[] diff;
	public Date startDate;
	public Date finishDate;
	public String duration;
	public int totalScore;
	public int[] scoreOfDifficulty = new int[3];
        

	public QuizOfStudent(User student, int[] questionsIdOfQuiz, int[] difficulty, Answer[] answerOfStudent, Date startDate,
			Date finishDate) {
		// TODO Auto-generated constructor stub
		this.student = student;
		this.questionsIdOfQuiz = questionsIdOfQuiz;
		this.diff = difficulty;
                this.startDate = startDate;
		this.answerOfStudent = answerOfStudent;
		this.finishDate = finishDate;
	}

	public void getQuestionFromServe(CommunicateWithServe communicateWithServe) {
		this.questionsOfQuiz = communicateWithServe.getRandomQuestionListOfQuiz(diff, this.questionsIdOfQuiz.length);
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
