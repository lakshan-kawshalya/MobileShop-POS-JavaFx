/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class DBManager {

    //toggle status
    public static void changeStatus(String newStatus, DBTable DBtable, String id) {
        if (newStatus != null) {
            MySQL.iud("UPDATE `" + DBtable.getName() + "` SET `status_id` = " + getId(newStatus, DBTable.STATUS) + " WHERE " + DBtable.getName() + "_id = '" + id + "'");
        }

    }

    //get id
    public static int getId(String txt, DBTable DBtable) {
        int id = 0;

        if (txt != null) {

            if (DBtable == DBTable.PROVINCE) {
                txt = txt.split(" ")[0];
            }

            try {
                ResultSet rs = MySQL.search("SELECT `id` FROM `" + DBtable.getName() + "` WHERE `name` = '" + txt + "'");
                rs.next();
                id = rs.getInt("id");
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return id;

    }
    
     //get prefix id
    public static String getPrefixId(int id, DBTable DBtable) {
        String prefixId = "";

        if (id != 0) {

            try {
                ResultSet rs = MySQL.search("SELECT `"+DBtable.getName()+"_id` FROM `" + DBtable.getName() + "` WHERE `id` = '" + id + "'");
                rs.next();
                prefixId = rs.getString(DBtable.getName()+"_id");
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return prefixId;

    }
    
    //get absolute id
    public static int getAbsoluteId(String txt, DBTable DBtable) {
        int id = 0;

        if (txt != null) {
            
            try {
                System.out.println("SELECT `id` FROM `" + DBtable.getName() + "` WHERE `" + DBtable.getName() + "_id` = '" + txt + "'");
                ResultSet rs = MySQL.search("SELECT `id` FROM `" + DBtable.getName() + "` WHERE `" + DBtable.getName() + "_id` = '" + txt + "'");
                rs.next();
                id = rs.getInt("id");
            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return id;

    }

    //get name
    public static String getName(int id, DBTable DBtable) {
        String name = null;
        try {
            ResultSet rs = MySQL.search("SELECT `name` FROM `" + DBtable.getName() + "` WHERE `id` = " + id);
            rs.next();
            name = rs.getString("name");
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return name;
    }

    //get address
    public static HashMap<String, String> getAddress(int cityId) {
        HashMap<String, String> address = new HashMap();
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `city` INNER JOIN `district` ON `city`.`district_id` = `district`.`id` INNER JOIN `province` ON `district`.`province_id` = `province`.`id`  WHERE `city`.`id` = " + cityId);
            rs.next();
            address.put("province", rs.getString("province.name"));
            address.put("district", rs.getString("district.name"));
            address.put("city", rs.getString("city.name"));
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return address;
    }

    //get database table data
    public static ResultSet getTableData(DBTable DBTable) {

        ResultSet rs = null;
        try {
            rs = MySQL.search("SELECT * FROM `" + DBTable.getName() + "` ORDER BY `id`");
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    //get database table data
    public static ResultSet getTableData(DBTable DBTable, String id) {

        ResultSet rs = null;
        try {
            System.out.println("SELECT * FROM `" + DBTable.getName() + "` WHERE `"+DBTable.getName()+"_id` = '" + id + "' ORDER BY `id`");
            rs = MySQL.search("SELECT * FROM `" + DBTable.getName() + "` WHERE `"+DBTable.getName()+"_id` = '" + id + "' ORDER BY `id`");
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    //get database data
    public static ResultSet getDBData(String query) {

        ResultSet rs = null;
        try {
            rs = MySQL.search(query);
        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }
}
