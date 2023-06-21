/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.JFXManager;
import com.cyberx.model.Views;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ReportMgmtController implements Initializable {

    @FXML
    private Button btnGRNReport;
    @FXML
    private Button btnEmployeeReport;
    @FXML
    private Button btnSupplierReport;
    @FXML
    private Button btnProductReport;
    @FXML
    private BorderPane contentPane;
    @FXML
    private Button btnInvoiceReport;
    @FXML
    private Button btnSaleReport;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
    private void removeActiveBtn(){
        //invoice
        btnInvoiceReport.getStyleClass().remove("sidebar-btn-active");
        
        //grn
        btnGRNReport.getStyleClass().remove("sidebar-btn-active");
        
        //stock
        btnSaleReport.getStyleClass().remove("sidebar-btn-active");
        
        //employee
        btnEmployeeReport.getStyleClass().remove("sidebar-btn-active");
        
        //supplier
        btnSupplierReport.getStyleClass().remove("sidebar-btn-active");
        
        //product
        btnProductReport.getStyleClass().remove("sidebar-btn-active");
    }
    
    private void setActiveBtn(Button btn){
        btn.getStyleClass().add("sidebar-btn-active");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void btnGRNReportActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnGRNReport);
        new JFXManager().LoadUi(Views.REPORT_GRN, contentPane);
    }


    @FXML
    private void btnEmployeeReportActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnSupplierReportActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnProductReportActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnProductReport);
        new JFXManager().LoadUi(Views.REPORT_PRODUCTS, contentPane);
    }

    @FXML
    private void btnInvoiceReportActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnInvoiceReport);
        new JFXManager().LoadUi(Views.REPORT_INVOICE, contentPane);
    }

    @FXML
    private void btnSaleReportActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnSaleReport);
        new JFXManager().LoadUi(Views.REPORT_SALES, contentPane);
    }
    
}
