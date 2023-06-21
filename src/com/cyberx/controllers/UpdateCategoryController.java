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
import com.cyberx.model.TableManager;
import com.cyberx.model.tables.CategoryTbl;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class UpdateCategoryController implements Initializable {
    
    TableView<CategoryTbl> listCategoryTable;
    
    private String categoryId;

    @FXML
    private TextField txtName;
    @FXML
    private Button btnReset;
    @FXML
    private Label lblId;
    @FXML
    private Button btnUpdate;
    
     private void initializingInputData(String catId) {
        ResultSet rs = DBManager.getTableData(DBTable.CATEGORY, catId);

        try {
            rs.next();

            lblId.setText(rs.getString("category_id"));
            txtName.setText(rs.getString("name"));

        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void initData(String catId, TableView<CategoryTbl> table) {
        this.categoryId = catId;
        this.listCategoryTable = table;
        initializingInputData(categoryId);
    }

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
    }


    @FXML
    private void btnResetActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        if (verifyInputs()) {
            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `category` WHERE `name` = '" + input.get("email") + "' AND `category_id` != '" + categoryId + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Category is already registered.");
                    alert.show();
                } else {
                    //insert data into the database

                    MySQL.iud("UPDATE `category` SET `name` = '" + input.get("name") + "' WHERE `category_id` = '" + categoryId + "'");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Category updated successfully.");
                    alert.show();
                    initializingInputData(categoryId);
                    new TableManager().loadCategoryTable(listCategoryTable, DBManager.getDBData("SELECT * FROM `category` INNER JOIN `sub_category` ON `sub_category`.`category_id` = `category`.`id`"));
                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
