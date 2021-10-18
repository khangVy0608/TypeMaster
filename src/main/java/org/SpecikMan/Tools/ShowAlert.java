package org.SpecikMan.Tools;

import javafx.scene.control.Alert;

public class ShowAlert {
    public static void ShowAlert(String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notify");
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
