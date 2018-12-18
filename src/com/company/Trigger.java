package com.company;

import java.util.Vector;
/*
This was going to be more complex, but ended up just being a non-abstract reimplementation of the
Master Trigger class. This ended up being used as the "check" trigger, i.e. the thing that would
check to see if an action had occured, and would then update the "locks" description and activate any subtriggers.
Meanwhile, the DoorTrigger and ItemTrigger classes were primarily used as
subtriggers that were only activated by a Trigger's result method, but would do all of the complicated actions
that changed the game world.
 */
public class Trigger extends MasterTrigger {


    public Trigger(Item key, Item lock, Action triggerAction){
        super(key, lock, triggerAction);

    }
    public Trigger(Item key, Item lock, String[] verbs){
        super(key, lock, verbs);
    }

}
