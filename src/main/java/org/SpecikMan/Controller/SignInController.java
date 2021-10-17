package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;

import javax.swing.*;

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
        if (login(username, password)) {
            JOptionPane.showMessageDialog(null, "Login Successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed!");
        }
    }

    //endregion
    //region Controller Class
    public boolean login(String username, String password) {
        Account acc = null;
        for (Account item : accountDao.getAll()) {
            if (item.getUsername().equals(username) && item.getPassword().equals(password)) {
                acc = item;
            }
        }
        return acc != null;
    }
    //endregion
}
