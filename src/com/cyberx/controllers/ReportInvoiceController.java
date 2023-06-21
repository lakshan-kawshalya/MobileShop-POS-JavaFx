/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.InputManager;
import com.cyberx.model.ReportManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.tables.ReportInvoiceTbl;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ReportInvoiceController implements Initializable {
    
    ReportInvoiceTbl reportInvoiceTblData;

    @FXML
    private TextField txtCustomerName;
    @FXML
    private DatePicker dchDateFrom;
    @FXML
    private TextField txtINVId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private DatePicker dchDateTo;
    @FXML
    private TableView<ReportInvoiceTbl> table;
    @FXML
    private TableColumn<ReportInvoiceTbl, String> colNo;
    @FXML
    private TableColumn<ReportInvoiceTbl, String> colINVId;
    @FXML
    private TableColumn<ReportInvoiceTbl, String> colEmployeeName;
    @FXML
    private TableColumn<ReportInvoiceTbl, String> colCustomerName;
    @FXML
    private TableColumn<ReportInvoiceTbl, String> colDate;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnEmployee;
    
    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colINVId.setCellValueFactory(new PropertyValueFactory<>("invId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    
    public String searchInvoice() {
        String query = "SELECT * FROM `invoice` INNER JOIN `employee` ON `employee`.`id` = `invoice`.`employee_id` INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_id` ";

        String invId = "INV-" + (txtINVId.getText());
        String customerName = txtCustomerName.getText();
        String employeeName = txtEmployeeName.getText();

        String dateFrom = "";
        String dateTo = "";

        if (dchDateFrom.getValue() != null) {
            dateFrom = dchDateFrom.getValue().toString();
        }
        if (dchDateTo.getValue() != null) {
            dateTo = dchDateTo.getValue().toString();
        }

        boolean isSetWhereClause = false;

        if (!invId.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`invoice`.`invoice_id` LIKE '%" + invId + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`invoice`.`invoice_id` LIKE '%" + invId + "%')";
            }
        }

        if (!customerName.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`customer`.`fname` LIKE '%" + customerName + "%' OR `customer`.`lname` LIKE '%" + customerName + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`customer`.`fname` LIKE '%" + customerName + "%' OR `customer`.`lname` LIKE '%" + customerName + "%')";
            }
        }

        if (!employeeName.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`employee`.`fname` LIKE '%" + employeeName + "%' OR `employee`.`lname` LIKE '%" + employeeName + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`employee`.`fname` LIKE '%" + employeeName + "%' OR `employee`.`lname` LIKE '%" + employeeName + "%')";
            }
        }

        if (!dateFrom.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`invoice`.`date_added` >= '" + dateFrom + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`invoice`.`date_added` >= '" + dateFrom + "')";
            }
        }

        if (!dateTo.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`invoice`.`date_added` <= '" + dateTo + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`invoice`.`date_added` <= '" + dateTo + "')";
            }
        }

        query = query + " ORDER BY `invoice`.`date_added` DESC";

        return query;
    }
    
    private void initializingItemStateChangeListeners() {
        dchDateFrom.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidDateKey(evt);
            }

        });

        dchDateTo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidDateKey(evt);
            }

        });

        dchDateFrom.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                 new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
            }
        });
        
        dchDateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                 new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
            }
        });
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        initializingItemStateChangeListeners();
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData("SELECT * FROM `invoice` INNER JOIN `employee` ON `employee`.`id` = `invoice`.`employee_id` INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_id` "));
    }    

    @FXML
    private void txtCustomerNameKeyReleased(KeyEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }


    @FXML
    private void dchDateFromKeyReleased(KeyEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }

    @FXML
    private void txtGRNIdKeyReleased(KeyEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }

    @FXML
    private void txtEmployeeNameKeyReleased(KeyEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }

    @FXML
    private void btnEmployeeActionPerformed(ActionEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }

    @FXML
    private void dchDateToKeyReleased(KeyEvent event) {
        new TableManager().loadReportInvoiceTable(table, DBManager.getDBData(searchInvoice()));
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if(table.getItems()!=null){
            reportInvoiceTblData = table.getSelectionModel().getSelectedItem();
            if(evt.getClickCount()==2){
                new ReportManager().generateInvoiceReport(reportInvoiceTblData.getInvId());
            }
        }
    }

    @FXML
    private void btnSupplierActionPerformed(ActionEvent event) {
    }

    
}
