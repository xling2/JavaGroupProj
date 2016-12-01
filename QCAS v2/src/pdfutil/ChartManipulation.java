/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfutil;

import java.io.*;
import java.nio.file.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Yixin1
 */
public abstract class ChartManipulation {

    public static void saveChart(Chart chart, File file) {
        WritableImage chartImage = chart.snapshot(new SnapshotParameters(), null);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(chartImage, null), "png", file);
        } catch (IOException e) {
            System.out.println("IOException @pdfutil/ChartManipulation: " + e);
            e.printStackTrace();
        }
    }

    public static void deleteSavedChart(File file) {
        String stringPath = file.getAbsolutePath();
        Path path = Paths.get(stringPath);
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
}
