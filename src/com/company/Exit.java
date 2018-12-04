package com.company;

public class Exit {
    enum Direction{UNDEFINED,NORTH,SOUTH,EAST,WEST,UP,DOWN,
        NORTHEAST,NORTHWEST,SOUTHEAST, SOUTHWEST, IN, OUT}
    String[] shortDirName = {"NULL", "N", "S", "E", "W", "U", "D", "NE", "NW", "SE", "SW", "I", "O"};

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

    public void setExitDirection(Direction exitDirection) {
        this.exitDirection = exitDirection;
    }

    public void setLeadsTo(Location leadsTo) {
        this.leadsTo = leadsTo;
    }
}
