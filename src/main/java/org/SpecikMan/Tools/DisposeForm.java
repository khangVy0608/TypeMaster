package org.SpecikMan.Tools;

import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DisposeForm {
    public static void Dispose(Control control){
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
    }
}
