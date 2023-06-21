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
public class ReportInvoiceTbl {
    
    private int no;
    private String invId;
    private String employeeName;
    private String customerName;
    private String date;

    public ReportInvoiceTbl(int no, String invId, String employeeName, String customerName, String date) {
        this.no = no;
        this.invId = invId;
        this.employeeName = employeeName;
        this.customerName = customerName;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
