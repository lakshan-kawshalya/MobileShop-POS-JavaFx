/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.FileManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.SupplierTbl;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ViewSupplierInfoDialogController implements Initializable {
    
    private String supplierId;
    TableView<SupplierTbl> listSupplierTable;

    @FXML
    private Circle imageCircle;
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
    private Label lblCompanyName;
    @FXML
    private Label lblId;
    @FXML
    private Button btnUpdate;
    
    private void loadData(String supId) {
        ResultSet rs = DBManager.getTableData(DBTable.SUPPLIER, supId);

        try {
            rs.next();

            lblId.setText(rs.getString("supplier_id"));
            lblName.setText(rs.getString("fname") + " " + rs.getString("lname"));
            lblEmail.setText(rs.getString("email"));
            lblContactNo1.setText(rs.getString("contact_no"));
            lblContactNo2.setText(rs.getString("contact_no_2"));
            lblRegisteredDate.setText((rs.getString("registered_date")).split(" ")[0]);            
            
            ResultSet company_rs = DBManager.getDBData("SELECT * FROM `company` WHERE `id` = '" + rs.getInt("company_id") + "'");
            company_rs.next();
            
            lblCompanyName.setText(company_rs.getString("name"));

            new FileManager().setImage(imageCircle, company_rs.getString("logo_image"), FileManager.COMPANY_LOGO_PATH);

        } catch (SQLException ex) {
            Logger.getLogger(ViewEmployeeInfoDialogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(String supId, TableView<SupplierTbl> table) {
        this.supplierId = supId;
        this.listSupplierTable = table;
        loadData(supplierId);
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        FXMLLoader updateSupplierFxmlLoader = new JFXManager().openDialog(Views.UPDATE_SUPPLIER_DIALOG);

        //get controller associate with view
        UpdateSupplierDialogController updateSupplierController = updateSupplierFxmlLoader.getController();

        updateSupplierController.initData(supplierId, listSupplierTable);
        final Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }
    
}
