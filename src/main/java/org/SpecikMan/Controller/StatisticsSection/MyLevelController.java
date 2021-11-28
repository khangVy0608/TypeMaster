package org.SpecikMan.Controller.StatisticsSection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.SpecikMan.Controller.PracticeSection.DashboardController;
import org.SpecikMan.DAL.*;
import org.SpecikMan.Entity.*;
import org.SpecikMan.Tools.FileRW;
import javafx.scene.control.ScrollPane;
import org.SpecikMan.Tools.LoadForm;
import org.SpecikMan.Tools.ShowAlert;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyLevelController {

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnSaveMyLevel;

    @FXML
    private ComboBox<Difficulty> cbbDiff;

    @FXML
    private ComboBox<Mode> cbbMode;

    @FXML
    private Button txtAdd;

    @FXML
    private TextArea txtContent;

    @FXML
    private TextField txtFilePath;

    @FXML
    private Button txtFind;

    @FXML
    private TextField txtMinute;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSecond;

    @FXML
    private VBox vboxItems;
    @FXML
    private ScrollPane scrollPane;
    private boolean[] isSelected;
    FileChooser fileChooser = new FileChooser();
    @FXML
    void btnAddClicked(MouseEvent event) {
        LoadForm.load("/fxml/PracticeFXMLs/AddLevel.fxml","Add Level",true);
        initialize();

    }

    @FXML
    void btnBrowseClicked(MouseEvent event) {
        if(txtFilePath.getText().equals("")){
            configuringFileChooser(fileChooser);
            String path = fileChooser.showOpenDialog(btnBrowse.getScene().getWindow()).getPath();
            txtContent.setText(FileRW.Read(path));
        }else {
            txtContent.setText(FileRW.Read(txtFilePath.getText()));
        }
    }

    @FXML
    void btnFindClicked(MouseEvent event) {
        LevelDao levelDao = new LevelDao();
        List<Level> levelsM = levelDao.getAll();
        List<Level> levels = new ArrayList<>();
        if (txtSearch.getText().trim().equals("")) {
            levels.addAll(levelsM);
        } else {
            for (Level i : levelsM) {
                if (i.getNameLevel().contains(txtSearch.getText().trim()) || i.getLevelContent().trim().contains(txtSearch.getText().trim())) {
                    levels.add(i);
                }
            }
        }
        showAll(levels);
    }

    @FXML
    void btnSaveClicked(MouseEvent event) {
        Difficulty diff = cbbDiff.getSelectionModel().getSelectedItem();
        Mode mode = cbbMode.getSelectionModel().getSelectedItem();
        LevelDao levelDao = new LevelDao();
        Level level = levelDao.get(FileRW.Read(FilePath.getPlayLevel()));
        level.setNameLevel(txtName.getText());
        level.setUpdatedDate(Date.valueOf(LocalDate.now()));
        level.setLevelContent(txtContent.getText());
        level.setTotalWords(txtContent.getText().split(" ").length);
        level.setTime(txtMinute.getText() + ":" + txtSecond.getText());
        level.setDifficulty(new Difficulty(diff.getIdDifficulty(), diff.getNameDifficulty()));
        level.setMode(new Mode(mode.getIdMode(), mode.getNameMode()));
        levelDao.update(level);
        ShowAlert.show("Notice", "Modify successfully");
        initialize();
    }
    public void initialize(){
        disableMid();
        DifficultyDao difficultyDao = new DifficultyDao();
        ModeDao modeDao = new ModeDao();
        ObservableList<Difficulty> diffs = FXCollections.observableList(difficultyDao.getAll());
        ObservableList<Mode> modes = FXCollections.observableList(modeDao.getAll());
        cbbDiff.setItems(diffs);
        cbbMode.setItems(modes);
        LevelDao levelDao = new LevelDao();
        List<Level> list = new ArrayList<>();
        for (Level i : levelDao.getAll()) {
            if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                list.add(i);
            }
        }
        showAll(list);
    }
    public void showAll(List<Level> listLevel) {
        try {
            vboxItems.getChildren().clear();
            if (listLevel.size() != 0) {
                AccountDao accountDao = new AccountDao();
                Node[] nodes = new Node[listLevel.size()];
                isSelected = new boolean[listLevel.size()];
                for (int i = 0; i < nodes.length; i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(DashboardController.class.getResource("/fxml/StatisticsFXMLs/MyLevel_item.fxml"));
                    nodes[i] = loader.load();
                    Level level = listLevel.get(i);
                    MyLevelItemController controller = loader.getController();
                    controller.setItemInfo(level.getNameLevel(), accountDao.get(level.getIdAccount()).getUsername(), level.getDifficulty().getIdDifficulty(), level.getTotalWords(), level.getNumLike(), level.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())));
                    final int h = i;
                    nodes[i].setOnMouseEntered(evt -> {
                        if (!isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        }
                    });
                    nodes[i].setOnMouseExited(evt -> {
                        if (isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        } else {
                            nodes[h].setStyle("-fx-background-color: #b4b4b4");
                        }
                    });
                    nodes[i].setOnMousePressed(evt -> {
                        enableMid();
                        Arrays.fill(isSelected, Boolean.FALSE);
                        isSelected[h] = true;
                        for (Node n : nodes) {
                            n.setStyle("-fx-background-color: #b4b4b4");
                        }
                        if (isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        }
                        txtName.setText(level.getNameLevel());
                        txtMinute.setText(level.getTime().split(":")[0]);
                        txtSecond.setText(level.getTime().split(":")[1]);
                        cbbDiff.setValue(level.getDifficulty());
                        cbbMode.setValue(level.getMode());
                        txtContent.setText(level.getLevelContent().trim());
                        FileRW.Write(FilePath.getPlayLevel(),level.getIdLevel());
                    });
                    vboxItems.getChildren().add(nodes[i]);
                }
            }
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void disableMid(){
        txtName.setDisable(true);
        txtMinute.setDisable(true);
        txtSecond.setDisable(true);
        cbbMode.setDisable(true);
        cbbDiff.setDisable(true);
        txtFilePath.setDisable(true);
        btnBrowse.setDisable(true);
        btnSaveMyLevel.setDisable(true);
        txtContent.setDisable(true);
    }
    public void enableMid(){
        txtName.setDisable(false);
        txtMinute.setDisable(false);
        txtSecond.setDisable(false);
        cbbMode.setDisable(false);
        cbbDiff.setDisable(false);
        txtFilePath.setDisable(false);
        btnBrowse.setDisable(false);
        btnSaveMyLevel.setDisable(false);
        txtContent.setDisable(false);
    }

}
