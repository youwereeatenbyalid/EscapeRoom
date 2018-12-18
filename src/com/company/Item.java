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
    //the description of the item.
    private String itemDescription;

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

    /*
    Constructors
     */
    //Constructor used to specify if the item can be moved.
    Item(String itemNames[], boolean movable){
        triggers = new Vector();
        this.itemNames = itemNames;
        this.itemDescription = "placeholder description";
        this.movable = movable;
        itemLocation = null;

    }
    //Default constructor, used to create standard items
    Item(String itemNames[]){
        triggers = new Vector();
        this.itemNames = itemNames;
        this.itemDescription = "placeholder description";
        movable = true;
        itemLocation = null;
    }
    /*
    Methods
     */
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


    //add a trigger to an item, used in setup.
    public void addTrigger(MasterTrigger trigger){
        triggers.add(trigger);
    }

    //remove a trigger from an item, used to prevent a trigger from being called after it has been used.
    public void removeTrigger(MasterTrigger trigger){
        triggers.remove(trigger);
    }

    //checks to see if the item and verb correspond to the key and action for any triggers on this lock.
    //if they do, it returns that trigger.
    public MasterTrigger checkTriggers(Action action, Item item, String interpreterCommand){
        //for all triggers in this object's trigger list
        for (Object o : triggers){
            MasterTrigger a_trigger = (MasterTrigger) o;
            //check to see if the item matches
                if(a_trigger.itemMatch(item)) {
                    //then check to see if the action matches.
                    if (a_trigger.actionMatch(action))
                        return a_trigger;
                    //if it doesn't, check to see if it matches any custom commands.
                    else if(a_trigger.verbMatch(interpreterCommand))
                        return a_trigger;
                }
        }
        //return null if no valid triggers
        return null;
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

    /*Used to specify if the item in question is a "framework item"
    Framework items are used by locations to hold any triggers that would activate when no item was specified.
     */
    public boolean isFramework() {
        return framework;
    }

    //Sets whether the item is a framework item.
    public void setFramework(boolean framework) {
        this.framework = framework;
    }
}
