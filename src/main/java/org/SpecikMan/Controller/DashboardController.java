package org.SpecikMan.Controller;

import com.jfoenix.controls.JFXTreeTableView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.FileRW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
    private Button btnHightestLikedLevels;
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
    private JFXTreeTableView<?> tvScoreboard;
    @FXML
    private TextField txtSearch;
    @FXML
    private VBox vboxItems;
    @FXML
    private Pane rightPane;
    private boolean[] isSelected;
    private static final String LOGIN_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\loginAcc";

    @FXML
    public void onBtnAllClicked(MouseEvent event) {
        showAll();
    }

    public void initialize() {
        showAll();
    }

    public void showAll() {
        try {
            disappearRightPane();
            LevelDao levelDao = new LevelDao();
            AccountDao accountDao = new AccountDao();
            List<Level> levels = levelDao.getAll();
            DetailsDao detailDao = new DetailsDao();
            Node[] nodes = new Node[levels.size()];
            List<AccountLevelDetails> details = detailDao.getAll();
            isSelected = new boolean[levels.size()];
            for (int i = 0; i < nodes.length; i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DashboardController.class.getResource("/fxml/Dashboard_item.fxml"));
                nodes[i] = loader.load();
                Level level = levels.get(i);
                ItemController controller = loader.getController();
                controller.setItemInfo(level.getNameLevel(), accountDao.get(level.getIdAccount()).getUsername(), level.getDifficulty().getIdDifficulty(), level.getTotalWords());
                final int h = i;
                nodes[i].setOnMouseEntered(evt -> {
                    if (!isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #4498e9;");
                    }
                });
                nodes[i].setOnMouseExited(evt -> {
                    if(isSelected[h]) {
                        nodes[h].setStyle("-fx-background-color: #4498e9;");
                    } else {
                        nodes[h].setStyle("-fx-background-color: #b4b4b4");
                    }
                });
                nodes[i].setOnMousePressed(evt -> {
                    Arrays.fill(isSelected,Boolean.FALSE);
                    isSelected[h] = true;
                    for(Node n:nodes){
                        n.setStyle("-fx-background-color: #b4b4b4");
                    }
                    if(isSelected[h]) {
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
                    List<AccountLevelDetails> levelDetail = new ArrayList<>();
                    AccountLevelDetails userDetail = new AccountLevelDetails();
                    for (AccountLevelDetails detail : details) {
                        if (detail.getLevel().getNameLevel().equals(levels.get(h).getNameLevel())) {
                            levelDetail.add(detail);
                        }
                        if (detail.getLevel().getNameLevel().equals(levels.get(h).getNameLevel()) && detail.getIdAccount().equals(FileRW.Read(LOGIN_PATH))) {
                            userDetail = detail;
                        }
                    }
                    if(userDetail.getIdAccount()!=null) {
                        levelDetail.sort(Comparator.comparingInt(AccountLevelDetails::getScore));
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
                });
                vboxItems.getChildren().add(nodes[i]);
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
}
