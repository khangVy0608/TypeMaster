package org.SpecikMan.Controller.ShopSection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.InventoryDao;
import org.SpecikMan.DAL.ShopDao;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Inventory;
import org.SpecikMan.Entity.Shop;
import org.SpecikMan.Tools.FileRW;

import java.io.IOException;
import java.util.List;

public class ShopController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxItems;

    @FXML
    void btnAllClicked(MouseEvent event) {
    }

    public void initialize() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ShopDao shopDao = new ShopDao();
        showAll(shopDao.getAll());
    }

    public void showAll(List<Shop> items) {
        try {
            vboxItems.getChildren().clear();
            ShopDao shopDao = new ShopDao();
            InventoryDao inventoryDao = new InventoryDao();
            Node[] nodes = new Node[items.size()];
            for (int i = 0; i < nodes.length; i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ShopController.class.getResource("/fxml/ShopFXMLs/Shop_item.fxml"));
                nodes[i] = loader.load();
                Shop item = items.get(i);
                Inventory inventory = new Inventory();
                for (Inventory j : inventoryDao.getAll()) {
                    if (j.getItem().getIdItem().equals(item.getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))) {
                        inventory = j;
                    }
                }
                ItemController controller = loader.getController();
                controller.transferData(item.getItemName(), item.getTimeUsed(), item.getCost(), inventory.getCurrentlyHave(),
                        item.getDescription(), item.getImagePath());
                vboxItems.getChildren().add(nodes[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
