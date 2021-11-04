package org.SpecikMan.Controller.PracticeSection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateRandomNumbers;
import org.SpecikMan.Tools.LoadForm;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class PlayController {
    @FXML
    private Button btnPause;
    @FXML
    private Label lbAccuracy;
    @FXML
    private Label lbCorrect;
    @FXML
    private Label lbTime;
    @FXML
    private Label lbTotal;
    @FXML
    private Label lbWPM;
    @FXML
    private Label lbWrong;
    @FXML
    private TextFlow textflow;
    @FXML
    private Label lbWPS;
    @FXML
    private Label lbLevelName;
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbPublisher;
    @FXML
    private Label lbCombo;
    @FXML
    private Label lbScore;
    @FXML
    private Label lbTimeUp;
    @FXML
    private AnchorPane paneLightOff;
    private static final String NOT_TYPED_PATH = FilePath.getNotTyped();
    private static final String TYPED_PATH = FilePath.getTYPED();
    private static final String ORIGINAL_PATH = FilePath.getORIGINAL();
    int correct = 0;
    int wrong = 0;
    int total = 0;
    int combo = 0;
    int maxCombo = 0;
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private Timer timer2;
    private int second2, minute2;
    private String ddSecond2, ddMinute2;
    private Timer timer3;
    private int second3;
    private final DecimalFormat dFormat = new DecimalFormat("00");

    public void onBtnPauseClicked(MouseEvent e) {
        if (btnPause.getText().equals("Start")) {
            btnPause.setDisable(true);
            second = 5;
            minute = 0;
            CountDownTimer();
            timer.start();
        } else if (btnPause.getText().equals("Pause")) {
            btnPause.setText("Resume");
            timer.stop();
            timer2.stop();
        } else {
            btnPause.setText("Pause");
            timer.start();
            timer2.stop();
        }
    }

    public void initialize() {
        textflow.getChildren().clear();
        correct = 0;
        wrong = 0;
        total = 0;
        combo = 0;
        maxCombo = 0;
        FileRW.Write(FilePath.getChartData(), "");
        btnPause.setText("Start");
        btnPause.setDisable(false);
        LevelDao levelDao = new LevelDao();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        AccountDao accountDao = new AccountDao();
        Account accountPlay = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        Account publisher = accountDao.get(level.getIdAccount());
        FileRW.Write(FilePath.getORIGINAL(),level.getLevelContent().trim()+"_");
        lbPublisher.setText(publisher.getUsername());
        lbLevelName.setText(level.getNameLevel());
        lbUsername.setText(accountPlay.getUsername());
        char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
        FileRW.Write(NOT_TYPED_PATH, String.valueOf(chars));
        FileRW.Write(TYPED_PATH, "");
        chars = Arrays.copyOfRange(chars,0,chars.length-1);
        for (char c : chars) {
            Label l = new Label(String.valueOf(c));
            l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
            textflow.getChildren().add(l);
        }
        lbCorrect.setText("0");
        lbWrong.setText("0");
        lbTotal.setText("0");
        lbAccuracy.setText("100%");
        lbTime.setText("00:00");
        lbTimeUp.setText("00:00");
        lbCombo.setText("0");
        lbScore.setText("0");
        lbWPM.setText("0");
        lbWPS.setText("0");
        lbTime.setStyle("-fx-text-fill: black");
    }

    public void keyPressed(KeyEvent e) {
        LevelDao levelDao = new LevelDao();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        char[] typed = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).toCharArray();
        char[] notTyped = Objects.requireNonNull(FileRW.Read(NOT_TYPED_PATH)).toCharArray();
        String input = e.getText();
        double total_minutes = (((double) second2 / 60) + minute2);
        int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
        switch(level.getMode().getNameMode()){
            case "Normal":{
                if (btnPause.getText().equals("Pause")) {
                    if(String.valueOf(notTyped[1]).equals("_")){
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 1);
                        typed[typed.length - 1] = input.toCharArray()[0];
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                        for (char i : typed) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;");
                            textflow.getChildren().add(tmp);
                        }
                        for (char i : notTyped) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                            textflow.getChildren().add(tmp);
                        }
                        correct++;
                        total++;
                        combo++;
                        if(combo > maxCombo){
                            maxCombo = combo;
                        }
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                        lbCombo.setText(String.valueOf(combo));
                        lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+(Integer.parseInt(lbAccuracy.getText().split("%")[0])*(total*500)/100)+"");
                        timer.stop();
                        timer2.stop();
                        transferData();
                        chartData();
                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                        if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                            initialize();
                        } else {
                            DisposeForm.Dispose(lbScore);
                        }
                    } else {
                        if (notTyped[0] != notTyped[notTyped.length - 1]) {
                            if (!String.valueOf(notTyped[1]).equals(" ")) {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 1);
                                    typed[typed.length - 1] = input.toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    wrong++;
                                    total++;
                                    combo = 0;
                                    lbWrong.setText(String.valueOf(wrong));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                                }
                            } else {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 2, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 2);
                                    typed[typed.length - 2] = input.toCharArray()[0];
                                    typed[typed.length - 1] = " ".toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    wrong++;
                                    total++;
                                    combo = 0;
                                    lbWrong.setText(String.valueOf(wrong));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                                }
                            }
                        } else {
                            if (String.valueOf(notTyped[0]).equals(input)) {
                                textflow.getChildren().clear();
                                notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                typed = Arrays.copyOf(typed, typed.length + 1);
                                typed[typed.length - 1] = input.toCharArray()[0];
                                FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                for (char i : typed) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;");
                                    textflow.getChildren().add(tmp);
                                }
                                for (char i : notTyped) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                    textflow.getChildren().add(tmp);
                                }
                                correct++;
                                total++;
                                combo++;
                                if(combo > maxCombo){
                                    maxCombo = combo;
                                }
                                lbCorrect.setText(String.valueOf(correct));
                                lbTotal.setText(String.valueOf(total));
                                lbCombo.setText(String.valueOf(combo));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                            } else {
                                wrong++;
                                total++;
                                combo = 0;
                                lbWrong.setText(String.valueOf(wrong));
                                lbTotal.setText(String.valueOf(total));
                                lbCombo.setText(String.valueOf(combo));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                            }
                        }
                    }
                } else {
                    char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                    textflow.getChildren().clear();
                    chars = Arrays.copyOfRange(chars,0,chars.length);
                    for (char c : chars) {
                        Label l = new Label(String.valueOf(c));
                        l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
                        textflow.getChildren().add(l);
                    }
                }
                break;
            }
            case "Instant Death": {
                if (btnPause.getText().equals("Pause")) {
                    if(String.valueOf(notTyped[1]).equals("_")){
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 1);
                        typed[typed.length - 1] = input.toCharArray()[0];
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                        for (char i : typed) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;");
                            textflow.getChildren().add(tmp);
                        }
                        for (char i : notTyped) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                            textflow.getChildren().add(tmp);
                        }
                        correct++;
                        total++;
                        combo++;
                        if(combo > maxCombo){
                            maxCombo = combo;
                        }
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                        lbCombo.setText(String.valueOf(combo));
                        lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+(Integer.parseInt(lbAccuracy.getText().split("%")[0])*(total*500)/100)+"");
                        timer.stop();
                        timer2.stop();
                        transferData();
                        chartData();
                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                        if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                            initialize();
                        } else {
                            DisposeForm.Dispose(lbScore);
                        }
                    } else {
                        if (notTyped[0] != notTyped[notTyped.length - 1]) {
                            if (!String.valueOf(notTyped[1]).equals(" ")) {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 1);
                                    typed[typed.length - 1] = input.toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    timer.stop();
                                    timer2.stop();
                                    LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                    if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                                        initialize();
                                    } else {
                                        DisposeForm.Dispose(lbScore);
                                    }
                                }
                            } else {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 2, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 2);
                                    typed[typed.length - 2] = input.toCharArray()[0];
                                    typed[typed.length - 1] = " ".toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    timer.stop();
                                    timer2.stop();
                                    LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                    if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                                        initialize();
                                    } else {
                                        DisposeForm.Dispose(lbScore);
                                    }
                                }
                            }
                        } else {
                            if (String.valueOf(notTyped[0]).equals(input)) {
                                textflow.getChildren().clear();
                                notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                typed = Arrays.copyOf(typed, typed.length + 1);
                                typed[typed.length - 1] = input.toCharArray()[0];
                                FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                for (char i : typed) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;");
                                    textflow.getChildren().add(tmp);
                                }
                                for (char i : notTyped) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                    textflow.getChildren().add(tmp);
                                }
                                correct++;
                                total++;
                                combo++;
                                if(combo > maxCombo){
                                    maxCombo = combo;
                                }
                                lbCorrect.setText(String.valueOf(correct));
                                lbTotal.setText(String.valueOf(total));
                                lbCombo.setText(String.valueOf(combo));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                            } else {
                                timer.stop();
                                timer2.stop();
                                LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                                    initialize();
                                } else {
                                    DisposeForm.Dispose(lbScore);
                                }
                            }
                        }
                    }
                } else {
                    char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                    textflow.getChildren().clear();
                    chars = Arrays.copyOfRange(chars,0,chars.length);
                    for (char c : chars) {
                        Label l = new Label(String.valueOf(c));
                        l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
                        textflow.getChildren().add(l);
                    }
                }
                break;
            }
            case "Blackout":{
                int number = Integer.parseInt(GenerateRandomNumbers.generate());
                if(number%5==0){
                    paneLightOff.toFront();
                    paneLightOff.setStyle("-fx-background-color: #000000");
                    second3 = getRandomNumber(1,2);
                    CountDownTimerBlackout();
                }
                if (btnPause.getText().equals("Pause")) {
                    if(String.valueOf(notTyped[1]).equals("_")){
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 1);
                        typed[typed.length - 1] = input.toCharArray()[0];
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                        for (char i : typed) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;");
                            textflow.getChildren().add(tmp);
                        }
                        for (char i : notTyped) {
                            Label tmp = new Label(String.valueOf(i));
                            tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                            textflow.getChildren().add(tmp);
                        }
                        correct++;
                        total++;
                        combo++;
                        if(combo > maxCombo){
                            maxCombo = combo;
                        }
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                        lbCombo.setText(String.valueOf(combo));
                        lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+(Integer.parseInt(lbAccuracy.getText().split("%")[0])*(total*500)/100)+"");
                        timer.stop();
                        timer2.stop();
                        transferData();
                        chartData();
                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                        if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                            initialize();
                        } else {
                            DisposeForm.Dispose(lbScore);
                        }
                    } else {
                        if (notTyped[0] != notTyped[notTyped.length - 1]) {
                            if (!String.valueOf(notTyped[1]).equals(" ")) {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 1);
                                    typed[typed.length - 1] = input.toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    wrong++;
                                    total++;
                                    combo = 0;
                                    lbWrong.setText(String.valueOf(wrong));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                                }
                            } else {
                                if (String.valueOf(notTyped[0]).equals(input)) {
                                    textflow.getChildren().clear();
                                    notTyped = Arrays.copyOfRange(notTyped, 2, notTyped.length);
                                    typed = Arrays.copyOf(typed, typed.length + 2);
                                    typed[typed.length - 2] = input.toCharArray()[0];
                                    typed[typed.length - 1] = " ".toCharArray()[0];
                                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                    notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                    for (char i : typed) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    for (char i : notTyped) {
                                        Label tmp = new Label(String.valueOf(i));
                                        tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                        textflow.getChildren().add(tmp);
                                    }
                                    correct++;
                                    total++;
                                    combo++;
                                    if(combo > maxCombo){
                                        maxCombo = combo;
                                    }
                                    lbCorrect.setText(String.valueOf(correct));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                                } else {
                                    wrong++;
                                    total++;
                                    combo = 0;
                                    lbWrong.setText(String.valueOf(wrong));
                                    lbTotal.setText(String.valueOf(total));
                                    lbCombo.setText(String.valueOf(combo));
                                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                    lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                                }
                            }
                        } else {
                            if (String.valueOf(notTyped[0]).equals(input)) {
                                textflow.getChildren().clear();
                                notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                                typed = Arrays.copyOf(typed, typed.length + 1);
                                typed[typed.length - 1] = input.toCharArray()[0];
                                FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                                FileRW.Write(TYPED_PATH, String.valueOf(typed));
                                notTyped = Arrays.copyOfRange(notTyped, 0, notTyped.length - 1);
                                for (char i : typed) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;");
                                    textflow.getChildren().add(tmp);
                                }
                                for (char i : notTyped) {
                                    Label tmp = new Label(String.valueOf(i));
                                    tmp.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                                    textflow.getChildren().add(tmp);
                                }
                                correct++;
                                total++;
                                combo++;
                                if(combo > maxCombo){
                                    maxCombo = combo;
                                }
                                lbCorrect.setText(String.valueOf(correct));
                                lbTotal.setText(String.valueOf(total));
                                lbCombo.setText(String.valueOf(combo));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbScore.setText(Integer.parseInt(lbScore.getText())+(500)+(combo*50)+"");
                            } else {
                                wrong++;
                                total++;
                                combo = 0;
                                lbWrong.setText(String.valueOf(wrong));
                                lbTotal.setText(String.valueOf(total));
                                lbCombo.setText(String.valueOf(combo));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbScore.setText(Integer.parseInt(lbScore.getText())-(500)+(combo*50)+"");
                            }
                        }
                    }
                } else {
                    char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                    textflow.getChildren().clear();
                    chars = Arrays.copyOfRange(chars,0,chars.length);
                    for (char c : chars) {
                        Label l = new Label(String.valueOf(c));
                        l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
                        textflow.getChildren().add(l);
                    }
                }
                break;
            }
            case "Death Token":{

            }
        }


    }

    public void CountDownTimer() {
        timer = new Timer(1000, e -> Platform.runLater(() -> {
            //javaFX operations should go here
            second--;
            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);
            lbTime.setText(ddMinute + ":" + ddSecond);
            if (second == -1) {
                second = 59;
                minute--;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                lbTime.setText(ddMinute + ":" + ddSecond);
            }
            if (minute == 0 && second == 0) {
                timer.stop();
                btnPause.setText("Pause");
                lbTime.setStyle("-fx-text-fill: red");
                btnPause.setDisable(false);
                LevelDao levelDao = new LevelDao();
                Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
                second = Integer.parseInt(level.getTime().split(":")[1]);
                minute = Integer.parseInt(level.getTime().split(":")[0]);
                lbScore.setText(((minute*60)+second)*1000+"");
                CountDownTimerPlay();
                second2 = 0;
                minute2 = 0;
                normalTimer();
                timer.start();
                timer2.start();
            }
        }));
    }

    public void CountDownTimerPlay() {
        timer = new Timer(1000, e -> Platform.runLater(() -> {
            //javaFX operations should go here
            second--;
            ddSecond = dFormat.format(second);
            ddMinute = dFormat.format(minute);
            lbTime.setText(ddMinute + ":" + ddSecond);
            if (second == -1) {
                second = 59;
                minute--;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                lbTime.setText(ddMinute + ":" + ddSecond);
            }
            if (minute == 0 && second == 0) {
                timer.stop();
                timer2.stop();
                LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                if(Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")){
                    initialize();
                } else {
                    DisposeForm.Dispose(lbScore);
                }
            }
        }));
    }


    public void normalTimer() {
        timer2 = new Timer(1000, e -> Platform.runLater(() -> {
                    second2++;
                    ddSecond2 = dFormat.format(second2);
                    ddMinute2 = dFormat.format(minute2);
                    lbTimeUp.setText(ddMinute2 + ":" + ddSecond2);
                    double total_minutes = (((double) second2 / 60) + minute2);
                    int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                    lbScore.setText(Integer.parseInt(lbScore.getText()) - 1000 + "");
                    chartData();
                    if (second == 60) {
                        second2 = 0;
                        minute2++;
                        ddSecond2 = dFormat.format(second2);
                        ddMinute2 = dFormat.format(minute2);
                        lbTimeUp.setText(ddMinute2 + ":" + ddSecond2);
                    }
                }
        ));
    }
    public void CountDownTimerBlackout() {
        timer3 = new Timer(1000, e -> Platform.runLater(() -> {
            //javaFX operations should go here
            second3--;
            if (second3 == 0) {
                paneLightOff.toBack();
                paneLightOff.setStyle("-fx-background-color: transparent;");
            }
        }));
    }

    public void transferData() {
        String data = lbWPS.getText() + "-" + lbWPM.getText() + "-" + lbCorrect.getText() + "-" + lbWrong.getText() + "-" + lbTotal.getText() + "-"
                + lbCombo.getText() + "-" + maxCombo + "-" + lbAccuracy.getText() + "-" + ddMinute + ":" + ddSecond + "-" + ddMinute2 + ":" + ddSecond2 + "-" + lbScore.getText() + "-" + lbUsername.getText() + "-" + lbLevelName.getText();
        FileRW.Write(FilePath.getPlayResult(), data);
    }

    public void chartData() {
        String data = lbWPM.getText() + "-" +lbCorrect.getText() + "-" + lbWrong.getText() + "-" + lbCombo.getText() + "-" + lbAccuracy.getText() + "_";
        FileRW.Write(FilePath.getChartData(), FileRW.Read(FilePath.getChartData()) + data);
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
