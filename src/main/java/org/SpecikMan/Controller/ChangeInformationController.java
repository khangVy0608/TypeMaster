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

    /* Đường dẫn đến file ForgotPassword_id.txt để nhận dữ liệu được truyền từ form ForgotPassword*/
    private static final String FORGOT_ID_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\ForgotPassword_id.txt";
    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        /* Kiểm tra nhập*/
        AccountDao accountDao = new AccountDao();
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
            Account account = accountDao.get(FileRW.Read(FORGOT_ID_PATH));
            if (account != null) {
                account.setUsername(txtUsername.getText());
                account.setEmail(txtEmail.getText());
                account.setPassword(BCrypt.withDefaults().hashToString(12, txtNewPassword.getText().toCharArray()));
                accountDao.update(account); // Update account
                ShowAlert.show("Change Information", "Change success! Please log in again!");
                DisposeForm.Dispose(txtConfirmPassword);//Throw any controls to get it's stage // Xóa form hiện tại (=dispose() bên netbeans)
            }
        }
    }

    @FXML
    void onHlChangeEmailClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        if(hlChangeEmail.getText().equals("Change")){ //HyperLink là change nhấn vào -> Revert
            txtEmail.setDisable(false);
            hlChangeEmail.setText("Revert");
        } else {
            txtEmail.setDisable(true);//HyperLink là revert nhấn vào -> Change
            hlChangeEmail.setText("Change");
            Account acc = accountDao.get(FileRW.Read(FORGOT_ID_PATH));
            if (acc != null) {
                txtEmail.setText(acc.getEmail());//Hiển thị email
            }
        }
    }

    @FXML
    void onHlChangeUsernameClicked(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        if (hlChangeUsername.getText().equals("Change")) {
            txtUsername.setDisable(false);
            hlChangeUsername.setText("Revert");
        } else {
            txtUsername.setDisable(true);
            hlChangeUsername.setText("Change");
            Account acc = accountDao.get(FileRW.Read(FORGOT_ID_PATH));
            if (acc != null) {
                txtUsername.setText(acc.getUsername());//Hiển thị username
            }
        }
    }

    public void initialize() {//Chạy form sẽ thực hiện code trong init này trước
        AccountDao accountDao = new AccountDao();
        Account acc = accountDao.get(FileRW.Read(FORGOT_ID_PATH));
        if (acc != null) {
            txtEmail.setText(acc.getEmail());
            txtUsername.setText(acc.getUsername());
        }
    }
}
