package com.company;

import java.util.*;

public class Location {
    private String locationTitle;
    private String locationDescription;
    private Vector exits;
    private Vector items;
    private Vector triggers;
    private boolean firstTime;
    public Location(){
        exits = new Vector();
        items = new Vector();
        triggers = new Vector();
    }

    public Location(String title){
        locationTitle = title;
        exits = new Vector();
        items = new Vector();
        firstTime = true;

    }

    public Location(String title, String description){
        locationTitle = title;
        locationDescription = description;
        exits = new Vector();
        items = new Vector();
        firstTime = true;

    }

    @Override
    public String toString() {
        return locationTitle;
    }


    public String getLocationTitle() {
        return locationTitle;
    }
    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationTitle(String title) {
        locationTitle = title;
    }
    public void setLocationDescription(String description) {
        locationDescription = description;
    }

    public Vector getExits(){
        return (Vector) exits.clone();
    }

    public void addExit(Exit exit){
        exits.add(exit);
    }
    public void removeExit(Exit exit){
        exits.remove(exit);
    }

    public Vector getItems(){
        return (Vector) items.clone();
    }
    public void addItem(Item item){
        items.add(item);
    }
    public void removeItem(Item item){
        items.remove(item);
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
}
