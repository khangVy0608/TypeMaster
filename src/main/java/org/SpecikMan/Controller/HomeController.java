package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;
import org.SpecikMan.Tools.LoadFormTransparent;

public class HomeController
{
    @FXML
    private Button btnPlay;
    @FXML
    private Label lbUsername;
    @FXML
    private Button btnRank;
    public void initialize(){
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        lbUsername.setText("Welcome back , "+account.getUsername());
        btnRank.setOnMouseClicked(e->{
            LoadForm.load("/fxml/RankFXMLs/RankMenu.fxml","Rank Menu",false);
        });
    }


    @FXML
    public void onBtnPlayClicked(MouseEvent event) {
        LoadForm.load("/fxml/PracticeFXMLs/Dashboard.fxml","Play Dashboard",false);
    }

    @FXML
    void btnStatClicked(MouseEvent event) {
        LoadForm.load("/fxml/StatisticsFXMLs/HomeUser.fxml","Account Statistics",false);
    }
    @FXML
    void btnShopClicked(){
        LoadForm.load("/fxml/ShopFXMLs/Shop.fxml","Shop",false);
    }
    @FXML
    void btnSignoutClicked(){
        LoadFormTransparent.load("/fxml/LoginFXMLs/Login_Register.fxml");
        DisposeForm.Dispose(lbUsername);
    }
    @FXML
    void btnInventoryClicked(){
        LoadForm.load("/fxml/InventoryFXMLs/Inventory.fxml","Inventory",false);
    }

}
