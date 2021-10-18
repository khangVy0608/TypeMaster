package org.SpecikMan.Tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoadForm {
    public void LoadForm(String fxmlPath){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
