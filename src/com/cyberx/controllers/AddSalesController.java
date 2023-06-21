/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.controllers;

import static com.cyberx.main.Main.loggedUserId;
import com.cyberx.model.DBManager;
import com.cyberx.model.DBTable;
import com.cyberx.model.IDManager;
import com.cyberx.model.InputManager;
import com.cyberx.model.JFXManager;
import com.cyberx.model.MySQL;
import com.cyberx.model.Views;
import com.cyberx.model.tables.PaymentListTbl;
import com.cyberx.model.tables.SalesListTbl;
import java.net.URL;
import java.sql.ResultSet;
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
public class AddSalesController implements Initializable {

    String InvId;
    int AbsoluteInvId;

    SalesListTbl productTblData;
    PaymentListTbl paymentTblData;

    @FXML
    private Button btnAddCustomer;
    @FXML
    private Label lblCustomerContactNo;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblCustomerId;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private GridPane pnlProductInfo;
    @FXML
    private TextField txtIMEI;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblProductName;
    @FXML
    private Label lblCategoryName;
    @FXML
    private TextField txtColor;
    @FXML
    private Label lblProductId;
    @FXML
    private Label lblSellingPrice;
    @FXML
    private Button btnAddToList;
    @FXML
    private Button btnResetProduct;
    @FXML
    private TableView<SalesListTbl> productTable;
    @FXML
    private TableColumn<SalesListTbl, String> colNo;
    @FXML
    private TableColumn<SalesListTbl, String> colProductId;
    @FXML
    private TableColumn<SalesListTbl, String> colProductName;
    @FXML
    private TableColumn<SalesListTbl, String> colQuantity;
    @FXML
    private TableColumn<SalesListTbl, String> colColor;
    @FXML
    private TableColumn<SalesListTbl, String> colIMEI;
    @FXML
    private TableColumn<SalesListTbl, String> colSellingPrice;
    @FXML
    private TableColumn<SalesListTbl, String> colTotal;
    @FXML
    private Button btnRemoveFromList;
    @FXML
    private GridPane pnlPaymentInfo;
    @FXML
    private TextField txtPayment;
    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox<String> cmbPaymentMethod;
    @FXML
    private Button btnAddToPaymentList;
    @FXML
    private Button btnResetPayment;
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
    private Label lblBalance;
    @FXML
    private Label lblPaymentTotal;
    @FXML
    private Label lblCustomerEmail;
    @FXML
    private Button btnAddSale;
    @FXML
    private Label lblAvailableQty;

    private void initializingItemStateChangeListeners() {
        cmbPaymentMethod.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                InputManager.isValidComboBox(cmbPaymentMethod);
            }

        });

        lblCustomerId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                btnSearchProduct.setDisable(false);
            }

        });

        lblProductId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                txtQty.setDisable(false);
                txtColor.setDisable(false);
                btnResetProduct.setDisable(false);
                btnAddToList.setDisable(false);

                updateAvailableQty();

            }

        });

        productTable.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(ListChangeListener.Change<?> c) {
                updateTotal();
                updateAvailableQty();
            }
        });

        txtQty.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                InputManager.isValidQtyKey(evt);
            }

        });

        lblTotal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                if (Double.parseDouble(lblTotal.getText()) > 0) {
                    pnlPaymentInfo.setDisable(false);
                    btnResetPayment.setDisable(false);
                    btnAddToPaymentList.setDisable(false);
                } else {
                    pnlPaymentInfo.setDisable(true);
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
                    btnAddSale.setDisable(false);
                } else {
                    btnAddSale.setDisable(true);
                }
            }

        });
        
        lblBalance.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldSelectedItem, String newSelectedItem) {
                if (Double.parseDouble(lblBalance.getText()) == 0) {
                    btnAddSale.setDisable(false);
                } else {
                    btnAddSale.setDisable(true);
                }
            }

        });
    }

    private void loadComboBoxes() {
        InputManager.loadComboBox(cmbPaymentMethod, DBTable.PAYMENT_METHOD);
    }

    //customer
    private void resetCustomer() {
        lblCustomerName.setText("");
        lblCustomerId.setText("");
        lblCustomerEmail.setText("");
        lblCustomerContactNo.setText("");
    }

    //product
    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colIMEI.setCellValueFactory(new PropertyValueFactory<>("imeiNumber"));
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
        String sellingPrice = lblSellingPrice.getText();

        HashMap inputs = new HashMap();
        inputs.put("productId", productId);
        inputs.put("productName", productName);
        inputs.put("category", category);
        inputs.put("imei", imei);
        inputs.put("qty", qty);
        inputs.put("color", color);
        inputs.put("sellingPrice", sellingPrice);

        return inputs;
    }

    private void resetProductFields() {
        lblProductId.setText("");
        lblProductName.setText("");
        lblCategoryName.setText("");
        lblSellingPrice.setText("0");
        lblAvailableQty.setText("0 Items Left");

        txtIMEI.setText("");
        txtIMEI.getStyleClass().remove("invalid-input");

        txtQty.setText("");
        txtQty.getStyleClass().remove("invalid-input");

        txtColor.setText("");
        txtColor.getStyleClass().remove("invalid-input");
    }

    private boolean verifyProductInputs() {
        boolean isValid = true;

        InputManager.isValidQuantity(txtQty);
        InputManager.isValidTextField(txtColor);

        if (!txtIMEI.isDisabled()) {
            if (!InputManager.isValidTextField(txtIMEI)) {
                isValid = false;
            }
        }

        if (!(InputManager.isValidQuantity(txtQty) && InputManager.isValidTextField(txtColor))) {
            isValid = false;
        }
        
        if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(lblAvailableQty.getText().split(" ")[0])) {
            if (!txtQty.getStyleClass().contains("invalid-input")) {
                txtQty.getStyleClass().add("invalid-input");
                isValid = false;
            }
        }

        return isValid;
    }

    private void updateTotal() {
        double total = 0;
        int count = 0;

        for (SalesListTbl tableRow : productTable.getItems()) {

            count++;
            tableRow.setNo(count);

            Double itemTotal = tableRow.getTotal();
            total = total + itemTotal;
        }

        lblTotal.setText(String.valueOf(total));
    }

    private void addToInvoiceList(HashMap<String, String> inputs) {
        ObservableList<SalesListTbl> observableList = FXCollections.observableArrayList();

        double itemTotal = Double.parseDouble(inputs.get("qty")) * Double.parseDouble(inputs.get("sellingPrice"));

        observableList.add(new SalesListTbl(1, inputs.get("productId"), inputs.get("productName"), Integer.parseInt(inputs.get("qty")), inputs.get("color"), inputs.get("imei"), Double.parseDouble(inputs.get("sellingPrice")), itemTotal));

        productTable.getItems().addAll(observableList);
    }

    private void updateInvoiceListProductQty(HashMap<String, String> inputs) {

        SalesListTbl tableRow = getRowIndexOfProductInInvoiceList(inputs);
        if (tableRow != null) {
            tableRow.setQuantity(tableRow.getQuantity() + Integer.parseInt(inputs.get("qty")));
            tableRow.setTotal(tableRow.getQuantity() * tableRow.getSellingPrice());
            productTable.refresh();
            updateTotal();
            updateAvailableQty();
        }
    }

    private boolean isProductInInvoiceList(HashMap<String, String> inputs) {

        for (SalesListTbl tableRow : productTable.getItems()) {

            String productId = tableRow.getProductId();
            String color = tableRow.getColor();

            if (productId.equalsIgnoreCase(inputs.get("productId")) && color.equalsIgnoreCase(inputs.get("color"))) {
                return true;
            }
        }

        return false;
    }

    private SalesListTbl getRowIndexOfProductInInvoiceList(HashMap<String, String> inputs) {

        for (SalesListTbl tableRow : productTable.getItems()) {

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

    private void updateAvailableQty() {
        if (!lblProductId.getText().isBlank()) {
            ResultSet stockRs = DBManager.getDBData("SELECT * FROM `stock` WHERE `product_id` = '" + DBManager.getAbsoluteId(lblProductId.getText(), DBTable.PRODUCT) + "'");
            try {
                stockRs.next();
                lblSellingPrice.setText(stockRs.getString("selling_price"));
                lblAvailableQty.setText(stockRs.getString("quantity") + " Items Left");
            } catch (SQLException ex) {
                Logger.getLogger(AddSalesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
         for (SalesListTbl tableRow : productTable.getItems()) {
             if(lblProductId.getText().equalsIgnoreCase(tableRow.getProductId())){
                 int updatedAvailableQty = Integer.parseInt(lblAvailableQty.getText().split(" ")[0])-tableRow.getQuantity();
                 lblAvailableQty.setText(String.valueOf(updatedAvailableQty));
             }
         }
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

    //customer
    @FXML
    private void btnAddCustomerActionPerformed(ActionEvent event) {
        FXMLLoader addCustomerFxmlLoader = new JFXManager().loadAsDialog(Views.ADD_CUSTOMER);

        //get controller associate with view
        AddCustomerController addCustomerController = addCustomerFxmlLoader.getController();

        addCustomerController.initPopup(lblCustomerId, lblCustomerName, lblCustomerEmail, lblCustomerContactNo);
    }

    @FXML
    private void btnSearchCustomerActionPerformed(ActionEvent event) {
        FXMLLoader listCustomerFxmlLoader = new JFXManager().loadAsDialog(Views.LIST_CUSTOMER);

        //get controller associate with view
        ListCustomerController listCustomerController = listCustomerFxmlLoader.getController();

        listCustomerController.init(lblCustomerId, lblCustomerName, lblCustomerEmail, lblCustomerContactNo);
    }

    @FXML
    private void btnbtnSearchProductActionPerformed(ActionEvent event) {
        FXMLLoader listProductFxmlLoader = new JFXManager().loadAsDialog(Views.LIST_PRODUCT);

        //get controller associate with view
        ListProductController listproductController = listProductFxmlLoader.getController();

        listproductController.init(lblProductId, lblProductName, lblCategoryName, txtIMEI, txtQty);

        //set sellin price and available quantity
    }

    @FXML
    private void txtIMEIKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtIMEI);
        //search available imei in stocks
    }

    @FXML
    private void txtQtyKeyReleased(KeyEvent event) {
        InputManager.isValidQuantity(txtQty);
        if(txtQty.getText().isBlank()){
            if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(lblAvailableQty.getText().split(" ")[0])) {
            if (!txtQty.getStyleClass().contains("invalid-input")) {
                txtQty.getStyleClass().add("invalid-input");
            } else {
                if (!txtQty.getStyleClass().contains("invalid-input")) {
                    txtQty.getStyleClass().add("invalid-input");
                }
            }
        }
        }
        //search available quantity
    }

    @FXML
    private void txtColorKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtColor);
        //check available colors
    }

    @FXML
    private void btnAddToListActonPerformed(ActionEvent event) {
        if (verifyProductInputs()) {

            HashMap<String, String> inputs = getProductInputValues();

            if (isProductInInvoiceList(inputs)) {
                if (JFXManager.confirmationDialog(Alert.AlertType.NONE, "Product is already in list do you want to add this quantity to current quantity.")) {
                    updateInvoiceListProductQty(inputs);
                } else {
                    resetProductFields();
                }
            } else {
                addToInvoiceList(inputs);
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

    @FXML
    private void txtPaymentKeyReleased(KeyEvent event) {
        InputManager.isValidTextField(txtPayment);
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
    private void btnResetPaymentActionPerformed(ActionEvent event) {
        resetPaymentFields();
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
    private void btnRemoveFromPaymentListActonPerformed(ActionEvent event) {
        if (paymentTable != null) {
            paymentTable.getItems().remove(paymentTable.getSelectionModel().getSelectedIndex());
            paymentTable.refresh();
        }
    }

    @FXML
    private void btnAddSaleActionPerformed(ActionEvent event) {
        insertIntoInvoiceTable(lblCustomerId.getText());
    }

    //database insertion
    private void insertIntoInvoiceTable(String customerId) {
        InvId = IDManager.generateID(DBTable.INVOICE);
        System.out.println("INSERT INTO `invoice` (`invoice_id`, `employee_id`, `customer_id`) VALUES ('" + InvId + "', '" + loggedUserId + "', " + DBManager.getAbsoluteId(customerId, DBTable.CUSTOMER) + ")");
        MySQL.iud("INSERT INTO `invoice` (`invoice_id`, `employee_id`, `customer_id`) VALUES ('" + InvId + "', " + loggedUserId + ", " + DBManager.getAbsoluteId(customerId, DBTable.CUSTOMER) + ")");

        insertIntoInvoicePaymentTable();
        insertOrUpdateStockTable();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Invoice added successfully.");
        alert.show();

        resetAll();

    }

    private void insertIntoInvoicePaymentTable() {
        System.out.println(InvId);
        AbsoluteInvId = DBManager.getAbsoluteId(InvId, DBTable.INVOICE);
        System.out.println(AbsoluteInvId);

        for (PaymentListTbl tableRow : paymentTable.getItems()) {

            MySQL.iud("INSERT INTO `invoice_payment` (`invoice_id`, `payment_method_id`, `amount`) VALUES (" + AbsoluteInvId + ", " + DBManager.getId(tableRow.getPaymentMethod(), DBTable.PAYMENT_METHOD) + ", '" + tableRow.getAmount() + "')");

        }

    }

    private void insertOrUpdateStockTable() {

        for (SalesListTbl tableRow : productTable.getItems()) {

            try {
                ResultSet stockRs = DBManager.getDBData("SELECT * FROM `stock` WHERE `product_id` = " + DBManager.getAbsoluteId(tableRow.getProductId(), DBTable.PRODUCT) + " AND `selling_price` = " + tableRow.getSellingPrice() + "");

                int stock_id;

                stockRs.next();
                //update stock
                stock_id = stockRs.getInt("id");
                int stock_qty = stockRs.getInt("quantity");

                int updatedQty = stock_qty - tableRow.getQuantity();

                MySQL.iud("UPDATE `stock` SET `quantity` ='" + updatedQty + "' WHERE `id` = '" + stock_id + "'");

                insertIntoInvoiceItemTable(tableRow, stock_id);

            } catch (SQLException ex) {
                Logger.getLogger(AddGRNController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void insertIntoInvoiceItemTable(SalesListTbl tableRow, int stock_id) {

        MySQL.iud("INSERT INTO `invoice_item` (`invoice_id`, `quantity`, `color_id`, `imei`, `stock_id`) VALUES (" + DBManager.getAbsoluteId(InvId, DBTable.INVOICE) + ", " + tableRow.getQuantity() + ", " + DBManager.getId(tableRow.getColor(), DBTable.COLOR) + ", '" + tableRow.getImeiNumber() + "'," + stock_id + ")");

    }

    private void resetAll() {
        resetCustomer();
        resetProductFields();
        resetPaymentFields();
        productTable.getItems().clear();
        productTable.refresh();
        paymentTable.getItems().clear();
        paymentTable.refresh();

        InvId = null;
        AbsoluteInvId = 0;

        productTblData = null;
        paymentTblData = null;
    }

}
