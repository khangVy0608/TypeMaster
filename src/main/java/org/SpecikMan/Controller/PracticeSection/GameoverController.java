package org.SpecikMan.Controller.PracticeSection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;

public class GameoverController {
    @FXML
    private Button btnMenu;
    @FXML
    public void btnRetryClicked(){
        FileRW.Write(FilePath.getRetryOrMenu(), "retry");
        DisposeForm.Dispose(btnMenu);
    }
    @FXML
    public void btnMenuClicked(){
        FileRW.Write(FilePath.getRetryOrMenu(), "menu");
        DisposeForm.Dispose(btnMenu);
    }
}
