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
public class EmployeeTbl {

    private Circle img;
    private String empId;
    private String name;
    private String email;
    private String userRole;
    private String status;

    public EmployeeTbl(Circle img, String empId, String name, String email, String userRole, String status) {
        this.img = img;
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.status = status;
    }

    public Circle getImg() {
        return img;
    }

    public void setImg(Circle img) {
        this.img = img;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
