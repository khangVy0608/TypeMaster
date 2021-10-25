package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import org.SpecikMan.Tools.FileRW;

import java.util.Arrays;
import java.util.Objects;

public class PlayController {
    @FXML
    private Button btnPause;
    @FXML
    private Label lbAccuracy;
    @FXML
    private Label lbCorrect;
    @FXML
    private Label lbTime;
    @FXML
    private Label lbTotal;
    @FXML
    private Label lbWPM;
    @FXML
    private Label lbWrong;
    @FXML
    private TextFlow textflow;
    private static final String NOT_TYPED_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\notTyped.txt";
    private static final String TYPED_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\typed.txt";
    private static final String ORIGINAL_PATH = "D:\\Learning\\TypeMaster\\src\\main\\resources\\data\\origin.txt";
    int correct = 0;
    int wrong = 0;
    int total = 0;
    double accuracy = 100;

    public void onBtnPauseClicked(MouseEvent e) {
        if (btnPause.getText().equals("Start")) {
            btnPause.setText("Pause");
        } else {
            btnPause.setText("Start");
        }
    }

    public void initialize() {
        char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
        FileRW.Write(NOT_TYPED_PATH, String.valueOf(chars));
        FileRW.Write(TYPED_PATH, "");
        textflow.setPrefWidth(710);
        for (char c : chars) {
            Label l = new Label(String.valueOf(c));
            l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
            textflow.getChildren().add(l);
        }
        lbCorrect.setText("0");
        lbWrong.setText("0");
        lbTotal.setText("0");
        lbAccuracy.setText("100%");
        lbTime.setText("00:00");
    }

    public void keyPressed(KeyEvent e) {
        char[] typed = Objects.requireNonNull(FileRW.Read(TYPED_PATH)).toCharArray();
        char[] notTyped = Objects.requireNonNull(FileRW.Read(NOT_TYPED_PATH)).toCharArray();
        int pos = 0;
        String input = e.getText();
        if (btnPause.getText().equals("Pause")) {
            if (notTyped[0] != notTyped[notTyped.length - 1]) {
                if (!String.valueOf(notTyped[1]).equals(" ")) {
                    if (String.valueOf(notTyped[0]).equals(input)) {
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 1);
                        typed[typed.length - 1] = input.toCharArray()[0];
                        Label l = new Label(input);
                        Label l2 = new Label(String.valueOf(notTyped));
                        Label l1 = new Label(String.valueOf(typed));
                        l.setStyle("-fx-font-size: 20;");
                        l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                        l1.setStyle("-fx-font-size: 20;");
                        textflow.getChildren().addAll(l1, l2);
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        correct++;
                        total++;
                        accuracy = (double)(correct/total)*100;
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(accuracy +"%");
                    } else {
                        wrong++;
                        total++;
                        accuracy = (double)(correct/total)*100;
                        lbWrong.setText(String.valueOf(wrong));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(accuracy +"%");
                    }
                } else {
                    if (String.valueOf(notTyped[0]).equals(input)) {
                        textflow.getChildren().clear();
                        notTyped = Arrays.copyOfRange(notTyped, 2, notTyped.length);
                        typed = Arrays.copyOf(typed, typed.length + 2);
                        typed[typed.length - 2] = input.toCharArray()[0];
                        typed[typed.length - 1] = " ".toCharArray()[0];
                        Label l = new Label(input);
                        Label l2 = new Label(String.valueOf(notTyped));
                        Label l1 = new Label(String.valueOf(typed));
                        l.setStyle("-fx-font-size: 20;");
                        l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                        l1.setStyle("-fx-font-size: 20;");
                        textflow.getChildren().addAll(l1, l2);
                        FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                        FileRW.Write(TYPED_PATH, String.valueOf(typed));
                        correct++;
                        total++;
                        accuracy = (double) (correct / total) * 100;
                        lbCorrect.setText(String.valueOf(correct));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(accuracy + "%");
                    } else {
                        wrong++;
                        total++;
                        accuracy = (double) (correct / total) * 100;
                        lbWrong.setText(String.valueOf(wrong));
                        lbTotal.setText(String.valueOf(total));
                        lbAccuracy.setText(accuracy + "%");
                    }
                }
            } else {
                if (String.valueOf(notTyped[0]).equals(input)) {
                    textflow.getChildren().clear();
                    notTyped = Arrays.copyOfRange(notTyped, 1, notTyped.length);
                    typed = Arrays.copyOf(typed, typed.length + 1);
                    typed[typed.length - 1] = input.toCharArray()[0];
                    Label l = new Label(input);
                    Label l2 = new Label(String.valueOf(notTyped));
                    Label l1 = new Label(String.valueOf(typed));
                    l.setStyle("-fx-font-size: 20;");
                    l2.setStyle("-fx-font-size: 20;-fx-text-fill: gray;");
                    l1.setStyle("-fx-font-size: 20;");
                    textflow.getChildren().addAll(l1, l2);
                    FileRW.Write(NOT_TYPED_PATH, String.valueOf(notTyped));
                    FileRW.Write(TYPED_PATH, String.valueOf(typed));
                    correct++;
                    total++;
                    accuracy = (double)(correct/total)*100;
                    lbCorrect.setText(String.valueOf(correct));
                    lbTotal.setText(String.valueOf(total));
                    lbAccuracy.setText(accuracy +"%");
                } else {
                    wrong++;
                    total++;
                    accuracy = (double)(correct/total)*100;
                    lbWrong.setText(String.valueOf(wrong));
                    lbTotal.setText(String.valueOf(total));
                    lbAccuracy.setText(accuracy +"%");
                }
            }
        } else {
            char[] chars = Objects.requireNonNull(FileRW.Read(ORIGINAL_PATH)).toCharArray();
            textflow.getChildren().clear();
            textflow.setPrefWidth(710);
            for (char c : chars) {
                Label l = new Label(String.valueOf(c));
                l.setStyle("-fx-font-size: 20;-fx-text-fill: gray");
                textflow.getChildren().add(l);
            }
        }
    }
}
