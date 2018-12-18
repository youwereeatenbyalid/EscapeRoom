package com.company;

/*
Item trigger is an implementation of trigger that edits the property of the item it has been locked to.
While it can detect complex actions and be a trigger in its own right, it was primarily used when making the game
as a subtrigger.
 */
public class ItemTrigger extends MasterTrigger {
    /*
    ItemTriggerTypes is an enum that holds each way the ItemTrigger an alter the item it has been locked to.
    Null does nothing (in case the trigger is just being used as a holder for subtriggers)
    ADD will add the item to a location, enabling the ability to create "hidden" items.
    This was used to make the key, axe and keyCard pop into existence when the appropriate trigger occurred.
    REMOVE will remove the item from its location, chucking it into the void and out of the game.
    This is used to get rid of the pipe after it shatters.
    STUCK and UNSTUCK, while not used in this demo, could theoretically be used to change the status
    of an items ability to move.
    If for example, a player had to slam a wooden bar into place to hold a door open, STUCK could change it so
    the wooden bar was now clamped firmly into place and could not be moved.
    UNSTUCK, in comparison, could be used to create a scenario in which an item is inside a glass case,
    viewable but unreachable. If the player then unlocked the case (Or broke it open),
    the item would become unstuck, and could then be taken by the player.

     */
    enum ItemTriggerTypes{NULL,ADD,REMOVE,STUCK,UNSTUCK}
    //the current type is used to hold the type of action the itemTrigger will take when triggered.
    private ItemTriggerTypes currentType;
    /*
    Pending location is the location the item will be moved to when the trigger is activate for the ADD and REMOVE case.
    I put some thought into setting it up so that either the key or the lock could be used to derive the location that
    the item would be moved to, but decided against it. In the end, pendingLocation means an item can be added to a
    location completely unrelated to where the trigger was activated,
    meaning itemTriggers can act in much more powerful ways.
     */

    /*
    Constructors.
    Most of these didn't end up being used here with the exception of the subtrigger constructor.
    Still, it's nice to have the option.
     */
    private Location pendingLocation;
    public ItemTrigger (Item key, Item lock, Action triggerAction, ItemTriggerTypes type, Location pendingLocation) {
        super(key, lock,triggerAction);
        this.currentType = type;
        this.pendingLocation = pendingLocation;
    }
    public ItemTrigger (Item key, Item lock, String[] verbs, ItemTriggerTypes type, Location pendingLocation) {
        super(key, lock, verbs);
        this.currentType = type;
        this.pendingLocation = pendingLocation;
    }
    public ItemTrigger(Item key, Item lock, Action triggerAction){
        super(key,lock,triggerAction);
        currentType = ItemTriggerTypes.NULL;
        pendingLocation = null;
    }
    //subTrigger constructor
    public ItemTrigger(Item lock,ItemTriggerTypes type, Location pendingLocation) {
        super(lock);
        this.currentType = type;
        this.pendingLocation = pendingLocation;
    }

    /*
    Methods
     */
    //As with all triggers, it will run the commands assigned to the MasterTrigger  triggerResult method,
    //Then run its ItemTrigger commands.
    public void triggerResult(){
        super.updateLock();
        super.triggerResult();
        switch (currentType){
            //adds an item to a location using the locations addItem method.
            case ADD:
                pendingLocation.addItem(super.getLock());
                break;
            //removes an item from a location using the locations removeItem method.
            //it must first acquire the current location of the lock, as it may have changed at some point.
            case REMOVE:
                pendingLocation = super.getLock().getItemLocation();
                pendingLocation.removeItem(super.getLock());
                break;

            //STUCK and UNSTUCK just change the moveable boolean of the lock to false and true respectively.
            case STUCK:
                getLock().setMovable(false);
                break;
            case UNSTUCK:
                getLock().setMovable(true);
                break;
            case NULL:
                break;
        }

    }

}
