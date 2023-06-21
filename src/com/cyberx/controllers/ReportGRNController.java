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
import com.cyberx.model.tables.ReportGrnTbl;
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
public class ReportGRNController implements Initializable {

    ReportGrnTbl reportGRNTblData;

    @FXML
    private TextField txtSupplierName;
    @FXML
    private Button btnSupplier;
    @FXML
    private DatePicker dchDateFrom;
    @FXML
    private TextField txtGRNId;
    @FXML
    private TextField txtEmployeeName;
    @FXML
    private Button btnEmployee;
    @FXML
    private DatePicker dchDateTo;
    @FXML
    private TableView<ReportGrnTbl> table;
    @FXML
    private TableColumn<ReportGrnTbl, String> colNo;
    @FXML
    private TableColumn<ReportGrnTbl, String> colGRNId;
    @FXML
    private TableColumn<ReportGrnTbl, String> colEmployeeName;
    @FXML
    private TableColumn<ReportGrnTbl, String> colSupplierName;
    @FXML
    private TableColumn<ReportGrnTbl, String> colDate;

    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colGRNId.setCellValueFactory(new PropertyValueFactory<>("grnId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public String searchEmployee() {
        String query = "SELECT * FROM `grn` INNER JOIN `employee` ON `employee`.`id` = `grn`.`employee_id` INNER JOIN `supplier` ON `supplier`.`id` = `grn`.`supplier_id` ";

        String grnId = "GRN-" + (txtGRNId.getText());
        String supplierName = txtSupplierName.getText();
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

        if (!grnId.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`grn`.`grn_id` LIKE '%" + grnId + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`grn`.`grn_id` LIKE '%" + grnId + "%')";
            }
        }

        if (!supplierName.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`supplier`.`fname` LIKE '%" + supplierName + "%' OR `supplier`.`lname` LIKE '%" + supplierName + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`supplier`.`fname` LIKE '%" + supplierName + "%' OR `supplier`.`lname` LIKE '%" + supplierName + "%')";
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
                query = query + " AND (`grn`.`date_added` >= '" + dateFrom + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`grn`.`date_added` >= '" + dateFrom + "')";
            }
        }

        if (!dateTo.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`grn`.`date_added` <= '" + dateTo + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`grn`.`date_added` <= '" + dateTo + "')";
            }
        }

        query = query + " ORDER BY `grn`.`date_added` DESC";

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
                 new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
            }
        });
        
        dchDateTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                 new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
            }
        });
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
        initializingItemStateChangeListeners();
        new TableManager().loadReportGRNTable(table, DBManager.getDBData("SELECT * FROM `grn` INNER JOIN `employee` ON `employee`.`id` = `grn`.`employee_id` INNER JOIN `supplier` ON `supplier`.`id` = `grn`.`supplier_id` "));
    }

    @FXML
    private void btnSupplierActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnEmployeeActionPerformed(ActionEvent event) {
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if(table.getItems()!=null){
            reportGRNTblData = table.getSelectionModel().getSelectedItem();
            if(evt.getClickCount()==2){
                new ReportManager().generateGRNReport(reportGRNTblData.getGrnId());
            }
        }
    }

    @FXML
    private void txtGRNIdKeyReleased(KeyEvent event) {
        new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void txtSupplierNameKeyReleased(KeyEvent event) {
        new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void dchDateFromKeyReleased(KeyEvent event) {
        new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void txtEmployeeNameKeyReleased(KeyEvent event) {
        new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void dchDateToKeyReleased(KeyEvent event) {
        new TableManager().loadReportGRNTable(table, DBManager.getDBData(searchEmployee()));
    }

}
