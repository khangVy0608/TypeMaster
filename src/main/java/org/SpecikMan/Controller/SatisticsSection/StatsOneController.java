package org.SpecikMan.Controller.SatisticsSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
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
import org.SpecikMan.Tools.ShowAlert;

import java.util.*;

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
    private String finalData;
    final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};

    public void initialize() {
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
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                details.add(i);
            }
        }
        ObservableList<AccountLevelDetails> listLevel = FXCollections.observableList(details);
        cbbLevel.setItems(listLevel);
        ObservableList<String> listType = FXCollections.observableList(new ArrayList<>(Arrays.asList("All", "WPM", "Correct", "Wrong", "Combo", "Accuracy")));
        cbbType.setItems(listType);
        ObservableList<String> listResult = FXCollections.observableList(new ArrayList<>(Arrays.asList("All", "Latest", "Highest")));
        cbbResult.setItems(listResult);
        cbbType.getSelectionModel().select("All");
        cbbResult.getSelectionModel().select("All");
    }
    @FXML
    public void btnShowClicked() {
        if(cbbLevel.getSelectionModel().isEmpty()) {
            ShowAlert.show("Warning!","Please choose level to show");
        } else {
            DetailsDao detailsDao = new DetailsDao();
            DetailLogDao logsDao = new DetailLogDao();
            List<AccountLevelDetails> details = new ArrayList<>();
            List<DetailLog> logs = new ArrayList<>();
            lineChart.getData().clear();
            switch (cbbResult.getSelectionModel().getSelectedItem()) {
                case "All": {
                    switch (cbbType.getSelectionModel().getSelectedItem()) {
                        case "All": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail2 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log2 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            seriesCorrect.setName("Correct");
                            seriesWrong.setName("Wrong");
                            seriesCombo.setName("Combo");
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[0])));
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[1])));
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[2])));
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[3])));
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[4])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Arrays.asList(seriesWPM, seriesCorrect, seriesWrong, seriesCombo, seriesAccuracy)));
                            break;
                        }
                        case "WPM": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail2 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log2 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWPM)));
                            break;
                        }
                        case "Correct": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail2 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log2 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            seriesCorrect.setName("Correct");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[1])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCorrect)));
                            break;
                        }
                        case "Wrong": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail2 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log2 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            seriesWrong.setName("Wrong");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[2])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWrong)));
                            break;
                        }
                        case "Combo": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail1.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail1.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail1.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail1.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail1.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log1.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log1.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log1.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log1.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log1.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(element.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(element.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(element.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(element.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(element.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(item.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(item.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(item.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(item.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(item.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(value.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(value.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(value.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(value.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(value.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(detailLog.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(detailLog.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(detailLog.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(detailLog.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(detailLog.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            seriesCombo.setName("Combo");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[3])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCombo)));
                            break;
                        }
                        case "Accuracy": {
                            double wpm = 0;
                            double accuracy = 0;
                            int wrong = 0;
                            int score = 0;
                            int words = 0;
                            int correct = 0;
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                    wpm += i.getWpm();
                                    accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                                    wrong += i.getWrong();
                                    score += i.getScore();
                                    words += i.getCorrect() + i.getWrong();
                                    correct += i.getCorrect();
                                }
                            }
                            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
                            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
                            lbWrong.setText(wrong + "");
                            lbScore.setText(score + "");
                            lbWord.setText(words + "");
                            lbCorrect.setText(correct + "");
                            String[] s1 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail2 : details) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(detail2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log2 : logs) {
                                s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[0])) + "";
                                s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[1])) + "";
                                s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[2])) + "";
                                s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[3])) + "";
                                s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(log2.getChartData().split("_")[0].split("-")[4].split("%")[0])) + "";
                            }
                            s1[0] = (Double.parseDouble(s1[0]) / (details.size() + logs.size())) + "";
                            s1[1] = (Double.parseDouble(s1[1]) / (details.size() + logs.size())) + "";
                            s1[2] = (Double.parseDouble(s1[2]) / (details.size() + logs.size())) + "";
                            s1[3] = (Double.parseDouble(s1[3]) / (details.size() + logs.size())) + "";
                            s1[4] = (Double.parseDouble(s1[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s2 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails detail1 : details) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(detail1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog log1 : logs) {
                                s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[0])) + "";
                                s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[1])) + "";
                                s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[2])) + "";
                                s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[3])) + "";
                                s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(log1.getChartData().split("_")[1].split("-")[4].split("%")[0])) + "";
                            }
                            s2[0] = (Double.parseDouble(s2[0]) / (details.size() + logs.size())) + "";
                            s2[1] = (Double.parseDouble(s2[1]) / (details.size() + logs.size())) + "";
                            s2[2] = (Double.parseDouble(s2[2]) / (details.size() + logs.size())) + "";
                            s2[3] = (Double.parseDouble(s2[3]) / (details.size() + logs.size())) + "";
                            s2[4] = (Double.parseDouble(s2[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s3 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails element : details) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(element.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog item : logs) {
                                s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[0])) + "";
                                s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[1])) + "";
                                s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[2])) + "";
                                s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[3])) + "";
                                s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(item.getChartData().split("_")[2].split("-")[4].split("%")[0])) + "";
                            }
                            s3[0] = (Double.parseDouble(s3[0]) / (details.size() + logs.size())) + "";
                            s3[1] = (Double.parseDouble(s3[1]) / (details.size() + logs.size())) + "";
                            s3[2] = (Double.parseDouble(s3[2]) / (details.size() + logs.size())) + "";
                            s3[3] = (Double.parseDouble(s3[3]) / (details.size() + logs.size())) + "";
                            s3[4] = (Double.parseDouble(s3[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s4 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails levelDetails : details) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(levelDetails.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog value : logs) {
                                s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[0])) + "";
                                s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[1])) + "";
                                s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[2])) + "";
                                s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[3])) + "";
                                s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(value.getChartData().split("_")[3].split("-")[4].split("%")[0])) + "";
                            }
                            s4[0] = (Double.parseDouble(s4[0]) / (details.size() + logs.size())) + "";
                            s4[1] = (Double.parseDouble(s4[1]) / (details.size() + logs.size())) + "";
                            s4[2] = (Double.parseDouble(s4[2]) / (details.size() + logs.size())) + "";
                            s4[3] = (Double.parseDouble(s4[3]) / (details.size() + logs.size())) + "";
                            s4[4] = (Double.parseDouble(s4[4]) / (details.size() + logs.size())) + "";
                            //
                            String[] s5 = "0-0-0-0-0".split("-");
                            for (AccountLevelDetails accountLevelDetails : details) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(accountLevelDetails.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            for (DetailLog detailLog : logs) {
                                s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[0])) + "";
                                s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[1])) + "";
                                s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[2])) + "";
                                s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[3])) + "";
                                s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(detailLog.getChartData().split("_")[4].split("-")[4].split("%")[0])) + "";
                            }
                            s5[0] = (Double.parseDouble(s5[0]) / (details.size() + logs.size())) + "";
                            s5[1] = (Double.parseDouble(s5[1]) / (details.size() + logs.size())) + "";
                            s5[2] = (Double.parseDouble(s5[2]) / (details.size() + logs.size())) + "";
                            s5[3] = (Double.parseDouble(s5[3]) / (details.size() + logs.size())) + "";
                            s5[4] = (Double.parseDouble(s5[4]) / (details.size() + logs.size())) + "";
                            StringBuilder str1 = new StringBuilder();
                            StringBuilder str2 = new StringBuilder();
                            StringBuilder str3 = new StringBuilder();
                            StringBuilder str4 = new StringBuilder();
                            StringBuilder str5 = new StringBuilder();
                            for (int i = 0; i < 5; i++) {
                                if (i == 4) {
                                    str1.append(s1[i]);
                                    str2.append(s2[i]);
                                    str3.append(s3[i]);
                                    str4.append(s4[i]);
                                    str5.append(s5[i]);
                                } else {
                                    str1.append(s1[i]).append("-");
                                    str2.append(s2[i]).append("-");
                                    str3.append(s3[i]).append("-");
                                    str4.append(s4[i]).append("-");
                                    str5.append(s5[i]).append("-");
                                }
                            }
                            finalData = str1 + "_" + str2 + "_" + str3 + "_" + str4 + "_" + str5;
                            String[] chartData = Objects.requireNonNull(finalData.split("_"));
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(chartData[i].split("-")[4])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesAccuracy)));
                            break;
                        }
                    }
                    break;
                }
                case "Latest": {
                    switch (cbbType.getValue()) {
                        case "All": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            seriesCorrect.setName("Correct");
                            seriesWrong.setName("Wrong");
                            seriesCombo.setName("Combo");
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[0])));
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[1])));
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[2])));
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[3])));
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[4].split("%")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Arrays.asList(seriesWPM, seriesCorrect, seriesWrong, seriesCombo, seriesAccuracy)));
                            break;
                        }
                        case "WPM": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWPM)));
                            break;
                        }
                        case "Correct": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            seriesCorrect.setName("Correct");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[1])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCorrect)));
                            break;
                        }
                        case "Wrong": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            seriesWrong.setName("Wrong");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[2])));
                                break;
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWrong)));
                        }
                        case "Combo": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            seriesCombo.setName("Combo");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[3])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCombo)));
                            break;
                        }
                        case "Accuracy": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            for (DetailLog i : logsDao.getAll()) {
                                if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getIdLevel())) {
                                    logs.add(i);
                                }
                            }
                            String[] chartData;
                            logs.sort(Comparator.comparing(DetailLog::getDatePlayed).reversed());
                            details.sort(Comparator.comparing(AccountLevelDetails::getDatePlayed).reversed());
                            if (logs.isEmpty()) {
                                chartData = details.get(0).getChartData().split("_");
                                lbWPM.setText(details.get(0).getWpm() + "");
                                lbAccuracy.setText(details.get(0).getAccuracy());
                                lbWrong.setText(details.get(0).getWrong() + "");
                                lbScore.setText(details.get(0).getScore() + "");
                                lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                lbCorrect.setText(details.get(0).getCorrect() + "");
                            } else {
                                if (logs.get(0).getDatePlayed().after(details.get(0).getDatePlayed())) {
                                    chartData = logs.get(0).getChartData().split("_");
                                    lbWPM.setText(logs.get(0).getWpm() + "");
                                    lbAccuracy.setText(logs.get(0).getAccuracy());
                                    lbWrong.setText(logs.get(0).getWrong() + "");
                                    lbScore.setText(logs.get(0).getScore() + "");
                                    lbWord.setText(logs.get(0).getCorrect() + logs.get(0).getWrong() + "");
                                    lbCorrect.setText(logs.get(0).getCorrect() + "");
                                } else {
                                    chartData = details.get(0).getChartData().split("_");
                                    lbWPM.setText(details.get(0).getWpm() + "");
                                    lbAccuracy.setText(details.get(0).getAccuracy());
                                    lbWrong.setText(details.get(0).getWrong() + "");
                                    lbScore.setText(details.get(0).getScore() + "");
                                    lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                                    lbCorrect.setText(details.get(0).getCorrect() + "");
                                }
                            }
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[4].split("%")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesAccuracy)));
                            break;
                        }
                    }
                    break;
                }
                case "Highest": {
                    switch (cbbType.getValue()) {
                        case "All": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            seriesCorrect.setName("Correct");
                            seriesWrong.setName("Wrong");
                            seriesCombo.setName("Combo");
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[0])));
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[1])));
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[2])));
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[3])));
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[4].split("%")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Arrays.asList(seriesWPM, seriesCorrect, seriesWrong, seriesCombo, seriesAccuracy)));
                            break;
                        }
                        case "WPM": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                            seriesWPM.setName("WPM");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWPM)));
                            break;
                        }
                        case "Correct": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesCorrect = new XYChart.Series<>();
                            seriesCorrect.setName("Correct");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCorrect.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[1])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCorrect)));
                            break;
                        }
                        case "Wrong": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesWrong = new XYChart.Series<>();
                            seriesWrong.setName("Wrong");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesWrong.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[2])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesWrong)));
                            break;
                        }
                        case "Combo": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesCombo = new XYChart.Series<>();
                            seriesCombo.setName("Combo");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesCombo.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[3])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesCombo)));
                            break;
                        }
                        case "Accuracy": {
                            for (AccountLevelDetails i : detailsDao.getAll()) {
                                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && cbbLevel.getSelectionModel().getSelectedItem().getLevel().getIdLevel().equals(i.getLevel().getIdLevel())) {
                                    details.add(i);
                                }
                            }
                            String[] chartData;
                            chartData = details.get(0).getChartData().split("_");
                            lbWPM.setText(details.get(0).getWpm() + "");
                            lbAccuracy.setText(details.get(0).getAccuracy());
                            lbWrong.setText(details.get(0).getWrong() + "");
                            lbScore.setText(details.get(0).getScore() + "");
                            lbWord.setText(details.get(0).getCorrect() + details.get(0).getWrong() + "");
                            lbCorrect.setText(details.get(0).getCorrect() + "");
                            chartData = Arrays.copyOf(chartData, chartData.length - 1);
                            XYChart.Series<Number, Number> seriesAccuracy = new XYChart.Series<>();
                            seriesAccuracy.setName("Accuracy");
                            for (int i = 0; i < chartData.length; i++) {
                                seriesAccuracy.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(chartData[i].split("-")[4].split("%")[0])));
                            }
                            lineChart.getData().addAll(new ArrayList<>(Collections.singletonList(seriesAccuracy)));
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
