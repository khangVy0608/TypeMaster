package org.SpecikMan.Entity;

public class Inventory extends Account{
    private String idInventory;
    private Shop item;
    private Integer currentlyHave;
    private Integer timeUsed;

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

    public Integer getCurrentlyHave() {
        return currentlyHave;
    }

    public void setCurrentlyHave(Integer currentlyHave) {
        this.currentlyHave = currentlyHave;
    }

    public Integer getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(Integer timeUsed) {
        this.timeUsed = timeUsed;
    }

    public Inventory(String idAccount, String username, String idInventory, Shop item, Integer currentlyHave, Integer timeUsed) {
        super(idAccount, username);
        this.idInventory = idInventory;
        this.item = item;
        this.currentlyHave = currentlyHave;
        this.timeUsed = timeUsed;
    }

    public Inventory() {
    }
}
