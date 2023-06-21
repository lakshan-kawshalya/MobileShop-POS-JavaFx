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

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ListCompanyController implements Initializable {
    
    CompanyTbl companyTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private ComboBox<String> cmbCity;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private Button btnView;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnChangeStatus;
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
    
    private void setCellValueFactory() {
        colLogoImg.setCellValueFactory(new PropertyValueFactory<>("img"));
        colCOMId.setCellValueFactory(new PropertyValueFactory<>("comId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void setTableBtnActive(boolean status) {
        if (status) {
            btnView.setDisable(false);
            btnUpdate.setDisable(false);
            btnChangeStatus.setDisable(false);
        } else {
            btnView.setDisable(true);
            btnUpdate.setDisable(true);
            btnChangeStatus.setDisable(true);
        }
    }
    
    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbStatus, DBTable.STATUS);
        InputManager.loadProvince(cmbProvince);
        InputManager.loadComboBox(cmbDistrict, DBTable.DISTRICT);
        InputManager.loadComboBox(cmbCity, DBTable.CITY);
    }
    
    public String searchCompany() {
        String query = "SELECT * FROM `company` INNER JOIN `city` ON `company`.`city_id` = `city`.`id` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id`  ";

        String searchTxt = txtSearch.getText();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();
        String city = cmbCity.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`company_id` LIKE '%" + searchTxt + "%' OR `company`.`name` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`company_id` LIKE '%" + searchTxt + "%' OR `company`.`name` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            }
        }

        if (status != null) {
            if (!status.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `status_id` = " + DBManager.getId(status, DBTable.STATUS);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `status_id` = " + DBManager.getId(status, DBTable.STATUS);
                }
            }
        }

        if (province != null) {
            if (!province.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `province`.`id` = " + DBManager.getId(province, DBTable.PROVINCE);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `province`.`id` = " + DBManager.getId(province, DBTable.PROVINCE);
                }
            }
        }

        if (district != null) {
            if (!district.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `district`.`name` = '" + district + "'";
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `district`.`name` = '" + district + "'";
                }
            }
        }

        if (city != null) {
            if (!city.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `city`.`name` = '" + city + "'";
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `city`.`name` = '" + city + "'";
                }
            }
        }

        query = query + " ORDER BY `company`.`id`";

        setTableBtnActive(false);

        return query;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        initializingItemStateChangeListeners();
        new TableManager().loadCompanyTable(table, DBManager.getTableData(DBTable.COMPANY));
        loadComboBoxes();
    }    
    
    private void initializingItemStateChangeListeners() {
        cmbProvince.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                String selected_district = cmbDistrict.getSelectionModel().getSelectedItem();
                String selected_city = cmbCity.getSelectionModel().getSelectedItem();

                if (cmbProvince.getSelectionModel().getSelectedIndex() == 0) {
                    InputManager.loadComboBox(cmbDistrict, DBTable.DISTRICT);
                } else {
                    InputManager.loadDistrict(cmbDistrict, cmbProvince);
                }

                cmbDistrict.getSelectionModel().select(selected_district);

                if (cmbDistrict.getSelectionModel().getSelectedIndex() == 0 && cmbProvince.getSelectionModel().getSelectedIndex() == 0) {
                    InputManager.loadComboBox(cmbCity, DBTable.CITY);
                } else if (cmbDistrict.getSelectionModel().getSelectedIndex() == 0 && cmbProvince.getSelectionModel().getSelectedIndex() != 0) {
                    InputManager.loadCity(cmbCity, cmbProvince, false);
                } else if (cmbDistrict.getSelectionModel().getSelectedIndex() != 0) {
                    InputManager.loadCity(cmbCity, cmbDistrict);
                }

                cmbCity.getSelectionModel().select(selected_city);

                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });

        cmbDistrict.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                String selected_city = cmbCity.getSelectionModel().getSelectedItem();

                if (cmbDistrict.getSelectionModel().getSelectedIndex() == 0 && cmbProvince.getSelectionModel().getSelectedIndex() == 0) {
                    InputManager.loadComboBox(cmbCity, DBTable.CITY);
                } else if (cmbDistrict.getSelectionModel().getSelectedIndex() == 0 && cmbProvince.getSelectionModel().getSelectedIndex() != 0) {
                    InputManager.loadCity(cmbCity, cmbProvince, false);
                } else if (cmbDistrict.getSelectionModel().getSelectedIndex() != 0) {
                    InputManager.loadCity(cmbCity, cmbDistrict);
                }

                cmbCity.getSelectionModel().select(selected_city);

                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });

        cmbCity.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });

        cmbStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
            }

        });
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
    }

    @FXML
    private void btnViewActionPerformed(ActionEvent event) {
        FXMLLoader viewCompanyInfoFxmlLoader = new JFXManager().openDialog(Views.VIEW_COMPANY_INFO_DIALOG);
        
        //get controller associate with view
        ViewCompanyInfoDialogController viewCompanyInfoController = viewCompanyInfoFxmlLoader.getController();

        viewCompanyInfoController.initData(companyTblData.getComId(), table);
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        FXMLLoader updateCompanyFxmlLoader = new JFXManager().openDialog(Views.UPDATE_COMPANY_DIALOG);
        
        //get controller associate with view
        UpdateCompanyDialogController updateCompanyController = updateCompanyFxmlLoader.getController();

        updateCompanyController.initData(companyTblData.getComId(), table);
    }

    @FXML
    private void btnChangeStatusActionPerformed(ActionEvent event) {
        String com_id = companyTblData.getComId();
        String currentStatus = companyTblData.getStatus();

        String newStatus = null;
        if (currentStatus.equalsIgnoreCase("active")) {
            newStatus = "Deactive";
        } else {
            newStatus = "Active";
        }

        DBManager.changeStatus(newStatus, DBTable.COMPANY, com_id);

        new TableManager().loadCompanyTable(table, DBManager.getDBData(searchCompany()));
    }

    @FXML
    private void tableMouseClicked(MouseEvent event) {
        companyTblData = table.getSelectionModel().getSelectedItem();

        if (companyTblData.getComId()!= null) {
            setTableBtnActive(true);
        }
    }
    
}
