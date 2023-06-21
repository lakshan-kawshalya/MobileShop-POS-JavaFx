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
    import com.cyberx.model.tables.CompanyTbl;
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
    import javafx.scene.control.TableView;
    import javafx.scene.control.TextField;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.shape.Circle;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    /**
     * FXML Controller class
     *
     * @author LAKSHAN KAWSHALYA
     */
    public class UpdateCompanyDialogController implements Initializable {

        TableView<CompanyTbl> listCompanyTable;

        private FileChooser fileChooser = new FileChooser();
        private File imageFile;

        private String companyId;
        private String currentImageFileName;

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
        private Label lblId;
        @FXML
        private ComboBox<String> cmbStatus;
        @FXML
        private Button btnUpdate;
        @FXML
        private Button btnReset;

        private void initializeComboBoxes() {
            InputManager.loadProvince(cmbProvince);
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

            cmbStatus.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                    InputManager.isValidComboBox(cmbStatus);
                }

            });
        }

        private void initializingInputData(String comId) {
            ResultSet rs = DBManager.getTableData(DBTable.COMPANY, comId);

            try {
                rs.next();

                lblId.setText(rs.getString("company_id"));
                txtName.setText(rs.getString("name"));
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

                cmbStatus.getSelectionModel().select(DBManager.getName(rs.getInt("status_id"), DBTable.STATUS));

                currentImageFileName = rs.getString("logo_image");
                new FileManager().setImage(imageCircle, currentImageFileName, FileManager.COMPANY_LOGO_PATH);

            } catch (SQLException ex) {
                Logger.getLogger(UpdateEmployeeDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            InputManager.isValidComboBox(cmbStatus);

            if (!txtContactNo2.getText().isBlank()) {
                if (!InputManager.isValidContactNo(txtContactNo2)) {
                    isValid = false;
                }
            }

            if (!(InputManager.isValidTextField(txtName) && InputManager.isValidEmail(txtEmail) && InputManager.isValidTextField(txtAddressLine1) && InputManager.isValidContactNo(txtContactNo1) && InputManager.isValidComboBox(cmbProvince) && InputManager.isValidComboBox(cmbDistrict) && InputManager.isValidComboBox(cmbCity) && InputManager.isValidComboBox(cmbStatus))) {
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
            String status = cmbStatus.getSelectionModel().getSelectedItem();

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
            inputs.put("status", status);

            return inputs;
        }

        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            initializeComboBoxes();
            initializingItemStateChangeListeners();
        }   

        public void initData(String comId, TableView<CompanyTbl> table) {
            this.companyId = comId;
            this.listCompanyTable = table;
            initializingInputData(companyId);
        }

        @FXML
        private void btnCityActionPerformed(ActionEvent event) {
            FXMLLoader cityFxmlLoader = new JFXManager().openDialog(Views.CITY_DIALOG);
            CityDialogController cityDialogController = cityFxmlLoader.getController();

            cityDialogController.init(cmbCity, cmbProvince, cmbDistrict);
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
        private void btnUpdateActonPerformed(ActionEvent event) {
            if (verifyInputs()) {
                try {
                    HashMap input = getInputValues();
                    //check user in database
                    ResultSet rs = MySQL.search("SELECT * FROM `company` WHERE `email` = '" + input.get("email") + "' AND `company_id` != '" + companyId + "'");

                    if (rs.next()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Email address is already registered.\nYou can't register Company with already registered Email address.");
                        alert.show();
                    } else {
                        //insert data into the database
                        String fileName = currentImageFileName;

                        if (imageFile != null) {
                            //copy file to directory
                            fileName = FileManager.copyImage(imageFile, companyId, FileManager.COMPANY_LOGO_PATH);
                        }

                        MySQL.iud("UPDATE `company` SET `name` = '" + input.get("name") + "', `email` = '" + input.get("email") + "', `contact_no` =  '" + input.get("contactNo1") + "', `contact_no_2` =  '" + input.get("contactNo2") + "', `address_line_1` = '" + input.get("addressLine1") + "', `address_line_2` = '" + input.get("addressLine2") + "', `city_id` = " + DBManager.getId(input.get("city").toString(), DBTable.CITY) + ", `logo_image` =  '" + fileName + "', `status_id` = " + DBManager.getId(input.get("status").toString(), DBTable.STATUS) + " WHERE `company_id` = '" + companyId + "'");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Employee information updated successfully.");
                        alert.show();
                        initializingInputData(companyId);
                        new TableManager().loadCompanyTable(listCompanyTable, DBManager.getTableData(DBTable.COMPANY));
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
            initializingInputData(companyId);
        }

        @FXML
        private void txtNameKeyReleased(KeyEvent event) {
            InputManager.isValidTextField(txtName);
        }

    }
