package org.SpecikMan.Controller.PracticeSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
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

    public void initialize() {
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
        lbLevelName.setText(data[12]);
        lbxMulti.setText(data[13]);
        lbCoinEarned.setText(Integer.parseInt(lbAccuracy.getText().split("%")[0])+Integer.parseInt(lbWPM.getText())+"");
        LevelDao levelDao = new LevelDao();
        DetailsDao detailsDao = new DetailsDao();
        DetailLogDao logDao = new DetailLogDao();
        DetailLog log = new DetailLog();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        AccountLevelDetails detail = new AccountLevelDetails();
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (i.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel())) && i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                detail = i;
            }
        }
        if (detail.getIdLevelDetails() == null) {
            System.out.println("them vao detail");
            detail.setIdLevelDetails(GenerateID.genDetails());
            detail.setLevel(new Level(FileRW.Read(FilePath.getPlayLevel())));
            detail.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
            detail.setLike(false);
            detail.setWpm(Float.parseFloat(lbWPM.getText()));
            detail.setWps(Float.parseFloat(lbWPS.getText()));
            detail.setCorrect(Integer.parseInt(lbCorrect.getText()));
            detail.setWrong(Integer.parseInt(lbWrong.getText()));
            detail.setAccuracy(lbAccuracy.getText());
            detail.setTimeLeft(lbTimeLeft.getText());
            detail.setDatePlayed(Date.valueOf(LocalDate.now()));
            detail.setScore(Long.parseLong(lbTotalScore.getText()));
            detail.setChartData(FileRW.Read(FilePath.getChartData()));
            detailsDao.add(detail);
        } else {
            if(detail.getScore()<Integer.parseInt(lbTotalScore.getText())){
                System.out.println("them detail vao log, them hien tai vao detail");
                log.setIdLog(GenerateID.genLog());
                log.setIdLevel(level.getIdLevel());
                log.setLevelName(level.getNameLevel());
                log.setIdPublisher(level.getIdAccount());
                log.setPublisherName(level.getUsername());
                log.setIdPlayer(detail.getIdAccount());
                log.setPlayerName(detail.getUsername());
                log.setScore(detail.getScore());
                log.setWpm(detail.getWpm());
                log.setWps(detail.getWps());
                log.setCorrect(detail.getCorrect());
                log.setWrong(detail.getWrong());
                log.setAccuracy(detail.getAccuracy());
                log.setTimeLeft(detail.getTimeLeft());
                log.setDatePlayed(detail.getDatePlayed());
                log.setChartData(detail.getChartData());
                logDao.add(log);
                detail.setWpm(Float.parseFloat(lbWPM.getText()));
                detail.setWps(Float.parseFloat(lbWPS.getText()));
                detail.setCorrect(Integer.parseInt(lbCorrect.getText()));
                detail.setWrong(Integer.parseInt(lbWrong.getText()));
                detail.setAccuracy(lbAccuracy.getText());
                detail.setTimeLeft(lbTimeLeft.getText());
                detail.setDatePlayed(Date.valueOf(LocalDate.now()));
                detail.setScore(Long.parseLong(lbTotalScore.getText()));
                detail.setChartData(FileRW.Read(FilePath.getChartData()));
                detailsDao.update(detail);
            } else {
                System.out.println("them hien tai vao log");
                log.setIdLog(GenerateID.genLog());
                log.setIdLevel(level.getIdLevel());
                log.setLevelName(level.getNameLevel());
                log.setIdPublisher(level.getIdAccount());
                log.setPublisherName(level.getUsername());
                log.setIdPlayer(detail.getIdAccount());
                log.setPlayerName(detail.getUsername());
                log.setScore(Long.parseLong(lbTotalScore.getText()));
                log.setWpm(Float.parseFloat(lbWPM.getText()));
                log.setWps(Float.parseFloat(lbWPS.getText()));
                log.setCorrect(Integer.parseInt(lbCorrect.getText()));
                log.setWrong(Integer.parseInt(lbWrong.getText()));
                log.setAccuracy(lbAccuracy.getText());
                log.setTimeLeft(lbTimeLeft.getText());
                log.setDatePlayed(Date.valueOf(LocalDate.now()));
                log.setChartData(FileRW.Read(FilePath.getChartData()));
                logDao.add(log);
            }
        }
        account.setCoin(account.getCoin()+Integer.parseInt(lbCoinEarned.getText()));
        accountDao.update(account);
        BindDataToChart();
        BindDataToCombobox();
        chartZooming();
        cbbChartElement.getSelectionModel().select("All");
    }

    @FXML
    public void btnRetryClicked() {
        FileRW.Write(FilePath.getRetryOrMenu(), "retry");
        DisposeForm.Dispose(lbLevelName);
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

