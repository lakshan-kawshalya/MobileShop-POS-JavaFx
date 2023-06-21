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
import com.cyberx.model.tables.SupplierTbl;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ListSupplierController implements Initializable {

    boolean searchEnabled = false;
    Label supplierNameLbl;
    Label supplierIdLbl;
    Label companyIdLbl;
    Label companyNameLbl;

    SupplierTbl supplierTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbCompany;
    @FXML
    private Button btnView;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<SupplierTbl> table;
    @FXML
    private TableColumn<SupplierTbl, String> colSUPId;
    @FXML
    private TableColumn<SupplierTbl, String> colName;
    @FXML
    private TableColumn<SupplierTbl, String> colEmail;
    @FXML
    private TableColumn<SupplierTbl, String> colContactNo;
    @FXML
    private TableColumn<SupplierTbl, String> colCompany;

    private void setCellValueFactory() {
        colSUPId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
    }

    private void setTableBtnActive(boolean status) {
        if (status) {
            btnView.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            btnView.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbCompany, DBTable.COMPANY);
    }

    public String searchEmployee() {
        String query = "SELECT * FROM `supplier` ";

        String searchTxt = txtSearch.getText();
        String company = cmbCompany.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`supplier_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`supplier_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            }
        }

        if (company != null) {
            if (!company.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `company_id` = " + DBManager.getId(company, DBTable.COMPANY);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `company_id` = " + DBManager.getId(company, DBTable.COMPANY);
                }
            }
        }
        query = query + " ORDER BY `supplier`.`id`";

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
        new TableManager().loadSupplierTable(table, DBManager.getTableData(DBTable.SUPPLIER));
        loadComboBoxes();
    }

    public void init(Label supplierIdLabel, Label supplierNameLabel, Label companyIdLabel, Label companyNameLabel) {
        searchEnabled = true;
        this.supplierNameLbl = supplierNameLabel;
        this.supplierIdLbl = supplierIdLabel;
        this.companyIdLbl = companyIdLabel;
        this.companyNameLbl = companyNameLabel;
    }

    private void initializingItemStateChangeListeners() {
        cmbCompany.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadSupplierTable(table, DBManager.getDBData(searchEmployee()));
            }

        });
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadSupplierTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void btnViewActionPerformed(ActionEvent event) {
        FXMLLoader viewSupplierInfoFxmlLoader = new JFXManager().openDialog(Views.VIEW_SUPPLIER_INFO_DIALOG);

        //get controller associate with view
        ViewSupplierInfoDialogController viewSupplierInfoController = viewSupplierInfoFxmlLoader.getController();

        viewSupplierInfoController.initData(supplierTblData.getSupId(), table);
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        FXMLLoader updateSupplierFxmlLoader = new JFXManager().openDialog(Views.UPDATE_SUPPLIER_DIALOG);

        //get controller associate with view
        UpdateSupplierDialogController updateSupplierController = updateSupplierFxmlLoader.getController();

        updateSupplierController.initData(supplierTblData.getSupId(), table);
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            supplierTblData = table.getSelectionModel().getSelectedItem();

            if (supplierTblData.getSupId() != null) {
                setTableBtnActive(true);
            }

            ResultSet rs = DBManager.getDBData("SELECT `company_id` FROM `company` WHERE `name` = '" + supplierTblData.getCompany() + "'");
            String company_id = "";
            try {
                rs.next();
                company_id = rs.getString("company_id");
            } catch (SQLException ex) {
                Logger.getLogger(ListSupplierController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (searchEnabled) {
                if (evt.getClickCount() == 2) {
                    supplierNameLbl.setText(supplierTblData.getName());
                    supplierIdLbl.setText(supplierTblData.getSupId());
                    companyIdLbl.setText(company_id);
                    companyNameLbl.setText(supplierTblData.getCompany());

                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

}
