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
public class CompanyTbl {

    private Circle img;
    private String comId;
    private String name;
    private String email;
    private String status;

    public CompanyTbl(Circle img, String comId, String name, String email, String status) {
        this.img = img;
        this.comId = comId;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public Circle getImg() {
        return img;
    }

    public void setImg(Circle img) {
        this.img = img;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
