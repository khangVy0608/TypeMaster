package org.SpecikMan.Controller.RankSection;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.GroupDao;
import org.SpecikMan.DAL.RankingLevelDao;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView ivUser;
    @FXML
    private ImageView ivRank;

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
            lbRetain.setText("No." + account.getRank().getPromote() + " -> No." + account.getRank().getDemote());
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
                    hasGroup = true;
                    break;
                }
            }
            if(hasGroup==false){//user not have group
                if(groupRank.isEmpty()){
                    addNewGroup(GenerateID.genGroup());
                } else {
                    for(Group i: groupRank){
                        String key = i.getIdGroup();
                        List<Group> grs = new ArrayList<>();
                        for(Group j: grDao.getAll()){
                            if(j.getIdGroup().equals(key)){
                                grs.add(j);
                            }
                        }
                        if(grs.size()<15){
                            addNewGroup(key);
                            break;
                        }
                        if(i.equals(groupRank.get(groupRank.size()-1))){
                            addNewGroup(GenerateID.genGroup());
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
                lbLetters1.setText(rl.getLevelContent1().toCharArray().length+"");
                lbLetters2.setText(rl.getLevelContent2().toCharArray().length+"");
                lbLetters3.setText(rl.getLevelContent3().toCharArray().length+"");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displayGroup(Group gr){
        if(gr.getScore1()==null){
            lbScore1.setText("---");
        } else {
            lbScore1.setText(gr.getScore1()+"");
        }
        if(gr.getScore2()==null){
            lbScore2.setText("---");
        } else {
            lbScore2.setText(gr.getScore2()+"");
        }
        if(gr.getScore3()==null){
            lbScore3.setText("---");
        } else {
            lbScore3.setText(gr.getScore3()+"");
        }
        if(gr.getDatePlayed1()==null){
            lbDatePlayed1.setText("---");
        } else {
            lbDatePlayed1.setText(gr.getDatePlayed1()+"");
        }
        if(gr.getDatePlayed2()==null){
            lbDatePlayed2.setText("---");
        } else {
            lbDatePlayed2.setText(gr.getDatePlayed2()+"");
        }
        if(gr.getDatePlayed3()==null){
            lbDatePlayed3.setText("---");
        } else {
            lbDatePlayed3.setText(gr.getDatePlayed3()+"");
        }
        if(gr.getTotalScore()==null){
            lbTotalScore.setText("---");
        } else {
            lbTotalScore.setText(gr.getTotalScore()+"");
        }
    }
    public void addNewGroup(String key){
        GroupDao grDao = new GroupDao();
        Group gr = new Group();
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        gr.setIdGroup(key);
        gr.setRank(account.getRank());
        gr.setIdAccount(account.getIdAccount());
        if(getCurrentRankingLevel()==null){
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
}
