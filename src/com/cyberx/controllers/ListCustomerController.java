/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.JFXManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.CustomerTbl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class ListCustomerController implements Initializable {

    boolean searchEnabled = false;
    Label customerIdLbl;
    Label customerNameLbl;
    Label customerEmailLbl;
    Label customerContactNoLbl;

    CustomerTbl customerTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnView;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<CustomerTbl> table;
    @FXML
    private TableColumn<CustomerTbl, String> colCUSId;
    @FXML
    private TableColumn<CustomerTbl, String> colName;
    @FXML
    private TableColumn<CustomerTbl, String> colEmail;
    @FXML
    private TableColumn<CustomerTbl, String> colContactNo;
    @FXML
    private Button btnAddCustomer;

    private void setCellValueFactory() {
        colCUSId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
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

    public String searchCustomer() {
        String query = "SELECT * FROM `customer` ";

        String searchTxt = txtSearch.getText();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`customer_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`customer_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            }
        }

        query = query + " ORDER BY `customer`.`id`";

        setTableBtnActive(false);

        return query;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        new TableManager().loadCustomerTable(table, DBManager.getTableData(DBTable.CUSTOMER));
    }

    public void init(Label customerId, Label customerName, Label customerEmail, Label customerContactNo) {
        searchEnabled = true;
        this.customerIdLbl = customerId;
        this.customerNameLbl = customerName;
        this.customerEmailLbl = customerEmail;
        this.customerContactNoLbl = customerContactNo;
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadCustomerTable(table, DBManager.getDBData(searchCustomer()));
    }

    @FXML
    private void btnViewActionPerformed(ActionEvent event) {
        FXMLLoader viewCustomerInfoFxmlLoader = new JFXManager().openDialog(Views.VIEW_CUSTOMER_INFO_DIALOG);

        //get controller associate with view
        ViewCustomerInfoDialogController viewCustomerInfoController = viewCustomerInfoFxmlLoader.getController();

        viewCustomerInfoController.initData(customerTblData.getCusId(), table);
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        FXMLLoader updateCustomerFxmlLoader = new JFXManager().openDialog(Views.UPDATE_CUSTOMER_DIALOG);

        //get controller associate with view
        UpdateCustomerDialogController updateCustomerController = updateCustomerFxmlLoader.getController();

        updateCustomerController.initData(customerTblData.getCusId(), table);
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            customerTblData = table.getSelectionModel().getSelectedItem();

            if (customerTblData.getCusId() != null) {
                setTableBtnActive(true);
            }

            if (searchEnabled) {
                if (evt.getClickCount() == 2) {
                    customerNameLbl.setText(customerTblData.getName());
                    customerIdLbl.setText(customerTblData.getCusId());
                    customerEmailLbl.setText(customerTblData.getEmail());
                    customerContactNoLbl.setText(customerTblData.getContactNo());

                    final Stage stage = (Stage) btnUpdate.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    @FXML
    private void btnAddCustomerActionPerformed(ActionEvent event) {
        FXMLLoader addCustomerFxmlLoader = new JFXManager().loadAsDialog(Views.ADD_CUSTOMER);

        //get controller associate with view
        AddCustomerController addCustomerController = addCustomerFxmlLoader.getController();

        addCustomerController.init(table);
    }

}
