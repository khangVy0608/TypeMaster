package org.SpecikMan.Controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Tools.GetUUD;
import org.SpecikMan.Tools.LoadForm;
import org.SpecikMan.Tools.ShowAlert;

import java.io.IOException;
import java.util.List;

public class SignInController {
    //endregion
    //region Controller Declares
    private final AccountDao accountDao = new AccountDao();
    private final List<Account> accounts = accountDao.getAll();
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
            } else {
                ShowAlert.show("Warning!", "Sign In Failed");
            }
        }
    }

    @FXML
    public void onHlForgetPasswordClicked(MouseEvent e) throws IOException {
        LoadForm.load("/fxml/ForgotPassword.fxml", "Forgot Password");
    }

    @FXML
    public void onHlLoginGuestClicked(MouseEvent mouseEvent) {
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
            LoadForm.load("/fxml/LoginAsGuest.fxml", "Login as Guest");
        } else {
            ShowAlert.show("Warning!", "Welcome back " + acc.getUsername());
        }
    }

    //endregion
    //region Controller Class
    public boolean signIn(String username, String password) {
        for (Account account : accounts) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), account.getPassword());
            if ((account.getUsername().equals(username) || account.getEmail().equals(username)) && result.verified) {
                return true;
            }
        }
        return false;
    }
    //endregion
}
