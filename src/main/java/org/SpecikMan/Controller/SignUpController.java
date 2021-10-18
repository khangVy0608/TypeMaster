package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Tools.ShowAlert;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.SpecikMan.Tools.GenerateID;

import javax.swing.*;

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
    private final AccountDao accountDao = new AccountDao();
    //endregion
    //region FXMl Classes
    @FXML
    public void onBtnSignUpClicked(MouseEvent event) {
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()){
            ShowAlert.ShowAlert("Warning!","Please write username correctly");
        } else if(txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
            ShowAlert.ShowAlert("Warning!","Please write password correctly");
        } else if(txtConfirmPassword.getText() == null || txtConfirmPassword.getText().isEmpty()){
            ShowAlert.ShowAlert("Warning!","Please write confirm password correctly");
        } else if(txtEmail.getText() == null || txtEmail.getText().isEmpty()||!txtEmail.getText().contains("@")){
            ShowAlert.ShowAlert("Warning!","Please write email correctly");
        } else if(!txtPassword.getText().equals(txtConfirmPassword.getText())){
            ShowAlert.ShowAlert("Warning!","2 passwords are not match");
        } else {
            Account acc = new Account();
            acc.setIdAccount(GenerateID.genAccount());
            acc.setUsername(txtUsername.getText());
            acc.setPassword(BCrypt.withDefaults().hashToString(12, txtPassword.getText().toCharArray()));
            acc.setEmail(txtEmail.getText());
            acc.setIdRole("RL02");
            accountDao.add(acc);
            ShowAlert.ShowAlert("Notice","Sign Up successfully");
        }
    }
    //endregion
    //region Controller Classes
    //endregion

}
