/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.FileManager;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class AddCompanyController implements Initializable {
    FileChooser fileChooser = new FileChooser();
    File imageFile;

    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbCity;
    @FXML
    private Button btnCity;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContactNo1;
    @FXML
    private TextField txtContactNo2;
    @FXML
    private TextField txtAddressLine1;
    @FXML
    private TextField txtAddressLine2;
    @FXML
    private Button fchImage;
    @FXML
    private Circle imageCircle;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnReset;
    
    private void initializeComboBoxes() {
        InputManager.loadProvince(cmbProvince);
    }
    
    private void resetFields() {
        txtName.setText("");
        txtName.getStyleClass().remove("invalid-input");

        txtEmail.setText("");
        txtEmail.getStyleClass().remove("invalid-input");

        txtContactNo1.setText("");
        txtContactNo1.getStyleClass().remove("invalid-input");

        txtContactNo2.setText("");
        txtContactNo2.getStyleClass().remove("invalid-input");

        txtAddressLine1.setText("");
        txtAddressLine1.getStyleClass().remove("invalid-input");

        txtAddressLine2.setText("");
        txtAddressLine2.getStyleClass().remove("invalid-input");

        cmbProvince.getSelectionModel().select(0);
        cmbProvince.getStyleClass().remove("invalid-input");

        cmbDistrict.getSelectionModel().select(0);
        cmbDistrict.getStyleClass().remove("invalid-input");

        cmbCity.getSelectionModel().select(0);
        cmbCity.getStyleClass().remove("invalid-input");

        initializeDefaultImage();
    }
    
    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtName);
        InputManager.isValidEmail(txtEmail);
        InputManager.isValidTextField(txtAddressLine1);
        InputManager.isValidContactNo(txtContactNo1);
        InputManager.isValidComboBox(cmbProvince);
        InputManager.isValidComboBox(cmbDistrict);
        InputManager.isValidComboBox(cmbCity);

        if (!txtContactNo2.getText().isBlank()) {
            if (!InputManager.isValidContactNo(txtContactNo2)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidTextField(txtName) && InputManager.isValidEmail(txtEmail) && InputManager.isValidTextField(txtAddressLine1) && InputManager.isValidContactNo(txtContactNo1) && InputManager.isValidComboBox(cmbProvince) && InputManager.isValidComboBox(cmbDistrict) && InputManager.isValidComboBox(cmbCity))) {
            isValid = false;
        }

        return isValid;
    }
    
    private HashMap<String, String> getInputValues() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contactNo1 = txtContactNo1.getText();
        String contactNo2 = txtContactNo2.getText();
        String addressLine1 = txtAddressLine1.getText();
        String addressLine2 = txtAddressLine2.getText();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();
        String city = cmbCity.getSelectionModel().getSelectedItem();

        HashMap inputs = new HashMap();
        inputs.put("name", name);
        inputs.put("email", email);
        inputs.put("contactNo1", contactNo1);
        inputs.put("contactNo2", contactNo2);
        inputs.put("addressLine1", addressLine1);
        inputs.put("addressLine2", addressLine2);
        inputs.put("province", province);
        inputs.put("district", district);
        inputs.put("city", city);

        return inputs;
    }
    
    private void initializingItemStateChangeListeners() {
        cmbProvince.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.loadDistrict(cmbDistrict, cmbProvince);
                InputManager.isValidComboBox(cmbProvince);
            }

        });

        cmbDistrict.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.loadCity(cmbCity, cmbDistrict, btnCity);
                InputManager.isValidComboBox(cmbDistrict);
            }

        });

        cmbCity.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbCity);
            }

        });
    }
    
    private void initializeDefaultImage() {
        //setting image
        Image img = new Image(this.getClass().getResourceAsStream("/com/cyberx/resources/img/default-logo-image.png"));
        imageCircle.setFill(new ImagePattern(img));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBoxes();
        initializingItemStateChangeListeners();
        initializeDefaultImage();
    }    

    @FXML
    private void btnCityActionPerformed(ActionEvent event) {
        FXMLLoader cityFxmlLoader = new JFXManager().openDialog(Views.CITY_DIALOG);
        CityDialogController cityDialogController = cityFxmlLoader.getController();
        
        cityDialogController.init(cmbCity, cmbProvince, cmbDistrict);        
    }

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
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
    private void txtAddressLine1KeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtAddressLine1);
    }

    @FXML
    private void fchImageActionPerformed(ActionEvent event) {
        imageFile = new FileManager().getImage(fileChooser, imageCircle);
    }

    @FXML
    private void btnAddActonPerformed(ActionEvent event) {
        if (verifyInputs()) {

            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `company` WHERE `email` = '" + input.get("email") + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Email address is already registered.\nYou can't register company with already registered Email address.");
                    alert.show();
                } else {
                    //insert data into the database
                    String CompanyId = IDManager.generateID(DBTable.COMPANY);
                    String fileName = FileManager.DEFAULT_COMPANY_LOGO;

                    if (imageFile != null) {
                        //copy file to directory
                        fileName = FileManager.copyImage(imageFile, CompanyId, FileManager.COMPANY_LOGO_PATH);
                    }
                    MySQL.iud("INSERT INTO `company` (`company_id`, `name`, `email`, `contact_no`, `contact_no_2`, `address_line_1`, `address_line_2`, `city_id`, `logo_image`) VALUES ('" + CompanyId + "', '" + input.get("name") + "', '" + input.get("email") + "', '" + input.get("contactNo1") + "', '" + input.get("contactNo2") + "', '" + input.get("addressLine1") + "', '" + input.get("addressLine2") + "', " + DBManager.getId(input.get("city").toString(), DBTable.CITY) + ", '" + fileName + "')");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Company registered successfully.");
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
