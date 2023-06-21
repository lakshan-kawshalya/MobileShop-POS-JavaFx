/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.InputManager;
import com.cyberx.model.MySQL;
import com.cyberx.model.TableManager;
import com.cyberx.model.tables.CityTable;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class CityDialogController implements Initializable {

    CityTable cityTblData;

    ComboBox<String> provinceComboBox;
    ComboBox<String> districtComboBox;
    ComboBox<String> cityComboBox;

    @FXML
    private ComboBox<String> cmbDistrict;
    @FXML
    private ComboBox<String> cmbProvince;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<CityTable> table;
    @FXML
    private TableColumn<CityTable, String> colProvince;
    @FXML
    private TableColumn<CityTable, String> colDistrict;
    @FXML
    private TableColumn<CityTable, String> colCity;
    @FXML
    private Label lblNewCity;
    @FXML
    private Button btnAddCity;

    private void setCellValueFactory() {
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colDistrict.setCellValueFactory(new PropertyValueFactory<>("district"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
    }

    private String searchCity() {
        String query = "SELECT * FROM `city` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id`";

        String city_txt = txtSearch.getText();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();

        boolean isSetWhereClause = false;

        if (city_txt != null) {
            if (!city_txt.isBlank()) {
                if (isSetWhereClause) {
                    query = query + " AND `city`.`name` LIKE '%" + city_txt + "%'";
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `city`.`name` LIKE '%" + city_txt + "%'";
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
                    query = query + "WHERE `district`.`name` ='" + district + "'";
                }
            }
        }

        query = query + " ORDER BY `province`.`name`, `district`.`name`, `city`.`name` ASC";

        return query;
    }

    private void addCity() {
        String cityName = txtSearch.getText();
        String province = cmbProvince.getSelectionModel().getSelectedItem();
        String district = cmbDistrict.getSelectionModel().getSelectedItem();

        if (!cityName.isBlank() && !province.equalsIgnoreCase("select") && !district.equalsIgnoreCase("select")) {
            String query = "SELECT * FROM `city` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id` WHERE `city`.`name` = '" + cityName + "' AND `province`.`id` = " + DBManager.getId(province, DBTable.PROVINCE) + " AND `district`.`name` = '" + district + "'";

            try {
                ResultSet rs = DBManager.getDBData(query);
                if (!rs.next()) {
                    lblNewCity.setText(province + ", " + district + ",\n" + cityName + ".");
                    btnAddCity.setDisable(false);
                } else {
                    lblNewCity.setText("");
                    btnAddCity.setDisable(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CityDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            lblNewCity.setText("");
            btnAddCity.setDisable(true);
        }
    }

    private void initializingItemStateChangeListeners() {
        cmbProvince.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadCityTable(table, DBManager.getDBData(searchCity()));

                addCity();
            }

        });

        cmbDistrict.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadCityTable(table, DBManager.getDBData(searchCity()));

                addCity();
            }

        });
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
    }

    public void init(ComboBox<String> cityComboBox, ComboBox<String> provinceComboBox, ComboBox<String> districtComboBox) {
        this.provinceComboBox = provinceComboBox;
        this.districtComboBox = districtComboBox;
        this.cityComboBox = cityComboBox;

        String province_selected = provinceComboBox.getSelectionModel().getSelectedItem();
        String district_selected = districtComboBox.getSelectionModel().getSelectedItem();
        String city_selected = cityComboBox.getSelectionModel().getSelectedItem();

        InputManager.loadProvince(cmbProvince);
        cmbProvince.getSelectionModel().select(province_selected);

        InputManager.loadDistrict(cmbDistrict, cmbProvince);
        cmbDistrict.getSelectionModel().select(district_selected);

        if (city_selected != null) {
            if (!city_selected.equalsIgnoreCase("select")) {
                txtSearch.setText(city_selected);
            }
        }

        new TableManager().loadCityTable(table, DBManager.getDBData(searchCity()));
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadCityTable(table, DBManager.getDBData(searchCity()));

        addCity();
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        cityTblData = table.getSelectionModel().getSelectedItem();

        if (cityTblData.getProvince() != null) {
            if (evt.getClickCount() == 2) {
                provinceComboBox.getSelectionModel().select(cityTblData.getProvince());
                districtComboBox.getSelectionModel().select(cityTblData.getDistrict());
                cityComboBox.getSelectionModel().select(cityTblData.getCity());

                final Stage stage = (Stage) table.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void btnAddCityActionPerformed(ActionEvent event) {
        //insert city name into the database        
        MySQL.iud("INSERT INTO `city` (`name`, `district_id`) VALUES ('" + txtSearch.getText() + "', " + DBManager.getId(cmbDistrict.getSelectionModel().getSelectedItem(), DBTable.DISTRICT) + ")");

        new TableManager().loadCityTable(table, DBManager.getDBData(searchCity()));

        addCity();
    }

}
