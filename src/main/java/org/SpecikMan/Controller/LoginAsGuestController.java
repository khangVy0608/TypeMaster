package org.SpecikMan.Controller;

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

public class LoginAsGuestController {

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField txtUUD;

    @FXML
    private TextField txtUsername;

    private final AccountDao accountDao = new AccountDao();
    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write username correctly");
        } else {
            txtUsername.getText();
            Account acc = new Account();
            acc.setIdAccount(GenerateID.genAccount());
            acc.setUsername(txtUsername.getText());
            acc.setUud(BCrypt.withDefaults().hashToString(12,txtUUD.getText().toCharArray()));
            acc.setIdRole("RL03"); //RL03 = Guest
            accountDao.add(acc);
            ShowAlert.show("Notice","Login Success");
            DisposeForm.Dispose(txtUsername);//Throw any control to get it's stage
        }
    }
    public void initialize(){
        txtUUD.setText(GetUUD.get());

    }

}