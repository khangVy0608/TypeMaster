package org.SpecikMan.Controller.ShopSection;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class ItemController {

    @FXML
    private ImageView image;

    @FXML
    private Label lbDescription;

    @FXML
    private Label lbDetail;

    @FXML
    private Label lbName;

    public void transferData(String itemName,int timeUsed,int cost,int inInventory,String description,String imagePath) throws IOException {
        image.setImage(new Image(String.valueOf(getClass().getResource(imagePath))));
        lbName.setText(itemName);
        lbDetail.setText("Cost: "+cost+" - Used: "+timeUsed+" - In Inventory: "+inInventory);
        lbDescription.setText(description);
    }

}
