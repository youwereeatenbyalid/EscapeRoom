package com.company;

import java.util.*;
/*
Used to create the 'locations' the player is in.
Holds a description,
A list of all items in the location
and a list of all the exits in the location.
 */
public class Location {
    //Name of the location.
    private String locationTitle;

    //List of all exits
    private Vector exits;

    //List of all items
    private Vector items;

    //the framework item
    private Item frameworkItem;
    //Check used to see if it's the players first time in the location.
    private boolean firstTime;

    /*
    Constructor
     */

    public Location(String title){
        locationTitle = title;
        exits = new Vector();
        items = new Vector();
        //creates a "framework" item with the locations title and adds it to Items Vector.
        frameworkItem = new Item(new String[]{title},false);
        frameworkItem.setFramework(true);
        addItem(frameworkItem);
        firstTime = true;

    }


    /*
    Methods
     */

    //toString method used for debugging and to get the title
    @Override
    public String toString() {
        return locationTitle;
    }


    //Used to set the "location's" description.
    //Actually assigns it to the framework item.

    public void setLocationDescription(String description) {
        Item framework = (Item) items.firstElement();
        framework.setItemDescription(description);
    }
    //Used to get the "location's" description.
    //Actually retrieves the description of the framework item.

    public String getLocationDescription() {
        Item framework = (Item) items.firstElement();
        return framework.getItemDescription();
    }


    //Unused title setter. Might be helpful in other projects.
    public void setLocationTitle(String title) {
        locationTitle = title;
    }

    //returns a clone of the exits Vector.
    public Vector getExits(){
        return (Vector) exits.clone();
    }

    //returns a clone of the items Vector.
    public Vector getItems(){
        return (Vector) items.clone();
    }

    //adds an item to the location.
    //done by setting the items current location to this location
    //then adds the item to the items vector.
    public void addItem(Item item){
        item.setItemLocation(this);
        items.add(item);
    }

    //removes an item from this location in a similar fashion the addItem method.
    public void removeItem(Item item){
        item.setItemLocation(null);
        items.remove(item);
    }
    //adds an exit to the exits Vector.
    public void addExit(Exit exit){
        exits.add(exit);
    }

    //removes a vector from the exits Vector.
    public void removeExit(Exit exit){
        exits.remove(exit);
    }

    //Allows a trigger to be added to the location
    //by adding it to the framework item
    public void addTrigger(MasterTrigger trigger){
        Item framework = (Item) items.firstElement();
        framework.addTrigger(trigger);
    }

    //removes the trigger in a similar way if that was ever needed.
    public void removeTrigger(MasterTrigger trigger){
        Item framework = (Item) items.firstElement();
        framework.removeTrigger(trigger);
    }

    //retrieves the framework item.
    public Item getFrameworkItem(){
        return frameworkItem;
    }

    //getter and setter for the boolean that determines if it is the first time visiting this location.
    public boolean isFirstTime() {
        return firstTime;
    }
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }



}
