package org.SpecikMan.Controller.StatisticsSection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;

import java.util.Calendar;

public class ChangeAccountController {
    @FXML
    private Button btnSaveChange;

    @FXML
    private ComboBox<?> cbbSexChange;

    @FXML
    private Hyperlink changeDob;

    @FXML
    private Hyperlink changeEmail;

    @FXML
    private Hyperlink changeFullName;

    @FXML
    private Hyperlink changePassword;

    @FXML
    private Hyperlink changeSex;

    @FXML
    private Hyperlink changeUserName;

    @FXML
    private DatePicker dpDobChange;

    @FXML
    private ImageView imRanks;

    @FXML
    private Label lbDateCreated;

    @FXML
    private Label lbDateRecentLogin;

    @FXML
    private Label lbMonthCreated;

    @FXML
    private Label lbMonthRecentLogin;

    @FXML
    private Label lbPoint;

    @FXML
    private Label lbTitleUserName;

    @FXML
    private Label lbTotalDate;

    @FXML
    private Label lbYearCreated;

    @FXML
    private Label lbYearRecentLogin;

    @FXML
    private TextField txtConfirmPasswordChange;

    @FXML
    private TextField txtEmailChange;

    @FXML
    private TextField txtFullNameChange;

    @FXML
    private TextField txtNewPasswordChange;

    @FXML
    private TextField txtUserNameChange;

    public void initialize() {
        final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        Calendar c = Calendar.getInstance();
        c.setTime(account.getCreateDate());
        lbTitleUserName.setText(account.getUsername());
        lbTotalDate.setText(account.getCountLoginDate() + "");
        lbDateCreated.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthCreated.setText(months[c.get(Calendar.MONTH)]);
        lbYearCreated.setText(String.valueOf(c.get(Calendar.YEAR)));
        c.setTime(account.getLatestLoginDate());
        lbDateRecentLogin.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthRecentLogin.setText(months[c.get(Calendar.MONTH)]);
        lbYearRecentLogin.setText(String.valueOf(c.get(Calendar.YEAR)));

    }
}
