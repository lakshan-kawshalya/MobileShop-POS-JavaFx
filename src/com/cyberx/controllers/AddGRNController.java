/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import static com.cyberx.main.Main.loggedUserId;
import com.cyberx.model.DBManager;
import java.sql.ResultSet;
import com.cyberx.model.DBTable;
import com.cyberx.model.IDManager;
import com.cyberx.model.InputManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.MySQL;
import com.cyberx.model.Views;
import com.cyberx.model.tables.GRNListTbl;
import com.cyberx.model.tables.PaymentListTbl;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author LAKSHAN KAWSHALYA
 */
public class AddGRNController implements Initializable {

    String GrnId;
    int AbsoluteGrnId;

    GRNListTbl productTblData;
    PaymentListTbl paymentTblData;

    @FXML
    private Button btnSearchSupplier;
    @FXML
    private Label lblCompanyName;
    @FXML
    private TextField txtIMEI;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private TextField txtQty;
    @FXML
    private TextField txtBuyingPrice;
    @FXML
    private TextField txtSellingPrice;
    @FXML
    private Button btnAddToList;
    @FXML
    private Button btnResetProduct;
    @FXML
    private TableView<GRNListTbl> productTable;
    @FXML
    private TableColumn<GRNListTbl, String> colNo;
    @FXML
    private TableColumn<GRNListTbl, String> colProductName;
    @FXML
    private TableColumn<GRNListTbl, String> colCategory;
    @FXML
    private TableColumn<GRNListTbl, String> colQuantity;
    @FXML
    private TableColumn<GRNListTbl, String> colBuyingPrice;
    @FXML
    private TableColumn<GRNListTbl, String> colSellingPrice;
    @FXML
    private TableColumn<GRNListTbl, String> colTotal;
    @FXML
    private TableColumn<GRNListTbl, String> colProductId;
    @FXML
    private TableColumn<GRNListTbl, String> colColor;
    @FXML
    private TableColumn<GRNListTbl, String> colIMEI;
    @FXML
    private TextField txtPayment;
    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox<String> cmbPaymentMethod;
    @FXML
    private Button btnAddToStock;
    @FXML
    private Button btnResetPayment;
    @FXML
    private Button btnRemoveFromList;
    @FXML
    private GridPane pnlProductInfo;
    @FXML
    private GridPane pnlPaymentInfo;
    @FXML
    private Label lblSupplierName;
    @FXML
    private Label lblSupplierId;
    @FXML
    private Label lblProductName;
    @FXML
    private Label lblCategoryName;
    @FXML
    private TextField txtColor;
    @FXML
    private Label lblProductId;
    @FXML
    private Button btnAddToPaymentList;
    @FXML
    private TableView<PaymentListTbl> paymentTable;
    @FXML
    private TableColumn<PaymentListTbl, String> colPaymentNo;
    @FXML
    private TableColumn<PaymentListTbl, String> colPaymentMethod;
    @FXML
    private TableColumn<PaymentListTbl, String> colPaymentAmount;
    @FXML
    private Button btnRemoveFromPaymentList;
    @FXML
    private GridPane pnlPaymentInfo1;
    @FXML
    private Label lblPaymentTotal;
    @FXML
    private Label lblBalance;
    @FXML
    private Label lblCompanyId;

    private void initializingItemStateChangeListeners() {
        cmbPaymentMethod.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbPaymentMethod);
            }

        });

        lblSupplierId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                btnSearchProduct.setDisable(false);
            }

        });

        lblProductName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                txtQty.setDisable(false);
                txtBuyingPrice.setDisable(false);
                txtSellingPrice.setDisable(false);
                txtColor.setDisable(false);
                btnResetProduct.setDisable(false);
                btnAddToList.setDisable(false);
            }

        });

        productTable.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(ListChangeListener.Change<?> c) {
                updateTotal();
            }
        });

        txtQty.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidQtyKey(evt);
            }

        });

        txtBuyingPrice.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidPriceKey(evt);
            }

        });

        txtSellingPrice.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidPriceKey(evt);
            }

        });

        lblTotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                if (Double.parseDouble(lblTotal.getText()) > 0) {
                    pnlPaymentInfo.setDisable(false);
                    pnlPaymentInfo1.setDisable(false);
                    btnResetPayment.setDisable(false);
                    btnAddToPaymentList.setDisable(false);
                } else {
                    pnlPaymentInfo.setDisable(true);
                    pnlPaymentInfo1.setDisable(true);
                    btnResetPayment.setDisable(true);
                    btnAddToPaymentList.setDisable(true);
                    resetPaymentFields();
                }
            }

        });

        txtPayment.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidPriceKey(evt);
            }

        });

        paymentTable.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(ListChangeListener.Change<?> c) {
                updateTotalPayAmount();
            }
        });

        lblPaymentTotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                if (Double.parseDouble(lblPaymentTotal.getText()) > 0) {
                    btnAddToStock.setDisable(false);
                } else {
                    btnAddToStock.setDisable(true);
                }
            }

        });
    }

    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbPaymentMethod, DBTable.PAYMENT_METHOD);
    }

    //supplier
    private void resetSupplier() {
        lblSupplierName.setText("");
        lblSupplierId.setText("");
        lblCompanyId.setText("");
        lblCompanyName.setText("");
    }

    //product
    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colIMEI.setCellValueFactory(new PropertyValueFactory<>("imeiNumber"));
        colBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private HashMap<String, String> getProductInputValues() {
        String productId = lblProductId.getText();
        String productName = lblProductName.getText();
        String category = lblCategoryName.getText();
        String imei = txtIMEI.getText();
        String qty = txtQty.getText();
        String color = txtColor.getText();
        String buyingPrice = txtBuyingPrice.getText();
        String sellingPrice = txtSellingPrice.getText();

        HashMap inputs = new HashMap();
        inputs.put("productId", productId);
        inputs.put("productName", productName);
        inputs.put("category", category);
        inputs.put("imei", imei);
        inputs.put("qty", qty);
        inputs.put("color", color);
        inputs.put("buyingPrice", buyingPrice);
        inputs.put("sellingPrice", sellingPrice);

        return inputs;
    }

    private void resetProductFields() {
        lblProductId.setText("");
        lblProductName.setText("");
        lblCategoryName.setText("");

        txtIMEI.setText("");
        txtIMEI.getStyleClass().remove("invalid-input");

        txtQty.setText("");
        txtQty.getStyleClass().remove("invalid-input");

        txtColor.setText("");
        txtColor.getStyleClass().remove("invalid-input");

        txtBuyingPrice.setText("");
        txtBuyingPrice.getStyleClass().remove("invalid-input");

        txtSellingPrice.setText("");
        txtSellingPrice.getStyleClass().remove("invalid-input");
    }

    private boolean verifyProductInputs() {
        boolean isValid = true;

        InputManager.isValidQuantity(txtQty);
        InputManager.isValidTextField(txtColor);
        InputManager.isValidPrice(txtBuyingPrice);
        InputManager.isValidPrice(txtSellingPrice);

        if (!txtIMEI.isDisabled()) {
            if (!InputManager.isValidTextField(txtIMEI)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidQuantity(txtQty) && InputManager.isValidPrice(txtBuyingPrice) && InputManager.isValidPrice(txtSellingPrice) && InputManager.isValidTextField(txtColor))) {
            isValid = false;
        }

        return isValid;
    }

    private void updateTotal() {
        double total = 0;
        int count = 0;

        for (GRNListTbl tableRow : productTable.getItems()) {

            count++;
            tableRow.setNo(count);

            Double itemTotal = tableRow.getTotal();
            total = total + itemTotal;
        }

        lblTotal.setText(String.valueOf(total));
    }

    private void addToGRNList(HashMap<String, String> inputs) {
        ObservableList<GRNListTbl> observableList = FXCollections.observableArrayList();

        double itemTotal = Double.parseDouble(inputs.get("qty")) * Double.parseDouble(inputs.get("buyingPrice"));

        observableList.add(new GRNListTbl(1, inputs.get("productId"), inputs.get("productName"), inputs.get("category"), Integer.parseInt(inputs.get("qty")), inputs.get("color"), inputs.get("imei"), Double.parseDouble(inputs.get("buyingPrice")), Double.parseDouble(inputs.get("sellingPrice")), itemTotal));

        productTable.getItems().addAll(observableList);
    }

    private void updateGRNListProductQty(HashMap<String, String> inputs) {

        GRNListTbl tableRow = getRowIndexOfProductInGRNList(inputs);
        if (tableRow != null) {
            tableRow.setQuantity(tableRow.getQuantity() + Integer.parseInt(inputs.get("qty")));
            tableRow.setTotal(tableRow.getQuantity() * tableRow.getBuyingPrice());
            productTable.refresh();
            updateTotal();
        }
    }

    private boolean isProductInGRNList(HashMap<String, String> inputs) {

        for (GRNListTbl tableRow : productTable.getItems()) {

            String productId = tableRow.getProductId();
            String color = tableRow.getColor();
            String imei = tableRow.getImeiNumber();

            if (!imei.isBlank()) {
                if (imei.equalsIgnoreCase(inputs.get("imei"))) {
                    return true;
                }
            } else {
                if (productId.equalsIgnoreCase(inputs.get("productId")) && color.equalsIgnoreCase(inputs.get("color"))) {
                    return true;
                }
            }

        }

        return false;
    }

    private GRNListTbl getRowIndexOfProductInGRNList(HashMap<String, String> inputs) {

        for (GRNListTbl tableRow : productTable.getItems()) {

            String productId = tableRow.getProductId();
            String color = tableRow.getColor();

            if (productId.equalsIgnoreCase(inputs.get("productId")) && color.equalsIgnoreCase(inputs.get("color"))) {
                return tableRow;
            }
        }

        return null;
    }

    //payment
    private void setPaymentTblCellValueFactory() {
        colPaymentNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private HashMap<String, String> getPaymentInputValues() {
        String paymentMethod = cmbPaymentMethod.getSelectionModel().getSelectedItem();
        String amount = txtPayment.getText();

        HashMap inputs = new HashMap();
        inputs.put("paymentMethod", paymentMethod);
        inputs.put("amount", amount);

        return inputs;
    }

    private void resetPaymentFields() {

        cmbPaymentMethod.getSelectionModel().select(0);
        cmbPaymentMethod.getStyleClass().remove("invalid-input");

        txtPayment.setText("");
        txtPayment.getStyleClass().remove("invalid-input");
    }

    private boolean verifyPaymentInputs() {
        boolean isValid = true;

        InputManager.isValidComboBox(cmbPaymentMethod);
        InputManager.isValidTextField(txtPayment);

        if (!(InputManager.isValidTextField(txtPayment) && InputManager.isValidComboBox(cmbPaymentMethod))) {
            isValid = false;
        }

        return isValid;
    }

    private void updateTotalPayAmount() {
        double paidTotal = 0;
        int count = 0;

        for (PaymentListTbl tableRow : paymentTable.getItems()) {

            count++;
            tableRow.setNo(count);

            Double itemTotal = tableRow.getAmount();
            paidTotal = paidTotal + itemTotal;
        }

        lblPaymentTotal.setText(String.valueOf(paidTotal));

        double balance = paidTotal - Double.parseDouble(lblTotal.getText());

        lblBalance.setText(String.valueOf(balance));
    }

    private void addToPaymentList(HashMap<String, String> inputs) {
        ObservableList<PaymentListTbl> observableList = FXCollections.observableArrayList();

        observableList.add(new PaymentListTbl(1, inputs.get("paymentMethod"), Double.parseDouble(inputs.get("amount"))));

        paymentTable.getItems().addAll(observableList);
    }

    private void updatePaymentListAmount(HashMap<String, String> inputs) {

        PaymentListTbl tableRow = getRowIndexOfPaymentMethodInPaymentList(inputs);
        if (tableRow != null) {
            tableRow.setAmount(tableRow.getAmount() + Integer.parseInt(inputs.get("amount")));
            paymentTable.refresh();
            updateTotalPayAmount();
        }
    }

    private boolean isPaymentMethodInPaymentist(HashMap<String, String> inputs) {

        for (PaymentListTbl tableRow : paymentTable.getItems()) {

            String paymentMethod = tableRow.getPaymentMethod();

            if (paymentMethod.equalsIgnoreCase(inputs.get("paymentMethod"))) {
                return true;
            }
        }

        return false;
    }

    private PaymentListTbl getRowIndexOfPaymentMethodInPaymentList(HashMap<String, String> inputs) {

        for (PaymentListTbl tableRow : paymentTable.getItems()) {

            String paymentMethod = tableRow.getPaymentMethod();

            if (paymentMethod.equalsIgnoreCase(inputs.get("paymentMethod"))) {
                return tableRow;
            }
        }

        return null;
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
        setPaymentTblCellValueFactory();
        loadComboBoxes();
        initializingItemStateChangeListeners();
    }

    //supplier
    @FXML
    private void btnSearchSupplierActionPerformed(ActionEvent event) {

        FXMLLoader listSupplierFxmlLoader = new JFXManager().loadAsDialog(Views.LIST_SUPPLIER);

        //get controller associate with view
        ListSupplierController listSupplierController = listSupplierFxmlLoader.getController();

        listSupplierController.init(lblSupplierId, lblSupplierName, lblCompanyId, lblCompanyName);
    }

    //product
    @FXML
    private void btnbtnSearchProductActionPerformed(ActionEvent event) {
        FXMLLoader listProductFxmlLoader = new JFXManager().loadAsDialog(Views.LIST_PRODUCT);

        //get controller associate with view
        ListProductController listproductController = listProductFxmlLoader.getController();

        listproductController.init(lblProductId, lblProductName, lblCategoryName, txtIMEI, txtQty);
    }

    @FXML
    private void txtIMEIKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtIMEI);
    }

    @FXML
    private void txtQtyKeyReleased(KeyEvent evt) {
        InputManager.isValidQuantity(txtQty);
    }

    @FXML
    private void txtColorKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtColor);
    }

    @FXML
    private void txtBuyingPriceKeyReleased(KeyEvent event) {
        InputManager.isValidPrice(txtBuyingPrice);
    }

    @FXML
    private void txtSellingPriceKeyReleased(KeyEvent event) {
        InputManager.isValidPrice(txtSellingPrice);
    }

    @FXML
    private void btnAddToListActonPerformed(ActionEvent event) {
        if (verifyProductInputs()) {

            HashMap<String, String> inputs = getProductInputValues();

            if (isProductInGRNList(inputs)) {
                if (JFXManager.confirmationDialog(Alert.AlertType.NONE, "Product is already in list do you want to add this quantity to current quantity.")) {
                    updateGRNListProductQty(inputs);
                } else {
                    resetProductFields();
                }
            } else {
                addToGRNList(inputs);
            }

        }

    }

    @FXML
    private void btnResetProductActionPerformed(ActionEvent event) {
        resetProductFields();
    }

    @FXML
    private void btnRemoveFromListActonPerformed(ActionEvent event) {
        if (productTblData != null) {
            productTable.getItems().remove(productTable.getSelectionModel().getSelectedIndex());
            productTable.refresh();
        }
    }

    @FXML
    private void productTableMouseClicked(MouseEvent event) {

        if (productTable.getSelectionModel().getSelectedItem() != null) {
            productTblData = productTable.getSelectionModel().getSelectedItem();

            if (productTblData.getProductId() != null) {
                btnRemoveFromList.setDisable(false);
            }
        }
    }

    //payment
    @FXML
    private void txtPaymentKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtPayment);
    }

    @FXML
    private void btnResetPaymentActionPerformed(ActionEvent event) {
        resetPaymentFields();
    }

    @FXML
    private void btnRemoveFromPaymentListActonPerformed(ActionEvent event) {
        if (paymentTable != null) {
            paymentTable.getItems().remove(paymentTable.getSelectionModel().getSelectedIndex());
            paymentTable.refresh();
        }
    }

    @FXML
    private void paymentTableMouseClicked(MouseEvent event) {

        if (paymentTable.getSelectionModel().getSelectedItem() != null) {
            paymentTblData = paymentTable.getSelectionModel().getSelectedItem();

            if (paymentTblData.getPaymentMethod() != null) {
                btnRemoveFromPaymentList.setDisable(false);
            }
        }
    }

    @FXML
    private void btnAddToPaymentListActionPerformed(ActionEvent event) {
        if (verifyPaymentInputs()) {

            HashMap<String, String> inputs = getPaymentInputValues();

            if (isPaymentMethodInPaymentist(inputs)) {
                if (JFXManager.confirmationDialog(Alert.AlertType.NONE, "Payment method is already in list do you want to update the current amount.")) {
                    updatePaymentListAmount(inputs);
                } else {
                    resetPaymentFields();
                }
            } else {
                addToPaymentList(inputs);
            }

        }
    }

    @FXML
    private void btnAddToStockActionPerformed(ActionEvent event) {
        insertIntoGRNTable(lblSupplierId.getText());
    }

    //database insertion
    private void insertIntoGRNTable(String supplierId) {
        GrnId = IDManager.generateID(DBTable.GRN);
        System.out.println("INSERT INTO `grn` (`grn_id`, `employee_id`, `supplier_id`) VALUES ('" + GrnId + "', '" + loggedUserId + "', " + DBManager.getAbsoluteId(supplierId, DBTable.SUPPLIER) + ")");
        MySQL.iud("INSERT INTO `grn` (`grn_id`, `employee_id`, `supplier_id`) VALUES ('" + GrnId + "', " + loggedUserId + ", " + DBManager.getAbsoluteId(supplierId, DBTable.SUPPLIER) + ")");

        insertIntoGRNPaymentTable();
        insertOrUpdateStockTable();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("GRN added successfully.");
        alert.show();

        resetAll();

    }

    private void insertIntoGRNPaymentTable() {
        System.out.println(GrnId);
        AbsoluteGrnId = DBManager.getAbsoluteId(GrnId, DBTable.GRN);
        System.out.println(AbsoluteGrnId);

        for (PaymentListTbl tableRow : paymentTable.getItems()) {

            MySQL.iud("INSERT INTO `grn_payment` (`grn_id`, `payment_method_id`, `amount`) VALUES (" + AbsoluteGrnId + ", " + DBManager.getId(tableRow.getPaymentMethod(), DBTable.PAYMENT_METHOD) + ", '" + tableRow.getAmount() + "')");

        }

    }

    private void insertOrUpdateStockTable() {

        for (GRNListTbl tableRow : productTable.getItems()) {

            try {
                ResultSet stockRs = DBManager.getDBData("SELECT * FROM `stock` WHERE `product_id` = " + DBManager.getAbsoluteId(tableRow.getProductId(), DBTable.PRODUCT) + " AND `selling_price` = " + tableRow.getSellingPrice() + "");

                int stock_id;

                if (stockRs.next()) {
                    //update stock
                    stock_id = stockRs.getInt("id");
                    int stock_qty = stockRs.getInt("quantity");

                    int updatedQty = stock_qty + tableRow.getQuantity();

                    MySQL.iud("UPDATE `stock` SET `quantity` ='" + updatedQty + "' WHERE `id` = '" + stock_id + "'");
                } else {
                    //insert stock
                    MySQL.iud("INSERT INTO `stock` (`product_id`, `quantity`, `selling_price`) VALUES (" + DBManager.getAbsoluteId(tableRow.getProductId(), DBTable.PRODUCT) + ", " + tableRow.getQuantity() + ", " + tableRow.getSellingPrice() + ")");

                    ResultSet insertedStockRs = DBManager.getDBData("SELECT * FROM `stock` WHERE `product_id` = " + DBManager.getAbsoluteId(tableRow.getProductId(), DBTable.PRODUCT) + " AND `quantity` = " + tableRow.getQuantity() + " AND `selling_price` = " + tableRow.getSellingPrice() + "");
                    insertedStockRs.next();
                    stock_id = insertedStockRs.getInt("id");

                }

                insertIntoGRNItemTable(tableRow, stock_id);

            } catch (SQLException ex) {
                Logger.getLogger(AddGRNController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void insertIntoGRNItemTable(GRNListTbl tableRow, int stock_id) {

        MySQL.iud("INSERT INTO `grn_item` (`grn_id`, `quantity`, `buying_price`, `color_id`, `imei`, `stock_id`) VALUES (" + DBManager.getAbsoluteId(GrnId, DBTable.GRN) + ", " + tableRow.getQuantity() + ", " + tableRow.getBuyingPrice() + ", " + DBManager.getId(tableRow.getColor(), DBTable.COLOR) + ", '" + tableRow.getImeiNumber() + "'," + stock_id + ")");

    }

    private void resetAll() {
        resetSupplier();
        resetProductFields();
        resetPaymentFields();
        productTable.getItems().clear();
        productTable.refresh();
        paymentTable.getItems().clear();
        paymentTable.refresh();

        GrnId = null;
        AbsoluteGrnId = 0;

        productTblData = null;
        paymentTblData = null;
    }
}
