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
import com.cyberx.model.tables.CustomerTbl;
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
public class UpdateCustomerDialogController implements Initializable {

    TableView<CustomerTbl> listCustomerTable;

    private String customerId;

    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContactNo1;
    @FXML
    private TextField txtContactNo2;
    @FXML
    private Label lblId;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnReset;

    private void initializingInputData(String cusId) {
        ResultSet rs = DBManager.getTableData(DBTable.CUSTOMER, cusId);

        try {
            rs.next();

            lblId.setText(rs.getString("customer_id"));
            txtFName.setText(rs.getString("fname"));
            txtLName.setText(rs.getString("lname"));
            txtEmail.setText(rs.getString("email"));
            txtContactNo1.setText(rs.getString("contact_no"));
            txtContactNo2.setText(rs.getString("contact_no_2"));

        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtFName);
        InputManager.isValidTextField(txtLName);
        InputManager.isValidEmail(txtEmail);
        InputManager.isValidContactNo(txtContactNo1);

        if (!txtContactNo2.getText().isBlank()) {
            if (!InputManager.isValidContactNo(txtContactNo2)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidTextField(txtFName) && InputManager.isValidTextField(txtLName) && InputManager.isValidEmail(txtEmail) && InputManager.isValidContactNo(txtContactNo1))) {
            isValid = false;
        }

        return isValid;
    }
    
    private HashMap<String, String> getInputValues() {
        String fname = txtFName.getText();
        String lname = txtLName.getText();
        String email = txtEmail.getText();
        String contactNo1 = txtContactNo1.getText();
        String contactNo2 = txtContactNo2.getText();

        HashMap inputs = new HashMap();
        inputs.put("fname", fname);
        inputs.put("lname", lname);
        inputs.put("email", email);
        inputs.put("contactNo1", contactNo1);
        inputs.put("contactNo2", contactNo2);

        return inputs;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initData(String cusId, TableView<CustomerTbl> table) {
        this.customerId = cusId;
        this.listCustomerTable = table;
        initializingInputData(customerId);
    }
    @FXML
    private void txtFNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtFName);
    }

    @FXML
    private void txtLNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtLName);
    }

    @FXML
    private void txtEmailKeyReleased(KeyEvent event) {
        InputManager.isValidEmail(txtEmail);
    }

    @FXML
    private void txtContactNo1KeyReleased(KeyEvent event) {
        InputManager.isValidContactNo(txtContactNo1);
    }

    @FXML
    private void txtContactNo2KeyReleased(KeyEvent event) {
        if (!txtContactNo2.getText().isBlank()) {
            InputManager.isValidContactNo(txtContactNo2);
        }
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        if (verifyInputs()) {
            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `customer` WHERE `email`  = '" + input.get("email") + "' AND `customer_id` != '" + customerId + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Email address is already registered.\nYou can't register customer with already registered Email address.");
                    alert.show();
                } else {
                    //insert data into the database

                    MySQL.iud("UPDATE `customer` SET `fname` = '" + input.get("fname") + "', `lname` = '" + input.get("lname") + "', `email` = '" + input.get("email") + "', `contact_no` =  '" + input.get("contactNo1") + "', `contact_no_2` =  '" + input.get("contactNo2") + "' WHERE `customer_id` = '" + customerId + "'");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Customer information updated successfully.");
                    alert.show();
                    initializingInputData(customerId);
                    new TableManager().loadCustomerTable(listCustomerTable, DBManager.getTableData(DBTable.CUSTOMER));
                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnResetActionPerformed(ActionEvent event) {
        initializingInputData(customerId);
    }

}
