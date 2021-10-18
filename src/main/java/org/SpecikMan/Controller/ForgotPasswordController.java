package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Tools.GenerateRandomNumbers;
import org.SpecikMan.Tools.MailSender;
import org.SpecikMan.Tools.ShowAlert;

import java.util.List;

public class ForgotPasswordController {
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnSendCode;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtVerificationCode;
    private final AccountDao accountDao = new AccountDao();
    private final List<Account> accounts = accountDao.getAll();
    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        Account acc = null;
        String email = txtEmail.getText();
        for (Account element : accounts) {
            if (element.getEmail().equals(email)) {
                acc = element;
            }
        }
        assert acc != null;
        if(acc.getVerificationCode().equals(txtVerificationCode.getText())) {
            ShowAlert.ShowAlert("Notice!","Verify Success");
        } else {
            ShowAlert.ShowAlert("Notice!","Verify Failed");
        }
    }

    @FXML
    void onBtnSendCodeClicked(MouseEvent event) {
        Account acc = null;
        String email = txtEmail.getText();
        for (Account element : accounts) {
            if (element.getEmail().equals(email)) {
                acc = element;
            }
        }
        if (acc == null) {
            ShowAlert.ShowAlert("Warning!", "Email is not found");
        } else {
            ShowAlert.ShowAlert("Notice","Already sent verification code. Check your email");
            String num = GenerateRandomNumbers.generate();
            acc.setVerificationCode(num);
            accountDao.update(acc);
            MailSender.send(email, num);
            txtEmail.setDisable(true);
            btnConfirm.setDisable(false);
            btnSendCode.setDisable(true);
        }
    }
}


