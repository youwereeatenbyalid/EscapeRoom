package com.company;

public class DoorTrigger extends MasterTrigger {
    Location location;
    Exit exit;
    enum DoorTriggerTypes {REMOVE,ADD}
    private DoorTriggerTypes currentType = null;

    public DoorTrigger(Item lock, Location location, DoorTriggerTypes type, Exit exit){
        super(lock);
        currentType = type;
        this.location =location;
        this.exit = exit;
    }


    public void triggerResult(){
        super.triggerResult();
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
