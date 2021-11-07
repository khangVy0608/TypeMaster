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

import java.util.*;

public class StatsAllController {
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
    String finalData = "";

    public void initialize() {
        final String[] months = new String[]{"Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."};
        AccountDao accountDao = new AccountDao();
        DetailsDao detailsDao = new DetailsDao();
        DetailLogDao logDao = new DetailLogDao();
        List<DetailLog> logs = new ArrayList<>();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        List<AccountLevelDetails> details = new ArrayList<>();
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                details.add(i);
            }
        }
        for (DetailLog i : logDao.getAll()) {
            if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                logs.add(i);
            }
        }
        if (logs.isEmpty() && details.isEmpty()) {
            lbUsername.setText(account.getUsername());
            lbTotalDate.setText(account.getCountLoginDate() + "");
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
            for (DetailLog i : logs) {
                wpm += i.getWpm();
                accuracy += Integer.parseInt(i.getAccuracy().split("%")[0]);
                wrong += i.getWrong();
                score += i.getScore();
                words += i.getCorrect() + i.getWrong();
                correct += i.getCorrect();
            }
            lbWPM.setText(wpm / (details.size() + logs.size()) + "");
            lbAccuracy.setText(accuracy / (details.size() + logs.size()) + "%");
            lbWrong.setText(wrong + "");
            lbScore.setText(score + "");
            lbWord.setText(words + "");
            lbCorrect.setText(correct + "");
            BindDataToChart();
            BindDataToCombobox();
        }
    }

    public void BindDataToChart() {
        lineChart.getData().clear();
        DetailsDao detailsDao = new DetailsDao();
        DetailLogDao logDao = new DetailLogDao();
        List<AccountLevelDetails> details = new ArrayList<>();
        List<DetailLog> logs = new ArrayList<>();
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                details.add(i);
            }
        }
        for (DetailLog i : logDao.getAll()) {
            if (i.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                logs.add(i);
            }
        }
        String[] datas = new String[5];
        String[] s1 = "0-0-0-0-0".split("-");
        for (int i = 0; i < details.size(); i++) {
            s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(details.get(i).getChartData().split("_")[0].split("-")[0]))+"";
            s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(details.get(i).getChartData().split("_")[0].split("-")[1]))+"";
            s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(details.get(i).getChartData().split("_")[0].split("-")[2]))+"";
            s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(details.get(i).getChartData().split("_")[0].split("-")[3]))+"";
            s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(details.get(i).getChartData().split("_")[0].split("-")[4].split("%")[0]))+"";
        }
        for (int i = 0; i < logs.size(); i++) {
            s1[0] = (Double.parseDouble(s1[0]) + Double.parseDouble(logs.get(i).getChartData().split("_")[0].split("-")[0]))+"";
            s1[1] = (Double.parseDouble(s1[1]) + Double.parseDouble(logs.get(i).getChartData().split("_")[0].split("-")[1]))+"";
            s1[2] = (Double.parseDouble(s1[2]) + Double.parseDouble(logs.get(i).getChartData().split("_")[0].split("-")[2]))+"";
            s1[3] = (Double.parseDouble(s1[3]) + Double.parseDouble(logs.get(i).getChartData().split("_")[0].split("-")[3]))+"";
            s1[4] = (Double.parseDouble(s1[4]) + Double.parseDouble(logs.get(i).getChartData().split("_")[0].split("-")[4].split("%")[0]))+"";
        }
        s1[0] = (Double.parseDouble(s1[0])/(details.size()+logs.size()))+"";
        s1[1] = (Double.parseDouble(s1[1])/(details.size()+logs.size()))+"";
        s1[2] = (Double.parseDouble(s1[2])/(details.size()+logs.size()))+"";
        s1[3] = (Double.parseDouble(s1[3])/(details.size()+logs.size()))+"";
        s1[4] = (Double.parseDouble(s1[4])/(details.size()+logs.size()))+"";
        //
        String[] s2 = "0-0-0-0-0".split("-");
        for (int i = 0; i < details.size(); i++) {
            s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(details.get(i).getChartData().split("_")[1].split("-")[0]))+"";
            s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(details.get(i).getChartData().split("_")[1].split("-")[1]))+"";
            s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(details.get(i).getChartData().split("_")[1].split("-")[2]))+"";
            s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(details.get(i).getChartData().split("_")[1].split("-")[3]))+"";
            s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(details.get(i).getChartData().split("_")[1].split("-")[4].split("%")[0]))+"";
        }
        for (int i = 0; i < logs.size(); i++) {
            s2[0] = (Double.parseDouble(s2[0]) + Double.parseDouble(logs.get(i).getChartData().split("_")[1].split("-")[0]))+"";
            s2[1] = (Double.parseDouble(s2[1]) + Double.parseDouble(logs.get(i).getChartData().split("_")[1].split("-")[1]))+"";
            s2[2] = (Double.parseDouble(s2[2]) + Double.parseDouble(logs.get(i).getChartData().split("_")[1].split("-")[2]))+"";
            s2[3] = (Double.parseDouble(s2[3]) + Double.parseDouble(logs.get(i).getChartData().split("_")[1].split("-")[3]))+"";
            s2[4] = (Double.parseDouble(s2[4]) + Double.parseDouble(logs.get(i).getChartData().split("_")[1].split("-")[4].split("%")[0]))+"";
        }
        s2[0] = (Double.parseDouble(s2[0])/(details.size()+logs.size()))+"";
        s2[1] = (Double.parseDouble(s2[1])/(details.size()+logs.size()))+"";
        s2[2] = (Double.parseDouble(s2[2])/(details.size()+logs.size()))+"";
        s2[3] = (Double.parseDouble(s2[3])/(details.size()+logs.size()))+"";
        s2[4] = (Double.parseDouble(s2[4])/(details.size()+logs.size()))+"";
        //
        String[] s3 = "0-0-0-0-0".split("-");
        for (int i = 0; i < details.size(); i++) {
            s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(details.get(i).getChartData().split("_")[2].split("-")[0]))+"";
            s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(details.get(i).getChartData().split("_")[2].split("-")[1]))+"";
            s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(details.get(i).getChartData().split("_")[2].split("-")[2]))+"";
            s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(details.get(i).getChartData().split("_")[2].split("-")[3]))+"";
            s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(details.get(i).getChartData().split("_")[2].split("-")[4].split("%")[0]))+"";
        }
        for (int i = 0; i < logs.size(); i++) {
            s3[0] = (Double.parseDouble(s3[0]) + Double.parseDouble(logs.get(i).getChartData().split("_")[2].split("-")[0]))+"";
            s3[1] = (Double.parseDouble(s3[1]) + Double.parseDouble(logs.get(i).getChartData().split("_")[2].split("-")[1]))+"";
            s3[2] = (Double.parseDouble(s3[2]) + Double.parseDouble(logs.get(i).getChartData().split("_")[2].split("-")[2]))+"";
            s3[3] = (Double.parseDouble(s3[3]) + Double.parseDouble(logs.get(i).getChartData().split("_")[2].split("-")[3]))+"";
            s3[4] = (Double.parseDouble(s3[4]) + Double.parseDouble(logs.get(i).getChartData().split("_")[2].split("-")[4].split("%")[0]))+"";
        }
        s3[0] = (Double.parseDouble(s3[0])/(details.size()+logs.size()))+"";
        s3[1] = (Double.parseDouble(s3[1])/(details.size()+logs.size()))+"";
        s3[2] = (Double.parseDouble(s3[2])/(details.size()+logs.size()))+"";
        s3[3] = (Double.parseDouble(s3[3])/(details.size()+logs.size()))+"";
        s3[4] = (Double.parseDouble(s3[4])/(details.size()+logs.size()))+"";
        //
        String[] s4 = "0-0-0-0-0".split("-");
        for (int i = 0; i < details.size(); i++) {
            s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(details.get(i).getChartData().split("_")[3].split("-")[0]))+"";
            s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(details.get(i).getChartData().split("_")[3].split("-")[1]))+"";
            s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(details.get(i).getChartData().split("_")[3].split("-")[2]))+"";
            s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(details.get(i).getChartData().split("_")[3].split("-")[3]))+"";
            s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(details.get(i).getChartData().split("_")[3].split("-")[4].split("%")[0]))+"";
        }
        for (int i = 0; i < logs.size(); i++) {
            s4[0] = (Double.parseDouble(s4[0]) + Double.parseDouble(logs.get(i).getChartData().split("_")[3].split("-")[0]))+"";
            s4[1] = (Double.parseDouble(s4[1]) + Double.parseDouble(logs.get(i).getChartData().split("_")[3].split("-")[1]))+"";
            s4[2] = (Double.parseDouble(s4[2]) + Double.parseDouble(logs.get(i).getChartData().split("_")[3].split("-")[2]))+"";
            s4[3] = (Double.parseDouble(s4[3]) + Double.parseDouble(logs.get(i).getChartData().split("_")[3].split("-")[3]))+"";
            s4[4] = (Double.parseDouble(s4[4]) + Double.parseDouble(logs.get(i).getChartData().split("_")[3].split("-")[4].split("%")[0]))+"";
        }
        s4[0] = (Double.parseDouble(s4[0])/(details.size()+logs.size()))+"";
        s4[1] = (Double.parseDouble(s4[1])/(details.size()+logs.size()))+"";
        s4[2] = (Double.parseDouble(s4[2])/(details.size()+logs.size()))+"";
        s4[3] = (Double.parseDouble(s4[3])/(details.size()+logs.size()))+"";
        s4[4] = (Double.parseDouble(s4[4])/(details.size()+logs.size()))+"";
        //
        String[] s5 = "0-0-0-0-0".split("-");
        for (int i = 0; i < details.size(); i++) {
            s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(details.get(i).getChartData().split("_")[4].split("-")[0]))+"";
            s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(details.get(i).getChartData().split("_")[4].split("-")[1]))+"";
            s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(details.get(i).getChartData().split("_")[4].split("-")[2]))+"";
            s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(details.get(i).getChartData().split("_")[4].split("-")[3]))+"";
            s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(details.get(i).getChartData().split("_")[4].split("-")[4].split("%")[0]))+"";
        }
        for (int i = 0; i < logs.size(); i++) {
            s5[0] = (Double.parseDouble(s5[0]) + Double.parseDouble(logs.get(i).getChartData().split("_")[4].split("-")[0]))+"";
            s5[1] = (Double.parseDouble(s5[1]) + Double.parseDouble(logs.get(i).getChartData().split("_")[4].split("-")[1]))+"";
            s5[2] = (Double.parseDouble(s5[2]) + Double.parseDouble(logs.get(i).getChartData().split("_")[4].split("-")[2]))+"";
            s5[3] = (Double.parseDouble(s5[3]) + Double.parseDouble(logs.get(i).getChartData().split("_")[4].split("-")[3]))+"";
            s5[4] = (Double.parseDouble(s5[4]) + Double.parseDouble(logs.get(i).getChartData().split("_")[4].split("-")[4].split("%")[0]))+"";
        }
        s5[0] = (Double.parseDouble(s5[0])/(details.size()+logs.size()))+"";
        s5[1] = (Double.parseDouble(s5[1])/(details.size()+logs.size()))+"";
        s5[2] = (Double.parseDouble(s5[2])/(details.size()+logs.size()))+"";
        s5[3] = (Double.parseDouble(s5[3])/(details.size()+logs.size()))+"";
        s5[4] = (Double.parseDouble(s5[4])/(details.size()+logs.size()))+"";
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        StringBuilder str3 = new StringBuilder();
        StringBuilder str4 = new StringBuilder();
        StringBuilder str5 = new StringBuilder();
        for(int i = 0 ; i < 5 ; i++){
            if(i==4){
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
        finalData = str1 +"_"+ str2 +"_"+ str3 +"_"+ str4 +"_"+ str5;
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
    }
    public void BindDataToCombobox(){
        ObservableList<String> list = FXCollections.observableList(new ArrayList<>(Arrays.asList("All","WPM","Correct","Wrong","Combo","Accuracy")));
        cbbType.setItems(list);
        String[] chartData = Objects.requireNonNull(finalData.split("_"));
        chartData = Arrays.copyOf(chartData,chartData.length-1);
        String[] finalChartData = chartData;
        cbbType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->{
            switch (newValue) {
                case "WPM": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("WPM");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(finalChartData[i].split("-")[0])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Correct": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Correct");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(finalChartData[i].split("-")[1])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Wrong": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Wrong");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(finalChartData[i].split("-")[2])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Combo": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Combo");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(finalChartData[i].split("-")[3])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Accuracy": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Accuracy");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Double.parseDouble(finalChartData[i].split("-")[4].split("%")[0])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                default:
                    BindDataToChart();
                    break;
            }
        });
    }
}
