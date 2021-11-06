package org.SpecikMan.Controller.SatisticsSection;

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

public class VerifyCodeControllers {
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
    boolean isGuest = false;

    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        if(txtVerificationCode.getText().trim().equals(account.getVerificationCode().trim())){
            ShowAlert.show("Notice!", "Verify Success");
            LoadForm.load("/fxml/SatisticsFXMLs/ChangeSecurityInfo.fxml","Change Security Infomation",false);
            if(isGuest){
                account.setEmail(txtEmail.getText().trim());
                accountDao.update(account);
            }
            DisposeForm.Dispose(txtEmail);
        } else {
            ShowAlert.show("Notice!", "Verify Failed");
        }
    }

    @FXML
    void onBtnSendCodeClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        if (txtEmail.getText() == null || txtEmail.getText().isEmpty() || !txtEmail.getText().contains("@")) {
            ShowAlert.show("Warning!", "Please write email correctly");
        } else {
            String email = txtEmail.getText();
            String num = GenerateRandomNumbers.generate();
            account.setVerificationCode(num);
            accountDao.update(account);
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

    public void initialize() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        if (account.getNameRole().equals("Guest")) {
            isGuest = true;
        }
        if(!isGuest){
            txtEmail.setDisable(true);
            txtEmail.setText(account.getEmail());
        }
    }
}


