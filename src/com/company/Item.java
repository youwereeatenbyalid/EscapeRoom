package com.company;

import java.util.Vector;
/*
The item class is used to create all the things the player interacts with in the enviornment
that are not Locations or exits.
Divided into two types:
Items, which can be added to the player inventory, and
Objects, which can not.
This is not a hard difference, an item can become an object and vice versa, but it's a logical distinction used
For the purpose of clarity.
 */
public class Item {
    //A list of all names the player can use to refer to the item
    private String itemNames[];
    //the description of the item in the room it's located in
    private String itemDescription;
    //the description of the item if/when the player picks it up
    private String inventoryDescription;
    //value to check if the item can be added/dropped from the inventory
    private boolean movable;
    //indicates this item acts as framework for a location
    private boolean framework = false;
    //the items current location
    private Location itemLocation;
    //a vector of all triggers assigned to the item.
    //To clarify, these are all triggers that can be triggered BY the item, not every trigger
    //that can effect the item.
    private Vector triggers;

    //Constructor used to specify if the item can be moved.
    Item(String itemNames[], boolean movable){
        triggers = new Vector();
        this.itemNames = itemNames;
        this.itemDescription = "placeholder description";
        this.inventoryDescription = itemDescription;
        this.movable = movable;
        itemLocation = null;

    }
    //Default constructor, used to create standard items
    Item(String itemNames[]){
        triggers = new Vector();
        this.itemNames = itemNames;
        this.itemDescription = "placeholder description";
        this.inventoryDescription = itemDescription;
        movable = true;
        itemLocation = null;
    }
    //Sets the item to read as its first name for debugging clarity.
    @Override
    public String toString(){
        return this.itemNames[0];
    }
    //Get the name of the item if it's ever needed.
    public String getItemName() {
        return itemNames[0];
    }
    //get the item description when it needs to be displayed.
    public String getItemDescription() {
        return itemDescription;
    }
    //sets the item description, always used in setup to replace the placeholder text.
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    //checks if the item can be moved.
    public boolean isMoveable() {
        return movable;
    }

    //sets whether the item can be moved.
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    //check to see if any of the item names are mentioned in a string, Typically a user command.
    public boolean itemMatch(String test){
        for(int i = 0; i<itemNames.length; i++) {
            if (test.contains(itemNames[i].toUpperCase()))
                return true;
        }
            return false;
    }


    //return a clone of all triggers assigned to the item. Might be used at some point.
    public Vector getTriggers(){
        return (Vector) triggers.clone();
    }

    //add a trigger to an item, used in setup.
    public void addTrigger(MasterTrigger trigger){
        triggers.add(trigger);
    }

    //remove a trigger from an item, used to prevent a trigger from being called after it has been used.
    public void removeTrigger(MasterTrigger trigger){
        triggers.remove(trigger);
    }

    //checks to see if the item and verb correspond to the key and action for any triggers on this lock.


    public MasterTrigger checkTriggers(Action action, Item item, String interpreterCommand){
        for (Object o : triggers){
            MasterTrigger a_trigger = (MasterTrigger) o;
                if(a_trigger.itemMatch(item)) {
                    if (a_trigger.actionMatch(action))
                        return a_trigger;
                    else if(a_trigger.verbMatch(interpreterCommand))
                        return a_trigger;
                }
        }
        return null;
    }

    //use to check triggers that don't have a key?

    //return the description used when the item is in the player inventory.
    public String getInventoryDescription() {
        return inventoryDescription;
    }

    //set the description used when the item is in it's normal position.
    public void setInventoryDescription(String inventoryDescription) {
        this.inventoryDescription = inventoryDescription;
    }

    //get the current location of the item.
    public Location getItemLocation() {
        return itemLocation;
    }

    //set the current location of the item. NOTE: Only sets where the item believes it is, must be used as
    //part of the addItem method in Location to be used effectively.
    public void setItemLocation(Location itemLocation) {
        this.itemLocation = itemLocation;
    }

    public boolean isFramework() {
        return framework;
    }

    public void setFramework(boolean framework) {
        this.framework = framework;
    }
}
