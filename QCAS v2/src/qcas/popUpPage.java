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

    private static Scene parentScene;
    private static Scene popUpScene;

    public popUpPage() {

    }

    // simply open a new window from the parent scene
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

    // close the pop up window
    public void close() {
        Stage popUpWindow = (Stage) popUpScene.getWindow();
        popUpWindow.close();
    }

    // close the pop up window and open a new scene in mother window
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

    // set the parent scene
    public static void setParentScene(Node node) {
        parentScene = node.getScene();
    }

    // set the pop up scene
    public static void setpopUpScene(Node node) {
        popUpScene = node.getScene();
    }
}
