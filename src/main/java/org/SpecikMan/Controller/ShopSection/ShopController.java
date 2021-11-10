package org.SpecikMan.Controller.ShopSection;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.SpecikMan.DAL.AccountDao;
import org.SpecikMan.DAL.InventoryDao;
import org.SpecikMan.DAL.ShopDao;
import org.SpecikMan.Entity.Account;
import org.SpecikMan.Entity.FilePath;
import org.SpecikMan.Entity.Inventory;
import org.SpecikMan.Entity.Shop;
import org.SpecikMan.Tools.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShopController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vboxItems;
    @FXML
    private Label lbCoin;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Label lbItemName;
    @FXML
    private Label lbDetails;
    @FXML
    private Label lbTips;
    @FXML
    private Label lbEffectsBy;
    @FXML
    private Label lbLimit;
    @FXML
    private Label lbUsedAll;
    @FXML
    private Label lbUsedOne;
    @FXML
    private Label lbHave;
    @FXML
    private Label lbCostEach;
    @FXML
    private Label lbCostTotal;
    @FXML
    private Label lbAmount;
    @FXML
    private Label lbMinusCoin;
    @FXML
    private Button btnMinus;
    @FXML
    private Button btnPlus;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnTry;
    @FXML
    private Button btnPurchase;
    @FXML
    private Label lbEnough;
    @FXML
    private Label lbMinus;
    private boolean[] isSelected;
    private int stock;
    private String idItem;

    @FXML
    void btnAllClicked() {
        initialize();
    }

    @FXML
    void btnMostUsedClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> shops = shopDao.getAll();
        shops.sort(Comparator.comparingInt(Shop::getTimeUsed).reversed());
        showAll(shops);
    }

    @FXML
    void btnHighestClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = shopDao.getAll();
        items.sort(Comparator.comparingInt(Shop::getCost).reversed());
        showAll(items);
    }

    @FXML
    void btnLowestClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = shopDao.getAll();
        items.sort(Comparator.comparingInt(Shop::getCost));
        showAll(items);
    }

    @FXML
    void btnCorrectnessClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        for (Shop i : shopDao.getAll()) {
            if (i.getEffectsBy().contains("Correctness")) {
                items.add(i);
            }
        }
        showAll(items);
    }

    @FXML
    void btnSpeedClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        for (Shop i : shopDao.getAll()) {
            if (i.getEffectsBy().contains("Speed")) {
                items.add(i);
            }
        }
        showAll(items);
    }

    @FXML
    void btnTimeClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        for (Shop i : shopDao.getAll()) {
            if (i.getEffectsBy().contains("Time")) {
                items.add(i);
            }
        }
        showAll(items);
    }

    @FXML
    void btnComboClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        for (Shop i : shopDao.getAll()) {
            if (i.getEffectsBy().contains("Combo")) {
                items.add(i);
            }
        }
        showAll(items);
    }

    @FXML
    void btnAffordableClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        for (Shop i : shopDao.getAll()) {
            if (i.getCost() <= Integer.parseInt(lbCoin.getText())) {
                items.add(i);
            }
        }
        showAll(items);
    }

    @FXML
    void btnBackClicked() {
        DisposeForm.Dispose(lbCoin);
    }

    @FXML
    void btnMinusClicked() {
        if(Integer.parseInt(lbAmount.getText())>1){
            lbAmount.setText(Integer.parseInt(lbAmount.getText())-1+"");
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText().split("-")[0])-Integer.parseInt(lbCostEach.getText()))+"");
            lbCostTotal.setText(lbMinusCoin.getText());
        } else {
            lbAmount.setText("0");
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText().split("-")[0])-Integer.parseInt(lbCostEach.getText()))+"");
            lbCostTotal.setText(lbMinusCoin.getText());
            lbMinusCoin.setVisible(false);
            lbMinus.setVisible(false);
        }
    }

    @FXML
    void btnPlusClicked() {
        if(Integer.parseInt(lbAmount.getText())<stock&&Integer.parseInt(lbMinusCoin.getText())+Integer.parseInt(lbCostEach.getText())<=Integer.parseInt(lbCoin.getText()))
        {
            lbMinusCoin.setVisible(true);
            lbMinus.setVisible(true);
            lbAmount.setText(Integer.parseInt(lbAmount.getText()) + 1 + "");
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText()) + Integer.parseInt(lbCostEach.getText())) + "");
            lbCostTotal.setText(lbMinusCoin.getText());
        }
    }

    @FXML
    void btnTryClicked() {
    }

    @FXML
    void btnResetClicked() {
        lbAmount.setText("0");
        lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText().split("-")[0])-Integer.parseInt(lbCostEach.getText()))+"");
        lbCostTotal.setText(lbMinusCoin.getText());
        lbMinusCoin.setVisible(false);
        lbMinus.setVisible(false);
        lbCostTotal.setText("0");
    }

    @FXML
    void btnPurchaseClicked() {
        InventoryDao inventoryDao = new InventoryDao();
        AccountDao accountDao = new AccountDao();
        Inventory inventory = new Inventory();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        for(Inventory i:inventoryDao.getAll()){
            if(i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))&&i.getItem().getItemName().equals(lbItemName.getText())){
                inventory = i;
            }
        }
        if(inventory.getIdInventory()==null){
            inventory.setIdInventory(GenerateID.genInventory());
            inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
            inventory.setItem(new Shop(idItem));
            inventory.setCurrentlyHave(1);
            inventory.setTimeUsed(0);
            inventoryDao.add(inventory);
        } else {
            inventory.setCurrentlyHave(inventory.getCurrentlyHave()+Integer.parseInt(lbAmount.getText()));
            inventoryDao.update(inventory);
        }
        account.setCoin(account.getCoin()-Integer.parseInt(lbCostTotal.getText()));
        accountDao.update(account);
        ShowAlert.show("Notice","Buy Success");
        initialize();
    }

    @FXML
    void hlGetMoreCoinClicked() {
        LoadForm.load("/fxml/PracticeFXMLs/Dashboard.fxml","Practice",false);
        DisposeForm.Dispose(lbCoin);
    }
    public void initialize() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        lbCoin.setText(account.getCoin()+"");
        disappearRightPane();
        lbMinusCoin.setVisible(false);
        lbMinus.setVisible(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ShopDao shopDao = new ShopDao();
        showAll(shopDao.getAll());
    }

    public void showAll(List<Shop> items) {
        try {
            if (items.size() != 0) {
                vboxItems.getChildren().clear();
                InventoryDao inventoryDao = new InventoryDao();
                Node[] nodes = new Node[items.size()];
                isSelected = new boolean[items.size()];
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
                            nodes[h].setStyle("-fx-background-color: #ffffff");
                        }
                    });
                    Inventory finalInventory = inventory;
                    nodes[i].setOnMousePressed(evt -> {
                        Arrays.fill(isSelected, Boolean.FALSE);
                        isSelected[h] = true;
                        for (Node n : nodes) {
                            n.setStyle("-fx-background-color: #ffffff");
                        }
                        if (isSelected[h]) {
                            nodes[h].setStyle("-fx-background-color: #4498e9;");
                        }
                        appearRightPane();
                        stock = item.getMaxLimit() - finalInventory.getCurrentlyHave();
                        idItem = item.getIdItem();
                        imageView.setImage(new Image(String.valueOf(getClass().getResource(item.getImagePath()))));
                        lbItemName.setText(item.getItemName());
                        lbDetails.setText(item.getDescription());
                        lbTips.setText(item.getTips());
                        lbEffectsBy.setText(item.getEffectsBy());
                        lbLimit.setText(item.getMaxLimit()+"");
                        lbUsedAll.setText(item.getTimeUsed()+"");
                        lbUsedOne.setText(finalInventory.getTimeUsed()+"");
                        lbHave.setText(finalInventory.getCurrentlyHave()+"");
                        lbCostEach.setText(item.getCost()+"");
                        lbAmount.setText("0");
                        lbCostTotal.setText("0");
                        lbMinusCoin.setText("0");
                        lbMinus.setVisible(false);
                        lbMinusCoin.setVisible(false);
                        if(Integer.parseInt(lbCoin.getText())<Integer.parseInt(lbCostEach.getText())){
                            notEnoughCoin();
                        } else {
                            enoughCoin();
                        }
                    });
                    vboxItems.getChildren().add(nodes[i]);
                }
            } else {
                vboxItems.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disappearRightPane() {
        ObservableList<Node> contents = rightPane.getChildren();
        contents.forEach(e -> e.setVisible(false));

    }

    public void appearRightPane() {
        ObservableList<Node> contents = rightPane.getChildren();
        contents.forEach(e -> e.setVisible(true));
    }
    public void notEnoughCoin(){
        btnMinus.setDisable(true);
        btnPlus.setDisable(true);
        btnPurchase.setDisable(true);
        btnReset.setDisable(true);
        lbEnough.setVisible(true);
    }
    public void enoughCoin(){
        btnMinus.setDisable(false);
        btnPlus.setDisable(false);
        btnPurchase.setDisable(false);
        btnReset.setDisable(false);
        lbEnough.setVisible(false);
    }
}
