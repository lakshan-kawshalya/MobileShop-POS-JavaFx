package com.cyberx.model;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class InputManager {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_NUMBER_REGEX = Pattern.compile("\\d+");
    private static final Pattern VALID_QUANTITY_REGEX = Pattern.compile("[1-9][0-9]*");
    private static final Pattern VALID_PRICE_REGEX = Pattern.compile("(0|0[.]|0.[0-9]*)|[1-9]|[0-9]*|[1-9][0-9]*[.]|[1-9][0-9]*[.][0-9]*");

    //validate text field with error hint
    public static boolean isValidTextField(TextField txtField) {
        String txt = txtField.getText();
        boolean isValid = true;

        if (txt.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate email field
    public static boolean isValidEmail(String email) {
        boolean isValid = true;

        if (email.isBlank()) {
            isValid = false;
        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            isValid = false;
        }

        return isValid;
    }

    //validate email field with error hint
    public static boolean isValidEmail(TextField txtField) {
        String email = txtField.getText();
        boolean isValid = true;

        if (email.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate contact no field
    public static boolean isValidContactNo(String contact_no) {
        boolean isValid = true;

        if (contact_no.isBlank()) {
            isValid = false;
        } else if (contact_no.length() != 10) {
            isValid = false;
        }

        return isValid;
    }

    //validate contact no field with error hint
    public static boolean isValidContactNo(TextField txtField) {
        String contact_no = txtField.getText();
        boolean isValid = true;

        if (contact_no.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else if (contact_no.length() != 10) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate combo box field with error hint
    public static boolean isValidComboBox(ComboBox<String> comboBox) {
        int index = comboBox.getSelectionModel().getSelectedIndex();
        boolean isValid = true;

        if (index == 0) {
            if (!comboBox.getStyleClass().contains("invalid-input")) {
                comboBox.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (comboBox.getStyleClass().contains("invalid-input")) {
                comboBox.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate password field
    public static boolean isValidPassword(String password) {
        boolean isValid = true;

        if (password.isBlank()) {
            isValid = false;
        }

        return isValid;
    }

    //validate date chooser field
    public static boolean isValidDate(DatePicker dchField) {
        boolean isValid = true;

        if (dchField == null) {
            if (!dchField.getStyleClass().contains("invalid-input")) {
                dchField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else if (dchField.getValue().toString().isBlank()) {
            if (!dchField.getStyleClass().contains("invalid-input")) {
                dchField.getStyleClass().add("invalid-input");
            }
        } else {
            if (dchField.getStyleClass().contains("invalid-input")) {
                dchField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate password field with error hint
    public static boolean isValidPassword(TextField txtField) {
        String password = txtField.getText();
        boolean isValid = true;

        if (password.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate text field with error hint
    public static boolean isValidQuantity(TextField txtField) {
        String txt = txtField.getText();
        boolean isValid = true;

        if (txt.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else if (!VALID_QUANTITY_REGEX.matcher(txt).matches()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    //validate price field with error hint
    public static boolean isValidPrice(TextField txtField) {
        String txt = txtField.getText();
        boolean isValid = true;

        if (txt.isBlank()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else if (!VALID_PRICE_REGEX.matcher(txt).matches()) {
            if (!txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().add("invalid-input");
            }
            isValid = false;
        } else {
            if (txtField.getStyleClass().contains("invalid-input")) {
                txtField.getStyleClass().remove("invalid-input");
            }
        }

        return isValid;
    }

    public static boolean isDigit(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) >= 47 && (int) number.charAt(j) <= 57))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidQtyKey(KeyEvent evt) {
        String character = evt.getCharacter();
        if (!InputManager.isDigit(character)) {
            evt.consume();
            return false;
        }

        return true;
    }
    
    public static boolean isValidDateKey(KeyEvent evt) {
        String character = evt.getCharacter();
        if (!InputManager.isDigit(character)) {
            evt.consume();
            return false;
        }

        return true;
    }

    public static boolean isValidPriceKey(KeyEvent evt) {
        String character = evt.getCharacter();

        if (!InputManager.isDigit(character)) {

            if (!character.equalsIgnoreCase(".")) {
                evt.consume();
                return false;
            }

        }

        return true;
    }

    //load combo boxes 
    //load province 
    public static void loadProvince(ComboBox<String> comboBox) {

        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Select");

            ResultSet rs = MySQL.search("SELECT * FROM `province` ORDER BY `id`");

            while (rs.next()) {
                list.add(rs.getString("name") + " Province");
            }

            comboBox.setItems(list);
            comboBox.getSelectionModel().select("Select");

        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //load districts when province selected
    public static void loadDistrict(ComboBox<String> comboBox, ComboBox<String> provinceComboBox) {

        if (provinceComboBox.getSelectionModel().getSelectedIndex() == 0) {
            comboBox.setDisable(true);
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.setDisable(false);
            try {

                ObservableList<String> list = FXCollections.observableArrayList();
                list.add("Select");

                ResultSet rs = MySQL.search("SELECT * FROM `district` WHERE `province_id` = " + DBManager.getId(provinceComboBox.getSelectionModel().getSelectedItem(), DBTable.PROVINCE) + " ORDER BY `id`");

                while (rs.next()) {
                    list.add(rs.getString("name"));
                }

                comboBox.setItems(list);
                comboBox.getSelectionModel().select("Select");

            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //load cities when district selected
    public static void loadCity(ComboBox<String> comboBox, ComboBox<String> districtComboBox) {

        if (districtComboBox.getSelectionModel().getSelectedIndex() == 0) {
            comboBox.setDisable(true);
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.setDisable(false);
            try {
                ObservableList<String> list = FXCollections.observableArrayList();
                list.add("Select");

                ResultSet rs = MySQL.search("SELECT * FROM `city` WHERE `district_id` = " + DBManager.getId(districtComboBox.getSelectionModel().getSelectedItem(), DBTable.DISTRICT) + " ORDER BY `id`");

                while (rs.next()) {
                    list.add(rs.getString("name"));
                }

                comboBox.setItems(list);
                comboBox.getSelectionModel().select("Select");

            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //load cities when province selected
    public static void loadCity(ComboBox<String> comboBox, ComboBox<String> provinceComboBox, boolean districtStatus) {

        if (!districtStatus) {
            if (provinceComboBox.getSelectionModel().getSelectedIndex() == 0) {
                comboBox.setDisable(true);
            } else {
                comboBox.setDisable(false);
                try {
                    ObservableList<String> list = FXCollections.observableArrayList();
                    list.add("Select");

                    ResultSet rs = MySQL.search("SELECT * FROM `city` WHERE `district_id` IN (SELECT `id` FROM `district` WHERE `province_id` = " + DBManager.getId(provinceComboBox.getSelectionModel().getSelectedItem(), DBTable.PROVINCE) + ") ORDER BY `city`.`id`");

                    while (rs.next()) {
                        list.add(rs.getString("name"));
                    }

                    comboBox.setItems(list);
                    comboBox.getSelectionModel().select("Select");

                } catch (Exception ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    //load cities with add cities when district selected
    public static void loadCity(ComboBox<String> comboBox, ComboBox<String> districtComboBox, Button addCityBtn) {

        if (districtComboBox.getSelectionModel().getSelectedIndex() == 0) {
            comboBox.setDisable(true);
            addCityBtn.setDisable(true);
        } else {
            comboBox.setDisable(false);
            addCityBtn.setDisable(false);
            try {
                ObservableList<String> list = FXCollections.observableArrayList();
                list.add("Select");

                ResultSet rs = MySQL.search("SELECT * FROM `city` WHERE `district_id` = " + DBManager.getId(districtComboBox.getSelectionModel().getSelectedItem(), DBTable.DISTRICT) + " ORDER BY `id`");

                while (rs.next()) {
                    list.add(rs.getString("name"));
                }

                comboBox.setItems(list);
                comboBox.getSelectionModel().select("Select");

            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void loadComboBox(ComboBox<String> comboBox, DBTable DBtable) {

        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Select");

            ResultSet rs = MySQL.search("SELECT * FROM `" + DBtable.getName() + "` ORDER BY `id`");

            while (rs.next()) {
                list.add(rs.getString("name"));
            }

            comboBox.setItems(list);
            comboBox.getSelectionModel().select("Select");

        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //load sub-category when category selected
    public static void loadSubCategory(ComboBox<String> comboBox, ComboBox<String> categoryComboBox) {

        if (categoryComboBox.getSelectionModel().getSelectedIndex() == 0) {
            comboBox.setDisable(true);
            comboBox.getSelectionModel().selectFirst();
        } else {
            comboBox.setDisable(false);
            try {

                ObservableList<String> list = FXCollections.observableArrayList();
                list.add("Select");

                ResultSet rs = MySQL.search("SELECT * FROM `sub_category` WHERE `category_id` = " + DBManager.getId(categoryComboBox.getSelectionModel().getSelectedItem(), DBTable.CATEGORY) + " ORDER BY `id`");

                while (rs.next()) {
                    list.add(rs.getString("name"));
                }

                comboBox.setItems(list);
                comboBox.getSelectionModel().select("Select");

            } catch (Exception ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void loadSupplierComboBox(ComboBox<String> comboBox) {

        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Select");

            ResultSet rs = MySQL.search("SELECT * FROM `supplier` ORDER BY `id`");

            while (rs.next()) {
                list.add(rs.getString("fname") + " -- @ -- " + DBManager.getName(rs.getInt("company_id"), DBTable.COMPANY));
            }

            comboBox.setItems(list);
            comboBox.getSelectionModel().select("Select");

        } catch (Exception ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
