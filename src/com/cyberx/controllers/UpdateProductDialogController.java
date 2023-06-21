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
import com.cyberx.model.MySQL;
import com.cyberx.model.TableManager;
import com.cyberx.model.tables.ProductTbl;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class UpdateProductDialogController implements Initializable {

    TableView<ProductTbl> listProductTable;

    private FileChooser fileChooser = new FileChooser();
    private File imageFile;

    private String productId;
    private String currentImageFileName;

    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbSubCategory;
    @FXML
    private CheckBox chkIMEI;
    @FXML
    private TextField txtName;
    @FXML
    private Button fchImage;
    @FXML
    private Circle imageCircle;
    @FXML
    private Label lblId;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnReset;

    private void initializeComboBoxes() {
        InputManager.loadComboBox(cmbCategory, DBTable.CATEGORY);
    }

    private void initializingItemStateChangeListeners() {

        cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbCategory);
                InputManager.loadSubCategory(cmbSubCategory, cmbCategory);
            }

        });
    }

    private void initializingInputData(String prdId) {
        ResultSet rs = DBManager.getTableData(DBTable.PRODUCT, prdId);

        try {
            rs.next();

            lblId.setText(rs.getString("product_id"));
            txtName.setText(rs.getString("name"));

            if (rs.getInt("imei_status") == 1) {
                chkIMEI.setSelected(true);
            } else {
                chkIMEI.setSelected(false);
            }

            cmbCategory.getSelectionModel().select(DBManager.getName(rs.getInt("category_id"), DBTable.CATEGORY));
            InputManager.loadSubCategory(cmbSubCategory, cmbCategory);

            if (rs.getInt("sub_category_id") != 0) {
                cmbSubCategory.getSelectionModel().select(DBManager.getName(rs.getInt("sub_category_id"), DBTable.SUB_CATEGORY));
            }

            currentImageFileName = rs.getString("product_img");
            new FileManager().setImage(imageCircle, currentImageFileName, FileManager.PRODUCT_IMAGE_PATH);

        } catch (SQLException ex) {
            Logger.getLogger(UpdateEmployeeDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String subCategory = cmbSubCategory.getSelectionModel().getSelectedItem();

        String imei;
        if (chkIMEI.isSelected()) {
            imei = "1";
        } else {
            imei = "0";
        }

        HashMap inputs = new HashMap();
        inputs.put("name", name);
        inputs.put("category", category);
        inputs.put("subCategory", subCategory);
        inputs.put("imeiStatus", imei);

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

    public void initData(String prdId, TableView<ProductTbl> table) {
        this.productId = prdId;
        this.listProductTable = table;
        initializingInputData(productId);
    }

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
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
                //check product in database
                int SubCategory;

                String conditionQuery = "WHERE `name` = '" + input.get("name") + "' AND `category_id` = '" + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY) + "'";

                if (!input.get("subCategory").toString().isBlank()) {
                    SubCategory = DBManager.getId(input.get("subCategory").toString(), DBTable.SUB_CATEGORY);
                    conditionQuery = conditionQuery.concat("  AND `sub_category_id` = " + SubCategory + "");
                } else {
                    SubCategory = 0;
                }

                ResultSet rs = MySQL.search("SELECT * FROM `product` " + conditionQuery + " AND `product_id` != '" + productId + "'");

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Product is already registered.");
                    alert.show();
                } else {
                    //insert data into the database
                    String fileName = currentImageFileName;

                    if (imageFile != null) {
                        //copy file to directory
                        fileName = FileManager.copyImage(imageFile, productId, FileManager.PRODUCT_IMAGE_PATH);
                    }

                    MySQL.iud("UPDATE `product` SET `name` = '" + input.get("name") + "', `category_id` = " + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY) + ", `sub_category_id` = " + SubCategory + ", `product_img` =  '" + fileName + "', `imei_status` =  '" + input.get("imeiStatus") + "' WHERE `product_id` = '" + productId + "'");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Product information updated successfully.");
                    alert.show();
                    initializingInputData(productId);
                    new TableManager().loadProductTable(listProductTable, DBManager.getTableData(DBTable.PRODUCT));
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
        initializingInputData(productId);
    }

}
