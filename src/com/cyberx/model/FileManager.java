/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyberx.model;

//import com.cyberx.controllers.AddEmployeeController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author LAKSHAN KAWSHALYA
 */
public class FileManager {

    FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
    File imageFile;

    public static final String USER_IMAGE_PATH = "profile_img/";
    public static final String COMPANY_LOGO_PATH = "company_logo_img/";
    public static final String PRODUCT_IMAGE_PATH = "product_img/";
    public static final String DEFAULT_COMPANY_LOGO = "default-logo-image.png";
    public static final String DEFAULT_USER_IMAGE = "default-user-image.png";
    public static final String DEFAULT_PRODUCT_IMAGE = "default-product-image.png";

    private static String getPath(String path) {
        switch (path) {
            case USER_IMAGE_PATH:
                path = USER_IMAGE_PATH;
                break;

            case COMPANY_LOGO_PATH:
                path = COMPANY_LOGO_PATH;
                break;

            case PRODUCT_IMAGE_PATH:
                path = PRODUCT_IMAGE_PATH;
                break;
        }
        return path;
    }

    public static String copyImage(java.io.File file, String fileName, String copyLocation) {

        String path = getPath(copyLocation);

        try {
            fileName = fileName + ".png";

            Path src = Paths.get(file.getAbsolutePath().replace("\\", "//"));
            Path newDir = Paths.get("src//user_uploads//".concat(path));

            Files.createDirectories(newDir);

            Path target = newDir.resolve(fileName);
            Files.copy(src, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
        }

        return fileName;

    }

    public File getImage(FileChooser fileChooser, Circle imageCircle) {
        fileChooser.getExtensionFilters().add(imageFilter);
        imageFile = fileChooser.showOpenDialog(new Stage());

        //setting image
        InputStream absoluteFilePath;
        try {
            if (imageFile != null) {
                absoluteFilePath = new FileInputStream(imageFile);
                Image img = new Image(absoluteFilePath);
                imageCircle.setFill(new ImagePattern(img));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageFile;
    }

    public void setImage(Circle imageCircle, String fileName, String filePath) {

        String path = getPath(filePath);
        //setting image
        Image img = new Image(this.getClass().getResourceAsStream("/user_uploads/" + path + fileName));
        imageCircle.setFill(new ImagePattern(img));

    }

}
