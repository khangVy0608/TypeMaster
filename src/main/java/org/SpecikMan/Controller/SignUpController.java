package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Date;
import java.time.LocalDate;

public class SignUpController {
    //region FXML Declares
    @FXML
    private Button btnSignUp;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;
    //endregion
    //region Controller Declares
    //endregion
    //region FXMl Classes
    @FXML
    public void onBtnSignUpClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()){
            ShowAlert.show("Warning!","Please write username correctly");
        } else if(txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
            ShowAlert.show("Warning!","Please write password correctly");
        } else if(txtConfirmPassword.getText() == null || txtConfirmPassword.getText().isEmpty()){
            ShowAlert.show("Warning!","Please write confirm password correctly");
        } else if(txtEmail.getText() == null || txtEmail.getText().isEmpty()||!txtEmail.getText().contains("@")){
            ShowAlert.show("Warning!","Please write email correctly");
        } else if(!txtPassword.getText().equals(txtConfirmPassword.getText())){
            ShowAlert.show("Warning!","2 passwords are not match");
        } else {
            boolean flag = false;
            for(Account i: accountDao.getAll()){
                if(i.getUsername().trim().equals(txtUsername.getText().trim())){
                    flag = true;
                }
            }
            if(!flag){
                Account acc = new Account();
                acc.setIdAccount(GenerateID.genAccount());
                acc.setUsername(txtUsername.getText());
                acc.setPassword(BCrypt.withDefaults().hashToString(12, txtPassword.getText().toCharArray()));
                acc.setCreateDate(Date.valueOf(LocalDate.now()));
                acc.setLatestLoginDate(Date.valueOf(LocalDate.now()));
                acc.setCountLoginDate(1);
                acc.setEmail(txtEmail.getText());
                acc.setIdRole("RL2");
                accountDao.add(acc);
                ShowAlert.show("Notice","Sign Up successfully");
                LoadForm.load("/fxml/Home.fxml","TypeMaster",false);
                FileRW.Write(FilePath.getLoginAcc(),acc.getIdAccount());
                DisposeForm.Dispose(txtUsername);
            } else {
                ShowAlert.show("Notice","Given username already exists");
            }
        }
    }
    //endregion
    //region Controller Classes
    //endregion

}
