/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.InputManager;
import com.cyberx.model.MySQL;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class AddSubCategoryController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private Button btnCategory;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnReset;
    
    private void initializeComboBoxes() {
        InputManager.loadComboBox(cmbCategory, DBTable.CATEGORY);
    }
    
    private void resetFields() {
        txtName.setText("");
        txtName.getStyleClass().remove("invalid-input");

        cmbCategory.getSelectionModel().select(0);
        cmbCategory.getStyleClass().remove("invalid-input");
    }
    
    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtName);
        InputManager.isValidComboBox(cmbCategory);

        if (!(InputManager.isValidTextField(txtName) && InputManager.isValidComboBox(cmbCategory))) {
            isValid = false;
        }

        return isValid;
    }
    
    private HashMap<String, String> getInputValues() {
        String name = txtName.getText();
        String category = cmbCategory.getSelectionModel().getSelectedItem();

        HashMap inputs = new HashMap();
        inputs.put("name", name);
        inputs.put("category", category);

        return inputs;
    }
    
    private void initializingItemStateChangeListeners() {
        cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbCategory);
            }

        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBoxes();
        initializingItemStateChangeListeners();
    }    

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
    }

    @FXML
    private void btnCategoryActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnAddActonPerformed(ActionEvent event) {
        if (verifyInputs()) {

            try {
                HashMap input = getInputValues();
                //check sub-category in database
                ResultSet rs = MySQL.search("SELECT * FROM `sub_category` WHERE `name` = '" + input.get("name") + "' AND `category_id` = " + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY));

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Sub-Category is already registered.");
                    alert.show();
                } else {
                    //insert data into the database
                    
                    MySQL.iud("INSERT INTO `sub_category`(`name`, `category_id`) VALUES ('" + input.get("name") + "', " + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY) + ")");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sub-Category registered successfully.");
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
