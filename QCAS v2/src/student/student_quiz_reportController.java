/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import pdfutil.PDFGeneral;
import qcas.GoPage;
import utilclass.Answer;
import utilclass.Question;
import utilclass.TableUse;

/**
 *
 * @author mica
 */
public class student_quiz_reportController implements Initializable {

    @FXML
    private Label tips;
    @FXML
    private Label name;
    @FXML
    private Label time;
    @FXML
    private Label duration;
    @FXML
    private TableView<TableUse> table;
    @FXML
    private TableColumn<TableUse, Integer> number;
    @FXML
    private TableColumn<TableUse, String> questionType;
    @FXML
    private TableColumn<TableUse, String> difficulty;
    @FXML
    private TableColumn<TableUse, String> answer;
    @FXML
    private Label questionLabel;
    @FXML
    private Button hidebtn;
    @FXML
    private Label successedTips;
    @FXML
    private BarChart<Number, String> difficultyChart;

    private ObservableList<TableUse> scoreData = FXCollections.observableArrayList();

    private GoPage goPage;

    public ObservableList<TableUse> getTableData() {
        return scoreData;
    }

    @FXML
    private void hideAction(ActionEvent event) {
        hidebtn.setVisible(false);
        questionLabel.setVisible(false);
    }

    @FXML
    private void backAction(ActionEvent event) {
        goPage.goPage(goPage.back, name, (int) goPage.backX, (int) goPage.backY);
    }

    @FXML
    private void export(ActionEvent event) {
        File folder = new File("./Ouptput");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Document document = new Document();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String documentName = goPage.studentName + df.format(goPage.quizOfCurrentCheck.finishDate)
                + "_quiz_report" + ".PDF";
        File exportPDF = new File(folder + "/" + documentName);
        if (exportPDF.exists()) {
            successedTips.setVisible(false);
            tips.setText("Export failed, report already exist.");
            tips.setVisible(true);
        } else {
            try {
                PdfWriter.getInstance(document, new FileOutputStream(folder + "/" + documentName));
                document.open();
                PDFGeneral.addTitleLine(document, "Quiz Report");
                df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                PDFGeneral.addContentLine(document,
                        String.format("%s%s", PDFGeneral.getFormatString("studentName:", 30), goPage.studentName));
                PDFGeneral.addContentLine(document,
                        String.format("%s%s", PDFGeneral.getFormatString("StudentName:", 26), goPage.studentName));
                PDFGeneral.addContentLine(document, String.format("%s%s",
                        PDFGeneral.getFormatString("Finish Time:", 30),
                        df.format(goPage.quizOfCurrentCheck.finishDate)));
                PDFGeneral.addContentLine(document,
                        String.format("%s%s", PDFGeneral.getFormatString("Duration:", 32),
                                goPage.quizOfCurrentCheck.duration));
                PDFGeneral.addSubtitleLine(document, "Score:" + goPage.quizOfCurrentCheck.totalScore);
                PDFGeneral.addTableOfAnswer(document, goPage.quizOfCurrentCheck.answerOfStudent,
                        new ArrayList<Question>(Arrays.asList(goPage.quizOfCurrentCheck.questionsOfQuiz)));
                PDFGeneral.addQuestionList(document,
                        new ArrayList<Question>(Arrays.asList(goPage.quizOfCurrentCheck.questionsOfQuiz)));
                document.close();
                tips.setVisible(false);
                successedTips.setText("Export successed, file in " + folder.getAbsolutePath() + "/" + documentName);
                successedTips.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                successedTips.setVisible(false);
                tips.setText("Export failed, Please check your config is right.");
                tips.setVisible(true);
            }
        }
    }

    // initialChart ans score
    private void initialChart() {
        Series<Number, String> series = new XYChart.Series<Number, String>();
        int[] totalNumber = new int[]{0, 0, 0};
        int[] correctNumber = new int[]{0, 0, 0};
        for (int i = 0; i < goPage.quizOfCurrentCheck.answerOfStudent.length; i++) {
            Answer answer = goPage.quizOfCurrentCheck.answerOfStudent[i];
            Question question = goPage.quizOfCurrentCheck.questionsOfQuiz[i];
            totalNumber[question.questionDifficult]++;
            if (question.correctAnswer.equals(answer.toString())) {
                correctNumber[question.questionDifficult]++;
            }
        }
        score.setText((int) ((float) total(correctNumber) / (float) total(totalNumber) * 100.0) + "");
        for (int i = 0; i < 3; i++) {
            double x = (float) correctNumber[i] / (float) (totalNumber[i] == 0 ? 1 : totalNumber[i]) * 100.0;
            series.getData().add(new Data<Number, String>(x, Question.DIFFICULTY[i]));
        }

        difficultyChart.getData().add(series);
    }

    private int total(int[] x) {
        int j = 0;
        for (int i = 0; i < x.length; i++) {
            j += x[i];

        }
        return j;
    }

    @FXML
    private Label score;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        goPage = GoPage.getGoPage();
        tips.setVisible(false);
        successedTips.setVisible(false);
        name.setText(goPage.quizOfCurrentCheck.studentName);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        time.setText(df.format(goPage.quizOfCurrentCheck.finishDate));
        duration.setText(goPage.quizOfCurrentCheck.duration);
        // add score data
        for (int i = 0; i < goPage.quizOfCurrentCheck.answerOfStudent.length; i++) {
            scoreData.add(
                    new TableUse(i + 1, Question.TYPENAME[goPage.quizOfCurrentCheck.answerOfStudent[i].questionType],
                            Question.DIFFICULTY[goPage.quizOfCurrentCheck.questionsOfQuiz[i].questionDifficult],
                            goPage.quizOfCurrentCheck.answerOfStudent[i].toString(), "test"));
        }
        // double click setting
        table.setRowFactory(tv -> {
            TableRow<TableUse> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    hidebtn.setVisible(true);
                    questionLabel.setVisible(true);
                    TableUse rowData = row.getItem();
                    Question currentQuestion = goPage.quizOfCurrentCheck.questionsOfQuiz[rowData.getNumber() - 1];
                    questionLabel.setText(currentQuestion.toString() + "\nright answer : " + currentQuestion.correctAnswer);
                }
            });
            return row;
        });
        // initial table data
        questionType.setCellValueFactory(new PropertyValueFactory<TableUse, String>("questionType"));
        number.setCellValueFactory(new PropertyValueFactory<TableUse, Integer>("number"));
        difficulty.setCellValueFactory(new PropertyValueFactory<TableUse, String>("difficulty"));
        answer.setCellValueFactory(new PropertyValueFactory<TableUse, String>("answer"));
        // table set data
        table.setItems(getTableData());
        // table red setting
        answer.setCellFactory(column -> {
            return new TableCell<TableUse, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); // This is mandatory

                    if (item == null || empty) { // If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { // If the cell is not empty
                        TableUse currentTableUse = getTableView().getItems().get(getIndex());
                        setText(currentTableUse.getAnswer());
                        // Style all answer is correct
                        if (!currentTableUse.getAnswer()
                                .equals(goPage.quizOfCurrentCheck.questionsOfQuiz[getIndex()].correctAnswer)) {
                            setTextFill(Color.RED); // The text in red
                        }
                    }
                }
            };
        });
        //
        hidebtn.setVisible(false);
        questionLabel.setVisible(false);
        initialChart();

    }
}
