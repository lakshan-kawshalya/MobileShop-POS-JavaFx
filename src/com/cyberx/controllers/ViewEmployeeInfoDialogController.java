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
import com.cyberx.model.tables.EmployeeTbl;
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
public class ViewEmployeeInfoDialogController implements Initializable {

    private String employeeId;
    TableView<EmployeeTbl> listEmployeeTable;

    @FXML
    private Label lblId;
    @FXML
    private Button btnUpdate;
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
    private Label lblGender;
    @FXML
    private Label lblMartialStatus;
    @FXML
    private Label lblRegisteredDate;
    @FXML
    private Label lblUserRole;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblStatus;
    @FXML
    private Circle imageCircle;

    private void loadData(String empId) {
        ResultSet rs = DBManager.getTableData(DBTable.EMPLOYEE, empId);

        try {
            rs.next();

            lblId.setText(rs.getString("employee_id"));
            lblName.setText(rs.getString("fname") + " " + rs.getString("lname"));
            lblEmail.setText(rs.getString("email"));
            lblContactNo1.setText(rs.getString("contact_no"));
            lblContactNo2.setText(rs.getString("contact_no_2"));
            lblAddress.setText(rs.getString("address_line_1") + ", \n" + rs.getString("address_line_2") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("city") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("district") + ", \n" + DBManager.getAddress(rs.getInt("city_id")).get("province") + " Province.");
            lblGender.setText(DBManager.getName(rs.getInt("gender_id"), DBTable.GENDER));
            lblMartialStatus.setText(DBManager.getName(rs.getInt("martial_status_id"), DBTable.MARTIAL_STATUS));
            lblUserRole.setText(DBManager.getName(rs.getInt("user_role_id"), DBTable.USER_ROLE));
            lblStatus.setText(DBManager.getName(rs.getInt("status_id"), DBTable.STATUS));
            lblPassword.setText(rs.getString("password"));
            lblRegisteredDate.setText((rs.getString("registered_date")).split(" ")[0]);

            new FileManager().setImage(imageCircle, rs.getString("profile_image"), FileManager.USER_IMAGE_PATH);

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

    public void initData(String empId, TableView<EmployeeTbl> table) {
        this.employeeId = empId;
        this.listEmployeeTable = table;
        loadData(employeeId);
    }

    @FXML
    private void btnUpdateActonPerformed(ActionEvent event) {
        FXMLLoader updateEmployeeFxmlLoader = new JFXManager().openDialog(Views.UPDATE_EMPLOYEE_DIALOG);

        //get controller associate with view
        UpdateEmployeeDialogController updateEmployeeController = updateEmployeeFxmlLoader.getController();

        updateEmployeeController.initData(employeeId, listEmployeeTable);
        final Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
    }

}
