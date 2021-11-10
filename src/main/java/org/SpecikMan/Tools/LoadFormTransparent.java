package org.SpecikMan.Tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class LoadFormTransparent {
    public static void load(String fxmlPath){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoadForm.class.getResource(fxmlPath)));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
