package org.SpecikMan.Controller.PracticeSection;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewProfileController {

    @FXML
    private ImageView imRanksViewProfile;

    @FXML
    private Label lbAccuracy;

    @FXML
    private Label lbCorrect;

    @FXML
    private Label lbCreateDay;

    @FXML
    private Label lbCreateMonth;

    @FXML
    private Label lbCreateYear;

    @FXML
    private Label lbDob;

    @FXML
    private Label lbFullName;

    @FXML
    private Label lbPoint;

    @FXML
    private Label lbRecentDay;

    @FXML
    private Label lbRecentMonth;

    @FXML
    private Label lbRecentYear;

    @FXML
    private Label lbScore;

    @FXML
    private Label lbSex;

    @FXML
    private Label lbTotalDate;

    @FXML
    private Label lbUsername;

    @FXML
    private Label lbWPM;

    @FXML
    private Label lbWord;

    @FXML
    private Label lbWrong;

    public void initialize(){
        final String[] months = new String[]{"Jan.","Feb.","Mar.","Apr.","May","Jun.","Jul.","Aug.","Sep.","Oct.","Nov.","Dec."};
        AccountDao accountDao = new AccountDao();
        DetailsDao detailsDao = new DetailsDao();
        DetailLogDao logDao = new DetailLogDao();
        List<DetailLog> logs = new ArrayList<>();
        Account account = accountDao.get(FileRW.Read(FilePath.getChooseProfile()));
        List<AccountLevelDetails> details = new ArrayList<>();
        for(AccountLevelDetails i: detailsDao.getAll()){
            if(i.getIdAccount().equals(FileRW.Read(FilePath.getChooseProfile()))){
                details.add(i);
            }
        }
        for(DetailLog i: logDao.getAll()){
            if(i.getIdPlayer().equals(FileRW.Read(FilePath.getChooseProfile()))){
                logs.add(i);
            }
        }
        if(logs.isEmpty()&&details.isEmpty()){
            lbUsername.setText(account.getUsername());
            lbTotalDate.setText(account.getCountLoginDate()+"");
            lbCreateDay.setText("---");
            lbCreateMonth.setText("---");
            lbCreateYear.setText("---");
            lbRecentDay.setText("---");
            lbRecentMonth.setText("---");
            lbRecentYear.setText("---");
            lbWPM.setText("---");
            lbAccuracy.setText("---");
            lbWrong.setText("---");
            lbScore.setText("---");
            lbWord.setText("---");
            lbCorrect.setText("---");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(account.getCreateDate());
            lbUsername.setText(account.getUsername());
            lbTotalDate.setText(account.getCountLoginDate() + "");
            lbCreateDay.setText(c.get(Calendar.DAY_OF_MONTH) + "");
            lbCreateMonth.setText(months[c.get(Calendar.MONTH)]);
            lbCreateYear.setText(String.valueOf(c.get(Calendar.YEAR)));
            c.setTime(account.getLatestLoginDate());
            lbRecentDay.setText(c.get(Calendar.DAY_OF_MONTH) + "");
            lbRecentMonth.setText(months[c.get(Calendar.MONTH)]);
            lbRecentYear.setText(String.valueOf(c.get(Calendar.YEAR)));
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
            double wpm = 0;
            double accuracy = 0;
            int wrong = 0;
            int score = 0;
            int words = 0;
            int correct = 0;
            for (AccountLevelDetails i : details) {
                wpm += i.getWpm();
                accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                wrong += i.getWrong();
                score += i.getScore();
                words += i.getCorrect() + i.getWrong();
                correct += i.getCorrect();
            }
            for(DetailLog i: logs){
                wpm += i.getWpm();
                accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                wrong += i.getWrong();
                score += i.getScore();
                words += i.getCorrect() + i.getWrong();
                correct += i.getCorrect();
            }
            lbWPM.setText(wpm / (details.size()+logs.size()) + "");
            lbAccuracy.setText(accuracy / (details.size()+logs.size()) + "%");
            lbWrong.setText(wrong + "");
            lbScore.setText(score + "");
            lbWord.setText(words + "");
            lbCorrect.setText(correct + "");
        }
    }

}
