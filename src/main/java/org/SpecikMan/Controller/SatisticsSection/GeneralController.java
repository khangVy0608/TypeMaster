package org.SpecikMan.Controller.SatisticsSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class GeneralController {
    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cbbSexChange;

    @FXML
    private DatePicker dpDobChange;

    @FXML
    private ImageView imRanks;

    @FXML
    private Label lbDayCreated;

    @FXML
    private Label lbDayRecentLogin;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbMonthCreated;

    @FXML
    private Label lbMonthRecentLogin;

    @FXML
    private Label lbPassword;

    @FXML
    private Label lbPoint;

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

    @FXML
    private TextField txtFullNameChange;

    @FXML
    void onClickEdit(MouseEvent event) {

        txtFullNameChange.setEditable(true);
        cbbSexChange.setEditable(true);
        dpDobChange.setEditable(true);
        btnSave.setDisable(false);

        ObservableList<String> gt = FXCollections.observableArrayList();
        gt.add("Male");
        gt.add("Female");
        cbbSexChange.setItems(gt);
    }

    @FXML
    void onClickSave(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        account.setFullname(txtFullNameChange.getText());
        account.setDob(Date.valueOf(dpDobChange.getValue()));
        account.setGender(cbbSexChange.getValue().equals("Male"));
        accountDao.update(account);
        btnSave.setDisable(true);
        txtFullNameChange.setEditable(false);
        cbbSexChange.setEditable(false);
        dpDobChange.setEditable(false);
    }

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


        boolean sex = account.isGender();
        if (sex){
            cbbSexChange.getSelectionModel().select("Male");
        }
        else {
            cbbSexChange.getSelectionModel().select("Female");
        }
        cbbSexChange.setEditable(false);
        if (account.getFullname() == null) {
            txtFullNameChange.setText("Your name");
        } else {
            txtFullNameChange.setText(account.getFullname() + "");
        }
        if(account.getDob()==null){
            dpDobChange.setValue(LocalDate.now());
        } else {
            String d = account.getDob().toString();
            dpDobChange.setValue(LocalDate.parse(d));
        }
    }
}

