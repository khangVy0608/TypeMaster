package org.SpecikMan.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
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
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DashboardController {
    @FXML
    private FontAwesomeIconView btnAdd;
    @FXML
    private Button btnAll;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnBlackout;
    @FXML
    private FontAwesomeIconView btnClose;
    @FXML
    private Button btnCopyAndModify;
    @FXML
    private Button btnDeathToken;
    @FXML
    private Button btnDefaultLevels;
    @FXML
    private Button btnDiffEasy;
    @FXML
    private Button btnDiffHard;
    @FXML
    private Button btnDiffNormal;
    @FXML
    private Button btnHighestLikedLevels;
    @FXML
    private Button btnInstantDeath;
    @FXML
    private Button btnLike;
    @FXML
    private Button btnNormal;
    @FXML
    private Button btnOtherPlayersLevel;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnRecentPlayed;
    @FXML
    private Button btnYourLevels;
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
    private TableColumn<AccountLevelDetails, Date> tcDate;

    @FXML
    private TableColumn<String, Integer> tcNo;

    @FXML
    private TableColumn<AccountLevelDetails, String> tcScore;
    @FXML
    private TableColumn<AccountLevelDetails, String> tcTime;
    @FXML
    private TableColumn<AccountLevelDetails, String> tcUsername;
    @FXML
    private TableView<AccountLevelDetails> tvDetail;
    @FXML
    private VBox vboxItems;
    @FXML
    private Pane rightPane;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lbIdLevel;
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
                DetailsDao detailDao = new DetailsDao();
                Node[] nodes = new Node[listLevel.size()];
                List<AccountLevelDetails> details = detailDao.getAll();
                isSelected = new boolean[listLevel.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DashboardController.class.getResource("/fxml/Dashboard_item.fxml"));
                    nodes[i] = loader.load();
                    Level level = listLevel.get(i);
                    ItemController controller = loader.getController();
                    controller.setItemInfo(level.getNameLevel(), accountDao.get(level.getIdAccount()).getUsername(), level.getDifficulty().getIdDifficulty(), level.getTotalWords());
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
                            if (detail.getLevel().getNameLevel().equals(listLevel.get(h).getNameLevel())) {
                                levelDetail.add(detail);
                            }
                            if (detail.getLevel().getNameLevel().equals(listLevel.get(h).getNameLevel()) && detail.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                                userDetail = detail;
                            }
                        }
                        levelDetail.sort(Comparator.comparingInt(AccountLevelDetails::getScore).reversed());
                        if (userDetail.getIdAccount() != null) {
                            lbNo.setText("#" + (levelDetail.indexOf(userDetail) + 1));
                            lbHighestScore.setText(String.valueOf(userDetail.getScore()));
                            lbTimeLeft.setText(userDetail.getTimeLeft());
                            lbPlayedDate.setText(userDetail.getDatePlayed().toString());
                        } else {
                            lbNo.setText("---");
                            lbHighestScore.setText("---");
                            lbTimeLeft.setText("---");
                            lbPlayedDate.setText("---");
                        }
                        BindingDataToTable(levelDetail);
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
    public void BindingDataToTable(List<AccountLevelDetails> listtmp){
        ObservableList<AccountLevelDetails> list = FXCollections.observableList(listtmp);
        tcNo.setCellFactory(col -> {
            TableCell<String, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<String>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<String> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex+1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });
        tcUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("datePlayed"));
        tcScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("timeLeft"));
        tvDetail.setItems(list);
    }
    @FXML
    public void btnBlackoutClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(i.getMode().getNameMode().equals("Blackout")){
                list.add(i);
            }
        }
        showAll(list);
    }

    @FXML
    public void btnDeathTokenClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            if(i.getMode().getNameMode().equals("Death Token")){
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
        LoadForm.load("/fxml/Home.fxml","Home",false);
        DisposeForm.Dispose(lbTime);
    }
    @FXML
    void btnDefaultLevelClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for(Level i: levelDao.getAll()){
            System.out.println(i.getIdAccount().split("AC")[1]);
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
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.isLike()) {
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
                if (i.getNameLevel().contains(txtSearch.getText().trim()) || i.getLevelContent().trim().contains(txtSearch.getText().trim())) {
                    levels.add(i);
                }
            }
        }
        showAll(levels);
    }
    @FXML
    public void btnPlayClicked(){
        LoadForm.load("/fxml/Play.fxml","Play",false);
    }
}
