package org.SpecikMan.Tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LoadForm {
    public static void load(String fxmlPath, String title){
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoadForm.class.getResource(fxmlPath)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
