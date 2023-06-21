/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.InputManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.CompanyTbl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class SearchCompanyDialogController implements Initializable {

    CompanyTbl companyTblData;

    ComboBox<String> companyNameComboBox;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private TableView<CompanyTbl> table;
    @FXML
    private TableColumn<CompanyTbl, String> colLogoImg;
    @FXML
    private TableColumn<CompanyTbl, String> colCOMId;
    @FXML
    private TableColumn<CompanyTbl, String> colName;
    @FXML
    private TableColumn<CompanyTbl, String> colEmail;
    @FXML
    private TableColumn<CompanyTbl, String> colStatus;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnView;

    private void setCellValueFactory() {
        colLogoImg.setCellValueFactory(new PropertyValueFactory<>("img"));
        colCOMId.setCellValueFactory(new PropertyValueFactory<>("comId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private String searchCompany() {
        String query = "SELECT * FROM `company` INNER JOIN `city` ON `company`.`city_id` = `city`.`id` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id`";

        String search_txt = txtSearch.getText();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (search_txt != null) {
            if (!search_txt.isBlank()) {
                if (isSetWhereClause) {
                    query = query + " AND (`company`.`company_id` LIKE '%" + search_txt + "%' OR `company`.`name` LIKE '%" + search_txt + "%' OR `company`.`email` LIKE '%" + search_txt + "%' OR `company`.`contact_no` LIKE '%" + search_txt + "%' OR `company`.`contact_no_2` LIKE '%" + search_txt + "%') ";
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE (`company`.`company_id` LIKE '%" + search_txt + "%' OR `company`.`name` LIKE '%" + search_txt + "%' OR `company`.`email` LIKE '%" + search_txt + "%' OR `company`.`contact_no` LIKE '%" + search_txt + "%' OR `company`.`contact_no_2` LIKE '%" + search_txt + "%')";
                }
            }
        }

        if (province != null) {
            if (!province.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `province`.`name` = '" + province.replace(" Province", "") + "'";

                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `province`.`name` = '" + province.replace(" Province", "") + "'";
                }
            }
        }

        if (district != null) {
            if (!district.equalsIgnoreCase(
                    "select")) {
                if (isSetWhereClause) {
                    query = query + " AND `district`.`name` = '" + district + "'";
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `district`.`name` ='" + district + "'";
                }
            }
        }

        query = query + " ORDER BY `company`.`company_id`, `company`.`name` ASC";
        
        setTableBtnActive(false);

        return query;
    }

    private void setTableBtnActive(boolean status) {
        if (status) {
            btnView.setDisable(false);
        } else {
            btnView.setDisable(true);
        }
    }

    private void initializingItemStateChangeListeners() {
        cmbProvince.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                String selected_district = cmbDistrict.getSelectionModel().getSelectedItem();

                if (cmbProvince.getSelectionModel().getSelectedIndex() == 0) {
                    InputManager.loadComboBox(cmbDistrict, DBTable.DISTRICT);
                } else {
                    InputManager.loadDistrict(cmbDistrict, cmbProvince);
                }

                cmbDistrict.getSelectionModel().select(selected_district);

                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });

        cmbDistrict.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });
    }

    private void loadComboBoxes() {
        InputManager.loadProvince(cmbProvince);
        InputManager.loadDistrict(cmbDistrict, cmbProvince);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        initializingItemStateChangeListeners();
        loadComboBoxes();
    }

    public void init(ComboBox<String> companyNameComboBox) {
        this.companyNameComboBox = companyNameComboBox;

        String company_selected = companyNameComboBox.getSelectionModel().getSelectedItem();

        if (company_selected != null) {
            if (!company_selected.equalsIgnoreCase("select")) {
                txtSearch.setText(company_selected);
            }
        }
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        companyTblData = table.getSelectionModel().getSelectedItem();

        if (companyTblData.getComId()!= null) {
            setTableBtnActive(true);
            
            if (evt.getClickCount() == 2) {
                companyNameComboBox.getSelectionModel().select(companyTblData.getName());

                final Stage stage = (Stage) table.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void btnAddActionPerformed(ActionEvent event) {
    }

    @FXML
    private void btnViewActionPerformed(ActionEvent event) {
        FXMLLoader viewCompanyInfoFxmlLoader = new JFXManager().openDialog(Views.VIEW_COMPANY_INFO_DIALOG);
        
        //get controller associate with view
        ViewCompanyInfoDialogController viewCompanyInfoController = viewCompanyInfoFxmlLoader.getController();

        viewCompanyInfoController.initData(companyTblData.getComId(), table);
    }

}
