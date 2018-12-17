package com.company;

public class ItemTrigger extends MasterTrigger {
    enum ItemTriggerTypes{NULL,ADD,REMOVE,STUCK,UNSTUCK}
    private ItemTriggerTypes currentType;
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
    public ItemTrigger(Item lock,ItemTriggerTypes type, Location pendingLocation) {
        super(lock);
        this.currentType = type;
        this.pendingLocation = pendingLocation;
    }

    public void triggerResult(){
        super.updateLock();
        super.triggerResult();
        switch (currentType){
            case ADD:
                pendingLocation.addItem(super.getLock());
                break;
            case REMOVE:
                Location tempLocation = super.getLock().getItemLocation();
                        tempLocation.removeItem(super.getLock());
                break;
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
