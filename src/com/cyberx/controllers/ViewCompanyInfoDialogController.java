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
import com.cyberx.model.tables.CompanyTbl;
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
public class ViewCompanyInfoDialogController implements Initializable {
    
    private String companyId;
    TableView<CompanyTbl> listCompanyTable;

    @FXML
    private Label lblId;
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
    private Label lblAddress;
    @FXML
    private Label lblRegisteredDate;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblRegNo;
    @FXML
    private Label lblStatus;
    
    private void loadData(String comId) {
        ResultSet rs = DBManager.getTableData(DBTable.COMPANY, comId);

        try {
            rs.next();

            lblId.setText(rs.getString("company_id"));
            lblName.setText(rs.getString("name"));
            lblEmail.setText(rs.getString("email"));
            lblContactNo1.setText(rs.getString("contact_no"));
            lblContactNo2.setText(rs.getString("contact_no_2"));
            lblAddress.setText(rs.getString("address_line_1") + ", \n" + rs.getString("address_line_2") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("city") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("district") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("province") + " Province.");
            lblStatus.setText(DBManager.getName(rs.getInt("status_id"), DBTable.STATUS));
            lblRegisteredDate.setText((rs.getString("registered_date")).split(" ")[0]);

            new FileManager().setImage(imageCircle, rs.getString("logo_image"), FileManager.COMPANY_LOGO_PATH);

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
    
    public void initData(String empId, TableView<CompanyTbl> table) {
        this.companyId = empId;
        this.listCompanyTable = table;
        loadData(companyId);
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        FXMLLoader updateCompanyFxmlLoader = new JFXManager().openDialog(Views.UPDATE_COMPANY_DIALOG);

        //get controller associate with view
        UpdateCompanyDialogController updateCompanyController = updateCompanyFxmlLoader.getController();

        updateCompanyController.initData(companyId, listCompanyTable);
        final Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }
    
}
