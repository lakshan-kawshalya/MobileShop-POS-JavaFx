/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.InputManager;
import com.cyberx.model.ReportManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ReportSalesController implements Initializable {

    @FXML
    private DatePicker dchDay;
    @FXML
    private DatePicker dchDateFrom;
    @FXML
    private DatePicker dchDateTo;
    @FXML
    private Button btnGenerateDayReport;
    @FXML
    private Button btnGenerateQueryReport;

    private void initializingItemStateChangeListeners() {
        dchDay.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidDateKey(evt);
            }

        });

        dchDateFrom.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidDateKey(evt);
            }

        });

        dchDateTo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidDateKey(evt);
            }

        });
    }

    private boolean verifyQueryReportInputs() {
        boolean isValid = true;

        if (dchDateFrom.getValue() != null) {
            InputManager.isValidDate(dchDateFrom);
        }

        if (dchDateTo.getValue() != null) {
            InputManager.isValidDate(dchDateTo);
        }

        if (dchDateTo.getValue() != null && dchDateFrom.getValue() != null) {
            if (!(InputManager.isValidDate(dchDateFrom) && InputManager.isValidDate(dchDateTo))) {
                isValid = false;
            }
        }

        return isValid;
    }

    private boolean verifyDayReportInputs() {
        boolean isValid = true;
        if (dchDay.getValue() != null) {
            if (!(InputManager.isValidDate(dchDay))) {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializingItemStateChangeListeners();
    }


    @FXML
    private void btnGenerateDayReportActionPerformed(ActionEvent event) {
        if (verifyDayReportInputs()) {
            if (dchDay.getValue() != null) {
                new ReportManager().generateDaySaleReport(dchDay.getValue().toString());
            }
        }
    }

    @FXML
    private void dchDayKeyReleased(KeyEvent event) {
    }

    @FXML
    private void dchDateFromKeyReleased(KeyEvent event) {
    }

    @FXML
    private void dchDateToKeyReleased(KeyEvent event) {
    }

    @FXML
    private void btnGenerateQueryReportActionPerformed(ActionEvent event) {
    }


}
