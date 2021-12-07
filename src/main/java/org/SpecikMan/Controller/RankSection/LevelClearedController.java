package org.SpecikMan.Controller.RankSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import org.SpecikMan.DAL.*;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LevelClearedController {

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnRetry;

    @FXML
    private Label lbAccuracy;

    @FXML
    private Label lbCombo;

    @FXML
    private Label lbCorrect;

    @FXML
    private Label lbCorrectnessScore;

    @FXML
    private Label lbLevelName;

    @FXML
    private Label lbMax;

    @FXML
    private Label lbTitle;

    @FXML
    private Label lbPlayerName;

    @FXML
    private Label lbTimeLeft;

    @FXML
    private Label lbTimeScore;

    @FXML
    private Label lbTimeUsed;

    @FXML
    private Label lbTotal;

    @FXML
    private Label lbTotalScore;
    @FXML
    private Label lbWPM;
    @FXML
    private Label lbWPS;
    @FXML
    private Label lbWrong;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private ComboBox<String> cbbChartElement;
    @FXML
    private Label lbxMulti;
    @FXML
    private Label lbComboScore;
    @FXML
    private Label lbCoinEarned;
    @FXML
    private Label lbExp;
    public void initialize() {
        GroupDao grDao = new GroupDao();
        Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
        lbTitle.setText(gr.getRank().getRankName() + " Ranking Stage Cleared");
        lbLevelName.setText(FileRW.Read(FilePath.getRound()));
        String[] data = Objects.requireNonNull(FileRW.Read(FilePath.getPlayResult())).split("-");
        lbWPS.setText(data[0]);
        lbWPM.setText(data[1]);
        lbCorrect.setText(data[2]);
        lbWrong.setText(data[3]);
        lbTotal.setText(data[4]);
        lbCombo.setText(data[5]);
        lbMax.setText(data[6]);
        lbAccuracy.setText(data[7]);
        lbTimeLeft.setText(data[8]);
        lbTimeUsed.setText(data[9]);
        lbTotalScore.setText(data[10]);
        lbTimeScore.setText(((Integer.parseInt(data[8].split(":")[0]) * 60 + Integer.parseInt(data[8].split(":")[1])) * 1000) + "");
        lbComboScore.setText(data[14]);
        lbCorrectnessScore.setText(Integer.parseInt(lbTotalScore.getText()) - Integer.parseInt(lbTimeScore.getText()) -Integer.parseInt(lbComboScore.getText())+"");
        lbPlayerName.setText(data[11]);
        lbxMulti.setText(data[13]);
        lbCoinEarned.setText(Integer.parseInt(lbAccuracy.getText().split("%")[0])+Integer.parseInt(lbWPM.getText())+"");
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        GroupDao groupDao = new GroupDao();
        List<Group> grs = groupDao.getAll();
        Group user = new Group();
        for(Group e : grs){
            if(e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))&&e.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))){
                user = e;
            }
        }
        switch (Integer.parseInt(FileRW.Read(FilePath.getRound()))) {
            case 1: {
                user.setScore1(Long.parseLong(lbTotalScore.getText()));
                user.setTotalScore(user.getScore1()+user.getScore2()+user.getScore3());
                user.setDatePlayed1(Date.valueOf(LocalDate.now()));
                break;
            }
            case 2: {
                user.setScore2(Long.parseLong(lbTotalScore.getText()));
                user.setTotalScore(user.getScore1()+user.getScore2()+user.getScore3());
                user.setDatePlayed2(Date.valueOf(LocalDate.now()));
                break;
            }
            case 3: {
                user.setScore3(Long.parseLong(lbTotalScore.getText()));
                user.setTotalScore(user.getScore1()+user.getScore2()+user.getScore3());
                user.setDatePlayed3(Date.valueOf(LocalDate.now()));
                break;
            }
        }
        groupDao.update(user);
        int currentLevel = account.getAccountLevel();
        int currentExp = account.getLevelExp();
        int expCap = account.getLevelCap();
        int expGet = Integer.parseInt(lbCoinEarned.getText());
        String coin = account.getCoin()+"";
        if (currentExp + expGet > expCap) {
            account.setCoin(account.getCoin() + Integer.parseInt(lbCoinEarned.getText())+expCap);
            lbCoinEarned.setText((Integer.parseInt(lbCoinEarned.getText())+expCap)+"(+"+expCap+" Lv.Up)");
            expCap+=((currentExp + expGet)/500)*50;
            currentLevel += (currentExp + expGet)/500;
            currentExp =  (currentExp + expGet)%500;
        } else {
            currentExp+=expGet;
            account.setCoin(account.getCoin() + Integer.parseInt(lbCoinEarned.getText()));
        }
        lbExp.setText(coin+" -> Lv."+currentLevel+" - "+currentExp+"/"+expCap);
        account.setLevelExp(currentExp);
        account.setAccountLevel(currentLevel);
        account.setLevelCap(expCap);
        accountDao.update(account);
        BindDataToChart();
        BindDataToCombobox();
        chartZooming();
        cbbChartElement.getSelectionModel().select("All");
    }

    @FXML
    public void btnMenuClicked() {
        FileRW.Write(FilePath.getRetryOrMenu(), "menu");
        DisposeForm.Dispose(lbPlayerName);
    }

    public void BindDataToChart() {
        lineChart.getData().clear();
        String[] chartData = Objects.requireNonNull(FileRW.Read(FilePath.getChartData())).split("_");
        chartData = Arrays.copyOf(chartData,chartData.length-1);
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
            seriesWPM.getData().add(new XYChart.Data<>((i + 1),Integer.parseInt(chartData[i].split("-")[0])));
            seriesCorrect.getData().add(new XYChart.Data<>((i + 1),Integer.parseInt(chartData[i].split("-")[1])));
            seriesWrong.getData().add(new XYChart.Data<>((i + 1),Integer.parseInt(chartData[i].split("-")[2])));
            seriesCombo.getData().add(new XYChart.Data<>((i + 1),Integer.parseInt(chartData[i].split("-")[3])));
            seriesAccuracy.getData().add(new XYChart.Data<>((i + 1),Integer.parseInt(chartData[i].split("-")[4].split("%")[0])));
        }
        lineChart.getData().addAll(new ArrayList<>(Arrays.asList(seriesWPM, seriesCorrect, seriesWrong, seriesCombo, seriesAccuracy)));
    }
    public void BindDataToCombobox(){
        ObservableList<String> list = FXCollections.observableList(new ArrayList<>(Arrays.asList("All","WPM","Correct","Wrong","Combo","Accuracy")));
        cbbChartElement.setItems(list);
        String[] chartData = Objects.requireNonNull(FileRW.Read(FilePath.getChartData())).split("_");
        chartData = Arrays.copyOf(chartData,chartData.length-1);
        String[] finalChartData = chartData;
        cbbChartElement.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->{
            switch (newValue) {
                case "WPM": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("WPM");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(finalChartData[i].split("-")[0])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Correct": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Correct");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(finalChartData[i].split("-")[1])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Wrong": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Wrong");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(finalChartData[i].split("-")[2])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Combo": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Combo");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(finalChartData[i].split("-")[3])));
                    }
                    lineChart.getData().add(seriesWPM);
                    break;
                }
                case "Accuracy": {
                    lineChart.getData().clear();
                    XYChart.Series<Number, Number> seriesWPM = new XYChart.Series<>();
                    seriesWPM.setName("Accuracy");
                    for (int i = 0; i < finalChartData.length; i++) {
                        seriesWPM.getData().add(new XYChart.Data<>((i + 1), Integer.parseInt(finalChartData[i].split("-")[4].split("%")[0])));
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
    public void chartZooming(){
        ChartPanManager panner = new ChartPanManager(lineChart);
        panner.setMouseFilter(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {//set your custom combination to trigger navigation
                // let it through
            } else {
                mouseEvent.consume();
            }
        });
        panner.start();

        //holding the right mouse button will draw a rectangle to zoom to desired location
        JFXChartUtil.setupZooming(lineChart, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)//set your custom combination to trigger rectangle zooming
                mouseEvent.consume();
        });
    }
}

