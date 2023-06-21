/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model.tables;

import javafx.scene.shape.Circle;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class ProductTbl {
    private Circle img;
    private String prdId;
    private String name;
    private String category;
    private String subCategory;
    private String imeiStatus;

    public ProductTbl(Circle img, String prdId, String name, String category, String subCategory, String imeiStatus) {
        this.img = img;
        this.prdId = prdId;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.imeiStatus = imeiStatus;
    }

    public Circle getImg() {
        return img;
    }

    public void setImg(Circle img) {
        this.img = img;
    }

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getImeiStatus() {
        return imeiStatus;
    }

    public void setImeiStatus(String imeiStatus) {
        this.imeiStatus = imeiStatus;
    }
    
    
    
}
