package pdfutil;

import com.itextpdf.text.BadElementException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.Chart;
import static pdfutil.ChartManipulation.*;

import utilclass.Answer;
import utilclass.Question;

public class PDFGeneral {

    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 24,
            Font.BOLD);
    private static Font subtitle = new Font(Font.FontFamily.HELVETICA, 20,
            Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.HELVETICA, 14,
            Font.NORMAL);

    public static String getFormatString(String s, int length) {
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            if (i < s.length()) {
                chars[i] = s.charAt(i);
            } else {
                chars[i] = ' ';
            }
        }
        return String.valueOf(chars);
    }

    public static void addTitleLine(Document document, String title)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(title, titleFont));
        addEmptyLine(preface, 1);
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    public static void addSubtitleLine(Document document, String subTitle)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(subTitle, subtitle));
        addEmptyLine(preface, 1);
        document.add(preface);
    }

    public static void addContentLine(Document document, String content) throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.add(new Paragraph(content, normalFont));
        document.add(preface);
    }

    public static void addTableOfAnswer(Document document, Answer[] answers, ArrayList<Question> questions)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);

        PdfPCell c1 = new PdfPCell(new Phrase("No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Difficulties"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        c1 = new PdfPCell(new Phrase("Answer"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (int i = 0; i < answers.length; i++) {
            Answer answer = answers[i];
            table.addCell((i + 1) + "");
            table.addCell(Question.TYPENAME[answer.questionType]);
            table.addCell(Question.DIFFICULTY[questions.get(i).questionDifficult]);
            table.addCell(answer.toString());
        }
        document.add(table);
    }

    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void addChartGraph(Document document, Chart chart, File file) {
        saveChart(chart, file);
        Image image = null;

        try {
            image = Image.getInstance(file.getAbsolutePath());
        } catch (BadElementException ex) {
            Logger.getLogger(PDFGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            document.add(image);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }

        deleteSavedChart(file);
    }

    public static void addQuestionList(Document document, ArrayList<Question> questions) throws DocumentException {
        document.newPage();
        addTitleLine(document, "Appendix:Question List");
        for (int j = 0; j < questions.size(); j++) {
            Question question = questions.get(j);
            addSubtitleLine(document, "Question No." + (j + 1));
            addContentLine(document, question.toString() + "\n" + "Correct Answer : " + question.correctAnswer);
        }
    }

}
