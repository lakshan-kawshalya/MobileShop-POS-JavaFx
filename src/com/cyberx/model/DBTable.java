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
public enum DBTable {
    EMPLOYEE {
        @Override
        public String getName() {
            return "employee";
        }
    }, COMPANY {
        @Override
        public String getName() {
            return "company";
        }
    }, SUPPLIER {
        @Override
        public String getName() {
            return "supplier";
        }
    }, PROVINCE {
        @Override
        public String getName() {
            return "province";
        }
    }, DISTRICT {
        @Override
        public String getName() {
            return "district";
        }
    }, CITY {
        @Override
        public String getName() {
            return "city";
        }
    }, GENDER {
        @Override
        public String getName() {
            return "gender";
        }
    }, MARTIAL_STATUS {
        @Override
        public String getName() {
            return "martial_status";
        }
    }, STATUS {
        @Override
        public String getName() {
            return "status";
        }
    }, USER_ROLE {
        @Override
        public String getName() {
            return "user_role";
        }
    }, CUSTOMER {
        @Override
        public String getName() {
            return "customer";
        }
    }, CATEGORY {
        @Override
        public String getName() {
            return "category";
        }
    }, SUB_CATEGORY {
        @Override
        public String getName() {
            return "sub_category";
        }
    }, PRODUCT {
        @Override
        public String getName() {
            return "product";
        }
    }, PAYMENT_METHOD {
        @Override
        public String getName() {
            return "payment_method";
        }
    }, COLOR {
        @Override
        public String getName() {
            return "color";
        }
    }, GRN {
        @Override
        public String getName() {
            return "grn";
        }
    }, GRN_ITEM {
        @Override
        public String getName() {
            return "grn_item";
        }
    }, STOCK {
        @Override
        public String getName() {
            return "stock";
        }
    }, GRN_PAYMENT {
        @Override
        public String getName() {
            return "grn_payment";
        }
    }, INVOICE {
        @Override
        public String getName() {
            return "invoice";
        }
    }, INVOICE_ITEM {
        @Override
        public String getName() {
            return "invoice_item";
        }
    }, INVOICE_PAYMENT {
        @Override
        public String getName() {
            return "invoice_payment";
        }
    };

    public String getName() {
        return "";
    }
}
