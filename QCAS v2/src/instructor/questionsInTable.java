/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instructor;

import javafx.beans.property.*;
import utilclass.Question;

/**
 *
 * @author Yixin1
 */
public class questionsInTable {

    private final SimpleStringProperty correctAnswer;
    private final SimpleStringProperty content;
    private final SimpleStringProperty type;
    private final SimpleStringProperty difficulty;

    private questionsInTable(Question question) {

        correctAnswer = new SimpleStringProperty(question.correctAnswer);
        content = new SimpleStringProperty(question.content);
        type = new SimpleStringProperty(Question.TYPENAME[question.questionType]);
        difficulty = new SimpleStringProperty(Question.DIFFICULTY[question.questionDifficult]);

    }

}
