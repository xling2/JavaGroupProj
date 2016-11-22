package utilclass;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableUse {
	private IntegerProperty number;
	private StringProperty questionType;
	private StringProperty difficulty;
	private StringProperty answer;
	
	public String getAnswer() {
		return answer.get();
	}
	public int getNumber() {
		return number.get();
	}
	public String getDifficulty() {
		return difficulty.get();
	}
	public String getQuestionType() {
		return questionType.get();
	}
	public String getAnswerProperty() {
		return answer.get();
	}
	public IntegerProperty getNumberProperty() {
		return number;
	}
	public StringProperty getDifficultyProperty() {
		return difficulty;
	}
	public StringProperty getQuestionTypeProperty() {
		return questionType;
	}
	public TableUse(int number, String questionType, String difficulty,
			String answer, String showBtn) {
		this.answer = new SimpleStringProperty(answer);
		this.number = new SimpleIntegerProperty(number);
		this.questionType = new SimpleStringProperty(questionType);
		this.difficulty = new SimpleStringProperty(difficulty);
	}
	public void setNumber(int number) {
		this.number.set(number);
	}
	public void setQuestionType(String questionType) {
		this.questionType.set(questionType);
	}
	public void setDifficulty(String difficulty) {
		this.difficulty.set(difficulty);;
	}
	public void setAnswer(String answer) {
		this.answer.set(answer);;
	}
	
}
