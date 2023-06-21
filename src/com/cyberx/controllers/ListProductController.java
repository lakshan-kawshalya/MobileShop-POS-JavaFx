/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.InputManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.ProductTbl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ListProductController implements Initializable {

    boolean searchEnabled = false;
    Label productIdLbl;
    Label productNameLbl;
    Label categoryNameLbl;
    TextField imeiTxt;
    TextField qtyTxt;

    ProductTbl productTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbSubCategory;
    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<ProductTbl> table;
    @FXML
    private TableColumn<ProductTbl, String> colProductImg;
    @FXML
    private TableColumn<ProductTbl, String> colPRDId;
    @FXML
    private TableColumn<ProductTbl, String> colName;
    @FXML
    private TableColumn<ProductTbl, String> colCategory;
    @FXML
    private TableColumn<ProductTbl, String> colSubCategory;
    @FXML
    private TableColumn<ProductTbl, String> colIMEIStatus;

    private void setCellValueFactory() {
        colProductImg.setCellValueFactory(new PropertyValueFactory<>("img"));
        colPRDId.setCellValueFactory(new PropertyValueFactory<>("prdId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        colIMEIStatus.setCellValueFactory(new PropertyValueFactory<>("imeiStatus"));
    }

    private void setTableBtnActive(boolean status) {
        if (status) {
            btnUpdate.setDisable(false);
        } else {
            btnUpdate.setDisable(true);
        }
    }

    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbCategory, DBTable.CATEGORY);
    }

    public String searchProduct() {
        String query = "SELECT * FROM `product`  ";

        String searchTxt = txtSearch.getText();
        String category = cmbCategory.getSelectionModel().getSelectedItem();
        String subCategory = cmbSubCategory.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`product_id` LIKE '%" + searchTxt + "%' OR `name` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`product_id` LIKE '%" + searchTxt + "%' OR `name` LIKE '%" + searchTxt + "%')";
            }
        }

        if (category != null) {
            if (!category.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `user_role_id` = " + DBManager.getId(category, DBTable.CATEGORY);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `user_role_id` = " + DBManager.getId(category, DBTable.CATEGORY);
                }
            }
        }

        if (subCategory != null) {
            if (!subCategory.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `status_id` = " + DBManager.getId(subCategory, DBTable.SUB_CATEGORY);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `status_id` = " + DBManager.getId(subCategory, DBTable.SUB_CATEGORY);
                }
            }
        }

        query = query + " ORDER BY `product`.`id`";

        setTableBtnActive(false);

        return query;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        initializingItemStateChangeListeners();
        new TableManager().loadProductTable(table, DBManager.getTableData(DBTable.PRODUCT));
        loadComboBoxes();
    }

    public void init(Label productIdLbl, Label productNameLbl, Label categoryNameLbl, TextField imeiTxt, TextField qtyTxt) {
        searchEnabled = true;
        this.productIdLbl = productIdLbl;
        this.productNameLbl = productNameLbl;
        this.categoryNameLbl = categoryNameLbl;
        this.imeiTxt = imeiTxt;
        this.qtyTxt = qtyTxt;
    }

    private void initializingItemStateChangeListeners() {
        cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadProductTable(table, DBManager.getDBData(searchProduct()));
            }

        });

        cmbSubCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadProductTable(table, DBManager.getDBData(searchProduct()));
            }

        });
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadProductTable(table, DBManager.getDBData(searchProduct()));
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        FXMLLoader updateEmployeeFxmlLoader = new JFXManager().openDialog(Views.UPDATE_PRODUCT_DIALOG);

        //get controller associate with view
        UpdateProductDialogController updateProductController = updateEmployeeFxmlLoader.getController();

        updateProductController.initData(productTblData.getPrdId(), table);
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            productTblData = table.getSelectionModel().getSelectedItem();

            if (productTblData.getPrdId() != null) {
                setTableBtnActive(true);
            }

            if (searchEnabled) {
                if (evt.getClickCount() == 2) {
                    productIdLbl.setText(productTblData.getPrdId());
                    productNameLbl.setText(productTblData.getName());
                    categoryNameLbl.setText(productTblData.getCategory());

                    if (productTblData.getImeiStatus().equalsIgnoreCase("required")) {
                        imeiTxt.setText("");
                        imeiTxt.setDisable(false);
                        qtyTxt.setText("1");
                        qtyTxt.setDisable(true);
                    }else{
                        imeiTxt.setText("");
                        imeiTxt.setDisable(true);
                        qtyTxt.setText("");
                        qtyTxt.setDisable(false);
                    }

                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

}
