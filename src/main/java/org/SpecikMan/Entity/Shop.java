package org.SpecikMan.Entity;

public class Shop {
    private String idItem;
    private String itemName;
    private String description;
    private int cost;
    private int maxLimit;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }

    public Shop() {
    }

    public Shop(String idItem, String itemName, String description, int cost, int maxLimit) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.description = description;
        this.cost = cost;
        this.maxLimit = maxLimit;
    }
}
