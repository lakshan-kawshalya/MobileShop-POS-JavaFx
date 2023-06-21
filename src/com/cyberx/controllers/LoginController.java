/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.main.Main;
import com.cyberx.model.DBManager;
import com.cyberx.model.InputManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class LoginController implements Initializable {
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnLogin;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblAlert;

    private boolean verifyInputs() {
        boolean isValid = true;
        InputManager.isValidEmail(txtEmail);
        InputManager.isValidTextField(txtPassword);

        if (!(InputManager.isValidEmail(txtEmail) && InputManager.isValidPassword(txtPassword))) {
            isValid = false;
        }

        return isValid;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void txtEmailKeyReleased(KeyEvent event) {
        InputManager.isValidEmail(txtEmail);
    }

    @FXML
    private void btnLoginActionPerformed(ActionEvent event) {
        if (verifyInputs()) {
            try {
                ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `email` = '" + txtEmail.getText() + "' AND `password` = '" + txtPassword.getText() + "' AND `status_id` = 1");
                if (employeeRs.next()) {
                    
                    Main.loggedUserId = employeeRs.getInt("id");

                    final Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close();

                    Stage primaryStage = new Stage();

                    Parent root = FXMLLoader.load(getClass().getResource("/com/cyberx/views/MainWindow.fxml"));

                    Scene scene = new Scene(root);

                    //adding style sheet
                    String css = getClass().getResource("/com/cyberx/css/style.css").toExternalForm();
                    scene.getStylesheets().add(css);

                    primaryStage.setTitle("Mobile Shop POS System");
                    primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/cyberx/resources/img/shop-logo.jpg")));

                    primaryStage.setMaximized(true);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } else {
                    System.out.println("alert");
                    lblAlert.setVisible(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
