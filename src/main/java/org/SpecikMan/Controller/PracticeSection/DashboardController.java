package org.SpecikMan.Controller.PracticeSection;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Table.HyperLinkCell;
import org.SpecikMan.Table.TableViewItem;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.LoadForm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DashboardController {
    @FXML
    private Button btnLike;
    @FXML
    private Hyperlink hlAuthorLink;
    @FXML
    private FontAwesomeIconView iconEasy;
    @FXML
    private FontAwesomeIconView iconHard;
    @FXML
    private FontAwesomeIconView iconNormal;
    @FXML
    private Label lbCreateTime;
    @FXML
    private Label lbDifficulty;
    @FXML
    private Label lbHighestScore;
    @FXML
    private Label lbLevelName;
    @FXML
    private Label lbModeName;
    @FXML
    private Label lbNo;
    @FXML
    private Label lbPlayedDate;
    @FXML
    private Label lbTime;
    @FXML
    private Label lbTimeLeft;
    @FXML
    private Label lbTotalWords;
    @FXML
    private Label lbUpdatedTime;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableColumn<TableViewItem, Date> tcDate;
    @FXML
    private TableColumn<String, Integer> tcNo;
    @FXML
    private TableColumn<TableViewItem, String> tcScore;
    @FXML
    private TableColumn<TableViewItem, String> tcTime;
    @FXML
    private TableColumn<TableViewItem, Hyperlink> tcUsername;
    @FXML
    private TableView<TableViewItem> tvDetail;
    @FXML
    private TableColumn<DetailLog, Date> tcDateA;
    @FXML
    private TableColumn<DetailLog, Integer> tcScoreA;
    @FXML
    private TableColumn<DetailLog, String> tcTimeA;
    @FXML
    private TableColumn<DetailLog, String> tcAccuracyA;
    @FXML
    private TableColumn<DetailLog, Integer> tcCorrectA;
    @FXML
    private TableColumn<DetailLog, Integer> tcWrongA;
    @FXML
    private TableColumn<DetailLog, Float> tcWPMA;
    @FXML
    private TableView<DetailLog> tvAttempts;
    @FXML
    private VBox vboxItems;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lbWPM;
    @FXML
    private Label lbAccuracy;
    private boolean[] isSelected;

    @FXML
    public void onBtnAllClicked() {
        LevelDao levelDao = new LevelDao();
        showAll(levelDao.getAll());
    }

    public void initialize() {
        LevelDao levelDao = new LevelDao();
        showAll(levelDao.getAll());
    }

    public void showAll(List<Level> listLevel) {
        try {
            vboxItems.getChildren().clear();
            disappearRightPane();
            if(listLevel.size()!=0) {
                AccountDao accountDao = new AccountDao();
                Node[] nodes = new Node[listLevel.size()];
                isSelected = new boolean[listLevel.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DashboardController.class.getResource("/fxml/PracticeFXMLs/Dashboard_item.fxml"));
                    nodes[i] = loader.load();
                    Level level = listLevel.get(i);
                    ItemController controller = loader.getController();
                    controller.setItemInfo(level.getNameLevel(), accountDao.get(level.getIdAccount()).getUsername(), level.getDifficulty().getIdDifficulty(), level.getTotalWords(),level.getNumLike(), level.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())),accountDao.get(level.getIdAccount()).getPathImage());
                    final int h = i;
                    nodes[i].setOnMouseEntered(evt -> {
                        if (!isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        }
                    });
                    nodes[i].setOnMouseExited(evt -> {
                        if (isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        } else {
                            nodes[h].setStyle("-fx-background-color: #b4b4b4");
                        }
                    });
                    nodes[i].setOnMousePressed(evt -> {
                        DetailsDao detailDao = new DetailsDao();
                        List<AccountLevelDetails> details = detailDao.getAll();
                        Arrays.fill(isSelected, Boolean.FALSE);
                        isSelected[h] = true;
                        for (Node n : nodes) {
                            n.setStyle("-fx-background-color: #b4b4b4");
                        }
                        if (isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        }
                        appearRightPane();
                        lbLevelName.setText(level.getNameLevel());
                        hlAuthorLink.setText(accountDao.get(level.getIdAccount()).getUsername());
                        lbCreateTime.setText(level.getCreateDate().toString());
                        lbUpdatedTime.setText(level.getUpdatedDate().toString());
                        lbDifficulty.setText(level.getDifficulty().getNameDifficulty());
                        diff(level.getDifficulty().getNameDifficulty());
                        lbTotalWords.setText(String.valueOf(level.getTotalWords()));
                        lbTime.setText(level.getTime());
                        lbModeName.setText(level.getMode().getNameMode());
                        FileRW.Write(FilePath.getPlayLevel(),level.getIdLevel());
                        List<AccountLevelDetails> levelDetail = new ArrayList<>();
                        AccountLevelDetails userDetail = new AccountLevelDetails();
                        for (AccountLevelDetails detail : details) {
                            if (detail.getLevel().getIdLevel().equals(listLevel.get(h).getIdLevel())&&detail.getScore()!=null) {
                                levelDetail.add(detail);
                            }
                            if (detail.getLevel().getIdLevel().equals(listLevel.get(h).getIdLevel()) && detail.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                                userDetail = detail;
                            }
                        }
                            levelDetail.sort(Comparator.comparingLong(AccountLevelDetails::getScore).reversed());
                        if (userDetail.getDatePlayed() != null) {
                            lbNo.setText("#" + (levelDetail.indexOf(userDetail) + 1));
                            lbHighestScore.setText(String.valueOf(userDetail.getScore()));
                            lbTimeLeft.setText(userDetail.getTimeLeft());
                            lbPlayedDate.setText(userDetail.getDatePlayed().toString());
                            lbWPM.setText(userDetail.getWpm()+"");
                            lbAccuracy.setText(userDetail.getAccuracy());
                        } else {
                            lbNo.setText("---");
                            lbHighestScore.setText("---");
                            lbTimeLeft.setText("---");
                            lbPlayedDate.setText("---");
                            lbWPM.setText("---");
                            lbAccuracy.setText("---");
                        }
                        AccountLevelDetails removeEle = new AccountLevelDetails();
                        for (AccountLevelDetails z : levelDetail) {
                            if (z.getDatePlayed() == null) {
                                removeEle = z;
                            }
                        }
                        levelDetail.remove(removeEle);
                        BindingDataToTableLeaderboard(levelDetail);
                        DetailLogDao logDao = new DetailLogDao();
                        List<DetailLog> logs = new ArrayList<>();
                        for (DetailLog z : logDao.getAll()) {
                            if (z.getIdPlayer().equals(FileRW.Read(FilePath.getLoginAcc())) && z.getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))) {
                                logs.add(z);
                            }
                        }
                        logs.add(new DetailLog(userDetail.getScore(), userDetail.getWpm(), userDetail.getCorrect(),
                                userDetail.getWrong(), userDetail.getAccuracy(), userDetail.getTimeLeft(), userDetail.getDatePlayed()));
                        BindingDataToTableAttempts(logs);
                        if(userDetail.getIdLevelDetails()==null){
                            btnLike.setText("Like");
                            btnLike.setStyle("-fx-background-color:  #aeadad;");
                        } else {
                            if (userDetail.getLike()) {
                                btnLike.setText("Liked");
                                btnLike.setStyle("-fx-background-color:  #4498e9;");
                            } else {
                                btnLike.setText("Like");
                                btnLike.setStyle("-fx-background-color:  #aeadad;");
                            }
                        }
                    });
                    vboxItems.getChildren().add(nodes[i]);
                }
            } else {
                vboxItems.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void disappearRightPane() {
        ObservableList<Node> contents = rightPane.getChildren();
        contents.forEach(node -> node.setVisible(false));
    }

    public void appearRightPane() {
        ObservableList<Node> contents = rightPane.getChildren();
        contents.forEach(node -> node.setVisible(true));
    }
    public void diff(String diff) {
        if (diff.equals("Easy")) {
            iconEasy.setVisible(true);
            iconNormal.setVisible(false);
            iconHard.setVisible(false);
        } else if (diff.equals("Normal")) {
            iconEasy.setVisible(true);
            iconNormal.setVisible(true);
            iconHard.setVisible(false);
        } else {
            iconEasy.setVisible(true);
            iconNormal.setVisible(true);
            iconHard.setVisible(true);
        }
    }

    public void BindingDataToTableLeaderboard(List<AccountLevelDetails> listtmp) {
        List<TableViewItem> listItem = new ArrayList<>();
        int k = 1;
        for (AccountLevelDetails i : listtmp) {
            listItem.add(new TableViewItem(k, new Hyperlink(i.getUsername()), i.getScore(), i.getTimeLeft(), i.getDatePlayed()));
            k++;
        }
        ObservableList<TableViewItem> list = FXCollections.observableList(listItem);
        tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcUsername.setCellFactory(new HyperLinkCell());
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        tvDetail.setItems(list);
    }

    public void BindingDataToTableAttempts(List<DetailLog> logs) {
        ObservableList<DetailLog> item = FXCollections.observableList(logs);
        tcDateA.setCellValueFactory(new PropertyValueFactory<>("datePlayed"));
        tcScoreA.setCellValueFactory(new PropertyValueFactory<>("score"));
        tcTimeA.setCellValueFactory(new PropertyValueFactory<>("timeLeft"));
        tcAccuracyA.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        tcCorrectA.setCellValueFactory(new PropertyValueFactory<>("correct"));
        tcWrongA.setCellValueFactory(new PropertyValueFactory<>("wrong"));
        tcWPMA.setCellValueFactory(new PropertyValueFactory<>("wpm"));
        tvAttempts.setItems(item);
    }

    @FXML
    public void btnBlackoutClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getMode().getNameMode().equals("Blackout")) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    public void btnLikeClicked() {
        DetailsDao detailsDao = new DetailsDao();
        AccountLevelDetails detail = new AccountLevelDetails();
        LevelDao levelDao = new LevelDao();
        Level level = new Level();
        if (btnLike.getText().equals("Like")) {
            for (AccountLevelDetails i : detailsDao.getAll()) {
                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))) {
                    detail = i;
                }
            }
            if (detail.getIdAccount() == null) {
                detail.setIdLevelDetails(GenerateID.genDetails());
                detail.setLike(true);
                detail.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                detail.setLevel(new Level(FileRW.Read(FilePath.getPlayLevel())));
                detailsDao.add(detail);
            } else {
                detail.setLike(true);
                detailsDao.update(detail);
            }
            level = levelDao.get(detail.getLevel().getIdLevel());
            level.setNumLike(level.getNumLike() + 1);
            levelDao.update(level);
            btnLike.setText("Liked");
            btnLike.setStyle("-fx-background-color:  #4498e9;");
        } else {
            for (AccountLevelDetails i : detailsDao.getAll()) {
                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))) {
                    detail = i;
                }
            }
            detail.setLike(false);
            detailsDao.update(detail);
            level = levelDao.get(detail.getLevel().getIdLevel());
            level.setNumLike(level.getNumLike() - 1);
            levelDao.update(level);
            btnLike.setText("Like");
            btnLike.setStyle("-fx-background-color:  #aeadad;");
        }
    }

    @FXML
    public void btnDeathTokenClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getMode().getNameMode().equals("Death Token")) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    public void btnInstantDeathClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(i.getMode().getNameMode().equals("Instant Death")){
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    public void btnNormalClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(i.getMode().getNameMode().equals("Normal")){
                list.add(i);
            }
        }
        showAll(list);
    }
    @FXML
    void btnBackClicked(MouseEvent event) {
        DisposeForm.Dispose(lbTime);
    }
    @FXML
    void btnDefaultLevelClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(Integer.parseInt(i.getIdAccount().split("AC")[1])<10){
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnDiffEasyClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(i.getDifficulty().getNameDifficulty().equals("Easy")){
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnDiffHardClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getDifficulty().getNameDifficulty().equals("Hard")) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnDiffNormalClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getDifficulty().getNameDifficulty().equals("Normal")) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnOtherPlayerLevelsClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (!(i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) && !(Integer.parseInt(i.getIdAccount().split("AC")[1]) < 10)) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnYourLevelsClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    void btnRecentPlayedClicked() {
        DetailsDao detailsDao = new DetailsDao();
        List<AccountLevelDetails> details = new ArrayList<>();
        LevelDao levelDao = new LevelDao();
        List<Level> levels = new ArrayList<>();
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (DiffDate(i.getDatePlayed(), date) < 5 && i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                details.add(i);
            }
        }
        for (AccountLevelDetails i : details) {
            levels.add(levelDao.get(i.getLevel().getIdLevel()));
        }
        showAll(levels);
    }

    @FXML
    void btnHighestLikedLevelsClicked() {
        LevelDao levelDao = new LevelDao();
        List<Level> levels = levelDao.getAll();
        levels.sort(Comparator.comparingInt(Level::getNumLike).reversed());
        showAll(levels);
    }

    @FXML
    void btnLikeLevelsClicked() {
        DetailsDao detailsDao = new DetailsDao();
        LevelDao levelDao = new LevelDao();
        List<AccountLevelDetails> details = new ArrayList<>();
        List<Level> levels = new ArrayList<>();
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getLike()) {
                details.add(i);
            }
        }
        for (AccountLevelDetails i : details) {
            levels.add(levelDao.get(i.getLevel().getIdLevel()));
        }
        showAll(levels);
    }

    public long DiffDate(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toDays(duration);
    }
    @FXML
    public void btnFindClicked(){
        LevelDao levelDao = new LevelDao();
        List<Level> levelsM = levelDao.getAll();
        List<Level> levels = new ArrayList<>();
        if (txtSearch.getText().trim().equals("")) {
            levels.addAll(levelsM);
        } else {
            for (Level i : levelsM) {
                if (i.getNameLevel().contains(txtSearch.getText().trim()) || i.getLevelContent().trim().contains(txtSearch.getText().trim())||i.getUsername().contains(txtSearch.getText().trim())) {
                    levels.add(i);
                }
            }
        }
        showAll(levels);
    }
    @FXML
    public void btnPlayClicked(){
        LoadForm.load("/fxml/PracticeFXMLs/Play.fxml","Play",false);
    }
    @FXML
    public void btnAddLevelClicked(){
        LoadForm.load("/fxml/PracticeFXMLs/AddLevel.fxml","Add Level",false);
    }
    @FXML
    public void onBtnCopyClicked(){
        LoadForm.load("/fxml/PracticeFXMLs/CopyLevel.fxml","Copy & Modify",false);
    }
    @FXML
    public void onHlAuthorClicked(){
        AccountDao accountDao = new AccountDao();
        Account account = new Account();
        for(Account i: accountDao.getAll()){
            if(i.getUsername().trim().equals(hlAuthorLink.getText().trim())){
                account = i;
            }
        }
        FileRW.Write(FilePath.getChooseProfile(),account.getIdAccount());
        LoadForm.load("/fxml/PracticeFXMLs/Profile.fxml","Profile",false);
    }
}
