package com.company;

import java.util.Vector;

public abstract class MasterTrigger {
    private Action triggerAction;
    private String[] actionVerbs;
    private Item lock;
    private Item key;
    private Vector subTriggers;
    private String result = null;
    private String lockUpdate = null;
    public MasterTrigger(Item key, Item lock, Action triggerAction){
        this.key = key;
        this.lock = lock;
        this.triggerAction = triggerAction;
        subTriggers = new Vector();
    }

    public MasterTrigger(Item key, Item lock, String[] verbs){
        this.key = key;
        this.lock = lock;
        subTriggers = new Vector();
        triggerAction = Action.CUSTOM;
        actionVerbs=verbs;

    }
    public MasterTrigger(Item lock){
        this.lock = lock;
        key = null;
        triggerAction = null;
        subTriggers = null;
    }

    public Action getTriggerAction(){
        return triggerAction;
    }
    public boolean itemMatch(Item item){
        if(key == item)
            return true;
        else
            return false;
    }
    public boolean actionMatch(Action action){
        if (triggerAction == action)
            return true;
        else
            return false;
    }

    public void triggerResult(){
        if(subTriggers != null) {
            for (Object o : getSubTriggers()) {
                MasterTrigger a_trigger = (MasterTrigger) o;
                a_trigger.triggerResult();
            }
        }
            updateLock();
        lock.removeTrigger(this);
    }



    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }


    public void updateLock(){
        if(lockUpdate!=null)
            lock.setItemDescription(lockUpdate);
    }
    public boolean verbMatch(String verb){
        if(actionVerbs != null) {
            for (int i = 0; i < actionVerbs.length; i++) {
                if (verb.contains(actionVerbs[i]))
                    return true;
            }
        }
        return false;
    }



    public String getLockUpdate() {
        return lockUpdate;
    }

    public void setLockUpdate(String lockUpdate) {
        this.lockUpdate = lockUpdate;
    }

    public Item getLock(){
        return lock;
    }

    public void addSubTrigger(MasterTrigger subTrigger){
        subTriggers.add(subTrigger);
    }
    public void removeSubTrigger(MasterTrigger subTrigger){
        subTriggers.remove(subTrigger);
    }
    public Vector getSubTriggers(){
        return (Vector) subTriggers.clone();
    }

    public String[] getActionVerbs() {
        return actionVerbs;
    }

    public void setActionVerbs(String[] actionVerbs) {
        this.actionVerbs = actionVerbs;
    }
}
