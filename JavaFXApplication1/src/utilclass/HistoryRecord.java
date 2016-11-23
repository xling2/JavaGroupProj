package utilclass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryRecord {
	public int quizId;
	public Date finishDate;
	public HistoryRecord(int quizId, Date finishDate) {
		// TODO Auto-generated constructor stub
		this.quizId = quizId;
		this.finishDate = finishDate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return df.format(this.finishDate);
	}
}
