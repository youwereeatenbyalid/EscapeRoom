package com.company;
/*
Door trigger has simple but important functionality.
It allows triggers to add an remove exits from the game, enabling the fundamental puzzle mechanics to work correctly.
 */
public class DoorTrigger extends MasterTrigger {

    //The location the exit will be added to.
    private Location location;
    //The exit that will be added.
    private Exit exit;
    //Enum that determines if the exit is being added or removed.
    enum DoorTriggerTypes {REMOVE,ADD}
    //The version of the enum this trigger will take.
    private DoorTriggerTypes currentType;

    /*
    Constructor
     */
    //DoorTrigger construction ended up solely being as subtrigger.
    public DoorTrigger(Item lock, Location location, DoorTriggerTypes type, Exit exit){
        super(lock);
        currentType = type;
        this.location =location;
        this.exit = exit;
    }


    /*
    Methods
     */
    //door trigger first runs the MasterTrigger result method
    public void triggerResult(){
        super.triggerResult();
        //and then either adds or removes the exit.
        switch (currentType){
            case REMOVE:
                location.removeExit(exit);
                break;
            case ADD:
                location.addExit(exit);
                break;
        }
    }

}
