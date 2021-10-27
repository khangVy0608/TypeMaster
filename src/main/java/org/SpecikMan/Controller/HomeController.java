package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class HomeController
{
    @FXML
    private Button btnPlay;

    @FXML
    public void onBtnPlayClicked(MouseEvent event) {
        LoadForm.load("/fxml/Dashboard.fxml","Play Dashboard",false);
    }

}
