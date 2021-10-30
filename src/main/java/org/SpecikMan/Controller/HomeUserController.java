package org.SpecikMan.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.SpecikMan.Tools.LoadForm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeUserController {

    @FXML
    private JFXButton btnAccountSetting;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnFeedback;

    @FXML
    private JFXButton btnGeneral;

    @FXML
    private JFXButton btnGeneralStatistics;

    @FXML
    private JFXButton btnMyLevels;

    @FXML
    private JFXButton btnSpecificStatistics;

    @FXML
    private ImageView imUser;

    @FXML
    private Label lbFullname;

    @FXML
    private Label lbUserName;

    @FXML
    private VBox pnl_scroll;

    @FXML
    void onbtnClickAccountSetting(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/ChangeAccount.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }

    @FXML
    void onbtnClickFeedback(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/Feedback.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }

    @FXML
    void onbtnClickGeneral(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = FXMLLoader.load(getClass().getResource("/fxml/General.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }


    @FXML
    void onbtnClickMyLevels(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/MyLevel.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }
}
