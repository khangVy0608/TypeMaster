package org.SpecikMan.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.DetailLog;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.LoadForm;
import org.SpecikMan.Tools.ShowAlert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemController {
    @FXML
    private FontAwesomeIconView iconEasy;
    @FXML
    private FontAwesomeIconView iconHard;
    @FXML
    private FontAwesomeIconView iconNormal;
    @FXML
    private FontAwesomeIconView iconReload;
    @FXML
    private FontAwesomeIconView iconClose;
    @FXML
    private Label lbDetails;
    @FXML
    private Label lbLevelName;

    public void setItemInfo(String levelName,String publisher,String difficulty,int totalWords,int like,boolean isEdit){
        lbLevelName.setText(levelName);
        lbDetails.setText(publisher+" - "+totalWords+" words - "+like+" likes");
        iconReload.setVisible(isEdit);
        iconClose.setVisible(isEdit);
        if(difficulty.equals("DF1")){
            iconEasy.setVisible(true);
            iconNormal.setVisible(false);
            iconHard.setVisible(false);
        } else if (difficulty.equals("DF2")){
            iconEasy.setVisible(true);
            iconNormal.setVisible(true);
            iconHard.setVisible(false);
        } else {
            iconEasy.setVisible(true);
            iconNormal.setVisible(true);
            iconHard.setVisible(true);
        }
    }
    public void onIconReloadClicked(){
        LoadForm.load("/fxml/ModifyLevel.fxml","Modify Level",false);
    }
    public void onIconCloseClicked(){//Still buggy
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to delete this level?");
        alert.setTitle("Warning");
        alert.setHeaderText("Delete Level");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get()==ButtonType.OK){
            LevelDao levelDao = new LevelDao();
            DetailsDao detailsDao = new DetailsDao();
            DetailLogDao logDao = new DetailLogDao();
            Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
            List<AccountLevelDetails> details = new ArrayList<>();
            for(AccountLevelDetails i:detailsDao.getAll()){
                if(i.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel()))){
                    details.add(i);
                }
            }
            for(AccountLevelDetails i:details){
                DetailLog log = new DetailLog();
                log.setIdLog(GenerateID.genLog());
                log.setIdLevel(level.getIdLevel());
                log.setLevelName(level.getNameLevel());
                log.setIdPublisher(level.getIdAccount());
                log.setPublisherName(level.getUsername());
                log.setIdPlayer(i.getIdAccount());
                log.setPlayerName(i.getUsername());
                log.setScore(i.getScore());
                log.setWpm(i.getWpm());
                log.setWps(i.getWps());
                log.setCorrect(i.getCorrect());
                log.setWrong(i.getWrong());
                log.setAccuracy(i.getAccuracy());
                log.setTimeLeft(i.getTimeLeft());
                log.setDatePlayed(i.getDatePlayed());
                logDao.add(log);
                detailsDao.delete(i);
            }
            levelDao.delete(level);
            ShowAlert.show("Notice","Delete level successfully");

        }
    }
    public void initialize(){

    }
}