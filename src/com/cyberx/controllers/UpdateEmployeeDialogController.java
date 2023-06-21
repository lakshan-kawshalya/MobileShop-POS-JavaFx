/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.FileManager;
import com.cyberx.model.InputManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.MySQL;
import com.cyberx.model.TableManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.EmployeeTbl;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class UpdateEmployeeDialogController implements Initializable {

    TableView<EmployeeTbl> listEmployeeTable;

    private FileChooser fileChooser = new FileChooser();
    private File imageFile;

    private String employeeId;
    private String currentImageFileName;

    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbCity;
    @FXML
    private ComboBox<String> cmbMartialStatus;
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
    private TextField txtAddressLine1;
    @FXML
    private TextField txtAddressLine2;
    @FXML
    private RadioButton radMale;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton radFemale;
    @FXML
    private Button fchImage;
    @FXML
    private Label lblId;
    @FXML
    private ComboBox<String> cmbUserRole;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private Button btnReset;
    @FXML
    private Circle imageCircle;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCity;

    private void initializeComboBoxes() {
        InputManager.loadProvince(cmbProvince);
        InputManager.loadComboBox(cmbMartialStatus, DBTable.MARTIAL_STATUS);
        InputManager.loadComboBox(cmbUserRole, DBTable.USER_ROLE);
        InputManager.loadComboBox(cmbStatus, DBTable.STATUS);
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

        cmbMartialStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbMartialStatus);
            }

        });

        cmbUserRole.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbUserRole);
            }

        });

        cmbStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbStatus);
            }

        });
    }

    private void initializingInputData(String empId) {
        ResultSet rs = DBManager.getTableData(DBTable.EMPLOYEE, empId);

        try {
            rs.next();

            lblId.setText(rs.getString("employee_id"));
            txtFName.setText(rs.getString("fname"));
            txtLName.setText(rs.getString("lname"));
            txtEmail.setText(rs.getString("email"));
            txtContactNo1.setText(rs.getString("contact_no"));
            txtContactNo2.setText(rs.getString("contact_no_2"));
            txtAddressLine1.setText(rs.getString("address_line_1"));
            txtAddressLine2.setText(rs.getString("address_line_2"));

            cmbProvince.getSelectionModel().select(DBManager.getAddress(rs.getInt("city_id")).get("province") + " Province");
            InputManager.loadDistrict(cmbDistrict, cmbProvince);
            cmbDistrict.getSelectionModel().select(DBManager.getAddress(rs.getInt("city_id")).get("district"));
            InputManager.loadCity(cmbCity, cmbDistrict, btnCity);
            cmbCity.getSelectionModel().select(DBManager.getAddress(rs.getInt("city_id")).get("city"));

            if (DBManager.getName(rs.getInt("gender_id"), DBTable.GENDER).equalsIgnoreCase("male")) {
                radMale.setSelected(true);
            } else if (DBManager.getName(rs.getInt("gender_id"), DBTable.GENDER).equalsIgnoreCase("female")) {
                radFemale.setSelected(true);
            }

            cmbMartialStatus.getSelectionModel().select(DBManager.getName(rs.getInt("martial_status_id"), DBTable.MARTIAL_STATUS));
            cmbUserRole.getSelectionModel().select(DBManager.getName(rs.getInt("user_role_id"), DBTable.USER_ROLE));
            cmbStatus.getSelectionModel().select(DBManager.getName(rs.getInt("status_id"), DBTable.STATUS));

            txtPassword.setText(rs.getString("password"));

            currentImageFileName = rs.getString("profile_image");
            new FileManager().setImage(imageCircle, currentImageFileName, FileManager.USER_IMAGE_PATH);

        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean verifyInputs() {
        boolean isValid = true;

        InputManager.isValidTextField(txtFName);
        InputManager.isValidTextField(txtLName);
        InputManager.isValidEmail(txtEmail);
        InputManager.isValidTextField(txtAddressLine1);
        InputManager.isValidContactNo(txtContactNo1);
        InputManager.isValidTextField(txtPassword);
        InputManager.isValidComboBox(cmbProvince);
        InputManager.isValidComboBox(cmbDistrict);
        InputManager.isValidComboBox(cmbCity);
        InputManager.isValidComboBox(cmbMartialStatus);
        InputManager.isValidComboBox(cmbUserRole);
        InputManager.isValidComboBox(cmbStatus);

        if (!txtContactNo2.getText().isBlank()) {
            if (!InputManager.isValidContactNo(txtContactNo2)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidTextField(txtFName) && InputManager.isValidTextField(txtLName) && InputManager.isValidEmail(txtEmail) && InputManager.isValidTextField(txtAddressLine1) && InputManager.isValidContactNo(txtContactNo1) && InputManager.isValidPassword(txtPassword) && InputManager.isValidComboBox(cmbProvince) && InputManager.isValidComboBox(cmbDistrict) && InputManager.isValidComboBox(cmbCity) && InputManager.isValidComboBox(cmbMartialStatus) && InputManager.isValidComboBox(cmbUserRole) && InputManager.isValidComboBox(cmbStatus))) {
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
        String addressLine1 = txtAddressLine1.getText();
        String addressLine2 = txtAddressLine2.getText();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();
        String city = cmbCity.getSelectionModel().getSelectedItem();
        RadioButton genderSelection = (RadioButton) this.gender.getSelectedToggle();
        String selected_gender = genderSelection.getText();
        String martialStatus = cmbMartialStatus.getSelectionModel().getSelectedItem();
        String userRole = cmbUserRole.getSelectionModel().getSelectedItem();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        String password = txtPassword.getText();

        HashMap inputs = new HashMap();
        inputs.put("fname", fname);
        inputs.put("lname", lname);
        inputs.put("email", email);
        inputs.put("contactNo1", contactNo1);
        inputs.put("contactNo2", contactNo2);
        inputs.put("addressLine1", addressLine1);
        inputs.put("addressLine2", addressLine2);
        inputs.put("province", province);
        inputs.put("district", district);
        inputs.put("city", city);
        inputs.put("gender", selected_gender);
        inputs.put("martialStatus", martialStatus);
        inputs.put("userRole", userRole);
        inputs.put("status", status);
        inputs.put("password", password);

        return inputs;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBoxes();
        initializingItemStateChangeListeners();
    }

    public void initData(String empId, TableView<EmployeeTbl> table) {
        this.employeeId = empId;
        this.listEmployeeTable = table;
        initializingInputData(employeeId);
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
    private void txtAddressLine1KeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtAddressLine1);
    }

    @FXML
    private void txtPasswordKeyReleased(KeyEvent event) {
        InputManager.isValidPassword(txtPassword);
    }

    @FXML
    private void fchImageActionPerformed(ActionEvent event) {
        imageFile = new FileManager().getImage(fileChooser, imageCircle);
    }

    @FXML
    private void btnResetActionPerformed(ActionEvent event) {
        initializingInputData(employeeId);
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        if (verifyInputs()) {
            try {
                HashMap input = getInputValues();
                //check user in database
                ResultSet rs = MySQL.search("SELECT * FROM `employee` WHERE `email` = '" + input.get("email") + "' AND `employee_id` != '" + employeeId + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Email address is already registered.\nYou can't register user with already registered Email address.");
                    alert.show();
                } else {
                    //insert data into the database
                    String fileName = currentImageFileName;

                    if (imageFile != null) {
                        //copy file to directory
                        fileName = FileManager.copyImage(imageFile, employeeId, FileManager.USER_IMAGE_PATH);
                    }

                    MySQL.iud("UPDATE `employee` SET `fname` = '" + input.get("fname") + "', `lname` = '" + input.get("lname") + "', `email` = '" + input.get("email") + "', `contact_no` =  '" + input.get("contactNo1") + "', `contact_no_2` =  '" + input.get("contactNo2") + "', `address_line_1` = '" + input.get("addressLine1") + "', `address_line_2` = '" + input.get("addressLine2") + "', `city_id` = " + DBManager.getId(input.get("city").toString(), DBTable.CITY) + ", `gender_id` = " + DBManager.getId(input.get("gender").toString(), DBTable.GENDER) + ", `martial_status_id` = " + DBManager.getId(input.get("martialStatus").toString(), DBTable.MARTIAL_STATUS) + ", `profile_image` =  '" + fileName + "', `user_role_id` = " + DBManager.getId(input.get("userRole").toString(), DBTable.USER_ROLE) + ", `status_id` = " + DBManager.getId(input.get("status").toString(), DBTable.STATUS) + ", `password` = '" + input.get("password") + "' WHERE `employee_id` = '" + employeeId + "'");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Employee information updated successfully.");
                    alert.show();
                    initializingInputData(employeeId);
                    new TableManager().loadEmployeeTable(listEmployeeTable, DBManager.getTableData(DBTable.EMPLOYEE));
                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnCityActionPerformed(ActionEvent event) {
        FXMLLoader cityFxmlLoader = new JFXManager().openDialog(Views.CITY_DIALOG);
        CityDialogController cityDialogController = cityFxmlLoader.getController();
        
        cityDialogController.init(cmbCity, cmbProvince, cmbDistrict);
    }

}
