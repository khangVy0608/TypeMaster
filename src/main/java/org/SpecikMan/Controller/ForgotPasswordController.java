package org.SpecikMan.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private final DecimalFormat dFormat = new DecimalFormat("00");

    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getAll();
        if (txtVerificationCode.getText() == null || txtVerificationCode.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Verification Code musts not empty");
        } else {
            Account acc = null;
            String email = txtEmail.getText();
            for (Account element : accounts) {
                if (element.getEmail() != null) {
                    if (element.getEmail().equals(email)) {
                        acc = element;
                    }
                }
            }
            assert acc != null;
            if (acc.getVerificationCode().equals(txtVerificationCode.getText())) {
                ShowAlert.show("Notice!", "Verify Success");
                FileRW.Write(FilePath.getForgotId(), acc.getIdAccount());
                LoadForm.load("/fxml/ChangeInformation.fxml", "Change Information", false);
                DisposeForm.Dispose(btnConfirm);//Pass any controls to get it's stage
            } else {
                ShowAlert.show("Notice!", "Verify Failed");
            }
        }
    }

    @FXML
    void onBtnSendCodeClicked(MouseEvent event) {
        Account acc = null;
        AccountDao accountDao = new AccountDao();
        List<Account> accounts = accountDao.getAll();
        if (txtEmail.getText() == null || txtEmail.getText().isEmpty() || !txtEmail.getText().contains("@")) {
            ShowAlert.show("Warning!", "Please write email correctly");
        } else {
            String email = txtEmail.getText();
            for (Account element : accounts) {
                if (element.getEmail() != null) {
                    if (element.getEmail().equals(email)) {
                        acc = element;
                    }
                }
            }
            if (acc == null) {
                ShowAlert.show("Warning!", "Email is not found");
            } else {
                String num = GenerateRandomNumbers.generate();
                acc.setVerificationCode(num);
                accountDao.update(acc);
                MailSender.send(email, num);
                ShowAlert.show("Notice", "Already sent verification code. Check your email");
                txtEmail.setDisable(true);
                btnConfirm.setDisable(false);
                btnSendCode.setDisable(true);
                btnSendCode.setText("00:20");
                second = 20;
                minute = 0;
                Timer();
                timer.start();
            }
        }
    }

    public void Timer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //javaFX operations should go here
                        second--;
                        ddSecond = dFormat.format(second);
                        ddMinute = dFormat.format(minute);
                        btnSendCode.setText(ddMinute + ":" + ddSecond);
                        if (second == -1) {
                            second = 59;
                            minute--;
                            ddSecond = dFormat.format(second);
                            ddMinute = dFormat.format(minute);
                            btnSendCode.setText(ddMinute + ":" + ddSecond);
                        }
                        if (minute == 0 && second == 0) {
                            timer.stop();
                            btnSendCode.setDisable(false);
                            btnSendCode.setText("Send Again");
                            txtEmail.setDisable(false);
                        }
                    }
                });
            }
        });
    }
}


