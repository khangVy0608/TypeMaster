package org.SpecikMan.Controller.StatisticsSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

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
        if(btnEdit.getText().equals("Edit")){
            btnEdit.setText("Back");
            txtFullNameChange.setDisable(false);
            cbbSexChange.setDisable(false);
            dpDobChange.setDisable(false);
            btnSave.setDisable(false);
            ObservableList<String> gt = FXCollections.observableArrayList();
            gt.add("Male");
            gt.add("Female");
            cbbSexChange.setItems(gt);
        } else {
            btnEdit.setText("Edit");
            txtFullNameChange.setDisable(true);
            cbbSexChange.setDisable(true);
            dpDobChange.setDisable(true);
            btnSave.setDisable(true);
        }

    }

    @FXML
    void onClickSave(MouseEvent event) {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        account.setFullName(txtFullNameChange.getText());
        account.setDob(Date.valueOf(dpDobChange.getValue()));
        account.setGender(cbbSexChange.getValue().equals("Male"));
        accountDao.update(account);
        btnSave.setDisable(true);
        txtFullNameChange.setDisable(true);
        cbbSexChange.setDisable(true);
        dpDobChange.setDisable(true);
        initialize();
    }

    public void initialize() {
        final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        Calendar c = Calendar.getInstance();
        c.setTime(account.getCreateDate());
        imRanks.setImage(new Image(account.getRank().getImagePath()));
        lbTitleUserName.setText(account.getUsername());
        lbUserName.setText(account.getUsername() + " Lv."+account.getAccountLevel()+" - "+account.getLevelExp()+"/"+account.getLevelCap());
        lbTotalDate.setText(account.getCountLoginDate() + "");
        lbDayCreated.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthCreated.setText(months[c.get(Calendar.MONTH)]);
        lbYearCreated.setText(String.valueOf(c.get(Calendar.YEAR)));
        c.setTime(account.getLatestLoginDate());
        lbDayRecentLogin.setText(c.get(Calendar.DAY_OF_MONTH) + "");
        lbMonthRecentLogin.setText(months[c.get(Calendar.MONTH)]);
        lbYearRecentLogin.setText(String.valueOf(c.get(Calendar.YEAR)));
        btnEdit.setText("Edit");
        btnSave.setDisable(true);
        lbEmail.setText(account.getEmail());


        Boolean sex = account.isGender();
        if (sex!=null && sex){
            cbbSexChange.getSelectionModel().select("Male");
        }
        else {
            cbbSexChange.getSelectionModel().select("Female");
        }
        cbbSexChange.setEditable(false);
        if (account.getFullName() == null) {
            txtFullNameChange.setText("Your name");
        } else {
            txtFullNameChange.setText(account.getFullName() + "");
        }
        if(account.getDob()==null){
            dpDobChange.setValue(LocalDate.now());
        } else {
            String d = account.getDob().toString();
            dpDobChange.setValue(LocalDate.parse(d));
        }
        txtFullNameChange.setDisable(true);
        cbbSexChange.setDisable(true);
        dpDobChange.setDisable(true);
        btnSave.setDisable(true);
    }
    @FXML
    public void btnEditSEClicked(){
        LoadForm.load("/fxml/StatisticsFXMLs/VerifyCode.fxml","Verify Email",true);
        initialize();
    }
}

