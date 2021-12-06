package org.SpecikMan.Controller.RankSection;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.LoadForm;

public class AllPlayersController {
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbScore;
    @FXML
    private Label lbNo;
    @FXML
    private Hyperlink hlProfile;
    @FXML
    private ImageView ivImage;
    private String id = "";

    public void initialize() {
        hlProfile.setOnMouseClicked(e -> {
            FileRW.Write(FilePath.getChooseProfile(), id);
            LoadForm.load("/fxml/PracticeFXMLs/Profile.fxml", "Profile", false);
        });
    }

    public void transferData(
            String idAccount, Integer no, Long score, String status
    ) {
        AccountDao accountDao = new AccountDao();
        Account acc = accountDao.get(idAccount);
        Image i = new Image(acc.getPathImage());
        ivImage.setImage(i);
        lbUsername.setText(acc.getUsername() + " - Lv." + acc.getAccountLevel());
        lbNo.setText("#" + no);
        lbScore.setText(score +" - "+ status);
        id = idAccount;
    }
}
