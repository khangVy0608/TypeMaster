package org.SpecikMan.Entity;

public class Shop {
    private String idItem;
    private String itemName;
    private String description;
    private int cost;
    private int maxLimit;
    private String imagePath;
    private int timeUsed;
    private String tips;
    private String effectsBy;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getEffectsBy() {
        return effectsBy;
    }

    public void setEffectsBy(String effectsBy) {
        this.effectsBy = effectsBy;
    }

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

    public Shop(String idItem, String itemName, String description, int cost, int maxLimit,String imagePath,int timeUsed,String tips,String effectsBy) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.description = description;
        this.cost = cost;
        this.maxLimit = maxLimit;
        this.imagePath = imagePath;
        this.timeUsed = timeUsed;
        this.tips = tips;
        this.effectsBy = effectsBy;
    }
    public Shop(String idItem){
        this.idItem = idItem;
    }
}
