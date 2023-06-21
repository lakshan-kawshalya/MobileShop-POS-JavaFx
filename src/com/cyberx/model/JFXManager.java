/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import com.cyberx.controllers.EmployeeMgmtController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class JFXManager {

    public static void setDialogCloseFunctionality(Dialog dialog) {
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
    }

    //load ui
    public FXMLLoader LoadUi(Views ui, BorderPane contentPane) {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/cyberx/views/" + ui.getName() + ".fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        contentPane.setCenter(root);

        return fxmlLoader;
    }

    public FXMLLoader openDialog(Views dialogUi) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            //load fxml file and create new popup dialog
            fxmlLoader.setLocation(getClass().getResource("/com/cyberx/views/" + dialogUi.getName() + ".fxml"));
            DialogPane updateEmployeeDialog = fxmlLoader.load();

            Dialog dialog = new Dialog();
            dialog.setDialogPane(updateEmployeeDialog);
            JFXManager.setDialogCloseFunctionality(dialog);
            dialog.setResizable(true);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeMgmtController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fxmlLoader;
    }

    public FXMLLoader loadAsDialog(Views view) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            //load fxml file and create new popup dialog
            fxmlLoader.setLocation(getClass().getResource("/com/cyberx/views/" + view.getName() + ".fxml"));
            AnchorPane Dialog = fxmlLoader.load();

            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(Dialog);
            JFXManager.setDialogCloseFunctionality(dialog);
            dialog.setResizable(true);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeMgmtController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fxmlLoader;
    }

    public FXMLLoader loadAsDialog(Views view, String pane) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            //load fxml file and create new popup dialog
            fxmlLoader.setLocation(getClass().getResource("/com/cyberx/views/" + view.getName() + ".fxml"));
            
            ScrollPane Dialog = null;
            if (pane.equalsIgnoreCase("ScrollPane")) {

                Dialog = fxmlLoader.load();
            }

            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(Dialog);
            JFXManager.setDialogCloseFunctionality(dialog);
            dialog.setHeight(600);
            dialog.setResizable(true);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeMgmtController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fxmlLoader;
    }

    public static boolean confirmationDialog(Alert.AlertType alertType, String statement) {
        Alert alert = new Alert(alertType, statement);
        alert.getButtonTypes().addAll(ButtonType.YES);
        alert.getButtonTypes().addAll(ButtonType.NO);
        Optional<ButtonType> choose = alert.showAndWait();

        return choose.get() == ButtonType.YES;
    }

}
