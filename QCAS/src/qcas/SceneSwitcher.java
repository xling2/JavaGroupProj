/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Yixin1
 */
public abstract class SceneSwitcher {

    public static void goToScene(Parent root, Scene currentScene){
        Stage stage;
        Scene scene = new Scene(root);
        stage = (Stage) currentScene.getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void goToScene(String view_historyfxml, Button back, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
