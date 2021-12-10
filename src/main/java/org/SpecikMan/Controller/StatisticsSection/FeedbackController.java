package org.SpecikMan.Controller.StatisticsSection;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.SpecikMan.DAL.FeedbackDao;
import org.SpecikMan.Entity.Feedback;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.ShowAlert;

import java.sql.Date;
import java.time.LocalDate;

public class FeedbackController {

    @FXML
    private TextArea taDetail;

    @FXML
    void btnSendClicked(MouseEvent event) {
        if (taDetail.getText().isEmpty() || taDetail.getText() == null) {
            ShowAlert.show("Warning!", "Please input all fields");
        } else {
            FeedbackDao fbDao = new FeedbackDao();
            Feedback fb = new Feedback();
            fb.setIdFeedback(GenerateID.genFB());
            fb.setSubmitDetail(taDetail.getText().trim());
            fb.setSubmitDate(Date.valueOf(LocalDate.now()));
            fb.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
            fbDao.add(fb);
            ShowAlert.show("Notice", "Send feedback success");
            taDetail.setText("");
        }
    }

}
