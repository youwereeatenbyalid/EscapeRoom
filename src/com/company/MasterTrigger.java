package com.company;

import java.util.Vector;

/*
Trigger is a complicated series of classes.
Triggers are created with:
 a "lock": The item they are assigned to;
 a "key": The item that indicates the trigger is valid;
 and an action: The action that sets off the trigger
 */
public abstract class MasterTrigger {
    //The action that activates the trigger
    private Action triggerAction;
    //A String array if the action is custom
    private String[] actionVerbs;
    //The "lock," the item the trigger is assigned to.
    private Item lock;
    //The "key," the item the trigger is validated by.
    private Item key;
    /*
    A subtriggers vector.
    Triggers can be attatched on to other triggers allow multiple triggers to be activated by the same event without
    having to run redundant checks.
     */
    private Vector subTriggers;
    //result is a string used to hold any text to be displayed when the trigger activates.
    private String result = null;
    //lockupdate holds text that replaces the locks description when the trigger is triggered.
    //used to update item descriptions to correspond to events happening in the game world.
    private String lockUpdate = null;

    /*
    Constructors
     */
    //Creates a trigger with a "key" item, a "lock item, and a default action,
    //like look or add or something equivalent.
    public MasterTrigger(Item key, Item lock, Action triggerAction){
        this.key = key;
        this.lock = lock;
        this.triggerAction = triggerAction;
        subTriggers = new Vector();
    }

    //creates a trigger using a series of words that correspond to a "Custom" action.
    public MasterTrigger(Item key, Item lock, String[] verbs){
        this.key = key;
        this.lock = lock;
        subTriggers = new Vector();
        triggerAction = Action.CUSTOM;
        actionVerbs=verbs;

    }
    //creates a subTrigger.
    //While not their own class, the subTrigger constructor is used to create
    //subTriggers that are activated by other triggers.
    //these subTriggers don't require a key or an action, as they are only triggered by other triggers.
    //I decided to avoid the headache of subTriggers assigned to subTriggers.
    public MasterTrigger(Item lock){
        this.lock = lock;
        key = null;
        triggerAction = null;
        subTriggers = null;
    }

    /*
    Methods.
     */

    //Returns the item that the trigger "locks"

    public Item getLock(){
        return lock;
    }

    //getter and setter for the result text to be displayed when the action triggers..
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    //getter and setter for the lock update text.
    public String getLockUpdate() {
        return lockUpdate;
    }
    public void setLockUpdate(String lockUpdate) {
        this.lockUpdate = lockUpdate;
    }

    //this method updates the lock's description with update text if it exists.
    //otherwise, it leaves well enough alone.
    public void updateLock(){
        if(lockUpdate!=null)
            lock.setItemDescription(lockUpdate);
    }


    //Gets a clone of the subTriggers vector,
    //and enables adding and removing of triggers from the subTriggers vector.
    //variations of these three methods ended up getting used A LOT over the course of this project.
    public Vector getSubTriggers(){
        return (Vector) subTriggers.clone();
    }

    public void addSubTrigger(MasterTrigger subTrigger){
        subTriggers.add(subTrigger);
    }
    public void removeSubTrigger(MasterTrigger subTrigger){
        subTriggers.remove(subTrigger);
    }


    //Returns the action that sets off the trigger. No getter because that's done in the constructor.
    public Action getTriggerAction(){
        return triggerAction;
    }


    //Getter and setter for the string of verbs that can trigger a custom action
    public String[] getActionVerbs() {
        return actionVerbs;
    }

    public void setActionVerbs(String[] actionVerbs) {
        this.actionVerbs = actionVerbs;
    }


    //Checks if the Item being compared to is the triggers "key."
    public boolean itemMatch(Item item){
        return (key == item);
    }
    //Checks if the action being compared to is the triggers action.
    public boolean actionMatch(Action action){
        return (triggerAction == action);

    }

    //checks to see if the string in question contains one of the verbs that correspond to the triggers custom action.
    public boolean verbMatch(String verb){
        if(actionVerbs != null) {
            for (int i = 0; i < actionVerbs.length; i++) {
                if (verb.contains(actionVerbs[i]))
                    return true;
            }
        }
        return false;
    }
    //activates the trigger properly.
    public void triggerResult(){
        //goes through any and all subTriggers and triggers them.
        if(subTriggers != null) {
            for (Object o : getSubTriggers()) {
                MasterTrigger a_trigger = (MasterTrigger) o;
                a_trigger.triggerResult();
            }
        }
        //updates the locks description text
            updateLock();
        //and then removes the trigger from the lock so it can't be activated again.
        lock.removeTrigger(this);
    }












}
