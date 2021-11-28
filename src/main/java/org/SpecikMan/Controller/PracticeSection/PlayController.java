package org.SpecikMan.Controller.PracticeSection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Hyperlink hlPublisher;
    @FXML
    private Label lbCombo;
    @FXML
    private Label lbScore;
    @FXML
    private Label lbTimeUp;
    @FXML
    private AnchorPane paneLightOff;
    @FXML
    private ImageView imagePublisher;
    @FXML
    private ImageView imagePlayer;
    @FXML
    private Label lbMaxCombo;
    @FXML
    private Label lbAccuracyScore;
    @FXML
    private Button btnRetry;
    @FXML
    private Label lbXMulti;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbTimeScore;
    private static final String NOT_TYPED_PATH = FilePath.getNotTyped();
    private static final String TYPED_PATH = FilePath.getTYPED();
    private static final String ORIGINAL_PATH = FilePath.getORIGINAL();
    int correct = 0;
    int wrong = 0;
    int total = 0;
    int combo = 0;
    int maxCombo = 0;
    long comboScore = 0;
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private Timer timer2;
    private int second2, minute2;
    private String ddSecond2, ddMinute2;
    private Timer timer4;
    private int second4, minute4;
    private String ddSecond4, ddMinute4;
    private Timer timer3;
    private int second3;
    private final DecimalFormat dFormat = new DecimalFormat("00");
    int[] tokens = new int[Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).replaceAll("\\s+", "").toCharArray().length / 2];

    public void onBtnPauseClicked(MouseEvent e) {
        if (btnPause.getText().equals("Start")) {
            btnPause.setDisable(true);
            second4 = 5;
            minute4 = 0;
            CountDownTimer();
            timer4.start();
        } else if (btnPause.getText().equals("Pause")) {
            btnPause.setText("Resume");
            timer.stop();
            timer2.stop();
        } else {
            btnPause.setText("Pause");
            timer.start();
            timer2.start();
        }
    }
    public void resetStatus() {
        try {
            btnRetry.setVisible(false);
            textflow.getChildren().clear();
            correct = 0;
            wrong = 0;
            total = 0;
            combo = 0;
            maxCombo = 0;
            FileRW.Write(FilePath.getChartData(), "");
            btnPause.setText("Start");
            btnPause.setDisable(false);
            LevelDao levelDao2 = new LevelDao();
            Level level2 = levelDao2.get(FileRW.Read(FilePath.getPlayLevel()));
            AccountDao accountDao = new AccountDao();
            Account accountPlay = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            Account publisher = accountDao.get(level2.getIdAccount());
            FileRW.Write(FilePath.getORIGINAL(), level2.getLevelContent().trim() + "_");
            hlPublisher.setText(publisher.getUsername());
            lbLevelName.setText(level2.getNameLevel());
            lbUsername.setText(accountPlay.getUsername());
            Image image = new Image(new FileInputStream(publisher.getPathImage()));
            imagePublisher.setImage(image);
            Image image2 = new Image(new FileInputStream(accountPlay.getPathImage()));
            imagePlayer.setImage(image2);
            char[] chars2 = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
            FileRW.Write(NOT_TYPED_PATH, String.valueOf(chars2));
            FileRW.Write(TYPED_PATH, "");
            chars2 = Arrays.copyOfRange(chars2, 0, chars2.length - 1);
            for (char c : chars2) {
                Label l = new Label(String.valueOf(c));
                l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
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
            lbMaxCombo.setText("0");
            lbAccuracyScore.setText("0");
            lbXMulti.setText("1x");
            lbTimeScore.setText("0");
            lbTime.setStyle("-fx-text-fill: black");
            if (level2.getMode().getNameMode().equals("Death Token")) {
                for (int i = 0; i < tokens.length / 2; i++) {
                    int random = getRandomNumber(3, tokens.length);
                    for (int j = 0; j < tokens.length / 2; j++) {
                        if (tokens[j] != random) {
                            tokens[i] = random;
                            break;
                        }
                    }
                }
                for (int i : tokens) {
                    System.out.println(i);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void onBtnRetryClicked(){
        initialize();
        timer2.stop();
        timer.stop();
        btnRetry.setVisible(false);
    }
    public void initialize(){
        try {
            btnRetry.setVisible(false);
            textflow.getChildren().clear();
            correct = 0;
            wrong = 0;
            total = 0;
            combo = 0;
            maxCombo = 0;
            FileRW.Write(FilePath.getChartData(), "");
            btnPause.setText("Start");
            btnPause.setDisable(false);
            LevelDao levelDao2 = new LevelDao();
            Level level2 = levelDao2.get(FileRW.Read(FilePath.getPlayLevel()));
            AccountDao accountDao = new AccountDao();
            Account accountPlay = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            Account publisher = accountDao.get(level2.getIdAccount());
            FileRW.Write(FilePath.getORIGINAL(), level2.getLevelContent().trim() + "_");
            hlPublisher.setText(publisher.getUsername());
            lbLevelName.setText(level2.getNameLevel());
            lbUsername.setText(accountPlay.getUsername());
            Image image = new Image(new FileInputStream(publisher.getPathImage()));
            imagePublisher.setImage(image);
            Image image2 = new Image(new FileInputStream(accountPlay.getPathImage()));
            imagePlayer.setImage(image2);
            char[] chars2 = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
            FileRW.Write(NOT_TYPED_PATH, String.valueOf(chars2));
            FileRW.Write(TYPED_PATH, "");
            chars2 = Arrays.copyOfRange(chars2, 0, chars2.length - 1);
            for (char c : chars2) {
                Label l = new Label(String.valueOf(c));
                l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
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
            lbMaxCombo.setText("0");
            lbAccuracyScore.setText("0");
            lbXMulti.setText("1x");
            lbTimeScore.setText("0");
            lbTime.setStyle("-fx-text-fill: black");
            if (level2.getMode().getNameMode().equals("Death Token")) {
                for (int i = 0; i < tokens.length / 2; i++) {
                    int random = getRandomNumber(3, tokens.length);
                    for (int j = 0; j < tokens.length / 2; j++) {
                        if (tokens[j] != random) {
                            tokens[i] = random;
                            break;
                        }
                    }
                }
                for (int i : tokens) {
                    System.out.println(i);
                }
            }
            anchorPane.addEventHandler(KeyEvent.KEY_TYPED, event -> {
                event.consume();
                LevelDao levelDao = new LevelDao();
                Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
                char[] typed = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).toCharArray();
                char[] notTyped = Objects.requireNonNull(FileRW.Read(NOT_TYPED_PATH)).toCharArray();
                String input = event.getCharacter();
                double total_minutes = (((double) second2 / 60) + minute2);
                int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
                if (!input.equals(" ")) {
                    switch (level.getMode().getNameMode()) {
                        case "Normal": {
                            if (btnPause.getText().equals("Pause")) {
                                if (String.valueOf(notTyped[1]).equals("_")) {
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
                                            tmp.setStyle("-fx-font-size: 17;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        for (char i : notTyped) {
                                            Label tmp = new Label(String.valueOf(i));
                                            tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        correct++;
                                        total++;
                                        combo++;
                                        if (combo > maxCombo) {
                                            maxCombo = combo;
                                        }
                                        if (combo % 100 == 0) {
                                            lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                        }
                                        comboScore +=combo * 50L;
                                        lbCorrect.setText(String.valueOf(correct));
                                        lbTotal.setText(String.valueOf(total));
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100) + "");
                                        timer.stop();
                                        timer2.stop();
                                        transferData();
                                        chartData();
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                            resetStatus();
                                            btnRetry.setVisible(false);
                                        } else {
                                            DisposeForm.Dispose(lbScore);
                                        }
                                    } else {
                                        wrong++;
                                        total++;
                                        combo = 0;
                                        lbWrong.setText(String.valueOf(wrong));
                                        lbTotal.setText(String.valueOf(total));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0].split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                tmp.setStyle("-fx-font-size: 17;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            for (char i : notTyped) {
                                                Label tmp = new Label(String.valueOf(i));
                                                tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            correct++;
                                            total++;
                                            combo++;
                                            comboScore +=combo * 50L;
                                            if (combo > maxCombo) {
                                                maxCombo = combo;
                                            }
                                            if (combo % 100 == 0) {
                                                lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                            }
                                            lbCorrect.setText(String.valueOf(correct));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        } else {
                                            wrong++;
                                            total++;
                                            combo = 0;
                                            lbWrong.setText(String.valueOf(wrong));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        }
                                    }
                                }
                            } else {
                                char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                                textflow.getChildren().clear();
                                chars = Arrays.copyOfRange(chars, 0, chars.length - 1);
                                for (char c : chars) {
                                    Label l = new Label(String.valueOf(c));
                                    l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
                                    textflow.getChildren().add(l);
                                }
                            }
                            break;
                        }
                        case "Instant Death": {
                            if (btnPause.getText().equals("Pause")) {
                                if (String.valueOf(notTyped[1]).equals("_")) {
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
                                            tmp.setStyle("-fx-font-size: 17;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        for (char i : notTyped) {
                                            Label tmp = new Label(String.valueOf(i));
                                            tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        correct++;
                                        total++;
                                        combo++;
                                        comboScore +=combo * 50L;
                                        if (combo > maxCombo) {
                                            maxCombo = combo;
                                        }
                                        if (combo % 100 == 0) {
                                            lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                        }
                                        lbCorrect.setText(String.valueOf(correct));
                                        lbTotal.setText(String.valueOf(total));
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100) + "");
                                        timer.stop();
                                        timer2.stop();
                                        transferData();
                                        chartData();
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                            resetStatus();
                                        } else {
                                            DisposeForm.Dispose(lbScore);
                                        }
                                    } else {
                                        System.out.println("end 1");
                                        timer.stop();
                                        timer2.stop();
                                        LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                resetStatus();
                                        } else {
                                            DisposeForm.Dispose(lbScore);
                                        }
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                System.out.println("end 2");
                                                timer.stop();
                                                timer2.stop();
                                                LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                    resetStatus();
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                System.out.println("end 3");
                                                timer.stop();
                                                timer2.stop();
                                                LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                    resetStatus();
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
                                                tmp.setStyle("-fx-font-size: 17;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            for (char i : notTyped) {
                                                Label tmp = new Label(String.valueOf(i));
                                                tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            correct++;
                                            total++;
                                            combo++;
                                            comboScore +=combo * 50L;
                                            if (combo > maxCombo) {
                                                maxCombo = combo;
                                            }
                                            if (combo % 100 == 0) {
                                                lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                            }
                                            lbCorrect.setText(String.valueOf(correct));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        } else {
                                            System.out.println("end 4");
                                            timer.stop();
                                            timer2.stop();
                                            LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                            if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                resetStatus();
                                            } else {
                                                DisposeForm.Dispose(lbScore);
                                            }
                                        }
                                    }
                                }
                            } else {
                                char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                                textflow.getChildren().clear();
                                chars = Arrays.copyOfRange(chars, 0, chars.length - 1);
                                for (char c : chars) {
                                    Label l = new Label(String.valueOf(c));
                                    l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
                                    textflow.getChildren().add(l);
                                }
                            }
                            break;
                        }
                        case "Blackout": {
                            int number = Integer.parseInt(GenerateRandomNumbers.generate());
                            if (number % 5 == 0) {
                                paneLightOff.toFront();
                                paneLightOff.setStyle("-fx-background-color: #000000");
                                second3 = getRandomNumber(1, 5);
                                CountDownTimerBlackout();
                                timer3.start();
                            }
                            if (btnPause.getText().equals("Pause")) {
                                if (String.valueOf(notTyped[1]).equals("_")) {
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
                                            tmp.setStyle("-fx-font-size: 17;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        for (char i : notTyped) {
                                            Label tmp = new Label(String.valueOf(i));
                                            tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        correct++;
                                        total++;
                                        combo++;
                                        comboScore +=combo * 50L;
                                        if (combo > maxCombo) {
                                            maxCombo = combo;
                                        }
                                        if (combo % 100 == 0) {
                                            lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                        }
                                        lbCorrect.setText(String.valueOf(correct));
                                        lbTotal.setText(String.valueOf(total));
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100) + "");
                                        timer.stop();
                                        timer2.stop();
                                        transferData();
                                        chartData();
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                            resetStatus();
                                        } else {
                                            DisposeForm.Dispose(lbScore);
                                        }
                                    } else {
                                        wrong++;
                                        total++;
                                        combo = 0;
                                        lbWrong.setText(String.valueOf(wrong));
                                        lbTotal.setText(String.valueOf(total));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                for (char i : notTyped) {
                                                    Label tmp = new Label(String.valueOf(i));
                                                    tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                tmp.setStyle("-fx-font-size: 17;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            for (char i : notTyped) {
                                                Label tmp = new Label(String.valueOf(i));
                                                tmp.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            correct++;
                                            total++;
                                            combo++;
                                            comboScore +=combo * 50L;
                                            if (combo > maxCombo) {
                                                maxCombo = combo;
                                            }
                                            if (combo % 100 == 0) {
                                                lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                            }
                                            lbCorrect.setText(String.valueOf(correct));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        } else {
                                            wrong++;
                                            total++;
                                            combo = 0;
                                            lbWrong.setText(String.valueOf(wrong));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        }
                                    }
                                }
                            } else {
                                char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                                textflow.getChildren().clear();
                                chars = Arrays.copyOfRange(chars, 0, chars.length - 1);
                                for (char c : chars) {
                                    Label l = new Label(String.valueOf(c));
                                    l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
                                    textflow.getChildren().add(l);
                                }
                            }
                            break;
                        }
                        case "Hidden": {
                            if (btnPause.getText().equals("Pause")) {
                                if (String.valueOf(notTyped[1]).equals("_")) {
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
                                            tmp.setStyle("-fx-font-size: 17;");
                                            textflow.getChildren().add(tmp);
                                        }
                                        //
                                        if (notTyped.length != 0) {
                                            Label tmp2 = new Label(String.valueOf(notTyped[0]));
                                            tmp2.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                            textflow.getChildren().add(tmp2);
                                        }
                                        //
                                        for (int i = 1; i < notTyped.length; i++) {
                                            Label tmp3 = new Label(String.valueOf(notTyped[i]));
                                            tmp3.setStyle("-fx-font-size: 17;-fx-text-fill: #bed7ed;");
                                            textflow.getChildren().add(tmp3);
                                        }
                                        correct++;
                                        total++;
                                        combo++;
                                        comboScore +=combo * 50L;
                                        if (combo > maxCombo) {
                                            maxCombo = combo;
                                        }
                                        if (combo % 100 == 0) {
                                            lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                        }
                                        lbCorrect.setText(String.valueOf(correct));
                                        lbTotal.setText(String.valueOf(total));
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100) + "");
                                        timer.stop();
                                        timer2.stop();
                                        transferData();
                                        chartData();
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        LoadForm.load("/fxml/PracticeFXMLs/LevelCleared.fxml", "Level Cleared", true);
                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                            resetStatus();
                                        } else {
                                            DisposeForm.Dispose(lbScore);
                                        }
                                    } else {
                                        wrong++;
                                        total++;
                                        combo = 0;
                                        lbWrong.setText(String.valueOf(wrong));
                                        lbTotal.setText(String.valueOf(total));
                                        lbCombo.setText(String.valueOf(combo));
                                        lbMaxCombo.setText(maxCombo + "");
                                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                        lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                if (notTyped.length != 0) {
                                                    Label tmp2 = new Label(String.valueOf(notTyped[0]));
                                                    tmp2.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp2);
                                                }
                                                //
                                                for (int i = 1; i < notTyped.length; i++) {
                                                    Label tmp3 = new Label(String.valueOf(notTyped[i]));
                                                    tmp3.setStyle("-fx-font-size: 17;-fx-text-fill: #bed7ed;");
                                                    textflow.getChildren().add(tmp3);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                    tmp.setStyle("-fx-font-size: 17;");
                                                    textflow.getChildren().add(tmp);
                                                }
                                                if (notTyped.length != 0) {
                                                    Label tmp2 = new Label(String.valueOf(notTyped[0]));
                                                    tmp2.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                    textflow.getChildren().add(tmp2);
                                                }
                                                //
                                                for (int i = 1; i < notTyped.length; i++) {
                                                    Label tmp3 = new Label(String.valueOf(notTyped[i]));
                                                    tmp3.setStyle("-fx-font-size: 17;-fx-text-fill: #bed7ed;");
                                                    textflow.getChildren().add(tmp3);
                                                }
                                                correct++;
                                                total++;
                                                combo++;
                                                comboScore +=combo * 50L;
                                                if (combo > maxCombo) {
                                                    maxCombo = combo;
                                                }
                                                if (combo % 100 == 0) {
                                                    lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                                }
                                                lbCorrect.setText(String.valueOf(correct));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                wrong++;
                                                total++;
                                                combo = 0;
                                                lbWrong.setText(String.valueOf(wrong));
                                                lbTotal.setText(String.valueOf(total));
                                                lbCombo.setText(String.valueOf(combo));
                                                lbMaxCombo.setText(maxCombo + "");
                                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                                lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
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
                                                tmp.setStyle("-fx-font-size: 17;");
                                                textflow.getChildren().add(tmp);
                                            }
                                            if (notTyped.length != 0) {
                                                Label tmp2 = new Label(String.valueOf(notTyped[0]));
                                                tmp2.setStyle("-fx-font-size: 17;-fx-text-fill: gray;");
                                                textflow.getChildren().add(tmp2);
                                            }
                                            //
                                            for (int i = 1; i < notTyped.length; i++) {
                                                Label tmp3 = new Label(String.valueOf(notTyped[i]));
                                                tmp3.setStyle("-fx-font-size: 17;-fx-text-fill: #bed7ed;");
                                                textflow.getChildren().add(tmp3);
                                            }
                                            correct++;
                                            total++;
                                            combo++;
                                            comboScore +=combo * 50L;
                                            if (combo > maxCombo) {
                                                maxCombo = combo;
                                            }
                                            if (combo % 100 == 0) {
                                                lbXMulti.setText(Integer.parseInt(lbXMulti.getText().split("x")[0]) + 1 + "x");
                                            }
                                            lbCorrect.setText(String.valueOf(correct));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) + (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        } else {
                                            wrong++;
                                            total++;
                                            combo = 0;
                                            lbWrong.setText(String.valueOf(wrong));
                                            lbTotal.setText(String.valueOf(total));
                                            lbCombo.setText(String.valueOf(combo));
                                            lbMaxCombo.setText(maxCombo + "");
                                            lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                            lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                            lbScore.setText(Integer.parseInt(lbScore.getText()) - (500) + (combo * 50) + "");
                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                        }
                                    }
                                }
                            } else {
                                char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
                                textflow.getChildren().clear();
                                chars = Arrays.copyOfRange(chars, 0, chars.length - 1);
                                for (char c : chars) {
                                    Label l = new Label(String.valueOf(c));
                                    l.setStyle("-fx-font-size: 17;-fx-text-fill: gray");
                                    textflow.getChildren().add(l);
                                }
                            }
                            break;
                        }
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void CountDownTimer() {
        timer4 = new Timer(1000, e -> Platform.runLater(() -> {
            //javaFX operations should go here
            second4--;
            ddSecond4 = dFormat.format(second4);
            ddMinute4 = dFormat.format(minute4);
            lbTime.setText(ddMinute4 + ":" + ddSecond4);
            if (second4 == -1) {
                second4 = 59;
                minute4--;
                ddSecond4 = dFormat.format(second4);
                ddMinute4 = dFormat.format(minute4);
                lbTime.setText(ddMinute4 + ":" + ddSecond4);
            }
            if (minute4 == 0 && second4 == 0) {
                timer4.stop();
                LevelDao levelDao = new LevelDao();
                Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
                second = Integer.parseInt(level.getTime().split(":")[1]);
                minute = Integer.parseInt(level.getTime().split(":")[0]);
                lbScore.setText(((minute * 60) + second) * 1000 + "");
                lbTimeScore.setText(((minute * 60) + second) * 1000 + "");
                CountDownTimerPlay();
                timer.start();
                timer2.start();
            }
        }));
    }

    public void CountDownTimerPlay() {
        second2 = -1;
        minute2 = 0;
        normalTimer();
        lbTime.setStyle("-fx-text-fill: red");
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
                    resetStatus();
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
                    lbTimeScore.setText(Integer.parseInt(lbTimeScore.getText()) - 1000 + "");
                    btnPause.setText("Pause");
                    btnRetry.setVisible(true);
                    btnPause.setDisable(false);
                    chartData();
                    if (second2 == 60) {
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
                timer3.stop();
                paneLightOff.toBack();
                paneLightOff.setStyle("-fx-background-color: transparent;");
            }
        }));
    }

    public void transferData() {
        String data = lbWPS.getText() + "-" + lbWPM.getText() + "-" + lbCorrect.getText() + "-" + lbWrong.getText() + "-" + lbTotal.getText() + "-"
                + lbCombo.getText() + "-" + maxCombo + "-" + lbAccuracy.getText() + "-" + ddMinute + ":" + ddSecond + "-" + ddMinute2 + ":" + ddSecond2 + "-" + lbScore.getText() + "-" + lbUsername.getText() + "-" + lbLevelName.getText() +"-"+ lbXMulti.getText()
                +"-"+comboScore;
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
