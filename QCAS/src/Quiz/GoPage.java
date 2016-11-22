/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mica
 */
public class GoPage {

    private static GoPage gopage = new GoPage();
    //some global data
    public int id = 0;
    public ArrayList<String> historyItems = new ArrayList<String>() {
        {
            add("8:00am");
            add("9:00pm");
            add("...");
        }
    };
    public int historySelectIndex = -1;
    public int questionCounts = 4;//question counts of quiz_report 
    public int quizDifficult = -1;
    public int currentNumber = 1;
    public ArrayList<Question> quiz = new ArrayList<>();

    private void GoPage() {
    }

    public static GoPage getGoPage() {
        return gopage;
    }

    public void goPage(String FXMLName, Node node, int... args) {
        Scene scene;
        int x = 600;
        int y = 400;
        if (args.length >= 2) {
            x = args[0];
            y = args[1];
        }
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource(FXMLName)));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GoPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getQuiz() {
        Random ra = new Random();
        quiz.clear();
        for (int i = 0; i < this.questionCounts; i++) {
            quiz.add(new Question(ra.nextInt(4), i + 1, "this is a question", new String[]{"choices1", "choices2", "choices3", "choices4"}));
        }
    }
}
