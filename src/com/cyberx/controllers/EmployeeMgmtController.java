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
public class EmployeeMgmtController implements Initializable {

    @FXML
    private BorderPane contentPane;
    @FXML
    private Button btnListEmployee;
    @FXML
    private Button btnAddEmployee;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
    private void removeActiveBtn(){
        //list employee
        btnListEmployee.getStyleClass().remove("sidebar-btn-active");
        
        //add employee
        btnAddEmployee.getStyleClass().remove("sidebar-btn-active");
    }
    
    private void setActiveBtn(Button btn){
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
    }

    @FXML
    private void btnListEmployeeActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListEmployee);
        new JFXManager().LoadUi(Views.LIST_EMPLOYEE, contentPane);
    }

    @FXML
    private void btnAddEmployeeActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddEmployee);
        new JFXManager().LoadUi(Views.ADD_EMPLOYEE, contentPane);
    }

}
