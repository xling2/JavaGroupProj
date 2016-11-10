/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A5Q1;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Yixin1
 */
public class Exam {

    public static void main(String args[]) {

        // Get the data file
        Path examPath = Paths.get("Exam.csv");
        File exam = examPath.toFile();
        // nQuiz the number of quizs of three questions
        int nQuiz = 1;
        // Readin the data from the file
        new DBCreator(exam);

        System.out.println("Quiz Start\n");

        do {
            // Show the question
            new QuestionPresenter();
            nQuiz++;
        // Decide if there are still questions for a quiz
        } while ((nQuiz) * 3 < QuestionPresenter.nQuestion);

        System.out.println("Quiz ends.");
    }
}
