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
import com.cyberx.model.JFXManager;
import com.cyberx.model.MySQL;
import com.cyberx.model.Views;
import java.io.File;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class AddSupplierController implements Initializable {

    FileChooser fileChooser = new FileChooser();
    File imageFile;

    @FXML
    private ComboBox<String> cmbCompany;
    @FXML
    private Button btnCompany;
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
    private Button btnAdd;
    @FXML
    private Button btnReset;

    private void initializeComboBoxes() {
        InputManager.loadComboBox(cmbCompany, DBTable.COMPANY);
    }

    private void resetFields() {
        txtFName.setText("");
        txtFName.getStyleClass().remove("invalid-input");

        txtLName.setText("");
        txtLName.getStyleClass().remove("invalid-input");

        txtEmail.setText("");
        txtEmail.getStyleClass().remove("invalid-input");

        txtContactNo1.setText("");
        txtContactNo1.getStyleClass().remove("invalid-input");

        txtContactNo2.setText("");
        txtContactNo2.getStyleClass().remove("invalid-input");

        cmbCompany.getSelectionModel().select(0);
        cmbCompany.getStyleClass().remove("invalid-input");
    }

    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtFName);
        InputManager.isValidTextField(txtLName);
        InputManager.isValidEmail(txtEmail);
        InputManager.isValidContactNo(txtContactNo1);
        InputManager.isValidComboBox(cmbCompany);

        if (!txtContactNo2.getText().isBlank()) {
            if (!InputManager.isValidContactNo(txtContactNo2)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidTextField(txtFName) && InputManager.isValidTextField(txtLName) && InputManager.isValidEmail(txtEmail) && InputManager.isValidContactNo(txtContactNo1) && InputManager.isValidComboBox(cmbCompany))) {
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
        String company = cmbCompany.getSelectionModel().getSelectedItem();

        HashMap inputs = new HashMap();
        inputs.put("fname", fname);
        inputs.put("lname", lname);
        inputs.put("email", email);
        inputs.put("contactNo1", contactNo1);
        inputs.put("contactNo2", contactNo2);
        inputs.put("company", company);

        return inputs;
    }
    
    private void initializingItemStateChangeListeners() {
        cmbCompany.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbCompany);
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
    private void btnCompanyActionPerformed(ActionEvent event) {
        FXMLLoader searchCompanyFxmlLoader = new JFXManager().openDialog(Views.SEARCH_COMPANY_DIALOG);
        SearchCompanyDialogController searchCompanyDialogController = searchCompanyFxmlLoader.getController();
        
        searchCompanyDialogController.init(cmbCompany);
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
    private void btnAddActonPerformed(ActionEvent event) {
        if (verifyInputs()) {

            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `supplier` WHERE `email` = '" + input.get("email") + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Email address is already registered.\nYou can't register supplier with already registered Email address.");
                    alert.show();
                } else {
                    //insert data into the database
                    String SupplierId = IDManager.generateID(DBTable.SUPPLIER);

                    MySQL.iud("INSERT INTO `supplier`(`supplier_id`, `fname`, `lname`, `email`, `contact_no`, `contact_no_2`, `company_id`) VALUES ('" + SupplierId + "', '" + input.get("fname") + "', '" + input.get("lname") + "', '" + input.get("email") + "', '" + input.get("contactNo1") + "', '" + input.get("contactNo2") + "', " + DBManager.getId(input.get("company").toString(), DBTable.COMPANY) + ")");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Supplier registered successfully.");
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
