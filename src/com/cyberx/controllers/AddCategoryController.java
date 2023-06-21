/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.IDManager;
import com.cyberx.model.InputManager;
import com.cyberx.model.MySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class AddCategoryController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnReset;

    private void resetFields() {
        txtName.setText("");
        txtName.getStyleClass().remove("invalid-input");
    }

    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtName);

        if (!(InputManager.isValidTextField(txtName))) {
            isValid = false;
        }

        return isValid;
    }
    
    private HashMap<String, String> getInputValues() {
        String name = txtName.getText();

        HashMap inputs = new HashMap();
        inputs.put("name", name);

        return inputs;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
    }

    @FXML
    private void btnAddActonPerformed(ActionEvent event) {
        if (verifyInputs()) {

            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `category` WHERE `name` = '" + input.get("name") + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Category is already registered.");
                    alert.show();
                } else {
                    //insert data into the database
                    String CategoryId = IDManager.generateID(DBTable.CATEGORY);
                    
                    MySQL.iud("INSERT INTO `category`(`category_id`, `name`) VALUES ('" + CategoryId + "', '" + input.get("name") + "')");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Category registered successfully.");
                    alert.show();
                    resetFields();
                }
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void btnResetActionPerformed(ActionEvent event) {
        resetFields();
    }

}
