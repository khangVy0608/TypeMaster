package org.SpecikMan.Controller.PracticeSection;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.SpecikMan.DAL.*;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateRandomNumbers;
import org.SpecikMan.Tools.LoadForm;

import javax.swing.Timer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
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
    @FXML
    private ScrollPane scrollPaneLeaderboard;
    @FXML
    private VBox vbLeaderboard;
    @FXML
    private ComboBox<Shop> cbbPassive;
    @FXML
    private ComboBox<Shop> cbbActive;
    @FXML
    private AnchorPane apYour;
    @FXML
    private Label lbPassive;
    @FXML
    private Label lbActive;
    @FXML
    private Label lbAccountLevel;
    private static final String NOT_TYPED_PATH = FilePath.getNotTyped();
    private static final String TYPED_PATH = FilePath.getTYPED();
    private static final String ORIGINAL_PATH = FilePath.getORIGINAL();
    private DetailsDao detailsDao = new DetailsDao();
    private List<AccountLevelDetails> details = detailsDao.getAll().stream().filter(p -> p.getScore() != null && p.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))).collect(Collectors.toList());
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
    private Timer timerPerk;
    private int secondPerk, minutePerk;
    private String ddSecondPerk, ddMinutePerk;
    //Item vars
    private int shield = 0;//
    private boolean time1 = false;//
    private boolean time2 = false;//
    private boolean time3 = false;//
    private boolean x2 = false;
    private int x2Remain = 0;
    private boolean x3 = false;
    private int x3Remain = 0;
    private boolean combo1 = false;//
    private boolean combo2 = false;//
    private boolean combo3 = false;//
    private boolean run1 = false;//
    private boolean run2 = false;//
    private boolean run3 = false;//
    private boolean speed1 = false;//
    private boolean speed2 = false;//
    private boolean speed3 = false;//
    private boolean immune1 = false;
    private boolean immune2 = false;
    private boolean immune3 = false;
    private boolean stopwatch1 = false;
    private boolean stopwatch2 = false;

    private final DecimalFormat dFormat = new DecimalFormat("00");
    int[] tokens = new int[Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).replaceAll("\\s+", "").toCharArray().length / 2];

    public void onBtnPauseClicked(MouseEvent e) {
        if (btnPause.getText().equals("Start")) {
            btnPause.setDisable(true);
            second4 = 5;
            minute4 = 0;
            cbbActive.setDisable(true);
            cbbPassive.setDisable(true);
            setItemToDefault();
            lbPassive.setStyle("-fx-text-fill: red");
            lbActive.setStyle("-fx-text-fill: red;");
            Shop choosePassive = cbbPassive.getSelectionModel().getSelectedItem();
            Shop chooseActive = cbbActive.getSelectionModel().getSelectedItem();
            InventoryDao inventoryDao = new InventoryDao();
            List<Inventory> invs = inventoryDao.getAll();
            invs = invs.stream().filter(ev->{
                if(ev.getItem().getIdItem().equals(choosePassive.getIdItem())&&ev.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            if(!invs.isEmpty()){
                Inventory inv = invs.get(0);
                inv.setCurrentlyHave(inv.getCurrentlyHave()-1);
                inv.setTimeUsed(inv.getTimeUsed()+1);
                inventoryDao.update(inv);
                ShopDao shopDao = new ShopDao();
                Shop item = shopDao.get(inv.getItem().getIdItem());
                item.setTimeUsed(item.getTimeUsed()+1);
                shopDao.update(item);
            }
            switch (choosePassive.getIdItem()) {
                case "IT1": {
                    shield += 1;//
                    lbPassive.setText("Using Shield Lv1. Remaining: "+shield);
                    break;
                }
                case "IT2": {
                    shield += 2;//
                    lbPassive.setText("Using Shield Lv2. Remaining: "+shield);
                    break;
                }
                case "IT3": {
                    shield += 3;//
                    lbPassive.setText("Using Shield Lv3. Remaining: "+shield);
                    break;
                }
                case "IT9": {
                    time1 = true;//
                    lbPassive.setText("Using Time Lv1");
                    break;
                }
                case "IT10": {
                    time2 = true;//
                    lbPassive.setText("Using Time Lv2");
                    break;
                }
                case "IT11": {
                    time3 = true;//
                    lbPassive.setText("Using Time Lv3");
                    break;
                }
                case "IT15": {
                    combo1 = true;//
                    lbPassive.setText("Using Combo Lv1");
                    break;
                }
                case "IT16": {
                    combo2 = true;//
                    lbPassive.setText("Using Combo Lv2");
                    break;
                }
                case "IT17": {
                    combo3 = true;//
                    lbPassive.setText("Using Combo Lv3");
                    break;
                }
                case "IT18": {
                    run3 = true;//
                    lbPassive.setText("Using Run Lv3");
                    break;
                }
                case "IT19": {
                    run2 = true;//
                    lbPassive.setText("Using Run Lv2");
                    break;
                }
                case "IT20": {
                    run1 = true;//
                    lbPassive.setText("Using Run Lv1");
                    break;
                }
                case "IT21": {
                    speed1 = true;
                    lbPassive.setText("Using Speed Lv1");
                    break;
                }
                case "IT22": {
                    speed2 = true;
                    lbPassive.setText("Using Speed Lv2");
                    break;
                }
                case "IT23": {
                    speed3 = true;
                    lbPassive.setText("Using Speed Lv3");
                    break;
                }
                case "ITNone": {
                    lbPassive.setText("Not use");
                    break;
                }
            }
            switch (chooseActive.getIdItem()) {
                case "IT4": {
                    immune1 = true;//
                    break;
                }
                case "IT5": {
                    immune2 = true;//
                    break;
                }
                case "IT6": {
                    immune3 = true;//
                    break;
                }
                case "IT7": {
                    stopwatch1 = true;//
                    break;
                }
                case "IT8": {
                    stopwatch2 = true;//
                    break;
                }
                case "IT13": {
                    x2 = true;
                    break;
                }
                case "IT14": {
                    x3 = true;
                    break;
                }
                case "ITNone": {
                    lbActive.setText("Not use");
                    break;
                }
            }
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
            DetailsDao tmpDao = new DetailsDao();
            details = tmpDao.getAll().stream().filter(p -> p.getScore() != null && p.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))).collect(Collectors.toList());
            showLeaderboard();
            BindDataToCombobox();
            setItemToDefault();
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
            lbAccountLevel.setText("Lv."+accountPlay.getAccountLevel()+" - "+accountPlay.getLevelExp()+"/"+accountPlay.getLevelCap());
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
            lbAccountLevel.setText("Lv."+accountPlay.getAccountLevel()+" - "+accountPlay.getLevelExp()+"/"+accountPlay.getLevelCap());
            showLeaderboard();
            BindDataToCombobox();
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
            scrollPaneLeaderboard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPaneLeaderboard.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            hlPublisher.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.SPACE) {
                        event.consume(); // to cancel space key
                    }
                }
            });
            anchorPane.addEventHandler(KeyEvent.KEY_TYPED, event -> {
                event.consume();
                LevelDao levelDao = new LevelDao();
                Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
                char[] typed = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).toCharArray();
                char[] notTyped = Objects.requireNonNull(FileRW.Read(NOT_TYPED_PATH)).toCharArray();
                String input = event.getCharacter();
                double total_minutes = (((double) second2 / 60) + minute2);
                int total_words = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).replaceAll("\\s+", "").length();
                if (input.equals("~")) {
                    InventoryDao inventoryDao = new InventoryDao();
                    ShopDao shopDao = new ShopDao();
                    List<Inventory> invs = inventoryDao.getAll();
                    invs = invs.stream().filter(e->{
                        if(e.getItem().getIdItem().equals(cbbActive.getSelectionModel().getSelectedItem().getIdItem())){
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(Collectors.toList());
                    if(!invs.isEmpty()){
                        Inventory inv = invs.get(0);
                        Shop item = shopDao.get(inv.getItem().getIdItem());
                        inv.setCurrentlyHave(inv.getCurrentlyHave()-1);
                        inv.setTimeUsed(inv.getTimeUsed()+1);
                        inventoryDao.update(inv);
                        item.setTimeUsed(item.getTimeUsed()+1);
                        shopDao.update(item);
                    }
                    if (immune1) {
                        secondPerk = 3;
                        CountDownTimerPerk();
                        timerPerk.start();
                        immune1 = false;
                        lbActive.setText("Immune Lv1 is activated");
                    }
                    if (immune2) {
                        secondPerk = 5;
                        CountDownTimerPerk();
                        timerPerk.start();
                        immune2 = false;
                        lbActive.setText("Immune Lv2 is activated");
                    }
                    if (immune3) {
                        secondPerk = 7;
                        CountDownTimerPerk();
                        timerPerk.start();
                        immune3 = false;
                        lbActive.setText("Immune Lv3 is activated");
                    }
                    if (stopwatch1) {
                        timer.stop();
                        timer2.stop();
                        secondPerk = 4;
                        CountDownTimerPerk();
                        timerPerk.start();
                        stopwatch1 = false;
                        lbActive.setText("Stopwatch Lv1 is activated");
                    }
                    if (stopwatch2) {
                        timer.stop();
                        timer2.stop();
                        secondPerk = 7;
                        CountDownTimerPerk();
                        timerPerk.start();
                        stopwatch2 = false;
                        lbActive.setText("Stopwatch Lv2 is activated");
                    }
                    if (x2) {
                        x2Remain = 20;
                        x2 = false;
                        lbActive.setText("X2 Correct is activated. Remaining:"+x2Remain);
                    }
                    if (x3) {
                        x3Remain = 20;
                        lbActive.setText("X2 Correct is activated. Remaining:"+x3Remain);
                    }
                }
                if (!input.equals(" ")) {
                    switch (level.getMode().getNameMode()) {
                        case "Normal": {
                            if (btnPause.getText().equals("Pause")) {
                                if(!input.equals("~")){
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
                                            if (x2Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x2Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                            }
                                            if (x3Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x3Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                            }
                                            if (combo1) {
                                                if (combo % 30 == 0) {
                                                    score += 10000;
                                                }
                                            }
                                            if (combo2) {
                                                if (combo % 40 == 0) {
                                                    score += 15000;
                                                }
                                            }
                                            if(combo3){
                                                if(combo%50==0){
                                                    score+=20000;
                                                }
                                            }
                                            if(run3){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])==100){
                                                    score+=100000;
                                                } else {
                                                    score-=50000;
                                                }
                                            }
                                            if(run2){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=95){
                                                    score+=75000;
                                                } else {
                                                    score-=30000;
                                                }
                                            }
                                            if(run1){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=90){
                                                    score+=50000;
                                                } else {
                                                    score-=25000;
                                                }
                                            }
                                            if(speed1){
                                                if(Double.parseDouble(lbWPM.getText())>50.0){
                                                    score = (score*107)/100;
                                                } else {
                                                    score = (score*86)/100;
                                                }
                                            }
                                            if(speed2){
                                                if(Double.parseDouble(lbWPM.getText())>70.0){
                                                    score = (score*114)/100;
                                                } else {
                                                    score = (score*72)/100;
                                                }
                                            }
                                            if(speed3){
                                                if(Double.parseDouble(lbWPM.getText())>90.0){
                                                    score = (score*120)/100;
                                                } else {
                                                    score = (score*60)/100;
                                                }
                                            }
                                            lbScore.setText(score + "");
                                            showLeaderboard();
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
                                            if (secondPerk == 0) {
                                                if (shield == 0) {
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
                                                    if (combo1) {
                                                        score -= 5000;
                                                    }
                                                    if (combo2) {
                                                        score -= 10000;
                                                    }
                                                    if (combo3) {
                                                        score -= 15000;
                                                    }
                                                    lbScore.setText(score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    shield -= 1;
                                                    lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0].split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                if (x2Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x2Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                }
                                                if (x3Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x3Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                }
                                                if (combo1) {
                                                    if (combo % 30 == 0) {
                                                        score += 10000;
                                                    }
                                                }
                                                if (combo2) {
                                                    if (combo % 40 == 0) {
                                                        score += 15000;
                                                    }
                                                }
                                                if(combo3){
                                                    if(combo%50==0){
                                                        score+=20000;
                                                    }
                                                }
                                                lbScore.setText( score + "");
                                                showLeaderboard();
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                if (secondPerk == 0) {
                                                    if (shield == 0) {
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
                                                        if (combo1) {
                                                            score -= 5000;
                                                        }
                                                        if (combo2) {
                                                            score -= 10000;
                                                        }
                                                        if (combo3) {
                                                            score -= 15000;
                                                        }
                                                        lbScore.setText(score + "");
                                                        showLeaderboard();
                                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                    } else {
                                                        shield -= 1;
                                                        lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                    }
                                                }
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
                        case "Instant Death": {
                            if (btnPause.getText().equals("Pause")) {
                                if(!input.equals("~")){
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
                                            long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                            if (x2Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x2Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                            }
                                            if (x3Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x3Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                            }
                                            if (combo1) {
                                                if (combo % 30 == 0) {
                                                    score += 10000;
                                                }
                                            }
                                            if (combo2) {
                                                if (combo % 40 == 0) {
                                                    score += 15000;
                                                }
                                            }
                                            if(combo3){
                                                if(combo%50==0){
                                                    score+=20000;
                                                }
                                            }
                                            if(run1){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])==100){
                                                    score+=100000;
                                                } else {
                                                    score-=50000;
                                                }
                                            }
                                            if(run2){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=95){
                                                    score+=75000;
                                                } else {
                                                    score-=30000;
                                                }
                                            }
                                            if(run3){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=90){
                                                    score+=50000;
                                                } else {
                                                    score-=25000;
                                                }
                                            }
                                            if(speed1){
                                                if(Double.parseDouble(lbWPM.getText())>50.0){
                                                    score = (score*107)/100;
                                                } else {
                                                    score = (score*86)/100;
                                                }
                                            }
                                            if(speed2){
                                                if(Double.parseDouble(lbWPM.getText())>70.0){
                                                    score = (score*114)/100;
                                                } else {
                                                    score = (score*72)/100;
                                                }
                                            }
                                            if(speed3){
                                                if(Double.parseDouble(lbWPM.getText())>90.0){
                                                    score = (score*120)/100;
                                                } else {
                                                    score = (score*60)/100;
                                                }
                                            }
                                            lbScore.setText( score + "");
                                            showLeaderboard();
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
                                            if (secondPerk == 0) {
                                                if (shield == 0) {
                                                    timer.stop();
                                                    timer2.stop();
                                                    LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                    if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                        resetStatus();
                                                    } else {
                                                        DisposeForm.Dispose(lbScore);
                                                    }
                                                } else {
                                                    shield -= 1;
                                                    lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
                                                            timer.stop();
                                                            timer2.stop();
                                                            LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                            if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                                resetStatus();
                                                            } else {
                                                                DisposeForm.Dispose(lbScore);
                                                            }
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
                                                            timer.stop();
                                                            timer2.stop();
                                                            LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                            if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                                resetStatus();
                                                            } else {
                                                                DisposeForm.Dispose(lbScore);
                                                            }
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                if (x2Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x2Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                }
                                                if (x3Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x3Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                }
                                                if (combo1) {
                                                    if (combo % 30 == 0) {
                                                        score += 10000;
                                                    }
                                                }
                                                if (combo2) {
                                                    if (combo % 40 == 0) {
                                                        score += 15000;
                                                    }
                                                }
                                                if(combo3){
                                                    if(combo%50==0){
                                                        score+=20000;
                                                    }
                                                }
                                                lbScore.setText( score + "");
                                                showLeaderboard();
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                if (secondPerk == 0) {
                                                    if (shield == 0) {
                                                        timer.stop();
                                                        timer2.stop();
                                                        LoadForm.load("/fxml/PracticeFXMLs/Gameover.fxml", "Game Over", true);
                                                        if (Objects.equals(FileRW.Read(FilePath.getRetryOrMenu()), "retry")) {
                                                            resetStatus();
                                                        } else {
                                                            DisposeForm.Dispose(lbScore);
                                                        }
                                                    } else {
                                                        shield -= 1;
                                                        lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                    }
                                                }
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
                                if(!input.equals("~")){
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
                                            long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                            if (x2Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x2Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                            }
                                            if (x3Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x3Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                            }
                                            if (combo1) {
                                                if (combo % 30 == 0) {
                                                    score += 10000;
                                                }
                                            }
                                            if (combo2) {
                                                if (combo % 40 == 0) {
                                                    score += 15000;
                                                }
                                            }
                                            if(combo3){
                                                if(combo%50==0){
                                                    score+=20000;
                                                }
                                            }
                                            if(run1){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])==100){
                                                    score+=100000;
                                                } else {
                                                    score-=50000;
                                                }
                                            }
                                            if(run2){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=95){
                                                    score+=75000;
                                                } else {
                                                    score-=30000;
                                                }
                                            }
                                            if(run3){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=90){
                                                    score+=50000;
                                                } else {
                                                    score-=25000;
                                                }
                                            }
                                            if(speed1){
                                                if(Double.parseDouble(lbWPM.getText())>50.0){
                                                    score = (score*107)/100;
                                                } else {
                                                    score = (score*86)/100;
                                                }
                                            }
                                            if(speed2){
                                                if(Double.parseDouble(lbWPM.getText())>70.0){
                                                    score = (score*114)/100;
                                                } else {
                                                    score = (score*72)/100;
                                                }
                                            }
                                            if(speed3){
                                                if(Double.parseDouble(lbWPM.getText())>90.0){
                                                    score = (score*120)/100;
                                                } else {
                                                    score = (score*60)/100;
                                                }
                                            }
                                            lbScore.setText( score + "");
                                            showLeaderboard();
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
                                            if (secondPerk == 0) {
                                                if (shield == 0) {
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
                                                    if (combo1) {
                                                        score -= 5000;
                                                    }
                                                    if (combo2) {
                                                        score -= 10000;
                                                    }
                                                    if (combo3) {
                                                        score -= 15000;
                                                    }
                                                    lbScore.setText(score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    shield -= 1;
                                                    lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                if (x2Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x2Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                }
                                                if (x3Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x3Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                }
                                                if (combo1) {
                                                    if (combo % 30 == 0) {
                                                        score += 10000;
                                                    }
                                                }
                                                if (combo2) {
                                                    if (combo % 40 == 0) {
                                                        score += 15000;
                                                    }
                                                }
                                                if(combo3){
                                                    if(combo%50==0){
                                                        score+=20000;
                                                    }
                                                }
                                                lbScore.setText( score + "");
                                                showLeaderboard();
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                if (secondPerk == 0) {
                                                    if (shield == 0) {
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
                                                        if (combo1) {
                                                            score -= 5000;
                                                        }
                                                        if (combo2) {
                                                            score -= 10000;
                                                        }
                                                        if (combo3) {
                                                            score -= 15000;
                                                        }
                                                        lbScore.setText(score + "");
                                                        showLeaderboard();
                                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                    } else {
                                                        shield -= 1;
                                                        lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                    }
                                                }
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
                        case "Hidden": {
                            if (btnPause.getText().equals("Pause")) {
                                if(!input.equals("~")){
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
                                            long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                            if (x2Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x2Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                            }
                                            if (x3Remain != 0) {
                                                score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50) + (Integer.parseInt(lbAccuracy.getText().split("%")[0]) * (total * 500) / 100);
                                                x3Remain--;
                                                lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                            }
                                            if (combo1) {
                                                if (combo % 30 == 0) {
                                                    score += 10000;
                                                }
                                            }
                                            if (combo2) {
                                                if (combo % 40 == 0) {
                                                    score += 15000;
                                                }
                                            }
                                            if(combo3){
                                                if(combo%50==0){
                                                    score+=20000;
                                                }
                                            }
                                            if(run1){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])==100){
                                                    score+=100000;
                                                } else {
                                                    score-=50000;
                                                }
                                            }
                                            if(run2){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=95){
                                                    score+=75000;
                                                } else {
                                                    score-=30000;
                                                }
                                            }
                                            if(run3){
                                                if(Integer.valueOf(lbAccuracy.getText().split("%")[0])>=90){
                                                    score+=50000;
                                                } else {
                                                    score-=25000;
                                                }
                                            }
                                            if(speed1){
                                                if(Double.parseDouble(lbWPM.getText())>50.0){
                                                    score = (score*107)/100;
                                                } else {
                                                    score = (score*86)/100;
                                                }
                                            }
                                            if(speed2){
                                                if(Double.parseDouble(lbWPM.getText())>70.0){
                                                    score = (score*114)/100;
                                                } else {
                                                    score = (score*72)/100;
                                                }
                                            }
                                            if(speed3){
                                                if(Double.parseDouble(lbWPM.getText())>90.0){
                                                    score = (score*120)/100;
                                                } else {
                                                    score = (score*60)/100;
                                                }
                                            }
                                            lbScore.setText( score + "");
                                            showLeaderboard();
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
                                            if (secondPerk == 0) {
                                                if (shield == 0) {
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
                                                    if (combo1) {
                                                        score -= 5000;
                                                    }
                                                    if (combo2) {
                                                        score -= 10000;
                                                    }
                                                    if (combo3) {
                                                        score -= 15000;
                                                    }
                                                    lbScore.setText(score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    shield -= 1;
                                                    lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                    long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    if (x2Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x2Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                    }
                                                    if (x3Remain != 0) {
                                                        score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                        x3Remain--;
                                                        lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                    }
                                                    if (combo1) {
                                                        if (combo % 30 == 0) {
                                                            score += 10000;
                                                        }
                                                    }
                                                    if (combo2) {
                                                        if (combo % 40 == 0) {
                                                            score += 15000;
                                                        }
                                                    }
                                                    if(combo3){
                                                        if(combo%50==0){
                                                            score+=20000;
                                                        }
                                                    }
                                                    lbScore.setText( score + "");
                                                    showLeaderboard();
                                                    lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                } else {
                                                    if (secondPerk == 0) {
                                                        if (shield == 0) {
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
                                                            if (combo1) {
                                                                score -= 5000;
                                                            }
                                                            if (combo2) {
                                                                score -= 10000;
                                                            }
                                                            if (combo3) {
                                                                score -= 15000;
                                                            }
                                                            lbScore.setText(score + "");
                                                            showLeaderboard();
                                                            lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                        } else {
                                                            shield -= 1;
                                                            lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                        }
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
                                                long score = Long.parseLong(lbScore.getText()) + (500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                if (x2Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (2 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x2Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x2Remain);
                                                }
                                                if (x3Remain != 0) {
                                                    score = Long.parseLong(lbScore.getText()) + (3 * 500 * Integer.parseInt(lbXMulti.getText().split("x")[0])) + (combo * 50);
                                                    x3Remain--;
                                                    lbActive.setText(lbActive.getText().substring(0,lbActive.getText().length()-1)+x3Remain);
                                                }
                                                if (combo1) {
                                                    if (combo % 30 == 0) {
                                                        score += 10000;
                                                    }
                                                }
                                                if (combo2) {
                                                    if (combo % 40 == 0) {
                                                        score += 15000;
                                                    }
                                                }
                                                if(combo3){
                                                    if(combo%50==0){
                                                        score+=20000;
                                                    }
                                                }
                                                lbScore.setText( score + "");
                                                showLeaderboard();
                                                lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                            } else {
                                                if (secondPerk == 0) {
                                                    if (shield == 0) {
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
                                                        if (combo1) {
                                                            score -= 5000;
                                                        }
                                                        if (combo2) {
                                                            score -= 10000;
                                                        }
                                                        if (combo3) {
                                                            score -= 15000;
                                                        }
                                                        lbScore.setText(score + "");
                                                        showLeaderboard();
                                                        lbAccuracyScore.setText(BigDecimal.valueOf((total * 500L) * Integer.parseInt(lbAccuracy.getText().split("%")[0]) / 100).stripTrailingZeros().toPlainString());
                                                    } else {
                                                        shield -= 1;
                                                        lbPassive.setText(lbPassive.getText().substring(0, lbPassive.getText().length() - 1) + shield);
                                                    }
                                                }
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
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void hlPublisherClicked(){
        AccountDao accountDao = new AccountDao();
        Account account = new Account();
        for(Account i: accountDao.getAll()){
            if(i.getUsername().trim().equals(hlPublisher.getText().trim())){
                account = i;
            }
        }
        FileRW.Write(FilePath.getChooseProfile(),account.getIdAccount());
        LoadForm.load("/fxml/PracticeFXMLs/Profile.fxml","Profile",false);
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
                if(time1){
                    second+=3;
                    time1 = false;
                }
                if (time2) {
                    second += 5;
                    time2 = false;
                }
                if (time3) {
                    second += 7;
                    time3 = false;
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

    public void CountDownTimerPerk() {
        timerPerk = new Timer(1000, e -> Platform.runLater(() -> {
            //javaFX operations should go here
            secondPerk--;
            if (secondPerk == 0) {
                timerPerk.stop();
                if (timer.isRunning() == false) {
                    timer.start();
                }
                if (timer2.isRunning() == false) {
                    timer2.start();
                }
            }
        }));
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
            AccountLevelDetails yourDetail = details.stream().filter(p -> p.getIdLevelDetails().equals("LDTest")).findFirst().orElse(null);
            if (yourDetail == null) {
                yourDetail = new AccountLevelDetails("LDTest", lbUsername.getText(), 0000L);
                details.add(yourDetail);
                String oldUsername = yourDetail.getUsername();
                details.forEach(e -> {
                    if (e.getUsername().equals(oldUsername) && e.getIdLevelDetails().equals("LDTest") == false)
                        e.setUsername(oldUsername + "(old)");
                });
                details.sort(Comparator.comparingLong(AccountLevelDetails::getScore).reversed());
                Node[] nodes = new Node[details.size()];
                for (int i = 0; i < details.size(); i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DashboardController.class.getResource("/fxml/PracticeFXMLs/PlayLeaderboard.fxml"));
                    nodes[i] = loader.load();
                    PlayLeaderboardController controller = loader.getController();
                    controller.set(String.valueOf(i + 1), details.get(i).getUsername(), details.get(i).getScore());
                    vbLeaderboard.getChildren().add(nodes[i]);
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DashboardController.class.getResource("/fxml/PracticeFXMLs/PlayLeaderboard.fxml"));
                Node node = loader.load();
                PlayLeaderboardController controller = loader.getController();
                controller.set(String.valueOf(details.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getScore());
                apYour.getChildren().add(node);
            } else {
                details.remove(yourDetail);
                yourDetail.setScore(Long.parseLong(lbScore.getText()));
                details.add(yourDetail);
                String oldUsername = yourDetail.getUsername();
                details.forEach(e -> {
                    if (e.getUsername().equals(oldUsername) && e.getIdLevelDetails().equals("LDTest") == false)
                        e.setUsername(oldUsername + "(old)");
                });
                details.sort(Comparator.comparingLong(AccountLevelDetails::getScore).reversed());
                Node[] nodes = new Node[details.size()];
                for (int i = 0; i < details.size(); i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DashboardController.class.getResource("/fxml/PracticeFXMLs/PlayLeaderboard.fxml"));
                    nodes[i] = loader.load();
                    PlayLeaderboardController controller = loader.getController();
                    controller.set(String.valueOf(i + 1), details.get(i).getUsername(), details.get(i).getScore());
                    vbLeaderboard.getChildren().add(nodes[i]);
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DashboardController.class.getResource("/fxml/PracticeFXMLs/PlayLeaderboard.fxml"));
                Node node = loader.load();
                PlayLeaderboardController controller = loader.getController();
                controller.set(String.valueOf(details.indexOf(yourDetail) + 1), yourDetail.getUsername(), yourDetail.getScore());
                apYour.getChildren().add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BindDataToCombobox() {
        InventoryDao iDao = new InventoryDao();
        ShopDao sDao = new ShopDao();
        ObservableList<Shop> invPassive = FXCollections.observableList(new ArrayList<>(Arrays.asList()));
        ObservableList<Shop> invActive = FXCollections.observableList(new ArrayList<>(Arrays.asList()));
        List<Inventory> userInv = iDao.getAll().stream().filter(e -> e.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))).collect(Collectors.toList());
        ObservableList<Shop> items = FXCollections.observableList(sDao.getAll().stream().filter(e -> {
            Inventory inv = userInv.stream().filter(f -> f.getItem().getIdItem().equals(e.getIdItem()) && f.getCurrentlyHave() > 0).findFirst().orElse(null);
            if (inv != null) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList()));
        for (Shop item : items) {
            if (item.getDescription().contains("activate")) {
                invActive.add(item);
            } else {
                invPassive.add(item);
            }
        }
        Shop none = new Shop("ITNone", "None", "test");
        invActive.add(none);
        invPassive.add(none);
        cbbActive.setItems(invActive);
        cbbPassive.setItems(invPassive);
        cbbActive.getSelectionModel().select(none);
        cbbPassive.getSelectionModel().select(none);
    }

    void setItemToDefault() {
        shield = 0;
        time1 = false;
        time2 = false;
        time3 = false;
        x2 = false;
        x3 = false;
        combo1 = false;
        combo2 = false;
        combo3 = false;
        run1 = false;
        run2 = false;
        run3 = false;
        speed1 = false;
        speed2 = false;
        speed3 = false;
        immune1 = false;
        immune2 = false;
        immune3 = false;
        stopwatch1 = false;
        stopwatch2 = false;
    }
}
