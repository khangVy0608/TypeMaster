package org.SpecikMan.Controller.LoginSection;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SignInController {
    //endregion
    //region Controller Declares
    //region FXML Declares
    @FXML
    private Button btnSignIn;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Hyperlink hlForgetPassword;
    //endregion
    //region FXML Class
    @FXML
    public void onBtnSignInClicked(MouseEvent e) {
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write username correctly");
        } else if (txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write password correctly");
        } else {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            if (signIn(username, password)) {
                ShowAlert.show("Warning!", "Sign In Success");
                LoadForm.load("/fxml/Home.fxml","TypeMaster",false);
                DisposeForm.Dispose(txtUsername);
            } else {
                ShowAlert.show("Warning!", "Sign In Failed");
            }
        }
    }

    @FXML
    public void onHlForgetPasswordClicked(MouseEvent e) throws IOException {
        LoadForm.load("/fxml/LoginFXMLs/ForgotPassword.fxml", "Forgot Password",false);
    }

    @FXML
    public void onHlLoginGuestClicked(MouseEvent mouseEvent) {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getAll();
        Account acc = null;
        for (Account i : accounts) {
            if (i.getUud() != null) {
                BCrypt.Result result = BCrypt.verifyer().verify(GetUUD.get().toCharArray(), i.getUud()); //
                if (result.verified) {
                    acc = i;
                }
            }
        }
        if (acc == null) {
            LoadForm.load("/fxml/LoginFXMLs/LoginAsGuest.fxml", "Login as Guest",false);
        } else {
            ShowAlert.show("Warning!", "Welcome back " + acc.getUsername());
            LoadForm.load("/fxml/Home.fxml","TypeMaster",false);
            FileRW.Write(FilePath.getLoginAcc(),acc.getIdAccount());
            if(!acc.getLatestLoginDate().equals(Date.valueOf(LocalDate.now()))){
                acc.setLatestLoginDate(Date.valueOf(LocalDate.now()));
                acc.setCountLoginDate(acc.getCountLoginDate()+1);
                accountDao.update(acc);
            }
            DisposeForm.Dispose(txtUsername);
        }
    }

    //endregion
    //region Controller Class
    public boolean signIn(String username, String password) {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getAll();
        for (Account account : accounts) {
            if(account.getNameRole().equals("User")) {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), account.getPassword());
                if ((account.getUsername().equals(username) || account.getEmail().equals(username)) && result.verified) {
                    FileRW.Write(FilePath.getLoginAcc(), account.getIdAccount());
                    if(!account.getLatestLoginDate().equals(Date.valueOf(LocalDate.now()))){
                        account.setLatestLoginDate(Date.valueOf(LocalDate.now()));
                        account.setCountLoginDate(account.getCountLoginDate()+1);
                        accountDao.update(account);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    //endregion
}
