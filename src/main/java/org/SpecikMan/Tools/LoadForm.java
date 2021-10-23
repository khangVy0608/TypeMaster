package org.SpecikMan.Tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class LoadForm {
    public static void load(String fxmlPath, String title, boolean isWait) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LoadForm.class.getResource(fxmlPath)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            if (isWait) {
                stage.showAndWait();
            } else {
                stage.show();
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
