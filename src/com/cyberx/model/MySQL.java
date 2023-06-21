/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class MySQL {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "L.K.@2002#code";
    private static final String DATABASE = "mobile-shop-POS-system-DB";

    private static Connection connection;

    //create connection
    private static Statement createConnection() throws Exception {

        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
        }
        return connection.createStatement();

    }

    //insert, update, delete method
    public static void iud(String query) {

        try {
            createConnection().executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //search method
    public static ResultSet search(String query) throws Exception {
        return createConnection().executeQuery(query);
    }

    
    //database connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE, USERNAME, PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
