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
import com.cyberx.model.tables.EmployeeTbl;
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
public class ListEmployeeController implements Initializable {

    EmployeeTbl employeeTblData;

    @FXML
    private Button btnView;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnChangeStatus;
    @FXML
    private TableView<EmployeeTbl> table;
    @FXML
    private TableColumn<EmployeeTbl, String> colProofileImg;
    @FXML
    private TableColumn<EmployeeTbl, String> colEMPId;
    @FXML
    private TableColumn<EmployeeTbl, String> colName;
    @FXML
    private TableColumn<EmployeeTbl, String> colEmail;
    @FXML
    private TableColumn<EmployeeTbl, String> colUserRole;
    @FXML
    private TableColumn<EmployeeTbl, String> colStatus;
    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbUserRole;
    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private ComboBox<String> cmbCity;
    @FXML
    private ComboBox<String> cmbMartialStatus;
    @FXML
    private ComboBox<String> cmbStatus;

    private void setCellValueFactory() {
        colProofileImg.setCellValueFactory(new PropertyValueFactory<>("img"));
        colEMPId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
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
        InputManager.loadComboBox(cmbUserRole, DBTable.USER_ROLE);
        InputManager.loadComboBox(cmbStatus, DBTable.STATUS);
        InputManager.loadProvince(cmbProvince);
        InputManager.loadComboBox(cmbDistrict, DBTable.DISTRICT);
        InputManager.loadComboBox(cmbCity, DBTable.CITY);
        InputManager.loadComboBox(cmbMartialStatus, DBTable.MARTIAL_STATUS);
    }

    public String searchEmployee() {
        String query = "SELECT * FROM `employee` INNER JOIN `city` ON `employee`.`city_id` = `city`.`id` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id`  ";

        String searchTxt = txtSearch.getText();
        String userRole = cmbUserRole.getSelectionModel().getSelectedItem();
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();
        String city = cmbCity.getSelectionModel().getSelectedItem();
        String martial_status = cmbMartialStatus.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`employee_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`employee_id` LIKE '%" + searchTxt + "%' OR `fname` LIKE '%" + searchTxt + "%' OR `lname` LIKE '%" + searchTxt + "%' OR `email` LIKE '%" + searchTxt + "%' OR `contact_no` LIKE '%" + searchTxt + "%' OR `contact_no_2` LIKE '%" + searchTxt + "%')";
            }
        }

        if (userRole != null) {
            if (!userRole.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `user_role_id` = " + DBManager.getId(userRole, DBTable.USER_ROLE);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `user_role_id` = " + DBManager.getId(userRole, DBTable.USER_ROLE);
                }
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

        if (martial_status != null) {
            if (!martial_status.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `martial_status_id` = " + DBManager.getId(martial_status, DBTable.MARTIAL_STATUS);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `martial_status_id` = " + DBManager.getId(martial_status, DBTable.MARTIAL_STATUS);
                }
            }
        }

        query = query + " ORDER BY `employee`.`id`";

        setTableBtnActive(false);

        return query;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        initializingItemStateChangeListeners();
        new TableManager().loadEmployeeTable(table, DBManager.getTableData(DBTable.EMPLOYEE));
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

                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
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

                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
            }

        });

        cmbCity.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
            }

        });

        cmbMartialStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
            }

        });

        cmbUserRole.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
            }

        });

        cmbStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
            }

        });
    }

    @FXML
    private void tableMouseClicked(MouseEvent event) {
        employeeTblData = table.getSelectionModel().getSelectedItem();

        if (employeeTblData.getEmpId() != null) {
            setTableBtnActive(true);
        }
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
    }

    @FXML
    private void btnViewActionPerformed(ActionEvent event) {
        FXMLLoader viewEmployeeInfoFxmlLoader = new JFXManager().openDialog(Views.VIEW_EMPLOYEE_INFO_DIALOG);
        
        //get controller associate with view
        ViewEmployeeInfoDialogController viewEmployeeInfoController = viewEmployeeInfoFxmlLoader.getController();

        viewEmployeeInfoController.initData(employeeTblData.getEmpId(), table);
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        FXMLLoader updateEmployeeFxmlLoader = new JFXManager().openDialog(Views.UPDATE_EMPLOYEE_DIALOG);
        
        //get controller associate with view
        UpdateEmployeeDialogController updateEmployeeController = updateEmployeeFxmlLoader.getController();

        updateEmployeeController.initData(employeeTblData.getEmpId(), table);

    }

    @FXML
    private void btnChangeStatusActionPerformed(ActionEvent event) {
        String emp_id = employeeTblData.getEmpId();
        String currentStatus = employeeTblData.getStatus();

        String newStatus = null;
        if (currentStatus.equalsIgnoreCase("active")) {
            newStatus = "Deactive";
        } else {
            newStatus = "Active";
        }

        DBManager.changeStatus(newStatus, DBTable.EMPLOYEE, emp_id);

        new TableManager().loadEmployeeTable(table, DBManager.getDBData(searchEmployee()));
    }

}
