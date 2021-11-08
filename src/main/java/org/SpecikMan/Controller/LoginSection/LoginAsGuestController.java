package org.SpecikMan.Controller.LoginSection;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.GetUUD;
import org.SpecikMan.Tools.ShowAlert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;

public class LoginAsGuestController {

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField txtUUD;

    @FXML
    private TextField txtUsername;

    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write username correctly");
        } else {
            txtUsername.getText();
            Account acc = new Account();
            acc.setIdAccount(GenerateID.genAccount());
            acc.setUsername(txtUsername.getText());
            acc.setUud(BCrypt.withDefaults().hashToString(12,txtUUD.getText().toCharArray()));
            acc.setIdRole("RL3"); //RL03 = Guest
            acc.setCreateDate(Date.valueOf(LocalDate.now()));
            acc.setLatestLoginDate(Date.valueOf(LocalDate.now()));
            acc.setCountLoginDate(1);
            acc.setCoin(0);
            accountDao.add(acc);
            ShowAlert.show("Notice","Signed up success");
            DisposeForm.Dispose(txtUsername);//Throw any control to get it's stage
        }
    }
    public void initialize(){
        txtUUD.setText(GetUUD.get());

    }

}