/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.SpecikMan.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Login_RegisterController implements Initializable {
    
    @FXML
    private VBox movedVbox;
    
    private Parent fxml;
    
    @FXML
    public void open_signup(ActionEvent event){
         TranslateTransition t = new TranslateTransition(Duration.seconds(1),movedVbox);
        t.setToX(0);
        t.play();
        t.setOnFinished(e->{
            try {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignUp.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
            }
        });
    }
    @FXML
    public void open_signin(ActionEvent event){
        TranslateTransition t = new TranslateTransition(Duration.seconds(1),movedVbox);
        t.setToX(movedVbox.getLayoutX() *21);
        t.play();
        t.setOnFinished(e->{
            try {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignIn.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
            }
        });
    }
    @FXML
    public void close(MouseEvent event){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1),movedVbox);
        t.setToX(movedVbox.getLayoutX() *21);
        t.play();
        t.setOnFinished(e->{
            try {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/SignIn.fxml")));
                movedVbox.getChildren().removeAll();
                movedVbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
            }
        });
    }
    
    
}
