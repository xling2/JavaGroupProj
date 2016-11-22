/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Yixin1
 */
public class popUpPage {

    private static popUpPage pup = new popUpPage();

    public static Scene parentScene;
    public static Scene popUpScene;

    public popUpPage() {

    }

    public void open(String FXMLName) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(FXMLName));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentScene.getWindow());
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(GoPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeToOpen(String FXMLName) {
        Stage popUpWindow = (Stage) popUpScene.getWindow();

        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(FXMLName)));
            Stage stage;
            stage = (Stage) parentScene.getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GoPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        popUpWindow.close();
    }

    public static void setParentScene(Node node) {
        parentScene = node.getScene();
    }

    public static void setpopUpScene(Node node) {
        popUpScene = node.getScene();
    }
}
