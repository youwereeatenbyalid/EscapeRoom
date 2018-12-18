package com.company;

/*
The Exit class is used to enable the player traversing from location to location in a logical manner.
 */
public class Exit {
    /*
    Direction is an enum that holds each possible direction
    And the two names that can be used to refer to it.
     */
    enum Direction{
        UNDEFINED("UNDEFINED",null),
        NORTH("NORTH","N"),
        SOUTH("SOUTH","S"),
        EAST("EAST","E"),
        WEST("WEST","W"),
        UP("UP","U"),
        DOWN("DOWN","D"),
        NORTHEAST("NORTHEAST","NE"),
        NORTHWEST("NORTHWEST", "NW"),
        SOUTHEAST("SOUTHEAST", "SE"),
        SOUTHWEST("SOUTHWEST","S"),
        IN("IN","I"),
        OUT("OUT","O");

        private final String longName;
        private final String shortName;

        Direction(String longName, String shortName){
            this.longName = longName;
            this.shortName = shortName;
        }


    }

    //The direction of the exit
    private Direction exitDirection;

    //Where the exit leads.
    private Location leadsTo;

    /*
    Constructor
     */
    public Exit(){
        exitDirection = Direction.UNDEFINED;
        leadsTo = null;

    }
    public Exit(Direction dir, Location leadsTo){
        exitDirection = dir;
        this.leadsTo = leadsTo;
    }
    /*
    Methods
     */

    @Override
    public String toString() {
        return exitDirection.toString();
    }


    //getter and setter for exit destination.

    public Location getLeadsTo() {
        return leadsTo;
    }

    public void setLeadsTo(Location leadsTo) {
        this.leadsTo = leadsTo;
    }

    //getter and setter for exit direction.
    public Direction getExitDirection() {
        return exitDirection;
    }

    public void setExitDirection(Direction exitDirection) {
        this.exitDirection = exitDirection;
    }

    //Checks if the string contains the long name or short name of the exit direction.
    public boolean exitMatch(String test){
        return (test.contains(exitDirection.longName)
                || test.contains(" "+exitDirection.shortName+" ")
                || test.endsWith(" "+exitDirection.shortName));
    }
}
