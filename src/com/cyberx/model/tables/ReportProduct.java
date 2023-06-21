/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model.tables;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ReportProduct {
    
    private int no;
    private String prdId;
    private String productName;
    private String categoryName;
    private String subCategoryName;

    public ReportProduct(int no, String prdId, String productName, String categoryName, String subCategoryName) {
        this.no = no;
        this.prdId = prdId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
    
    
    
}
