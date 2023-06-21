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
public class PaymentListTbl {
    private int no;
    private String paymentMethod;
    private double amount;

    public PaymentListTbl(int no, String paymentMethod, double amount) {
        this.no = no;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    
}
