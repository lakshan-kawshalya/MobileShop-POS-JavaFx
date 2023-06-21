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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ProductMgmtController implements Initializable {

    @FXML
    private Button btnListProduct;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnCategoryMgmt;
    @FXML
    private BorderPane contentPane;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
    
    
     private void removeActiveBtn() {
         //list product
        btnListProduct.getStyleClass().remove("sidebar-btn-active");
        
        //add product
        btnAddProduct.getStyleClass().remove("sidebar-btn-active");
        
        //category management
        btnCategoryMgmt.getStyleClass().remove("sidebar-btn-active");
    }

    private void setActiveBtn(Button btn) {
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
    private void btnListProductActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListProduct);
        new JFXManager().LoadUi(Views.LIST_PRODUCT, contentPane);
    }

    @FXML
    private void btnAddProductActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddProduct);
        new JFXManager().LoadUi(Views.ADD_PRODUCT, contentPane);
    }

    @FXML
    private void btnCategoryMgmtActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnCategoryMgmt);
        FXMLLoader fxmlLoader = new JFXManager().LoadUi(Views.CATEGORY_MGMT, contentPane);

        //initialize selected ui
        CategoryMgmtController categoryMgmtController = fxmlLoader.getController();
        BorderPane categoryMgmtContentPane = categoryMgmtController.getContentPane();
        new JFXManager().LoadUi(Views.LIST_CATEGORY, categoryMgmtContentPane);
    }
    
}
