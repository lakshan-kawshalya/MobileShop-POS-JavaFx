/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public enum Views {
    MAIN_WINDOW {
        @Override
        public String getName() {
            return "MainWindow";
        }
    },LOGIN {
        @Override
        public String getName() {
            return "Login";
        }
    },DASHBOARD {
        @Override
        public String getName() {
            return "Dashboard";
        }
    },  EMPLOYEE_MGMT{
        @Override
        public String getName() {
            return "EmployeeMgmt";
        }
    }, ADD_EMPLOYEE {
        @Override
        public String getName() {
            return "AddEmployee";
        }
    }, LIST_EMPLOYEE {
        @Override
        public String getName() {
            return "ListEmployee";
        }
    },  UPDATE_EMPLOYEE_DIALOG {
        @Override
        public String getName() {
            return "UpdateEmployeeDialog";
        }
    },  VIEW_EMPLOYEE_INFO_DIALOG {
        @Override
        public String getName() {
            return "ViewEmployeeInfoDialog";
        }
    },  CITY_DIALOG{
        @Override
        public String getName() {
            return "CityDialog";
        }
    },  SUPPLIER_MGMT{
        @Override
        public String getName() {
            return "SupplierMgmt";
        }
    }, ADD_COMPANY {
        @Override
        public String getName() {
            return "AddCompany";
        }
    }, ADD_SUPPLIER {
        @Override
        public String getName() {
            return "AddSupplier";
        }
    }, LIST_COMPANY {
        @Override
        public String getName() {
            return "ListCompany";
        }
    }, LIST_SUPPLIER {
        @Override
        public String getName() {
            return "ListSupplier";
        }
    }, UPDATE_COMPANY_DIALOG {
        @Override
        public String getName() {
            return "UpdateCompanyDialog";
        }
    }, UPDATE_SUPPLIER_DIALOG {
        @Override
        public String getName() {
            return "UpdateSupplierDialog";
        }
    }, VIEW_COMPANY_INFO_DIALOG {
        @Override
        public String getName() {
            return "ViewCompanyInfoDialog";
        }
    }, VIEW_SUPPLIER_INFO_DIALOG {
        @Override
        public String getName() {
            return "ViewSupplierInfoDialog";
        }
    }, SEARCH_COMPANY_DIALOG {
        @Override
        public String getName() {
            return "SearchCompanyDialog";
        }
    },  CUSTOMER_MGMT{
        @Override
        public String getName() {
            return "CustomerMgmt";
        }
    }, ADD_CUSTOMER {
        @Override
        public String getName() {
            return "AddCustomer";
        }
    }, LIST_CUSTOMER {
        @Override
        public String getName() {
            return "ListCustomer";
        }
    }, UPDATE_CUSTOMER_DIALOG {
        @Override
        public String getName() {
            return "UpdateCustomerDialog";
        }
    }, VIEW_CUSTOMER_INFO_DIALOG {
        @Override
        public String getName() {
            return "ViewCustomerInfoDialog";
        }
    },  PRODUCT_MGMT{
        @Override
        public String getName() {
            return "ProductMgmt";
        }
    },  CATEGORY_MGMT{
        @Override
        public String getName() {
            return "CategoryMgmt";
        }
    },  ADD_CATEGORY{
        @Override
        public String getName() {
            return "AddCategory";
        }
    },  ADD_SUB_CATEGORY{
        @Override
        public String getName() {
            return "AddSubCategory";
        }
    },  LIST_CATEGORY{
        @Override
        public String getName() {
            return "ListCategory";
        }
    },  UPDATE_CATEGORY{
        @Override
        public String getName() {
            return "UpdateCategory";
        }
    },  ADD_PRODUCT{
        @Override
        public String getName() {
            return "AddProduct";
        }
    },  LIST_PRODUCT{
        @Override
        public String getName() {
            return "ListProduct";
        }
    },  UPDATE_PRODUCT_DIALOG{
        @Override
        public String getName() {
            return "UpdateProductDialog";
        }
    },  STOCK_MGMT{
        @Override
        public String getName() {
            return "StockMgmt";
        }
    },  ADD_GRN{
        @Override
        public String getName() {
            return "AddGRN";
        }
    },  LIST_STOCK{
        @Override
        public String getName() {
            return "ListStock";
        }
    },  ADD_SALES{
        @Override
        public String getName() {
            return "AddSales";
        }
    },  REPORT_MGMT{
        @Override
        public String getName() {
            return "ReportMgmt";
        }
    },  REPORT_GRN{
        @Override
        public String getName() {
            return "ReportGRN";
        }
    },  REPORT_INVOICE{
        @Override
        public String getName() {
            return "ReportInvoice";
        }
    },  REPORT_SALES{
        @Override
        public String getName() {
            return "ReportSales";
        }
    },  REPORT_PRODUCTS{
        @Override
        public String getName() {
            return "ReportProducts";
        }
    };

    public String getName() {
        return "";
    }
}
