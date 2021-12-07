package org.SpecikMan.Controller.RankSection;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.GroupDao;
import org.SpecikMan.DAL.RankingLevelDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Group;
import org.SpecikMan.Entity.RankingLevel;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.LoadForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RankMenuController {
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbRankName;
    @FXML
    private Label lbRankReward;
    @FXML
    private Label lbPromote;
    @FXML
    private Label lbRetain;
    @FXML
    private Label lbDemote;
    @FXML
    private Label lbTitle;
    @FXML
    private Label lbPromoteReward;
    @FXML
    private Label lbRetainReward;
    @FXML
    private Label lbDemoteReward;
    @FXML
    private Label lbTime1;
    @FXML
    private Label lbTime2;
    @FXML
    private Label lbTime3;
    @FXML
    private Label lbLetters1;
    @FXML
    private Label lbLetters2;
    @FXML
    private Label lbLetters3;
    @FXML
    private Label lbScore1;
    @FXML
    private Label lbScore2;
    @FXML
    private Label lbScore3;
    @FXML
    private Label lbDatePlayed1;
    @FXML
    private Label lbDatePlayed2;
    @FXML
    private Label lbDatePlayed3;
    @FXML
    private Label lbTotalScore;
    @FXML
    private Label lbCurrentPlayers;
    @FXML
    private Label lbPosition;
    @FXML
    private Label lbPromoteAvr;
    @FXML
    private Label lbPromoteLowest;
    @FXML
    private Label lbPromoteHighest;
    @FXML
    private Label lbRetainAvr;
    @FXML
    private Label lbRetainLowest;
    @FXML
    private Label lbRetainHighest;
    @FXML
    private Label lbDemoteAvr;
    @FXML
    private Label lbDemoteLowest;
    @FXML
    private Label lbDemoteHighest;

    @FXML
    private ComboBox cbbRound;

    @FXML
    private ImageView ivUser;
    @FXML
    private ImageView ivRank;

    @FXML
    private ScrollPane spAllPlayers;
    @FXML
    private ScrollPane spRoundLeaderboard;
    @FXML
    private ScrollPane spPromote;
    @FXML
    private ScrollPane spRetain;
    @FXML
    private ScrollPane spDemote;

    @FXML
    private VBox vbAllPlayers;
    @FXML
    private VBox vbRoundLeaderboard;
    @FXML
    private VBox vbPromote;
    @FXML
    private VBox vbRetain;
    @FXML
    private VBox vbDemote;

    @FXML
    private Button btnPlay1;
    @FXML
    private Button btnPlay2;
    @FXML
    private Button btnPlay3;

    private int rangeStart = 0;
    private int rangeEnd = 0;
    public void resetStatus(){
        try {
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            //
            lbUsername.setText(account.getUsername() + " - " + "Lv." + account.getAccountLevel());
            lbRankName.setText(account.getRank().getRankName());
            lbRankReward.setText(account.getRank().getReward() + "");
            lbTitle.setText(account.getRank().getRankName() + " Ranking Leaderboard");
            if (account.getRank().getRankName().equals("God")) {
                lbPromote.setText("No.-- -> No.--");
                lbPromoteReward.setText("--");
            } else {
                lbPromote.setText("No.1 -> No." + account.getRank().getPromote());
                lbPromoteReward.setText((account.getRank().getReward() + 500) + "");
            }
            lbRetain.setText("No." + (account.getRank().getPromote() + 1) + " -> No." + (account.getRank().getDemote() - 1));
            lbRetainReward.setText(account.getRank().getReward() + "");
            if (account.getRank().getRankName().equals("Bronze")) {
                lbDemote.setText("No.-- -> No.--");
                lbDemoteReward.setText("--");
            } else {
                lbDemote.setText("No." + account.getRank().getDemote() + " -> No.15");
                lbDemoteReward.setText((account.getRank().getReward() - 500) + "");
            }
            //
            Image imageUser = new Image(new FileInputStream(account.getPathImage()));
            Image imageRank = new Image(new FileInputStream(account.getRank().getImagePath()));
            ivUser.setImage(imageUser);
            ivRank.setImage(imageRank);
            //
            RankingLevelDao rlDao = new RankingLevelDao();
            GroupDao grDao = new GroupDao();
            boolean hasGroup = false;
            List<Group> groupRank = grDao.getByRank(account.getRank().getIdRank());
            for(Group i: groupRank){//Check user has group or not
                String key = i.getIdGroup();
                Group userGr = null;
                for(Group j: grDao.getAll()){
                    if(j.getIdGroup().equals(key) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))){
                        userGr = j;
                    }
                }
                if(userGr!=null){//user already has group
                    displayGroup(userGr);
                    FileRW.Write(FilePath.getUserGroup(), key);
                    hasGroup = true;
                    break;
                }
            }
            if(hasGroup==false){//user not have group
                if(groupRank.isEmpty()){
                    addNewGroup(GenerateID.genGroup());
                } else {
                    for (Group i : groupRank) {
                        String key = i.getIdGroup();
                        List<Group> grs = new ArrayList<>();
                        for (Group j : grDao.getAll()) {
                            if (j.getIdGroup().equals(key)) {
                                grs.add(j);
                            }
                        }
                        if (grs.size() < 15) {
                            addNewGroup(key);
                            FileRW.Write(FilePath.getUserGroup(), key);
                            break;
                        }
                        if (i.equals(groupRank.get(groupRank.size() - 1))) {
                            String id = GenerateID.genGroup();
                            addNewGroup(id);
                            FileRW.Write(FilePath.getUserGroup(), id);
                            break;
                        }
                    }
                }
            }
            RankingLevel rl = getCurrentRankingLevel();
            if (rl != null) {
                lbTime1.setText(rl.getTime1());
                lbTime2.setText(rl.getTime2());
                lbTime3.setText(rl.getTime3());
                lbLetters1.setText(rl.getLevelContent1().toCharArray().length + "");
                lbLetters2.setText(rl.getLevelContent2().toCharArray().length + "");
                lbLetters3.setText(rl.getLevelContent3().toCharArray().length + "");
            }
            ShowAllPlayers();
            ShowPlayerByRound(1);
            cbbRound.getSelectionModel().select("Round 1");
            ShowPromote();
            ShowRetain();
            ShowDemote();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initialize() {
        try {
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            //
            lbUsername.setText(account.getUsername() + " - " + "Lv." + account.getAccountLevel());
            lbRankName.setText(account.getRank().getRankName());
            lbRankReward.setText(account.getRank().getReward() + "");
            lbTitle.setText(account.getRank().getRankName() + " Ranking Leaderboard");
            if (account.getRank().getRankName().equals("God")) {
                lbPromote.setText("No.-- -> No.--");
                lbPromoteReward.setText("--");
            } else {
                lbPromote.setText("No.1 -> No." + account.getRank().getPromote());
                lbPromoteReward.setText((account.getRank().getReward() + 500) + "");
            }
            lbRetain.setText("No." + (account.getRank().getPromote() + 1) + " -> No." + (account.getRank().getDemote() - 1));
            lbRetainReward.setText(account.getRank().getReward() + "");
            if (account.getRank().getRankName().equals("Bronze")) {
                lbDemote.setText("No.-- -> No.--");
                lbDemoteReward.setText("--");
            } else {
                lbDemote.setText("No." + account.getRank().getDemote() + " -> No.15");
                lbDemoteReward.setText((account.getRank().getReward() - 500) + "");
            }
            //
            Image imageUser = new Image(new FileInputStream(account.getPathImage()));
            Image imageRank = new Image(new FileInputStream(account.getRank().getImagePath()));
            ivUser.setImage(imageUser);
            ivRank.setImage(imageRank);
            //
            RankingLevelDao rlDao = new RankingLevelDao();
            GroupDao grDao = new GroupDao();
            boolean hasGroup = false;
            List<Group> groupRank = grDao.getByRank(account.getRank().getIdRank());
            for(Group i: groupRank){//Check user has group or not
                String key = i.getIdGroup();
                Group userGr = null;
                for(Group j: grDao.getAll()){
                    if(j.getIdGroup().equals(key) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))){
                        userGr = j;
                    }
                }
                if(userGr!=null){//user already has group
                    displayGroup(userGr);
                    FileRW.Write(FilePath.getUserGroup(), key);
                    hasGroup = true;
                    break;
                }
            }
            if(hasGroup==false){//user not have group
                if(groupRank.isEmpty()){
                    addNewGroup(GenerateID.genGroup());
                } else {
                    for (Group i : groupRank) {
                        String key = i.getIdGroup();
                        List<Group> grs = new ArrayList<>();
                        for (Group j : grDao.getAll()) {
                            if (j.getIdGroup().equals(key)) {
                                grs.add(j);
                            }
                        }
                        if (grs.size() < 15) {
                            addNewGroup(key);
                            FileRW.Write(FilePath.getUserGroup(), key);
                            break;
                        }
                        if (i.equals(groupRank.get(groupRank.size() - 1))) {
                            String id = GenerateID.genGroup();
                            addNewGroup(id);
                            FileRW.Write(FilePath.getUserGroup(), id);
                            break;
                        }
                    }
                }
            }
            RankingLevel rl = getCurrentRankingLevel();
            if (rl != null) {
                lbTime1.setText(rl.getTime1());
                lbTime2.setText(rl.getTime2());
                lbTime3.setText(rl.getTime3());
                lbLetters1.setText(rl.getLevelContent1().toCharArray().length + "");
                lbLetters2.setText(rl.getLevelContent2().toCharArray().length + "");
                lbLetters3.setText(rl.getLevelContent3().toCharArray().length + "");
            }
            ShowAllPlayers();

            cbbRound.setItems(FXCollections.observableList(new ArrayList<String>(Arrays.asList("Round 1", "Round 2", "Round 3"))));
            ShowPlayerByRound(1);
            cbbRound.getSelectionModel().select("Round 1");
            cbbRound.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
                switch (newValue.toString()) {
                    case "Round 1": {
                        ShowPlayerByRound(1);
                        break;
                    }
                    case "Round 2": {
                        ShowPlayerByRound(2);
                        break;
                    }
                    case "Round 3": {
                        ShowPlayerByRound(3);
                        break;
                    }
                }
            });
            ShowPromote();
            ShowRetain();
            ShowDemote();

            btnPlay1.setOnMouseClicked(e->{
                FileRW.Write(FilePath.getRound(),"1");
                FileRW.Write(FilePath.getOriginalRank(),rl.getLevelContent1()+"_");
                LoadForm.load("/fxml/RankFXMLs/Play.fxml","Play Rank",true);
                resetStatus();
            });
            btnPlay2.setOnMouseClicked(e->{
                FileRW.Write(FilePath.getRound(),"2");
                FileRW.Write(FilePath.getOriginalRank(),rl.getLevelContent2()+"_");
                LoadForm.load("/fxml/RankFXMLs/Play.fxml","Play Rank",true);
                resetStatus();
            });
            btnPlay3.setOnMouseClicked(e->{
                FileRW.Write(FilePath.getRound(),"3");
                FileRW.Write(FilePath.getOriginalRank(),rl.getLevelContent3()+"_");
                LoadForm.load("/fxml/RankFXMLs/Play.fxml","Play Rank",true);
                resetStatus();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayGroup(Group gr) {
        lbScore1.setText(gr.getScore1() + "");
        lbScore2.setText(gr.getScore2() + "");

        lbScore3.setText(gr.getScore3() + "");
        if (gr.getDatePlayed1() == null) {
            lbDatePlayed1.setText("---");
        } else {
            lbDatePlayed1.setText(gr.getDatePlayed1() + "");
        }
        if (gr.getDatePlayed2() == null) {
            lbDatePlayed2.setText("---");
        } else {
            lbDatePlayed2.setText(gr.getDatePlayed2() + "");
        }
        if (gr.getDatePlayed3() == null) {
            lbDatePlayed3.setText("---");
        } else {
            lbDatePlayed3.setText(gr.getDatePlayed3() + "");
        }

        lbTotalScore.setText(gr.getTotalScore() + "");
    }

    public void addNewGroup(String key) {
        GroupDao grDao = new GroupDao();
        Group gr = new Group();
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        gr.setIdGroup(key);
        gr.setRank(account.getRank());
        gr.setIdAccount(account.getIdAccount());
        gr.setScore1(0L);
        gr.setScore2(0L);
        gr.setScore3(0L);
        gr.setTotalScore(0L);
        if (getCurrentRankingLevel() == null) {
            gr.setRankingLevel(new RankingLevel());
        } else {
            gr.setRankingLevel(getCurrentRankingLevel());
        }
        grDao.add(gr);
        displayGroup(gr);
    }

    public RankingLevel getCurrentRankingLevel(){
        RankingLevelDao rlDao = new RankingLevelDao();
        RankingLevel rl = null;
        for (RankingLevel i : rlDao.getAll()) {
            LocalDate from = i.getFromRankPeriod().toLocalDate();
            LocalDate to = i.getToRankPeriod().toLocalDate();
            LocalDate startThisWeek = ZonedDateTime.now().with(DayOfWeek.MONDAY).toLocalDate();
            LocalDate endThisWeek = startThisWeek.plusDays(6);
            if (from.isEqual(startThisWeek) && to.isEqual(endThisWeek)) {
                rl = i;
            }
        }
        return rl;
    }

    public void ShowAllPlayers() {
        try {
            vbAllPlayers.getChildren().clear();
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            List<Group> grs = grDao.getAll().stream().filter(e -> {
                if (e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
            lbCurrentPlayers.setText(grs.size() + "/15");
            //
            spAllPlayers.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            spAllPlayers.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            //
            if (grs.size() != 0) {
                grs.sort(Comparator.comparingLong(Group::getTotalScore).reversed());
                List<Account> accs = new ArrayList<>();
                for (Group i : grs) {
                    accs.add(accountDao.get(i.getIdAccount()));
                }
                Node[] nodes = new Node[accs.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/AllPlayers_item.fxml"));
                    nodes[i] = loader.load();
                    AllPlayersController ctrl = loader.getController();

                    Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
                    String status = "";
                    AtomicInteger h = new AtomicInteger(); // any mutable integer wrapper
                    int finalI = i;
                    int pos = (accs.stream()
                            .peek(v -> h.incrementAndGet())
                            .anyMatch(user -> user.getIdAccount().equals(accs.get(finalI).getIdAccount())) ? // your predicate
                            h.get() - 1 : -1) + 1;
                    if (pos >= gr.getRank().getDemote()) {
                        lbPosition.setText("No." + pos + "/15 - Demote");
                        status = "Demote";
                    } else if (pos < gr.getRank().getDemote() && pos > gr.getRank().getPromote()) {
                        lbPosition.setText("No." + pos + "/15 - Retain");
                        status = "Retain";
                    } else {
                        lbPosition.setText("No." + pos + "/15 - Promote");
                        status = "Promote";
                    }
                    ctrl.transferData(accs.get(i).getIdAccount(), i + 1, grs.get(i).getTotalScore(), status);
                    vbAllPlayers.getChildren().add(nodes[i]);
                }
            } else {
                vbAllPlayers.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowPlayerByRound(Integer round) {
        try {
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            List<Group> grs = grDao.getAll().stream().filter(e -> {
                if (e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //
            spRoundLeaderboard.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            spRoundLeaderboard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            //
            if (grs.size() != 0) {
                if (round == 1) {
                    vbRoundLeaderboard.getChildren().clear();
                    grs.sort(Comparator.comparingLong(Group::getScore1).reversed());
                    List<Account> accs = new ArrayList<>();
                    for (Group i : grs) {
                        accs.add(accountDao.get(i.getIdAccount()));
                    }
                    Node[] nodes = new Node[accs.size()];
                    for (int i = 0; i < nodes.length; i++) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/AllPlayers_item.fxml"));
                        nodes[i] = loader.load();
                        AllPlayersController ctrl = loader.getController();
                        Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
                        ctrl.transferData(accs.get(i).getIdAccount(), i + 1, grs.get(i).getScore1(), "");
                        vbRoundLeaderboard.getChildren().add(nodes[i]);
                    }
                } else if (round == 2) {
                    vbRoundLeaderboard.getChildren().clear();
                    grs.sort(Comparator.comparingLong(Group::getScore2).reversed());
                    List<Account> accs = new ArrayList<>();
                    for (Group i : grs) {
                        accs.add(accountDao.get(i.getIdAccount()));
                    }
                    Node[] nodes = new Node[accs.size()];
                    for (int i = 0; i < nodes.length; i++) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/AllPlayers_item.fxml"));
                        nodes[i] = loader.load();
                        AllPlayersController ctrl = loader.getController();
                        Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
                        ctrl.transferData(accs.get(i).getIdAccount(), i + 1, grs.get(i).getScore2(), "");
                        vbRoundLeaderboard.getChildren().add(nodes[i]);
                    }
                } else {
                    vbRoundLeaderboard.getChildren().clear();
                    grs.sort(Comparator.comparingLong(Group::getScore3).reversed());
                    List<Account> accs = new ArrayList<>();
                    for (Group i : grs) {
                        accs.add(accountDao.get(i.getIdAccount()));
                    }
                    Node[] nodes = new Node[accs.size()];
                    for (int i = 0; i < nodes.length; i++) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/AllPlayers_item.fxml"));
                        nodes[i] = loader.load();
                        AllPlayersController ctrl = loader.getController();
                        Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
                        ctrl.transferData(accs.get(i).getIdAccount(), i + 1, grs.get(i).getScore3(), "");
                        vbRoundLeaderboard.getChildren().add(nodes[i]);
                    }
                }

            } else {
                vbRoundLeaderboard.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowPromote() {
        try {
            vbPromote.getChildren().clear();
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
            List<Group> grs = grDao.getAll().stream().filter(e -> {
                if (e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //
            spPromote.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            spPromote.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            //
            if (grs.size() != 0) {
                grs.sort(Comparator.comparingLong(Group::getTotalScore).reversed());
                List<Account> accs = new ArrayList<>();
                for (Group i : grs) {
                    accs.add(accountDao.get(i.getIdAccount()));
                }
                if(gr.getRank().getRankName().equals("God")){
                    grs.clear();
                }
                if (accs.size() >= acc.getRank().getPromote()) {
                    accs = accs.subList(0, acc.getRank().getPromote());
                }
                if (grs.size() >= gr.getRank().getPromote()) {
                    grs = grs.subList(0, gr.getRank().getPromote());
                }
                Node[] nodes = new Node[accs.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/Main_item.fxml"));
                    nodes[i] = loader.load();
                    MainItemController ctrl = loader.getController();
                    ctrl.transferData(accs.get(i).getIdAccount(), i + 1, grs.get(i).getTotalScore(), grs.get(i).getScore1(),grs.get(i).getScore2(),grs.get(i).getScore3());
                    vbPromote.getChildren().add(nodes[i]);
                }
                if(accs.size()>0){
                    long lowest = 0L;
                    long avr = 0L;
                    long highest = 0L;
                    highest = grs.get(0).getTotalScore();
                    lowest = grs.get(grs.size()-1).getTotalScore();
                    for(Group i: grs){
                        avr+=i.getTotalScore();
                    }
                    avr/=grs.size();
                    lbPromoteAvr.setText(avr+"");
                    lbPromoteLowest.setText(lowest+"");
                    lbPromoteHighest.setText(highest+"");
                }else {
                    lbPromoteAvr.setText("---");
                    lbPromoteLowest.setText("----");
                    lbPromoteHighest.setText("---");
                }
            } else {
                vbPromote.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowRetain() {
        try {
            vbRetain.getChildren().clear();
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
            List<Group> grs = grDao.getAll().stream().filter(e -> {
                if (e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //
            spRetain.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            spRetain.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            //
            if (grs.size() != 0) {
                grs.sort(Comparator.comparingLong(Group::getTotalScore).reversed());
                List<Account> accs = new ArrayList<>();
                for (Group i : grs) {
                    accs.add(accountDao.get(i.getIdAccount()));
                }
                if(accs.size()<=gr.getRank().getPromote()){
                    accs.clear();
                }
                if(accs.size()>gr.getRank().getPromote()){
                    accs = accs.subList(acc.getRank().getPromote(), acc.getRank().getDemote()-1);
                }
                if (grs.size() >= gr.getRank().getPromote()) {
                    grs = grs.subList(gr.getRank().getPromote(), gr.getRank().getDemote()-1);
                }
                Node[] nodes = new Node[accs.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/Main_item.fxml"));
                    nodes[i] = loader.load();
                    MainItemController ctrl = loader.getController();
                    ctrl.transferData(accs.get(i).getIdAccount(), i + accs.get(i).getRank().getPromote()+1, grs.get(i).getTotalScore(),
                            grs.get(i).getScore1(),grs.get(i).getScore2(),grs.get(i).getScore3() );
                    vbRetain.getChildren().add(nodes[i]);
                }
                if(accs.size() > 0){
                    long lowest = 0L;
                    long avr = 0L;
                    long highest = 0L;
                    highest = grs.get(0).getTotalScore();
                    lowest = grs.get(grs.size()-1).getTotalScore();
                    for(Group i: grs){
                        avr+=i.getTotalScore();
                    }
                    avr/=grs.size();
                    lbRetainAvr.setText(avr+"");
                    lbRetainLowest.setText(lowest+"");
                    lbRetainHighest.setText(highest+"");
                } else {
                    lbRetainAvr.setText("---");
                    lbRetainLowest.setText("---");
                    lbRetainHighest.setText("---");
                }
            } else {
                vbRetain.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowDemote() {
        try {
            vbDemote.getChildren().clear();
            AccountDao accountDao = new AccountDao();
            GroupDao grDao = new GroupDao();
            Group gr = grDao.get(FileRW.Read(FilePath.getUserGroup()));
            List<Group> grs = grDao.getAll().stream().filter(e -> {
                if (e.getIdGroup().equals(FileRW.Read(FilePath.getUserGroup()))) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //
            spDemote.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            spDemote.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Account acc = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            //
            if (grs.size() != 0) {
                grs.sort(Comparator.comparingLong(Group::getTotalScore).reversed());
                List<Account> accs = new ArrayList<>();
                for (Group i : grs) {
                    accs.add(accountDao.get(i.getIdAccount()));
                }
                if(gr.getRank().getRankName().equals("Bronze")){
                    accs.clear();
                }
                if(accs.size()>=gr.getRank().getDemote()){
                    accs = accs.subList(acc.getRank().getDemote()-1, accs.size());
                }
                if (grs.size() >= gr.getRank().getPromote()) {
                    grs = grs.subList(gr.getRank().getDemote()-1, grs.size());
                }
                Node[] nodes = new Node[accs.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(RankMenuController.class.getResource("/fxml/RankFXMLs/Main_item.fxml"));
                    nodes[i] = loader.load();
                    MainItemController ctrl = loader.getController();
                    ctrl.transferData(accs.get(i).getIdAccount(), i + accs.get(i).getRank().getDemote(), grs.get(i).getTotalScore(),
                            grs.get(i).getScore1(),grs.get(i).getScore2(),grs.get(i).getScore3());
                    vbDemote.getChildren().add(nodes[i]);
                }
                if(accs.size()>0){
                    long lowest = 0L;
                    long avr = 0L;
                    long highest = 0L;
                    highest = grs.get(0).getTotalScore();
                    lowest = grs.get(grs.size()-1).getTotalScore();
                    for(Group i: grs){
                        avr+=i.getTotalScore();
                    }
                    avr/=grs.size();
                    lbDemoteAvr.setText(avr+"");
                    lbDemoteLowest.setText(lowest+"");
                    lbDemoteHighest.setText(highest+"");
                } else {
                    lbDemoteAvr.setText("---");
                    lbDemoteLowest.setText("---");
                    lbDemoteHighest.setText("---");
                }
            } else {
                vbDemote.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
