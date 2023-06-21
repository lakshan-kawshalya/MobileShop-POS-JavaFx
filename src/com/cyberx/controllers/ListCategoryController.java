/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.Views;
import com.cyberx.model.tables.CategoryTbl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class ListCategoryController implements Initializable {

    CategoryTbl categoryTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<CategoryTbl> table;
    @FXML
    private TableColumn<CategoryTbl, String> colCATId;
    @FXML
    private TableColumn<CategoryTbl, String> colCategory;
    @FXML
    private TableColumn<CategoryTbl, String> colSubCategory;

    private void setCellValueFactory() {
        colCATId.setCellValueFactory(new PropertyValueFactory<>("catId"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
    }

    private void setTableBtnActive(boolean status) {
        if (status) {
            btnUpdate.setDisable(false);
        } else {
            btnUpdate.setDisable(true);
        }
    }

    public String searchCategory() {
        String query = "SELECT * FROM `category` INNER JOIN `sub_category` ON `sub_category`.`category_id` = `category`.`id` ";

        String searchTxt = txtSearch.getText();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`category.category_id` LIKE '%" + searchTxt + "%' OR `category.name` LIKE '%" + searchTxt + "%' OR `sub_category.name` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`category.category_id` LIKE '%" + searchTxt + "%' OR `category.name` LIKE '%" + searchTxt + "%' OR `sub_category.name` LIKE '%" + searchTxt + "%')";
            }
        }

        query = query + " ORDER BY `category`.`id`";

        setTableBtnActive(false);

        return query;
    }

    private void updateCategoryInfo() {
        int selected_row = table.getSelectionModel().getSelectedIndex();

        categoryTblData = table.getSelectionModel().getSelectedItem();

        String cat_id = categoryTblData.getCatId();

        if (cat_id.equals("")) {
            int row = selected_row;
            while (cat_id.isBlank()) {
                row = row - 1;
                categoryTblData = table.getItems().get(row);
                cat_id = categoryTblData.getCatId();
            }
        }

        if (table.getFocusModel().getFocusedCell().getColumn() == 2) {
//            FXMLLoader updateCategoryFxmlLoader = new JFXManager().openDialog(Views.UPDATE_EMPLOYEE_DIALOG);
//
//            //get controller associate with view
//            UpdateCategoryController updateCategoryController = updateCategoryFxmlLoader.getController();
//
//            updateCategoryController.initData(cat_id, table);
        } else {
            FXMLLoader updateCategoryFxmlLoader = new JFXManager().openDialog(Views.UPDATE_CATEGORY);

            //get controller associate with view
            UpdateCategoryController updateCategoryController = updateCategoryFxmlLoader.getController();

            updateCategoryController.initData(cat_id, table);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactory();
        new TableManager().loadCategoryTable(table, DBManager.getDBData(searchCategory()));
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadCategoryTable(table, DBManager.getDBData(searchCategory()));
    }

    @FXML
    private void btnUpdateActionPerformed(ActionEvent event) {
        updateCategoryInfo();
    }

    @FXML
    private void tableMouseClicked(MouseEvent event) {
        categoryTblData = table.getSelectionModel().getSelectedItem();

        if (categoryTblData.getCatId() != null) {
            setTableBtnActive(true);
        }
    }

}
