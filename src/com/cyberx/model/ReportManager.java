/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import com.cyberx.model.tables.DaySaleReport;
import com.cyberx.model.tables.GRNListTbl;
import com.cyberx.model.tables.ReportProduct;
import com.cyberx.model.tables.SalesListTbl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ReportManager {

    public void generateGRNReport(String GRNId) {
        try {
            InputStream filepath = this.getClass().getResourceAsStream("/com/cyberx/resources/reports/mobiHub_GRN_Report.jrxml");

            JasperDesign jasperDesign = JRXmlLoader.load(filepath);

            JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

            ResultSet grnItemRs = DBManager.getDBData("SELECT * FROM `grn_item` INNER JOIN `stock` ON `grn_item`.`stock_id` = `stock`.`id` WHERE `grn_item`.`grn_id` = '" + DBManager.getAbsoluteId(GRNId, DBTable.GRN) + "'");

            List<GRNListTbl> list = new ArrayList<>();
            int count = 0;
            double total = 0;
            while (grnItemRs.next()) {
                ResultSet productRs = DBManager.getTableData(DBTable.PRODUCT, DBManager.getPrefixId(grnItemRs.getInt("stock.product_id"), DBTable.PRODUCT));
                productRs.next();

                double itemTotal = grnItemRs.getInt("grn_item.quantity") * grnItemRs.getDouble("grn_item.buying_price");
                total = total + itemTotal;

                count++;

                GRNListTbl grn = new GRNListTbl(count, productRs.getString("product_id"), productRs.getString("name"), DBManager.getName(productRs.getInt("category_id"), DBTable.CATEGORY), grnItemRs.getInt("grn_item.quantity"), DBManager.getName(grnItemRs.getInt("color_id"), DBTable.COLOR), grnItemRs.getString("grn_item.imei"), grnItemRs.getDouble("grn_item.buying_price"), grnItemRs.getDouble("stock.selling_price"), itemTotal);
                list.add(grn);

            }

            ResultSet grnRs = DBManager.getDBData("SELECT * FROM `grn` WHERE `grn_id` = '" + GRNId + "'");
            grnRs.next();

            ResultSet companyRs = DBManager.getDBData("SELECT * FROM `company` INNER JOIN `supplier` ON `supplier`.`company_id` = `company`.`id` WHERE `supplier`.`id` = '" + grnRs.getInt("supplier_id") + "'");
            companyRs.next();

            ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `id` = '" + grnRs.getInt("employee_id") + "'");
            employeeRs.next();

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);

            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("GRNNumber", GRNId);
            parameters.put("Date", grnRs.getString("date_added").split(" ")[0]);
            parameters.put("CompanyName", companyRs.getString("company.name"));
            parameters.put("CompanyAddress", companyRs.getString("company.address_line_1") + " " + companyRs.getString("company.address_line_2"));
            parameters.put("EmployeeId", employeeRs.getString("employee_id"));
            parameters.put("EmployeeName", employeeRs.getString("fname") + " " + employeeRs.getString("lname"));
            parameters.put("ItemsTotal", total);

            parameters.put("GRNBeanParam", datasource);

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jp, false);

        } catch (JRException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateInvoiceReport(String INVId) {
        try {
            InputStream filepath = this.getClass().getResourceAsStream("/com/cyberx/resources/reports/mobiHub_Invoice_Report.jrxml");

            JasperDesign jasperDesign = JRXmlLoader.load(filepath);

            JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

            ResultSet grnItemRs = DBManager.getDBData("SELECT * FROM `invoice_item` INNER JOIN `stock` ON `invoice_item`.`stock_id` = `stock`.`id` WHERE `invoice_item`.`invoice_id` = '" + DBManager.getAbsoluteId(INVId, DBTable.INVOICE) + "'");

            List<SalesListTbl> list = new ArrayList<>();
            int count = 0;
            double total = 0;
            while (grnItemRs.next()) {
                ResultSet productRs = DBManager.getTableData(DBTable.PRODUCT, DBManager.getPrefixId(grnItemRs.getInt("stock.product_id"), DBTable.PRODUCT));
                productRs.next();

                double itemTotal = grnItemRs.getInt("invoice_item.quantity") * grnItemRs.getDouble("stock.selling_price");
                total = total + itemTotal;

                count++;

                SalesListTbl invoice = new SalesListTbl(count, productRs.getString("product_id"), productRs.getString("name"), grnItemRs.getInt("invoice_item.quantity"), DBManager.getName(grnItemRs.getInt("color_id"), DBTable.COLOR), grnItemRs.getString("invoice_item.imei"), grnItemRs.getDouble("stock.selling_price"), itemTotal);
                list.add(invoice);

            }

            ResultSet invoiceRs = DBManager.getDBData("SELECT * FROM `invoice` WHERE `invoice_id` = '" + INVId + "'");
            invoiceRs.next();

            ResultSet customerRs = DBManager.getDBData("SELECT * FROM `customer` WHERE `customer`.`id` = '" + invoiceRs.getInt("customer_id") + "'");
            customerRs.next();

            ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `id` = '" + invoiceRs.getInt("employee_id") + "'");
            employeeRs.next();

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);

            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("INVNumber", INVId);
            parameters.put("Date", invoiceRs.getString("date_added").split(" ")[0]);
            parameters.put("CustomerName", customerRs.getString("customer.fname") + " " + customerRs.getString("customer.lname"));
            parameters.put("EmployeeId", employeeRs.getString("employee_id"));
            parameters.put("EmployeeName", employeeRs.getString("fname") + " " + employeeRs.getString("lname"));
            parameters.put("ItemsTotal", total);

            parameters.put("InvoiceBeanParam", datasource);

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jp, false);

        } catch (JRException | SQLException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateDaySaleReport(String Day) {
        try {
            InputStream filepath = this.getClass().getResourceAsStream("/com/cyberx/resources/reports/mobiHub_Day_Sale_Report.jrxml");

            JasperDesign jasperDesign = JRXmlLoader.load(filepath);

            JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

            ResultSet invoiceRs = DBManager.getDBData("SELECT * FROM `invoice` WHERE `date_added` LIKE '%" + Day + "%'");

            List<DaySaleReport> list = new ArrayList<>();
            int count = 0;
            double grandTotal = 0;

            while (invoiceRs.next()) {
                ResultSet invoiceItemRs = DBManager.getDBData("SELECT * FROM `invoice_item` INNER JOIN `stock` ON `stock`.`id` = `invoice_item`.`stock_id` WHERE `invoice_item`.`invoice_id` = " + invoiceRs.getInt("id") + "");
                double total = 0;

                while (invoiceItemRs.next()) {
                    total = total + (invoiceItemRs.getInt("invoice_item.quantity") * invoiceItemRs.getDouble("stock.selling_price"));
                }

                grandTotal = grandTotal + total;

                ResultSet customerRs = DBManager.getDBData("SELECT * FROM `customer` WHERE `customer`.`id` = '" + invoiceRs.getInt("customer_id") + "'");
                customerRs.next();

                ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `id` = '" + invoiceRs.getInt("employee_id") + "'");
                employeeRs.next();

                count++;

                DaySaleReport invoice = new DaySaleReport(count, invoiceRs.getString("invoice_id"), employeeRs.getString("fname") + " " + employeeRs.getString("lname"), customerRs.getString("customer.fname") + " " + customerRs.getString("customer.lname"), total);
                list.add(invoice);

            }

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);

            HashMap<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("Date", Day);
            parameters.put("GrandTotal", grandTotal);

            parameters.put("DaySaleBeanParam", datasource);

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jp, false);

        } catch (JRException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public void generateQuerySaleReport(LocalDate DayFrom, LocalDate DayTo) {
//        LocalDate occuringDate = DayFrom;
//        while (occuringDate == DayTo) {
//            occuringDate = DayFrom.plusDays(1);
//
//            try {
//                InputStream filepath = this.getClass().getResourceAsStream("/com/cyberx/resources/reports/mobiHub_Query_Sale_Report.jrxml");
//
//                JasperDesign jasperDesign = JRXmlLoader.load(filepath);
//
//                JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
//
//                ResultSet invoiceRs = DBManager.getDBData("SELECT * FROM `product` WHERE `date_added` <= '" + DayFrom + "' AND `date_added` >= '" + DayTo + "'");
//
//                List<DaySaleReport> list = new ArrayList<>();
//                int count = 0;
//                double grandTotal = 0;
//
//                while (invoiceRs.next()) {
//                    ResultSet invoiceItemRs = DBManager.getDBData("SELECT * FROM `invoice_item` INNER JOIN `stock` ON `stock`.`id` = `invoice_item`.`stock_id` WHERE `invoice_item`.`invoice_id` = " + invoiceRs.getInt("id") + "");
//                    double total = 0;
//
//                    while (invoiceItemRs.next()) {
//                        total = total + (invoiceItemRs.getInt("invoice_item.quantity") * invoiceItemRs.getDouble("stock.selling_price"));
//                    }
//
//                    grandTotal = grandTotal + total;
//
//                    ResultSet customerRs = DBManager.getDBData("SELECT * FROM `customer` WHERE `customer`.`id` = '" + invoiceRs.getInt("customer_id") + "'");
//                    customerRs.next();
//
//                    ResultSet employeeRs = DBManager.getDBData("SELECT * FROM `employee` WHERE `id` = '" + invoiceRs.getInt("employee_id") + "'");
//                    employeeRs.next();
//
//                    count++;
//
//                    DaySaleReport product = new DaySaleReport(count, invoiceRs.getString("invoice_id"), employeeRs.getString("fname") + " " + employeeRs.getString("lname"), customerRs.getString("customer.fname") + " " + customerRs.getString("customer.lname"), total);
//                    list.add(product);
//
//                }
//            } catch (JRException | SQLException ex) {
//                Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);
//
//            HashMap<String, Object> parameters = new HashMap<String, Object>();
//
//            parameters.put("Date", Day);
//            parameters.put("GrandTotal", grandTotal);
//
//            parameters.put("DaySaleBeanParam", datasource);
//
//            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
//
//            JasperViewer.viewReport(jp, false);
//
//        }
//
//    }
    
    public void generateProductReport() {
        try {
            InputStream filepath = this.getClass().getResourceAsStream("/com/cyberx/resources/reports/mobiHub_Product_Report.jrxml");

            JasperDesign jasperDesign = JRXmlLoader.load(filepath);

            JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

            ResultSet productRs =  DBManager.getDBData("SELECT * FROM `product`");

            List<ReportProduct> list = new ArrayList<>();
            int count = 0;

            while (productRs.next()) {

                count++;

                ReportProduct product = new ReportProduct(count, productRs.getString("product_id"), productRs.getString("name") , DBManager.getName(productRs.getInt("category_id"), DBTable.CATEGORY), DBManager.getName(productRs.getInt("sub_category_id"), DBTable.SUB_CATEGORY));
                list.add(product);

            }

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);

            HashMap<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("ProductBeanParam", datasource);

            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

            JasperViewer.viewReport(jp, false);

        } catch (JRException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
