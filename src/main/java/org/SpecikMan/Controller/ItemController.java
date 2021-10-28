package org.SpecikMan.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;
import org.SpecikMan.Tools.ShowAlert;

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
            Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
            levelDao.delete(level);
            ShowAlert.show("Notice","Delete successfully");
        }
    }
}