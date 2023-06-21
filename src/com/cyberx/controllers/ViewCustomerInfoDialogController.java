/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.JFXManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.CustomerTbl;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ViewCustomerInfoDialogController implements Initializable {
    
    private String customerId;
    TableView<CustomerTbl> listCustomerTable;

    @FXML
    private Label lblId;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblContactNo1;
    @FXML
    private Label lblContactNo2;
    @FXML
    private Label lblRegisteredDate;
    @FXML
    private Button btnUpdate;
    
    private void loadData(String empId) {
        ResultSet rs = DBManager.getTableData(DBTable.CUSTOMER, empId);

        try {
            rs.next();

            lblId.setText(rs.getString("customer_id"));
            lblName.setText(rs.getString("fname") + " " + rs.getString("lname"));
            lblEmail.setText(rs.getString("email"));
            lblContactNo1.setText(rs.getString("contact_no"));
            lblContactNo2.setText(rs.getString("contact_no_2"));
            lblRegisteredDate.setText((rs.getString("registered_date")).split(" ")[0]);

        } catch (SQLException ex) {
            Logger.getLogger(ViewEmployeeInfoDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initData(String cusId, TableView<CustomerTbl> table) {
        this.customerId = cusId;
        this.listCustomerTable = table;
        loadData(customerId);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        FXMLLoader updateCustomerFxmlLoader = new JFXManager().openDialog(Views.UPDATE_CUSTOMER_DIALOG);

        //get controller associate with view
        UpdateCustomerDialogController updateCustomerController = updateCustomerFxmlLoader.getController();

        updateCustomerController.initData(customerId, listCustomerTable);
        final Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }
    
}
