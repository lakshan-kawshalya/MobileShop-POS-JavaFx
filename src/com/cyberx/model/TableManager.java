/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import com.cyberx.model.tables.CategoryTbl;
import com.cyberx.model.tables.CityTable;
import com.cyberx.model.tables.CompanyTbl;
import com.cyberx.model.tables.CustomerTbl;
import com.cyberx.model.tables.EmployeeTbl;
import com.cyberx.model.tables.ProductTbl;
import com.cyberx.model.tables.ReportGrnTbl;
import com.cyberx.model.tables.ReportInvoiceTbl;
import com.cyberx.model.tables.StockTbl;
import com.cyberx.model.tables.SupplierTbl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class TableManager {

    public void loadEmployeeTable(TableView table, ResultSet rs) {
        ObservableList<EmployeeTbl> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                Image img = new Image(this.getClass().getResourceAsStream("/user_uploads/profile_img/" + rs.getString("profile_image")));

                Circle imageCircle = new Circle(30.0);
                imageCircle.setFill(new ImagePattern(img));

                observableList.add(new EmployeeTbl(imageCircle, rs.getString("employee_id"), rs.getString("fname") + " " + rs.getString("lname"), rs.getString("email"), DBManager.getName(rs.getInt("user_role_id"), DBTable.USER_ROLE), DBManager.getName(rs.getInt("status_id"), DBTable.STATUS)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadCompanyTable(TableView table, ResultSet rs) {
        ObservableList<CompanyTbl> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                Image img = new Image(this.getClass().getResourceAsStream("/user_uploads/company_logo_img/" + rs.getString("logo_image")));

                Circle imageCircle = new Circle(30.0);
                imageCircle.setFill(new ImagePattern(img));

                observableList.add(new CompanyTbl(imageCircle, rs.getString("company_id"), rs.getString("name"), rs.getString("email"), DBManager.getName(rs.getInt("status_id"), DBTable.STATUS)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadSupplierTable(TableView table, ResultSet rs) {
        ObservableList<SupplierTbl> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {

                observableList.add(new SupplierTbl(rs.getString("supplier_id"), rs.getString("fname") + " " + rs.getString("lname"), rs.getString("email"), rs.getString("contact_no"), DBManager.getName(rs.getInt("company_id"), DBTable.COMPANY)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadCustomerTable(TableView table, ResultSet rs) {
        ObservableList<CustomerTbl> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {

                observableList.add(new CustomerTbl(rs.getString("customer_id"), rs.getString("fname") + " " + rs.getString("lname"), rs.getString("email"), rs.getString("contact_no")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    //load categoryId table
    public void loadCategoryTable(TableView table, ResultSet rs) {
        String categoryId = "";
        String categoryName = "";

        ObservableList<CategoryTbl> observableList = FXCollections.observableArrayList();

        try {

            while (rs.next()) {
                if (!categoryId.equalsIgnoreCase(rs.getString("category.category_id"))) {
                    categoryId = rs.getString("category.category_id");
                    categoryName = rs.getString("category.name");
                } else {
                    categoryId = "";
                    categoryName = "";
                }

                observableList.add(new CategoryTbl(categoryId, categoryName, rs.getString("sub_category.name")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(observableList);
    }

    public void loadCityTable(TableView table, ResultSet rs) {
        ObservableList<CityTable> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {

                observableList.add(new CityTable(rs.getString("province.name") + " Province", rs.getString("district.name"), rs.getString("city.name")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadProductTable(TableView table, ResultSet rs) {
        ObservableList<ProductTbl> observableList = FXCollections.observableArrayList();

        try {
            while (rs.next()) {
                Image img = new Image(this.getClass().getResourceAsStream("/user_uploads/product_img/" + rs.getString("product_img")));

                Circle imageCircle = new Circle(30.0);
                imageCircle.setFill(new ImagePattern(img));

                String SubCategory = "";
                if (Integer.parseInt(rs.getString("sub_category_id")) != 0) {
                    SubCategory = DBManager.getName(rs.getInt("sub_category_id"), DBTable.SUB_CATEGORY);
                }

                String IMEI = "Not Required";
                if (rs.getInt("imei_status") == 1) {
                    IMEI = "Required";
                }

                observableList.add(new ProductTbl(imageCircle, rs.getString("product_id"), rs.getString("name"), DBManager.getName(rs.getInt("category_id"), DBTable.CATEGORY), SubCategory, IMEI));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadStockTable(TableView table, ResultSet rs) {
        ObservableList<StockTbl> observableList = FXCollections.observableArrayList();

        try {
            int count = 0;

            while (rs.next()) {

                count++;

                observableList.add(new StockTbl(count, rs.getString("product.product_id"), rs.getString("product.name"), DBManager.getName(rs.getInt("product.category_id"), DBTable.CATEGORY), rs.getInt("stock.quantity"), rs.getDouble("stock.selling_price")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

    public void loadReportGRNTable(TableView table, ResultSet rs) {
        ObservableList<ReportGrnTbl> observableList = FXCollections.observableArrayList();

        try {

            int count = 0;

            while (rs.next()) {

                count++;

                observableList.add(new ReportGrnTbl(count, rs.getString("grn.grn_id"), rs.getString("employee.fname") + " " + rs.getString("employee.lname"), rs.getString("supplier.fname") + " " + rs.getString("supplier.lname"), rs.getString("grn.date_added")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }
    
    public void loadReportInvoiceTable(TableView table, ResultSet rs) {
        ObservableList<ReportInvoiceTbl> observableList = FXCollections.observableArrayList();

        try {

            int count = 0;

            while (rs.next()) {

                count++;

                observableList.add(new ReportInvoiceTbl(count, rs.getString("invoice.invoice_id"), rs.getString("employee.fname") + " " + rs.getString("employee.lname"), rs.getString("customer.fname") + " " + rs.getString("customer.lname"), rs.getString("invoice.date_added")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        table.setItems(observableList);

    }

}
