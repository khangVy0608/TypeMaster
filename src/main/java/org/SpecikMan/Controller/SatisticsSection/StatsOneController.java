package org.SpecikMan.Controller.SatisticsSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.FileRW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class StatsOneController {
    @FXML
    private ComboBox<String> cbbType;
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
    private Label lbTotalDate;
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbWPM;
    @FXML
    private Label lbWord;
    @FXML
    private Label lbWrong;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private ComboBox<AccountLevelDetails> cbbLevel;
    @FXML
    private ComboBox<String> cbbResult;

    public void initialize() {
        final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
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
        lbWPM.setText("---");
        lbAccuracy.setText("---");
        lbWrong.setText("---");
        lbScore.setText("---");
        lbWord.setText("---");
        lbCorrect.setText("---");
        BindDataToComboBox();
    }

    public void BindDataToComboBox() {
        DetailsDao detailsDao = new DetailsDao();
        List<AccountLevelDetails> details = new ArrayList<>();
        for(AccountLevelDetails i : detailsDao.getAll()){
            if(i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))){
                details.add(i);
            }
        }
        ObservableList<AccountLevelDetails> listLevel = FXCollections.observableList(details);
        cbbLevel.setItems(listLevel);
        ObservableList<String> listType = FXCollections.observableList(new ArrayList<>(Arrays.asList("All","WPM","Correct","Wrong","Combo","Accuracy")));
        cbbType.setItems(listType);
        ObservableList<String> listResult = FXCollections.observableList(new ArrayList<>(Arrays.asList("All","Latest","Highest")));
        cbbResult.setItems(listResult);
    }
    @FXML
    public void btnShowClicked(){
        switch(cbbResult.getValue()){
            case "All":{
                switch(cbbType.getValue()){
                    case "All":{

                    }
                    case "WPM":{

                    }
                    case "Correct":{

                    }
                    case "Wrong":{

                    }
                    case "Combo":{

                    }
                    case "Accuracy":{

                    }
                }
                break;
            }
            case "Latest":{
                switch(cbbType.getValue()){
                    case "All":{

                    }
                    case "WPM":{

                    }
                    case "Correct":{

                    }
                    case "Wrong":{

                    }
                    case "Combo":{

                    }
                    case "Accuracy":{

                    }
                }
                break;
            }
            case "Highest":{
                switch(cbbType.getValue()){
                    case "All":{

                    }
                    case "WPM":{

                    }
                    case "Correct":{

                    }
                    case "Wrong":{

                    }
                    case "Combo":{

                    }
                    case "Accuracy":{

                    }
                }
                break;
            }
        }
    }
}
