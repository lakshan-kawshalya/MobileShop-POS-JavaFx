/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class Main extends Application {

    public static int loggedUserId = 0;
    public static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/cyberx/views/Login.fxml"));

            Scene scene = new Scene(root);

            //adding style sheet
            String css = getClass().getResource("/com/cyberx/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);

            primaryStage.setTitle("Mobile Shop POS System - Login");
            primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/cyberx/resources/img/shop-logo.jpg")));
            
            primaryStage.setMaximized(false);
            primaryStage.resizableProperty().set(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
