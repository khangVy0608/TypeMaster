/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.SpecikMan.Controller;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.Transition;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login_RegisterController implements Initializable {
    @FXML
    private VBox movedVbox;
    @FXML
    private Pane pane;
    private Parent fxml;

    @FXML
    public void open_signup() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.75), movedVbox);
        t.setToX(0);
        t.play();
        t.setOnFinished(e -> {
            try {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignUp.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    public void open_signin() {
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.75), movedVbox);
        t.setToX(movedVbox.getLayoutX() * 21);
        t.play();
        t.setOnFinished(e -> {
            try {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignIn.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.75), movedVbox);
        t.setToX(movedVbox.getLayoutX() * 21);
        t.play();
        t.setOnFinished(e -> {
            try {
                fill();
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignIn.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void fill(){
       FillTransition t = new FillTransition();
        t.setShape(pane.getShape());
       t.setDuration(Duration.millis(5000));
       t.setAutoReverse(true);
        t.setCycleCount(50);
        t.setFromValue(Color.valueOf("#2196f3"));
        t.setToValue(Color.valueOf("#313538"));

        t.play();
    }
}
