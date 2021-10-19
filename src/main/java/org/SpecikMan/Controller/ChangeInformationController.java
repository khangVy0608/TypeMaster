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
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.ShowAlert;

import java.util.List;
import java.util.Optional;

public class ChangeInformationController {

    @FXML
    private Button btnConfirm;

    @FXML
    private Hyperlink hlChangeEmail;

    @FXML
    private Hyperlink hlChangeUsername;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtUsername;

    private final AccountDao accountDao = new AccountDao();
    private final List<Account> accounts = accountDao.getAll();
    private static final String FORGOT_ID_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\ForgotPassword_id.txt";
    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()){
            ShowAlert.show("Warning!","Please write username correctly");
        } else if(txtEmail.getText() == null || txtEmail.getText().isEmpty()||!txtEmail.getText().contains("@")) {
            ShowAlert.show("Warning!","Please write email correctly");
        } else if(txtNewPassword.getText() == null || txtNewPassword.getText().isEmpty()){
            ShowAlert.show("Warning!","Please write new password correctly");
        } else if(txtConfirmPassword.getText() == null || txtConfirmPassword.getText().isEmpty()){
            ShowAlert.show("Warning!","Please write confirm password correctly");
        } else if(!txtConfirmPassword.getText().equals(txtNewPassword.getText())){
            ShowAlert.show("Warning!","2 passwords are not match");
        } else {
            Account account = null;
            for (Account i : accounts) {
                if (i.getIdAccount().equals(FileRW.Read(FORGOT_ID_PATH))) {
                    account = i;
                }
            }
            assert account != null;
            account.setUsername(txtUsername.getText());
            account.setEmail(txtEmail.getText());
            account.setPassword(BCrypt.withDefaults().hashToString(12, txtNewPassword.getText().toCharArray()));
            accountDao.update(account);
            ShowAlert.show("Change Information","Change success! Please log in again!");
            DisposeForm.Dispose(txtConfirmPassword);//Throw any controls to get it's stage
        }
    }

    @FXML
    void onHlChangeEmailClicked(MouseEvent event) {
        if(hlChangeEmail.getText().equals("Change")){
            txtEmail.setDisable(false);
            hlChangeEmail.setText("Revert");
        } else {
            txtEmail.setDisable(true);
            hlChangeEmail.setText("Change");
            Account acc = null;
            for (Account i : accounts) {
                if (i.getIdAccount().equals(FileRW.Read(FORGOT_ID_PATH))) {
                    acc = i;
                }
            }
            assert acc != null;
            txtEmail.setText(acc.getEmail());
        }
    }

    @FXML
    void onHlChangeUsernameClicked(MouseEvent event) {
        if(hlChangeUsername.getText().equals("Change")){
            txtUsername.setDisable(false);
            hlChangeUsername.setText("Revert");

        } else {
            txtUsername.setDisable(true);
            hlChangeUsername.setText("Change");
            Account acc = null;
            for (Account i : accounts) {
                if (i.getIdAccount().equals(FileRW.Read(FORGOT_ID_PATH))) {
                    acc = i;
                }
            }
            assert acc != null;
            txtUsername.setText(acc.getUsername());
        }
    }
    public void initialize(){
        Account acc = null;
        for (Account i : accounts) {
            if (i.getIdAccount().equals(FileRW.Read(FORGOT_ID_PATH))) {
                acc = i;
            }
        }
        assert acc != null;
        txtEmail.setText(acc.getEmail());
        txtUsername.setText(acc.getUsername());
    }


}
