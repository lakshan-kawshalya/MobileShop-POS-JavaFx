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
public class SupplierMgmtController implements Initializable {

    @FXML
    private Button btnListSuppliers;
    @FXML
    private Button btnAddSupplier;
    @FXML
    private Button btnListCompany;
    @FXML
    private Button btnAddCompany;
    @FXML
    private BorderPane contentPane;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
        
    private void removeActiveBtn(){
        //list supplier
        btnListSuppliers.getStyleClass().remove("sidebar-btn-active");
        
        //add supplier
        btnAddSupplier.getStyleClass().remove("sidebar-btn-active");
        
        //list company
        btnListCompany.getStyleClass().remove("sidebar-btn-active");
        
        //add company
        btnAddCompany.getStyleClass().remove("sidebar-btn-active");
    }
    
    private void setActiveBtn(Button btn){
        btn.getStyleClass().add("sidebar-btn-active");
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnListSupplierActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListSuppliers);
        new JFXManager().LoadUi(Views.LIST_SUPPLIER, contentPane);
    }

    @FXML
    private void btnAddSupplierActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddSupplier);
        new JFXManager().LoadUi(Views.ADD_SUPPLIER, contentPane);
    }

    @FXML
    private void btnListCompanyActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListCompany);
        new JFXManager().LoadUi(Views.LIST_COMPANY, contentPane);
    }

    @FXML
    private void btnAddCompanyActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddCompany);
        new JFXManager().LoadUi(Views.ADD_COMPANY, contentPane);
    }
    
}
