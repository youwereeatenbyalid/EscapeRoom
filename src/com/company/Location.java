package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/*
Used to create the 'rooms' the player is in.
Holds a description,
A list of all items in the room
and a list of all the exits in the room.
 */
public class Location {
    //Name of the room.
    private String locationTitle;
    //Room description.
    private String locationDescription;
    //description upon initial discovery, used once then done. May be redundant.
    private String locationFirstTimeDescription;
    //List of all exits
    private Vector exits;
    //List of all items
    private Vector items;

    private Item defaultItem;
    //Check used to see if it's the players first time in the room.
    private boolean firstTime;

    /*
    Constructors
     */
    public Location(){
        exits = new Vector();
        items = new Vector();
        defaultItem = new Item(new String[]{"default"},false);
        defaultItem.setFramework(true);
        addItem(defaultItem);

    }

    public Location(String title){
        locationTitle = title;
        exits = new Vector();
        items = new Vector();
        defaultItem = new Item(new String[]{title},false);
        defaultItem.setFramework(true);
        addItem(defaultItem);
        firstTime = true;

    }

    public Location(String title, String description){
        locationTitle = title;
        locationDescription = description;
        exits = new Vector();
        items = new Vector();
        defaultItem = new Item(new String[]{title},false);
        defaultItem.setFramework(true);
        addItem(defaultItem);
        firstTime = true;

    }


    /*
    Methods
     */

    @Override
    public String toString() {
        return locationTitle;
    }


    public String getLocationTitle() {
        return locationTitle;
    }
    public String getLocationDescription() {
        Item framework = (Item) items.firstElement();
        return framework.getItemDescription();
    }

    public String getLocationFirstTimeDescription() { return locationFirstTimeDescription; }

    public void setLocationTitle(String title) {
        locationTitle = title;
    }
    public void setLocationDescription(String description) {
        Item framework = (Item) items.firstElement();
        framework.setItemDescription(description);
    }
    public void setLocationFirstTimeDescription(String locationFirstTimeDescription) {
        this.locationFirstTimeDescription = locationFirstTimeDescription;
    }


    public Vector getExits(){
        return (Vector) exits.clone();
    }
    public Vector getItems(){
        return (Vector) items.clone();
    }
    public void addItem(Item item){
        item.setItemLocation(this);
        items.add(item);
    }

    public void addExit(Exit exit){
        exits.add(exit);
    }

    public void removeExit(Exit exit){
        exits.remove(exit);
    }

    public void removeItem(Item item){
        item.setItemLocation(null);
        items.remove(item);
    }

    public void addTrigger(MasterTrigger trigger){
        Item framework = (Item) items.firstElement();
        framework.addTrigger(trigger);
    }
    public void removeTrigger(MasterTrigger trigger){
        Item framework = (Item) items.firstElement();
        framework.removeTrigger(trigger);
    }
    public Item getDefaultItem(){
        return defaultItem;
    }

    public boolean isFirstTime() {
        return firstTime;
    }
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }



}
