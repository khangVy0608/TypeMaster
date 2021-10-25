package org.SpecikMan.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {
    @FXML
    private Button btnAll;
    @FXML
    private TextField txtSearch;
    @FXML
    private VBox vboxItems;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    public void onBtnAllClicked(MouseEvent event) {

    }
    public void initialize(){
        try{
            Node[] nodes = new Node[15];
            for(int i = 0 ; i < nodes.length ; i++){
                nodes[i] = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/fxml/Dashboard_item.fxml"))));
                final int h = i;
                nodes[i].setOnMouseEntered(evt->{
                    nodes[h].setStyle("-fx-background-color: #4498e9;");
                });
                nodes[i].setOnMouseExited(evt->{
                   nodes[h].setStyle("-fx-background-color: #b4b4b4");
                });
                nodes[i].setOnMousePressed(evt->{

                });
                vboxItems.getChildren().add(nodes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
