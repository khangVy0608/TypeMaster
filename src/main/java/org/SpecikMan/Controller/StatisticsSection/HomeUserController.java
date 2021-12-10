package org.SpecikMan.Controller.StatisticsSection;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    void onClickImage(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/EditImage.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
//        LoadForm.load("/fxml/EditImage.fxml","EditImage",false);
    }

    @FXML
    void onbtnClickAccountSetting(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/ChangeAccount.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }

    @FXML
    void onbtnClickFeedback(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/Feedback.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }

    @FXML
    void onbtnClickGeneral(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/General.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }


    @FXML
    void onbtnClickMyLevels(MouseEvent event) throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = (Node) FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/MyLevel.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }
    public void initialize() throws FileNotFoundException {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        lbUserName.setText(account.getUsername());
        lbFullname.setText(account.getFullName());
        Image image = new Image(new FileInputStream(account.getPathImage()),123,123,false,false);
        imUser.setImage(image);
    }
    @FXML
    public void btnGeneralStatClicked() throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/StatsAll.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }
    @FXML
    public void btnSpecClicked() throws IOException {
        pnl_scroll.getChildren().clear();
        Node[] nodes = new Node[1];
        nodes[0] = FXMLLoader.load(getClass().getResource("/fxml/StatisticsFXMLs/StatsOne.fxml"));
        pnl_scroll.getChildren().add(nodes[0]);
    }
    @FXML
    void btnBackClicked(){
        DisposeForm.Dispose(lbFullname);
    }
}
