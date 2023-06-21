/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.InputManager;
import com.cyberx.model.TableManager;
import com.cyberx.model.tables.StockTbl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class ListStockController implements Initializable {

    StockTbl stockTblData;

    @FXML
    private TextField txtSearch;
    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ComboBox<String> cmbSubCategory;
    @FXML
    private TextField txtQtyFrom;
    @FXML
    private TextField txtSellingPriceFrom;
    @FXML
    private TextField txtQtyTo;
    @FXML
    private TextField txtSellingPriceTo;
    @FXML
    private TableView<StockTbl> table;
    @FXML
    private TableColumn<StockTbl, String> colNo;
    @FXML
    private TableColumn<StockTbl, String> colProductId;
    @FXML
    private TableColumn<StockTbl, String> colProductName;
    @FXML
    private TableColumn<StockTbl, String> colCategory;
    @FXML
    private TableColumn<StockTbl, String> colQty;
    @FXML
    private TableColumn<StockTbl, String> colSellingPrice;

    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
    }

    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbCategory, DBTable.CATEGORY);
        InputManager.loadSubCategory(cmbSubCategory, cmbCategory);
    }

    public String searchStock() {
        String query = "SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`product_id` = `product`.`id`  ";

        String searchTxt = txtSearch.getText();
        String category = cmbCategory.getSelectionModel().getSelectedItem();
        String subCategory = cmbSubCategory.getSelectionModel().getSelectedItem();
        String qtyFrom = txtQtyFrom.getText();
        String qtyTo = txtQtyTo.getText();
        String sellingPriceFrom = txtSellingPriceFrom.getText();
        String sellingPriceTo = txtSellingPriceTo.getText();

        boolean isSetWhereClause = false;

        if (!searchTxt.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`product`.`product_id` LIKE '%" + searchTxt + "%' OR `product`.`name` LIKE '%" + searchTxt + "%')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`product`.`product_id` LIKE '%" + searchTxt + "%' OR `product`.`name` LIKE '%" + searchTxt + "%')";
            }
        }

        if (category != null) {
            if (!category.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `product`.`category_id` = " + DBManager.getId(category, DBTable.CATEGORY);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `product`.`category_id` = " + DBManager.getId(category, DBTable.CATEGORY);
                }
            }
        }

        if (subCategory != null) {
            if (!subCategory.equalsIgnoreCase("select")) {
                if (isSetWhereClause) {
                    query = query + " AND `product`.`sub_category_id` = " + DBManager.getId(subCategory, DBTable.SUB_CATEGORY);
                } else {
                    isSetWhereClause = true;
                    query = query + "WHERE `product`.`sub_category_id` = " + DBManager.getId(subCategory, DBTable.SUB_CATEGORY);
                }
            }
        }

        if (!qtyFrom.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`stock`.`quantity` >= '" + qtyFrom + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`stock`.`quantity` >= '" + qtyFrom + "')";
            }
        }

        if (!qtyTo.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`stock`.`quantity` <= '" + qtyTo + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`stock`.`quantity` <= '" + qtyTo + "')";
            }
        }

        if (!sellingPriceFrom.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`stock`.`selling_price` >= '" + sellingPriceFrom + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`stock`.`selling_price` >= '" + sellingPriceFrom + "')";
            }
        }

        if (!sellingPriceTo.isBlank()) {
            if (isSetWhereClause) {
                query = query + " AND (`stock`.`selling_price` <= '" + sellingPriceTo + "')";
            } else {
                isSetWhereClause = true;
                query = query + "WHERE (`stock`.`selling_price` <= '" + sellingPriceTo + "')";
            }
        }

        query = query + " ORDER BY `stock`.`id`";

        return query;
    }

    private void initializingItemStateChangeListeners() {
        cmbCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.loadSubCategory(cmbSubCategory, cmbCategory);
                new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
            }

        });

        cmbSubCategory.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
            }

        });

        txtQtyTo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidQtyKey(evt);
            }

        });

        txtQtyFrom.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidQtyKey(evt);
            }

        });

        txtSellingPriceFrom.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidPriceKey(evt);
            }

        });

        txtSellingPriceTo.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidPriceKey(evt);
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
        new TableManager().loadStockTable(table, DBManager.getDBData("SELECT * FROM `stock` INNER JOIN `product` ON `stock`.`product_id` = `product`.`id`"));
        loadComboBoxes();
    }

    @FXML
    private void txtSearchKeyReleased(KeyEvent event) {
        new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
    }

    @FXML
    private void txtQtyFromKeyReleased(KeyEvent event) {
        new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
    }

    @FXML
    private void txtSellingPriceFromKeyReleased(KeyEvent event) {
        new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
    }

    @FXML
    private void txtQtyToKeyReleased(KeyEvent event) {
        new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
    }

    @FXML
    private void txtSellingPriceToKeyReleased(KeyEvent event) {
        new TableManager().loadStockTable(table, DBManager.getDBData(searchStock()));
    }

    @FXML
    private void tableMouseClicked(MouseEvent evt) {
        if (stockTblData != null) {
            stockTblData = table.getSelectionModel().getSelectedItem();

            if (stockTblData.getProductId() != null) {
                if (evt.getClickCount() == 2) {
                    //view stock info
                }
            }
        }
    }

}
