package com.company;

import java.util.Vector;

public class Trigger extends MasterTrigger {


    public Trigger(Item key, Item lock, Action triggerAction){
        super(key, lock, triggerAction);

    }
    public Trigger(Item key, Item lock, String[] verbs){
        super(key, lock, verbs);
    }

}
