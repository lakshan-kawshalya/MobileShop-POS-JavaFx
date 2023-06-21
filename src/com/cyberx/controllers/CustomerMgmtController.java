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
public class CustomerMgmtController implements Initializable {

    @FXML
    private Button btnListCustomer;
    @FXML
    private Button btnAddCustomer;
    @FXML
    private BorderPane contentPane;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
    private void removeActiveBtn(){
        //list employee
        btnListCustomer.getStyleClass().remove("sidebar-btn-active");
        
        //add employee
        btnAddCustomer.getStyleClass().remove("sidebar-btn-active");
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
    private void btnListCustomerActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListCustomer);
        new JFXManager().LoadUi(Views.LIST_CUSTOMER, contentPane);
    }

    @FXML
    private void btnAddCustomerActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddCustomer);
        new JFXManager().LoadUi(Views.ADD_CUSTOMER, contentPane);
    }
    
}
