package org.SpecikMan.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.FileRW;

import javax.swing.*;
import java.io.File;
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
    private static final String NOT_TYPED_PATH = FilePath.getNotTyped();
    private static final String TYPED_PATH = FilePath.getTYPED();
    private static final String ORIGINAL_PATH = FilePath.getORIGINAL();
    int correct = 0;
    int wrong = 0;
    int total = 0;
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private final DecimalFormat dFormat = new DecimalFormat("00");

    public void onBtnPauseClicked(MouseEvent e) {
        if (btnPause.getText().equals("Start")) {
            second = 7;
            minute = 0;
            CountDownTimer();
            timer.start();
        } else if (btnPause.getText().equals("Pause")) {
            btnPause.setText("Resume");
            timer.stop();
        } else {
            btnPause.setText("Pause");
            timer.start();
        }
    }

    public void initialize() {
        LevelDao levelDao = new LevelDao();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        AccountDao accountDao = new AccountDao();
        Account accountPlay = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        Account publisher = accountDao.get(level.getIdAccount());
        lbPublisher.setText(publisher.getUsername());
        lbLevelName.setText(level.getNameLevel());
        lbUsername.setText(accountPlay.getUsername());
        char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
        FileRW.Write(NOT_TYPED_PATH, String.valueOf(chars));
        FileRW.Write(TYPED_PATH, "");
        textflow.setPrefWidth(710);
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
    }

    public void keyPressed(KeyEvent e) {
        char[] typed = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).toCharArray();
        char[] notTyped = Objects.requireNonNull(FileRW.Read(NOT_TYPED_PATH)).toCharArray();
        int pos = 0;
        String input = e.getText();
        double total_minutes = (((double) second / 60) + minute);
        int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
        if (btnPause.getText().equals("Pause")) {
            if (notTyped[0] != notTyped[notTyped.length - 1]) {
                if (!String.valueOf(notTyped[1]).equals(" ")) {
                    if (String.valueOf(notTyped[0]).equals(input)) {
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 1);
                        typed[typed.length - 1] = input.toCharArray()[0];
                        Label l = new Label(input);
                        Label l2 = new Label(String.valueOf(notTyped));
                        Label l1 = new Label(String.valueOf(typed));
                        l.setStyle("-fx-font-size: 20;");
                        l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                        l1.setStyle("-fx-font-size: 20;");
                        textflow.getChildren().addAll(l1, l2);
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        correct++;
                        total++;
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                    } else {
                        wrong++;
                        total++;
                        lbWrong.setText(String.valueOf(wrong));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                    }
                } else {
                    if (String.valueOf(notTyped[0]).equals(input)) {
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 2, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 2);
                        typed[typed.length - 2] = input.toCharArray()[0];
                        typed[typed.length - 1] = " ".toCharArray()[0];
                        Label l = new Label(input);
                        Label l2 = new Label(String.valueOf(notTyped));
                        Label l1 = new Label(String.valueOf(typed));
                        l.setStyle("-fx-font-size: 20;");
                        l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                        l1.setStyle("-fx-font-size: 20;");
                        textflow.getChildren().addAll(l1, l2);
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        correct++;
                        total++;
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                    } else {
                        wrong++;
                        total++;
                        lbWrong.setText(String.valueOf(wrong));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                        lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                        lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                    }
                }
            } else {
                if (String.valueOf(notTyped[0]).equals(input)) {
                    textflow.getChildren().clear();
                    notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                    typed = Arrays.copyOf(typed, typed.length + 1);
                    typed[typed.length - 1] = input.toCharArray()[0];
                    Label l = new Label(input);
                    Label l2 = new Label(String.valueOf(notTyped));
                    Label l1 = new Label(String.valueOf(typed));
                    l.setStyle("-fx-font-size: 20;");
                    l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                    l1.setStyle("-fx-font-size: 20;");
                    textflow.getChildren().addAll(l1, l2);
                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                    correct++;
                    total++;
                    lbCorrect.setText(String.valueOf(correct));
                    lbTotal.setText(String.valueOf(total));
                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                } else {
                    wrong++;
                    total++;
                    lbWrong.setText(String.valueOf(wrong));
                    lbTotal.setText(String.valueOf(total));
                    lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                    lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                }
            }
        } else {
            char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
            textflow.getChildren().clear();
            textflow.setPrefWidth(710);
            for (char c : chars) {
                Label l = new Label(String.valueOf(c));
                l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
                textflow.getChildren().add(l);
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
                second = 0;
                minute = 0;
                normalTimer();
                timer.start();
            }
        }));
    }

    public void normalTimer() {
        timer = new Timer(1000, e -> Platform.runLater(() -> {
                    second++;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    lbTime.setText(ddMinute + ":" + ddSecond);
                    if (second == 60) {
                        second = 0;
                        minute++;
                        ddSecond = dFormat.format(second);
                        ddMinute = dFormat.format(minute);
                        lbTime.setText(ddMinute + ":" + ddSecond);
                    }
                    double total_minutes = (((double) second / 60) + minute);
                    int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
                    lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
            lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60))*100.0)/100.0));
                }
        ));
    }
}
