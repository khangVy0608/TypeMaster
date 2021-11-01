package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.Tools.LoadForm;

public class HomeController
{
    @FXML
    private Button btnPlay;

    @FXML
    public void onBtnPlayClicked(MouseEvent event) {
        LoadForm.load("/fxml/PracticeFXMLs/Dashboard.fxml","Play Dashboard",false);
    }

    @FXML
    void btnStatClicked(MouseEvent event) {
        LoadForm.load("/fxml/SatisticsFXMLs/HomeUser.fxml","Play Dashboard",false);
    }

}
