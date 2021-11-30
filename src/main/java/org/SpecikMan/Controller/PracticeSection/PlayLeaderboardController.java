package org.SpecikMan.Controller.PracticeSection;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayLeaderboardController {

    @FXML
    private Label lbRank;

    @FXML
    private Label lbScore;

    @FXML
    private Label lbUsername;

    public void set(String rank,String username,Long score){
        lbRank.setText(rank);
        lbUsername.setText(username);
        lbScore.setText(String.valueOf(score));
    }
}
