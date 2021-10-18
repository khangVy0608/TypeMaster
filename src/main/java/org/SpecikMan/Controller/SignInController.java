package org.SpecikMan.Controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Tools.ShowAlert;

import java.util.List;

public class SignInController {
    //endregion
    //region Controller Declares
    private final AccountDao accountDao = new AccountDao();
    //region FXML Declares
    @FXML
    private Button btnSignIn;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    //endregion

    //region FXML Class
    @FXML
    public void onBtnSignInClicked(MouseEvent e) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(signIn(username,password)){
            ShowAlert.ShowAlert("Warning!","Sign In Success");
        } else {
            ShowAlert.ShowAlert("Warning!","Sign In Failed");
        }
    }

    //endregion
    //region Controller Class
    public boolean signIn(String username, String password) {
        List<Account> accounts = accountDao.getAll();
        for(Account account:accounts){
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),account.getPassword());
            if((account.getUsername().equals(username)||account.getEmail().equals(username))&&result.verified){
                return true;
            }
        }
        return false;
    }
    //endregion
}
