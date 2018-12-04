package com.company;

import java.util.*;

public class Location {
    private String locationTitle;
    private String locationDescription;
    private Vector exits;
    private Vector triggers;
    public Location(){
        locationTitle = new String();
        locationDescription = new String();
        exits = new Vector();
        triggers = new Vector();
    }

    public Location(String title){
        locationTitle = title;
        locationDescription = new String();
        exits = new Vector();

    }

    public Location(String title, String description){
        locationTitle = title;
        locationDescription = description;
        exits = new Vector();

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

}
