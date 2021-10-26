package org.SpecikMan.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemController {
    @FXML
    private FontAwesomeIconView iconEasy;
    @FXML
    private FontAwesomeIconView iconHard;
    @FXML
    private FontAwesomeIconView iconNormal;
    @FXML
    private Label lbDetails;
    @FXML
    private Label lbLevelName;

    public void setItemInfo(String levelName,String publisher,String difficulty,int totalWords){
        lbLevelName.setText(levelName);
        lbDetails.setText(publisher+" - "+totalWords+" words");
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
}
