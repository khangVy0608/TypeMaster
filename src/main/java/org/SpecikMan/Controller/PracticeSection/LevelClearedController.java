package org.SpecikMan.Controller.PracticeSection;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.SpecikMan.DAL.DetailLogDao;
import org.SpecikMan.DAL.DetailsDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.AccountLevelDetails;
import org.SpecikMan.Entity.DetailLog;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LevelClearedController {

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnRetry;

    @FXML
    private Label lbAccuracy;

    @FXML
    private Label lbCombo;

    @FXML
    private Label lbCorrect;

    @FXML
    private Label lbCorrectnessScore;

    @FXML
    private Label lbLevelName;

    @FXML
    private Label lbMax;

    @FXML
    private Label lbPlayerName;

    @FXML
    private Label lbTimeLeft;

    @FXML
    private Label lbTimeScore;

    @FXML
    private Label lbTimeUsed;

    @FXML
    private Label lbTotal;

    @FXML
    private Label lbTotalScore;

    @FXML
    private Label lbWPM;

    @FXML
    private Label lbWPS;

    @FXML
    private Label lbWrong;

    public void initialize() {
        String[] data = Objects.requireNonNull(FileRW.Read(FilePath.getPlayResult())).split("-");
        lbWPS.setText(data[0]);
        lbWPM.setText(data[1]);
        lbCorrect.setText(data[2]);
        lbWrong.setText(data[3]);
        lbTotal.setText(data[4]);
        lbCombo.setText(data[5]);
        lbMax.setText(data[6]);
        lbAccuracy.setText(data[7]);
        lbTimeLeft.setText(data[8]);
        lbTimeUsed.setText(data[9]);
        lbTotalScore.setText(data[10]);
        lbTimeScore.setText(((Integer.parseInt(data[8].split(":")[0]) * 60 + Integer.parseInt(data[8].split(":")[1])) * 1000) + "");
        lbCorrectnessScore.setText(Integer.parseInt(lbTotalScore.getText()) - Integer.parseInt(lbTimeScore.getText()) + "");
        lbPlayerName.setText(data[11]);
        lbLevelName.setText(data[12]);
        LevelDao levelDao = new LevelDao();
        DetailsDao detailsDao = new DetailsDao();
        DetailLogDao logDao = new DetailLogDao();
        DetailLog log = new DetailLog();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        AccountLevelDetails detail = new AccountLevelDetails();
        for (AccountLevelDetails i : detailsDao.getAll()) {
            if (i.getLevel().getIdLevel().equals(FileRW.Read(FilePath.getPlayLevel())) && i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                detail = i;
            }
        }
        if(detail.getIdLevelDetails()==null){
            detail.setIdLevelDetails(GenerateID.genDetails());
            detail.setLevel(new Level(FileRW.Read(FilePath.getPlayLevel())));
            detail.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
            detail.setLike(false);
            detail.setWpm(Float.parseFloat(lbWPM.getText()));
            detail.setWps(Float.parseFloat(lbWPS.getText()));
            detail.setCorrect(Integer.parseInt(lbCorrect.getText()));
            detail.setWrong(Integer.parseInt(lbWrong.getText()));
            detail.setAccuracy(lbAccuracy.getText());
            detail.setTimeLeft(lbTimeLeft.getText());
            detail.setDatePlayed(Date.valueOf(LocalDate.now()));
            detail.setScore(Integer.parseInt(lbTotalScore.getText()));
            detailsDao.add(detail);
        } else {
            log.setIdLog(GenerateID.genLog());
            log.setIdLevel(level.getIdLevel());
            log.setLevelName(level.getNameLevel());
            log.setIdPublisher(level.getIdAccount());
            log.setPublisherName(level.getUsername());
            log.setIdPlayer(detail.getIdAccount());
            log.setPlayerName(detail.getUsername());
            log.setScore(detail.getScore());
            log.setWpm(detail.getWpm());
            log.setWps(detail.getWps());
            log.setCorrect(detail.getCorrect());
            log.setWrong(detail.getWrong());
            log.setAccuracy(detail.getAccuracy());
            log.setTimeLeft(detail.getTimeLeft());
            log.setDatePlayed(detail.getDatePlayed());
            logDao.add(log);
            detail.setWpm(Float.parseFloat(lbWPM.getText()));
            detail.setWps(Float.parseFloat(lbWPS.getText()));
            detail.setCorrect(Integer.parseInt(lbCorrect.getText()));
            detail.setWrong(Integer.parseInt(lbWrong.getText()));
            detail.setAccuracy(lbAccuracy.getText());
            detail.setTimeLeft(lbTimeLeft.getText());
            detail.setDatePlayed(Date.valueOf(LocalDate.now()));
            detail.setScore(Integer.parseInt(lbTotalScore.getText()));
            detailsDao.update(detail);
        }
    }
    @FXML
    public void btnRetryClicked(){
        FileRW.Write(FilePath.getRetryOrMenu(),"retry");
        DisposeForm.Dispose(lbLevelName);
    }
    @FXML
    public void btnMenuClicked(){
        FileRW.Write(FilePath.getRetryOrMenu(),"menu");
        DisposeForm.Dispose(lbPlayerName);
    }

}

