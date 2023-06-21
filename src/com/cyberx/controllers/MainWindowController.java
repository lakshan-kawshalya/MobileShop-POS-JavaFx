/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.main.Main;
import com.cyberx.model.DBManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.Views;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class MainWindowController implements Initializable {

    @FXML
    private BorderPane contentPane;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnEmployeeMgmt;
    @FXML
    private Button btnSupplierMgmt;
    @FXML
    private Button btnCustomerMgmt;
    @FXML
    private Button btnProductMgmt;
    @FXML
    private Button btnStockMgmt;
    @FXML
    private Button btnReports;
    @FXML
    private Circle imgUser;
    @FXML
    private Label lblHello;
    @FXML
    private Label lblDate;

    // custom methods
    //load ui
    private void LoadUi(String ui) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/com/cyberx/views/" + ui + ".fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        contentPane.setCenter(root);
    }

    private void removeActiveBtn() {
        //dashboard
        btnDashboard.getStyleClass().remove("sidebar-btn-active");

        //employee mgmt
        btnEmployeeMgmt.getStyleClass().remove("sidebar-btn-active");

        //supplier mgmt
        btnSupplierMgmt.getStyleClass().remove("sidebar-btn-active");

        //customer mgmt
        btnCustomerMgmt.getStyleClass().remove("sidebar-btn-active");

        //product mgmt
        btnProductMgmt.getStyleClass().remove("sidebar-btn-active");

        //stock mgmt
        btnStockMgmt.getStyleClass().remove("sidebar-btn-active");

        //report mgmt
        btnReports.getStyleClass().remove("sidebar-btn-active");
    }

    public void setLoggedUserData() {
        ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `id` = " + Main.loggedUserId + "");
        try {
            employeeRs.next();
            //set up logged user details
            Image img = new Image(getClass().getResourceAsStream("/user_uploads/profile_img/" + employeeRs.getString("profile_image")));
            imgUser.setFill(new ImagePattern(img));
            lblHello.setText("Hello, " + employeeRs.getString("fname"));
        } catch (SQLException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setActiveBtn(Button btn) {
        btn.getStyleClass().add("sidebar-btn-active");
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLoggedUserData();
        lblDate.setText(Main.sdfDate.format(new Date()));
        new JFXManager().LoadUi(Views.DASHBOARD, contentPane);
    }

    @FXML
    private void btnDashboardActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnDashboard);
        new JFXManager().LoadUi(Views.DASHBOARD, contentPane);
    }

    @FXML
    private void btnEmployeeMgmtActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnEmployeeMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.EMPLOYEE_MGMT, contentPane);

        //initialize selected ui
        EmployeeMgmtController employeeMgmtController = fxmlLoader.getController();
        BorderPane employeeMgmtContentPane = employeeMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_EMPLOYEE, employeeMgmtContentPane);
    }

    @FXML
    private void btnSupplierMgmtActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnSupplierMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.SUPPLIER_MGMT, contentPane);

        //initialize selected ui
        SupplierMgmtController supplierMgmtController = fxmlLoader.getController();
        BorderPane supplierMgmtContentPane = supplierMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_SUPPLIER, supplierMgmtContentPane);
    }

    @FXML
    private void btnCustomerMgmtActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnCustomerMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.CUSTOMER_MGMT, contentPane);

        //initialize selected ui
        CustomerMgmtController customerMgmtController = fxmlLoader.getController();
        BorderPane customerMgmtContentPane = customerMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_CUSTOMER, customerMgmtContentPane);
    }

    @FXML
    private void btnProductMgmtActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnProductMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.PRODUCT_MGMT, contentPane);

        //initialize selected ui
        ProductMgmtController productMgmtController = fxmlLoader.getController();
        BorderPane productMgmtContentPane = productMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_PRODUCT, productMgmtContentPane);
    }

    @FXML
    private void btnStockMgmtActionPerformed(ActionEvent event) {

        removeActiveBtn();
        setActiveBtn(btnStockMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.STOCK_MGMT, contentPane);

        //initialize selected ui
        StockMgmtController stockMgmtController = fxmlLoader.getController();
        BorderPane stockMgmtContentPane = stockMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_STOCK, stockMgmtContentPane);
    }

    @FXML
    private void btnReportsActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnReports);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.REPORT_MGMT, contentPane);

        //initialize selected ui
        ReportMgmtController reportMgmtController = fxmlLoader.getController();
        BorderPane reportMgmtContentPane = reportMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.REPORT_INVOICE, reportMgmtContentPane);
    }

}
