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
import com.cyberx.model.MySQL;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class AddProductController implements Initializable {

    FileChooser fileChooser = new FileChooser();
    File imageFile;

    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbSubCategory;
    @FXML
    private TextField txtName;
    @FXML
    private CheckBox chkIMEI;
    @FXML
    private Button fchImage;
    @FXML
    private Circle imageCircle;
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

        cmbSubCategory.getSelectionModel().select(0);
        cmbSubCategory.getStyleClass().remove("invalid-input");

        chkIMEI.setSelected(false);
        imageFile = null;

        initializeDefaultImage();
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
        String subCategory = "";
        if (cmbSubCategory.getSelectionModel().getSelectedIndex() != 0) {
            subCategory = cmbSubCategory.getSelectionModel().getSelectedItem();
        }

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

    private void initializingItemStateChangeListeners() {
        cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.loadSubCategory(cmbSubCategory, cmbCategory);
            }

        });
    }

    private void initializeDefaultImage() {
        //setting image
        Image img = new Image(this.getClass().getResourceAsStream("/com/cyberx/resources/img/default-product-image.png"));
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
    private void fchImageActionPerformed(ActionEvent event) {
        if (imageFile != null) {
            imageFile = new FileManager().getImage(fileChooser, imageCircle);
        }
    }

    @FXML
    private void btnAddActonPerformed(ActionEvent event) {
        if (verifyInputs()) {

            try {
                HashMap input = getInputValues();
                int SubCategory;

                //check product in database
                String conditionQuery = "WHERE `name` = '" + input.get("name") + "' AND `category_id` = '" + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY) + "'";

                if (!input.get("subCategory").toString().isBlank()) {
                    SubCategory = DBManager.getId(input.get("subCategory").toString(), DBTable.SUB_CATEGORY);
                    conditionQuery = conditionQuery.concat("  AND `sub_category_id` = " + SubCategory + "");
                } else {
                    SubCategory = 0;
                }

                ResultSet rs = MySQL.search("SELECT * FROM `product` " + conditionQuery);

                if (rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Product is already registered.");
                    alert.show();
                } else {
                    //insert data into the database
                    String ProductId = IDManager.generateID(DBTable.PRODUCT);
                    String fileName = FileManager.DEFAULT_PRODUCT_IMAGE;

                    if (imageFile != null) {
                        //copy file to directory
                        fileName = FileManager.copyImage(imageFile, ProductId, FileManager.PRODUCT_IMAGE_PATH);
                    }

                    MySQL.iud("INSERT INTO `product`(`product_id`, `name`, `category_id`, `sub_category_id`, `product_img`, `imei_status`) VALUES ('" + ProductId + "', '" + input.get("name") + "', " + DBManager.getId(input.get("category").toString(), DBTable.CATEGORY) + ", " + SubCategory + ", '" + fileName + "', " + Integer.parseInt(input.get("imeiStatus").toString()) + ")");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Product registered successfully.");
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

    @FXML
    private void txtNameKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtName);
    }

}
