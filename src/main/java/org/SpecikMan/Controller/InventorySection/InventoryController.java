package org.SpecikMan.Controller.InventorySection;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.SpecikMan.Controller.ShopSection.ItemController;
import org.SpecikMan.Controller.ShopSection.ShopController;
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

public class InventoryController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnMinus;

    @FXML
    private Button btnMinusCraft;

    @FXML
    private Button btnPlus;

    @FXML
    private Button btnPlusCraft;

    @FXML
    private Button btnPurchaseCraft;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnResetCraft;

    @FXML
    private Button btnSell;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lbAmount;

    @FXML
    private Label lbAmountCraft;

    @FXML
    private Label lbCoin;

    @FXML
    private Label lbCostEach;

    @FXML
    private Label lbCraftAvail;

    @FXML
    private Label lbCurrentlyHave;

    @FXML
    private Label lbDetails;

    @FXML
    private Label lbEffectsBy;

    @FXML
    private Label lbEnough;

    @FXML
    private Label lbHave;

    @FXML
    private Label lbItemName;

    @FXML
    private Label lbLimit;

    @FXML
    private Label lbMinus;

    @FXML
    private Label lbMinusCoin;

    @FXML
    private Label lbSell;

    @FXML
    private Label lbTips;

    @FXML
    private Label lbUsedAll;

    @FXML
    private Label lbUsedOne;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vboxItems;

    private boolean[] isSelected;
    private int have;
    private String idItem;
    private List<String> craft = new ArrayList<>(Arrays.asList("IT1", "IT10", "IT15", "IT16", "IT19", "IT2",
            "IT20", "IT21", "IT22", "IT4", "IT5",
            "IT7", "IT9"));
    private List<String> craftDes = new ArrayList<>(Arrays.asList("IT2", "IT11", "IT16", "IT17", "IT18", "IT3",
            "IT19", "IT22", "IT23", "IT5", "IT6",
            "IT8", "IT10"));

    @FXML
    void btnAllClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0) {
                    items.add(i);
                }
            }
        }
        showAll(items);
    }

    @FXML
    void btnBackClicked() {
        DisposeForm.Dispose(lbCoin);
    }

    @FXML
    void btnComboClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0 && i.getEffectsBy().contains("Combo")) {
                    items.add(i);
                }
            }
        }
        showAll(items);
    }

    @FXML
    void btnCorrectnessClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0 && i.getEffectsBy().contains("Correctness")) {
                    items.add(i);
                }
            }
        }
        showAll(items);
    }

    @FXML
    void btnCraftClicked(MouseEvent event) {
        if (lbAmountCraft.getText().equals("0")) {
            ShowAlert.show("Notice", "Select amount");
        } else {
            InventoryDao inventoryDao = new InventoryDao();
            AccountDao accountDao = new AccountDao();
            Inventory inventory = new Inventory();
            Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            switch (idItem) {
                case "IT1": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(0))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(0)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(0))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT10": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(1))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(1)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(1))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT15": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(2))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(2)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(2))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT16": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(3))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(3)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(3))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT19": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(4))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(4)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(4))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT2": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(5))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(5)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(5))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT20": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(6))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(6)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(6))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT21": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(7))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(7)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(7))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT22": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(8))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(8)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(8))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT4": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(9))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(9)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(9))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT5": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(10))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(10)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(10))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT7": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(11))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(11)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(11))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
                case "IT9": {
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craftDes.get(12))) {
                            inventory = i;
                        }
                    }
                    if (inventory.getIdInventory() == null) {
                        inventory.setIdInventory(GenerateID.genInventory());
                        inventory.setIdAccount(FileRW.Read(FilePath.getLoginAcc()));
                        inventory.setItem(new Shop(craftDes.get(12)));
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventory.setTimeUsed(0);
                        inventoryDao.add(inventory);
                    } else {
                        inventory.setCurrentlyHave(inventory.getCurrentlyHave() + Integer.parseInt(lbAmountCraft.getText()));
                        inventoryDao.update(inventory);
                    }
                    for (Inventory i : inventoryDao.getAll()) {
                        if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getIdItem().equals(craft.get(12))) {
                            inventory = i;
                        }
                    }
                    inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmountCraft.getText())*2);
                    inventoryDao.update(inventory);
                    ShowAlert.show("Notice", "Craft Success");
                    initialize();
                    break;
                }
            }
        }

    }

    @FXML
    void btnHighestClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0) {
                    items.add(i);
                }
            }
        }
        items.sort(Comparator.comparingInt(Shop::getCost).reversed());
        showAll(items);
    }

    @FXML
    void btnLowestClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0) {
                    items.add(i);
                }
            }
        }
        items.sort(Comparator.comparingInt(Shop::getCost));
        showAll(items);
    }

    @FXML
    void btnMinusClicked() {
        if (Integer.parseInt(lbAmount.getText()) > 1) {
            lbAmount.setText(Integer.parseInt(lbAmount.getText()) - 1 + "");
            int cost = Integer.parseInt(lbCostEach.getText()) * 75 / 100;
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText().split("-")[0]) - cost) + "");
            lbSell.setText(lbMinusCoin.getText());
        } else if (Integer.parseInt(lbAmount.getText()) == 1) {
            lbAmount.setText(Integer.parseInt(lbAmount.getText()) - 1 + "");
            int cost = Integer.parseInt(lbCostEach.getText()) * 75 / 100;
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText().split("-")[0]) - cost) + "");
            lbMinusCoin.setVisible(false);
            lbMinus.setVisible(false);
            lbSell.setText(lbMinusCoin.getText());
        } else {
            lbAmount.setText("0");
            lbMinusCoin.setText("0");
            lbSell.setText(lbMinusCoin.getText());
            lbMinusCoin.setVisible(false);
            lbMinus.setVisible(false);
        }
    }

    @FXML
    void btnMostUsedClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0) {
                    items.add(i);
                }
            }
        }
        items.sort(Comparator.comparingInt(Shop::getTimeUsed).reversed());
        showAll(items);
    }

    @FXML
    void btnPlusClicked() {
        if (Integer.parseInt(lbAmount.getText()) < have) {
            lbMinusCoin.setVisible(true);
            lbMinus.setVisible(true);
            lbAmount.setText(Integer.parseInt(lbAmount.getText()) + 1 + "");
            int cost = Integer.parseInt(lbCostEach.getText()) * 75 / 100;
            lbMinusCoin.setText((Integer.parseInt(lbMinusCoin.getText()) + cost + ""));
            lbSell.setText(lbMinusCoin.getText());
        }
    }

    @FXML
    void btnResetClicked() {
        lbAmount.setText("0");
        lbMinusCoin.setText("0");
        lbSell.setText("0");
        lbMinusCoin.setVisible(false);
        lbMinus.setVisible(false);
    }

    @FXML
    void btnResetCraftClicked(MouseEvent event) {
        lbAmountCraft.setText("0");
    }

    @FXML
    void btnSellClicked(MouseEvent event) {
        if (lbAmount.getText().equals("0")) {
            ShowAlert.show("Notice", "Select amount");
        } else {
            InventoryDao inventoryDao = new InventoryDao();
            AccountDao accountDao = new AccountDao();
            Inventory inventory = new Inventory();
            Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
            for (Inventory i : inventoryDao.getAll()) {
                if (i.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc())) && i.getItem().getItemName().equals(lbItemName.getText())) {
                    inventory = i;
                }
            }
            inventory.setCurrentlyHave(inventory.getCurrentlyHave() - Integer.parseInt(lbAmount.getText()));
            inventoryDao.update(inventory);
            account.setCoin(account.getCoin() + Integer.parseInt(lbSell.getText()));
            accountDao.update(account);
            ShowAlert.show("Notice", "Sell Success");
            initialize();
        }
    }

    @FXML
    void btnSpeedClicked(MouseEvent event) {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0 && i.getEffectsBy().contains("Speed")) {
                    items.add(i);
                }
            }
        }
        showAll(items);
    }

    @FXML
    void btnTimeClicked() {
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0 && i.getEffectsBy().contains("Time")) {
                    items.add(i);
                }
            }
        }
        showAll(items);
    }

    @FXML
    void hlGetMoreCoinClicked() {
        LoadForm.load("/fxml/PracticeFXMLs/Dashboard.fxml", "Practice", false);
        DisposeForm.Dispose(lbCoin);
    }

    @FXML
    void btnMinusCraftClicked() {
        if (Integer.parseInt(lbAmountCraft.getText()) >= 1) {
            lbAmountCraft.setText(Integer.parseInt(lbAmountCraft.getText()) - 1 + "");
        } else {
            lbAmountCraft.setText("0");
        }
    }

    @FXML
    void btnPlusCraftClicked() {
        if (Integer.parseInt(lbAmountCraft.getText()) < Integer.parseInt(lbCraftAvail.getText())) {
            lbAmountCraft.setText(Integer.parseInt(lbAmountCraft.getText()) + 1 + "");
        }
    }

    public void initialize() {
        AccountDao accountDao = new AccountDao();
        Account account = accountDao.get(FileRW.Read(FilePath.getLoginAcc()));
        lbCoin.setText(account.getCoin() + "");
        disappearRightPane();
        lbMinusCoin.setVisible(false);
        lbMinus.setVisible(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ShopDao shopDao = new ShopDao();
        List<Shop> items = new ArrayList<>();
        InventoryDao inventoryDao = new InventoryDao();
        for (Shop i : shopDao.getAll()) {
            for (Inventory j : inventoryDao.getAll()) {
                if (i.getIdItem().equals(j.getItem().getIdItem()) && j.getIdAccount().equals(FileRW.Read(FilePath.getLoginAcc()))
                        && j.getCurrentlyHave() > 0) {
                    items.add(i);
                }
            }
        }
        showAll(items);
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
                    if (inventory.getCurrentlyHave() == null) {
                        inventory.setCurrentlyHave(0);
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
                        have = finalInventory.getCurrentlyHave();
                        idItem = item.getIdItem();
                        imageView.setImage(new Image(String.valueOf(getClass().getResource(item.getImagePath()))));
                        lbItemName.setText(item.getItemName());
                        lbDetails.setText(item.getDescription());
                        lbTips.setText(item.getTips());
                        lbEffectsBy.setText(item.getEffectsBy());
                        lbLimit.setText(item.getMaxLimit() + "");
                        lbUsedAll.setText(item.getTimeUsed() + "");
                        lbUsedOne.setText(finalInventory.getTimeUsed() + "");
                        lbHave.setText(finalInventory.getCurrentlyHave() + "");
                        lbCostEach.setText(item.getCost() + "");
                        lbAmount.setText("0");
                        lbSell.setText("0");
                        lbMinusCoin.setText("0");
                        lbMinus.setVisible(false);
                        lbMinusCoin.setVisible(false);
                        if (craft.contains(item.getIdItem())) {
                            ObservableList<Node> contents = anchorPane.getChildren();
                            contents.forEach(e -> e.setVisible(true));
                            lbCurrentlyHave.setText(finalInventory.getCurrentlyHave() + "");
                            lbCraftAvail.setText((finalInventory.getCurrentlyHave() / 2) + "");
                            lbAmountCraft.setText("0");
                        } else {
                            ObservableList<Node> contents = anchorPane.getChildren();
                            contents.forEach(e -> e.setVisible(false));
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

}
