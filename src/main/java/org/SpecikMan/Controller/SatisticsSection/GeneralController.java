package org.SpecikMan.Controller.SatisticsSection;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.DetailLog;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GeneralController {
    @FXML
    private ImageView imRanks;

    @FXML
    private Label lbDayCreated;

    @FXML
    private Label lbDayRecentLogin;

    @FXML
    private Label lbDob;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbMonthCreated;

    @FXML
    private Label lbMonthRecentLogin;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbPoint;

    @FXML
    private Label lbSex;

    @FXML
    private Label lbTitleUserName;

    @FXML
    private Label lbTotalDate;

    @FXML
    private Label lbUserName;

    @FXML
    private Label lbYearCreated;

    @FXML
    private Label lbYearRecentLogin;

    public void initialize() {
        final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        Calendar c = Calendar.getInstance();
        c.setTime(account.getCreateDate());
        lbTitleUserName.setText(account.getUsername());
        lbUserName.setText(account.getUsername());
        lbTotalDate.setText(account.getCountLoginDate() + "");
        lbDayCreated.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthCreated.setText(months[c.get(Calendar.MONTH)]);
        lbYearCreated.setText(String.valueOf(c.get(Calendar.YEAR)));
        c.setTime(account.getLatestLoginDate());
        lbDayRecentLogin.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthRecentLogin.setText(months[c.get(Calendar.MONTH)]);
        lbYearRecentLogin.setText(String.valueOf(c.get(Calendar.YEAR)));
        if (account.isGender() == null) {
            lbSex.setText("Your gender");
        } else if (account.isGender()) {
            lbSex.setText("Male");
        } else if (!account.isGender()) {
            lbSex.setText("Female");
        }
        if (account.getFullname() == null) {
            lbFullName.setText("Your name");
        } else {
            lbFullName.setText(account.getFullname() + "");
        }
        if (account.getDob() == null) {
            lbDob.setText("Your DoB");
        } else {
            lbDob.setText(account.getDob() + "");
        }
    }
}

