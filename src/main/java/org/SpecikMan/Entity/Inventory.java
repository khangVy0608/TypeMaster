package org.SpecikMan.Entity;

public class Inventory extends Account{
    private String idInventory;
    private Shop item;
    private int currentlyHave;

    public String getIdInventory() {
        return idInventory;
    }

    public void setIdInventory(String idInventory) {
        this.idInventory = idInventory;
    }

    public Shop getItem() {
        return item;
    }

    public void setItem(Shop item) {
        this.item = item;
    }

    public int getCurrentlyHave() {
        return currentlyHave;
    }

    public void setCurrentlyHave(int currentlyHave) {
        this.currentlyHave = currentlyHave;
    }

    public Inventory(String idAccount, String username, String idInventory, Shop item, int currentlyHave) {
        super(idAccount, username);
        this.idInventory = idInventory;
        this.item = item;
        this.currentlyHave = currentlyHave;
    }

    public Inventory() {
    }
}
