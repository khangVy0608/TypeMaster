package org.SpecikMan.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.SpecikMan.DAL.DifficultyDao;
import org.SpecikMan.DAL.LevelDao;
import org.SpecikMan.DAL.ModeDao;
import org.SpecikMan.Entity.Difficulty;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Level;
import org.SpecikMan.Entity.Mode;
import org.SpecikMan.Tools.DisposeForm;
import org.SpecikMan.Tools.FileRW;
import org.SpecikMan.Tools.GenerateID;
import org.SpecikMan.Tools.ShowAlert;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;

public class CopyLevelController {

    @FXML
    private Button btnChooseFile;

    @FXML
    private Button btnConfirm;

    @FXML
    private ComboBox<Difficulty> cbbDifficulty;

    @FXML
    private ComboBox<Mode> cbbMode;

    @FXML
    private TextField txtContent;

    @FXML
    private TextField txtLevelName;

    @FXML
    private TextField txtMinute;

    @FXML
    private TextField txtSecond;

    FileChooser fileChooser = new FileChooser();
    @FXML
    void onBtnConfirmClicked(MouseEvent event) {
        if (txtLevelName.getText() == null || txtLevelName.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write level name correctly");
        } else if (txtContent.getText() == null || txtContent.getText().isEmpty()) {
            ShowAlert.show("Warning!", "Please write content correctly");
        } else if(txtMinute.getText() == null || txtMinute.getText().isEmpty()){
            ShowAlert.show("Warning!", "Please write minute correctly");
        } else if(txtSecond.getText() == null || txtSecond.getText().isEmpty()){
            ShowAlert.show("Warning!", "Please write second correctly");
        } else if(cbbMode.getValue()==null){
            ShowAlert.show("Warning!", "Please choose Mode");
        } else if(cbbDifficulty.getValue()==null){
            ShowAlert.show("Warning!", "Please choose difficulty");
        } else {
            Difficulty diff = cbbDifficulty.getSelectionModel().getSelectedItem();
            Mode mode = cbbMode.getSelectionModel().getSelectedItem();
             Level level = new Level();
             LevelDao levelDao = new LevelDao();
             level.setIdLevel(GenerateID.genLevel());
             level.setNameLevel(txtLevelName.getText());
             level.setNumLike(0);
             level.setCreateDate(Date.valueOf(LocalDate.now()));
             level.setUpdatedDate(Date.valueOf(LocalDate.now()));
             level.setLevelContent(txtContent.getText());
             level.setTotalWords(txtContent.getText().split(" ").length);
             level.setTime(txtMinute.getText()+":"+txtSecond.getText());
             level.setDifficulty(new Difficulty(diff.getIdDifficulty(),diff.getNameDifficulty()));
             level.setMode(new Mode(mode.getIdMode(),mode.getNameMode()));
             level.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
             levelDao.add(level);
             ShowAlert.show("Notice","Copy successfully");
             DisposeForm.Dispose(txtContent);
        }
    }

    @FXML
    void onBtnChooseFileClicked(MouseEvent event) {
        configuringFileChooser(fileChooser);
        String path = fileChooser.showOpenDialog(btnChooseFile.getScene().getWindow()).getPath();
        txtContent.setText(FileRW.Read(path));
    }
    public void initialize(){
        DifficultyDao difficultyDao = new DifficultyDao();
        LevelDao levelDao = new LevelDao();
        ModeDao modeDao = new ModeDao();
        ObservableList<Difficulty> diffs = FXCollections.observableList(difficultyDao.getAll());
        ObservableList<Mode> modes = FXCollections.observableList(modeDao.getAll());
        cbbDifficulty.setItems(diffs);
        cbbMode.setItems(modes);
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        txtContent.setText(level.getLevelContent());
        txtLevelName.setText(level.getNameLevel());
        txtMinute.setText(level.getTime().split(":")[0]);
        txtSecond.setText(level.getTime().split(":")[1]);
        cbbDifficulty.setValue(level.getDifficulty());
        cbbMode.setValue(level.getMode());
    }
    private void configuringFileChooser(FileChooser fileChooser) {

        // Set tiêu đề cho FileChooser
        fileChooser.setTitle("Select Text File");


        // Sét thư mục bắt đầu nhìn thấy khi mở FileChooser
        fileChooser.setInitialDirectory(new File("C:/"));


        // Thêm các bộ lọc file vào
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("WORD", "*.doc"),
                new FileChooser.ExtensionFilter("WORD", "*.docx"));
    }

}
