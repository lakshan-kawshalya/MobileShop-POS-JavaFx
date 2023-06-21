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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class DashboardController implements Initializable {

    @FXML
    private LineChart saleSummaryLineChart;

    public void generateSaleSummaryChart() {
        NumberAxis xAxix = new NumberAxis(1, 30, 1);
        xAxix.setLabel("Days");
        
        NumberAxis yAxix = new NumberAxis(0, 100000, 1000);
        yAxix.setLabel("Sales");
        
        saleSummaryLineChart = new LineChart(xAxix, xAxix);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void btnSalesActionPerformed(ActionEvent event) {
        new JFXManager().loadAsDialog(Views.ADD_SALES,"ScrollPane");
    }

    @FXML
    private void btnGRNActionPerformed(ActionEvent event) {
        new JFXManager().loadAsDialog(Views.ADD_GRN,"ScrollPane");
    }

    @FXML
    private void btnReportsActionPerformed(ActionEvent event) {
        new JFXManager().loadAsDialog(Views.REPORT_MGMT);
    }

}
