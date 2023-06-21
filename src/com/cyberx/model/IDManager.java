/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class IDManager {

    public static String generateID(DBTable DBtable) {

        String table = null;
        String prefix = null;

        switch (DBtable) {
            case EMPLOYEE:
                table = DBtable.EMPLOYEE.getName();
                prefix = "EMP";
                break;

            case COMPANY:
                table = DBtable.COMPANY.getName();
                prefix = "COM";
                break;

            case SUPPLIER:
                table = DBtable.SUPPLIER.getName();
                prefix = "SUP";
                break;

            case CUSTOMER:
                table = DBtable.CUSTOMER.getName();
                prefix = "CUS";
                break;

            case CATEGORY:
                table = DBtable.CATEGORY.getName();
                prefix = "CAT";
                break;

            case PRODUCT:
                table = DBtable.PRODUCT.getName();
                prefix = "PRD";
                break;

            case GRN:
                table = DBtable.GRN.getName();
                prefix = "GRN";
                break;

            case INVOICE:
                table = DBtable.INVOICE.getName();
                prefix = "INV";
                break;
        }

        String id = prefix + "-001";

        try {
            ResultSet rs = MySQL.search("SELECT `id` AS `last_id` FROM `" + table + "` WHERE `id` = (SELECT MAX(`id`) FROM `" + table + "`)");
            if (rs.next()) {
                int id_num = rs.getInt("last_id");
                id = (String.format(Locale.getDefault(), prefix + "-%03d", (id_num + 1)));
            }
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

}
