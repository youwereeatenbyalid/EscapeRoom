package com.company;

public class Item {
    private String itemNames[];
    private String itemDescription;
    private boolean movable;
    private boolean inInventory;

    Item(String itemNames[], String itemDescription, boolean movable){
        this.itemNames = itemNames;
        this.itemDescription = itemDescription;
        this.movable = movable;
        this.inInventory = false;
    }

    Item(String itemNames[], String itemDescription){
        this.itemNames = itemNames;
        this.itemDescription = itemDescription;
        movable = true;
        this.inInventory = false;
    }
    @Override
    public String toString(){
        return this.itemNames[0];
    }

    public String getItemName() {
        return itemNames[0];
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isMoveable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean itemMatch(String test){
        for(int i = 0; i<itemNames.length; i++) {
            if (test.contains(itemNames[i].toUpperCase()))
                return true;
        }
            return false;
    }

    public boolean isInInventory() {
        return inInventory;
    }

    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }
}
