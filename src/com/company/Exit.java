package com.company;

public class Exit {
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
    private Direction exitDirection;
    private Location leadsTo;

    public Exit(){
        exitDirection = Direction.UNDEFINED;
        leadsTo = null;

    }
    public Exit(Direction dir, Location leadsTo){
        exitDirection = dir;
        this.leadsTo = leadsTo;
    }

    @Override
    public String toString() {
        return exitDirection.toString();
    }

    public Direction getExitDirection() {
        return exitDirection;
    }

    public Location getLeadsTo() {
        return leadsTo;
    }

    public boolean exitMatch(String test){
        if (test.contains(exitDirection.longName) || test.contains(" "+exitDirection.shortName+" ")|| test.endsWith(" "+exitDirection.shortName))
            return true;
        else
            return false;
    }

    public void setExitDirection(Direction exitDirection) {
        this.exitDirection = exitDirection;
    }

    public void setLeadsTo(Location leadsTo) {
        this.leadsTo = leadsTo;
    }
}
