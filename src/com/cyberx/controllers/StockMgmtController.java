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
public class StockMgmtController implements Initializable {

    @FXML
    private Button btnListStock;
    @FXML
    private Button btnAddGRN;
    @FXML
    private BorderPane contentPane;
    @FXML
    private Button btnAddSale;

    public BorderPane getContentPane() {
        return contentPane;
    }
    
    
    
    private void removeActiveBtn(){
        //list stock
        btnListStock.getStyleClass().remove("sidebar-btn-active");
        
        //add GRN
        btnAddGRN.getStyleClass().remove("sidebar-btn-active");
        
        //add sale
        btnAddSale.getStyleClass().remove("sidebar-btn-active");
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
    private void btnListStockActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnListStock);
        new JFXManager().LoadUi(Views.LIST_STOCK, contentPane);
    }

    @FXML
    private void btnAddGRNActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddGRN);
        new JFXManager().LoadUi(Views.ADD_GRN, contentPane);
    }

    @FXML
    private void btnAddSaleActionPerformed(ActionEvent event) {
        removeActiveBtn();
        setActiveBtn(btnAddSale);
        new JFXManager().LoadUi(Views.ADD_SALES, contentPane);
    }
    
}
