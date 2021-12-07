package org.SpecikMan.Controller.RankSection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.SpecikMan.Controller.RankSection.PlayLeaderboardController;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.GroupDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private Label lbCombo;
    @FXML
    private Label lbScore;
    @FXML
    private Label lbTimeUp;
    @FXML
    private Label lbRound;
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
    @FXML
    private ScrollPane scrollPaneLeaderboard;
    @FXML
    private VBox vbLeaderboard;
    @FXML
    private AnchorPane apYour;
    @FXML
    private Label lbAccountLevel;
    private static final String NOT_TYPED_PATH = FilePath.getNotTypedRank();
    private static final String TYPED_PATH = FilePath.getTYPED();
    private static final String ORIGINAL_PATH = FilePath.getOriginalRank();
    private GroupDao grDao = new GroupDao();
    private List<Group> grs = grDao.getAll().stream().filter(p -> p.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))).collect(Collectors.toList());
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

    @FXML
    public void onBtnRetryClicked() {
        initialize();
        timer2.stop();
        timer.stop();
        btnRetry.setVisible(false);
    }

    public void initialize() {
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
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
            Account accountPlay = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            lbLevelName.setText(gr.getRank().getRankName());
            lbUsername.setText(accountPlay.getUsername());
            lbRound.setText("Round "+FileRW.Read(FilePath.getRound()));
            lbAccountLevel.setText("Lv." + accountPlay.getAccountLevel() + " - " + accountPlay.getLevelExp() + "/" + accountPlay.getLevelCap());
            showLeaderboard();
            Image image = new Image(new FileInputStream(gr.getRank().getImagePath()));
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
            scrollPaneLeaderboard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPaneLeaderboard.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
                                comboScore += combo * 50L;
                                lbCorrect.setText(String.valueOf(correct));
                                lbTotal.setText(String.valueOf(total));
                                lbAccuracy.setText(BigDecimal.valueOf(Math.round((double) correct / (double) total * 100)).stripTrailingZeros().toPlainString() + "%");
                                lbWPM.setText(BigDecimal.valueOf(Math.round(((double) total_words / 5) / total_minutes)).stripTrailingZeros().toPlainString());
                                lbWPS.setText(String.valueOf(Math.round((((double) total_words / 5) / (total_minutes * 60)) * 100.0) / 100.0));
                                lbCombo.setText(String.valueOf(combo));
                                lbMaxCombo.setText(maxCombo + "");
                                long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                lbScore.setText(score + "");
                                showLeaderboard();
                                timer.stop();
                                timer2.stop();
                                transferData();
                                chartData();
                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                LoadForm.load("/fxml/RankFXMLs/LevelCleared.fxml", "Level Cleared", true);
                                if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
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
                                long score = Long.parseLong(lbScore.getText()) - (500) + (combo * 50);
                                lbScore.setText(score + "");
                                showLeaderboard();
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
                                        comboScore += combo * 50L;
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
                                        long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                        lbScore.setText(score + "");
                                        showLeaderboard();
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
                                        long score = Long.parseLong(lbScore.getText()) - (500) + (combo * 50);
                                        lbScore.setText(score + "");
                                        showLeaderboard();
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
                                        comboScore += combo * 50L;
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
                                        long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                        lbScore.setText(score + "");
                                        showLeaderboard();
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
                                        long score = Long.parseLong(lbScore.getText()) - (500) + (combo * 50);

                                        lbScore.setText(score + "");
                                        showLeaderboard();
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
                                    comboScore += combo * 50L;
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
                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                    lbScore.setText(score + "");
                                    showLeaderboard();
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
                                    long score = Long.parseLong(lbScore.getText()) - (500) + (combo * 50);
                                    lbScore.setText(score + "");
                                    showLeaderboard();
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
                GroupDao grDao = new GroupDao();
                Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
                switch (Integer.parseInt(FileRW.Read(FilePath.getRound()))) {
                    case 1: {
                        second = Integer.parseInt(gr.getRankingLevel().getTime1().split(":")[1]);
                        minute = Integer.parseInt(gr.getRankingLevel().getTime1().split(":")[0]);
                        break;
                    }
                    case 2: {
                        second = Integer.parseInt(gr.getRankingLevel().getTime2().split(":")[1]);
                        minute = Integer.parseInt(gr.getRankingLevel().getTime2().split(":")[0]);
                        break;
                    }
                    case 3: {
                        second = Integer.parseInt(gr.getRankingLevel().getTime3().split(":")[1]);
                        minute = Integer.parseInt(gr.getRankingLevel().getTime3().split(":")[0]);
                        break;
                    }
                }
                if (second == 60) {
                    minute += 1;
                    second = 0;
                }
                if (second > 60) {
                    second -= 60;
                    minute += 1;
                }
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
                DisposeForm.Dispose(lbScore);
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
                    showLeaderboard();
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

    public void transferData() {
        String data = lbWPS.getText() + "-" + lbWPM.getText() + "-" + lbCorrect.getText() + "-" + lbWrong.getText() + "-" + lbTotal.getText() + "-"
                + lbCombo.getText() + "-" + maxCombo + "-" + lbAccuracy.getText() + "-" + ddMinute + ":" + ddSecond + "-" + ddMinute2 + ":" + ddSecond2 + "-" + lbScore.getText() + "-" + lbUsername.getText() + "-" + lbLevelName.getText() + "-" + lbXMulti.getText()
                + "-" + comboScore;
        FileRW.Write(FilePath.getPlayResult(), data);
    }

    public void chartData() {
        String data = lbWPM.getText() + "-" + lbCorrect.getText() + "-" + lbWrong.getText() + "-" + lbCombo.getText() + "-" + lbAccuracy.getText() + "_";
        FileRW.Write(FilePath.getChartData(), FileRW.Read(FilePath.getChartData()) + data);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void showLeaderboard() {
        try {
            vbLeaderboard.getChildren().clear();
            apYour.getChildren().clear();
            Group yourDetail = grs.stream().filter(p -> p.getIdAccount().equals("ACTest")).findFirst().orElse(null);
            if (yourDetail == null) {
                yourDetail = new Group("ACTest", lbUsername.getText(), 0L,0L,0L,0L);
                grs.add(yourDetail);
                String oldUsername = yourDetail.getUsername();
                grs.forEach(e -> {
                    if (e.getUsername().equals(oldUsername) && e.getIdAccount().equals("ACTest") == false)
                        e.setUsername(oldUsername + "(old)");
                });
                switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                    case 1:{
                        grs.sort(Comparator.comparingLong(Group::getScore1).reversed());
                        break;
                    }
                    case 2:{
                        grs.sort(Comparator.comparingLong(Group::getScore2).reversed());
                        break;
                    }
                    case 3:{
                        grs.sort(Comparator.comparingLong(Group::getScore3).reversed());
                        break;
                    }
                }
                Node[] nodes = new Node[grs.size()];
                for (int i = 0; i < grs.size(); i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PlayController.class.getResource("/fxml/RankFXMLs/PlayLeaderboard.fxml"));
                    nodes[i] = loader.load();
                    PlayLeaderboardController controller = loader.getController();
                    switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                        case 1:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore1());
                            break;
                        }
                        case 2:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore2());
                            break;
                        }
                        case 3:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore3());
                            break;
                        }
                    }
                    vbLeaderboard.getChildren().add(nodes[i]);
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PlayController.class.getResource("/fxml/RankFXMLs/PlayLeaderboard.fxml"));
                Node node = loader.load();
                PlayLeaderboardController controller = loader.getController();
                controller.set(String.valueOf(grs.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getTotalScore());
                apYour.getChildren().add(node);
            } else {
                grs.remove(yourDetail);
                switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                    case 1:{
                        yourDetail.setScore1(Long.parseLong(lbScore.getText()));
                        break;
                    }
                    case 2:{
                        yourDetail.setScore2(Long.parseLong(lbScore.getText()));
                        break;
                    }
                    case 3:{
                        yourDetail.setScore3(Long.parseLong(lbScore.getText()));
                        break;
                    }
                }
                grs.add(yourDetail);
                String oldUsername = yourDetail.getUsername();
                grs.forEach(e -> {
                    if (e.getUsername().equals(oldUsername) && e.getIdAccount().equals("ACTest") == false)
                        e.setUsername(oldUsername + "(old)");
                });
                switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                    case 1:{
                        grs.sort(Comparator.comparingLong(Group::getScore1).reversed());
                        break;
                    }
                    case 2:{
                        grs.sort(Comparator.comparingLong(Group::getScore2).reversed());
                        break;
                    }
                    case 3:{
                        grs.sort(Comparator.comparingLong(Group::getScore3).reversed());
                        break;
                    }
                }
                Node[] nodes = new Node[grs.size()];
                for (int i = 0; i < grs.size(); i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(PlayController.class.getResource("/fxml/RankFXMLs/PlayLeaderboard.fxml"));
                    nodes[i] = loader.load();
                    PlayLeaderboardController controller = loader.getController();
                    switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                        case 1:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore1());
                            break;
                        }
                        case 2:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore2());
                            break;
                        }
                        case 3:{
                            controller.set(String.valueOf(i + 1), grs.get(i).getUsername(), grs.get(i).getScore3());
                            break;
                        }
                    }
                    vbLeaderboard.getChildren().add(nodes[i]);
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(PlayController.class.getResource("/fxml/RankFXMLs/PlayLeaderboard.fxml"));
                Node node = loader.load();
                PlayLeaderboardController controller = loader.getController();
                switch(Integer.parseInt(FileRW.Read(FilePath.getRound()))){
                    case 1:{
                        controller.set(String.valueOf(grs.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getScore1());
                        break;
                    }
                    case 2:{
                        controller.set(String.valueOf(grs.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getScore2());
                        break;
                    }
                    case 3:{
                        controller.set(String.valueOf(grs.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getScore3());
                        break;
                    }
                }
                apYour.getChildren().add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
