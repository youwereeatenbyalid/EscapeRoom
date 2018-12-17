package com.company;

enum Action{
    NULL(null),
    EXAMINE(new String[]{"LOOK","VIEW","EXAMINE", "INSPECT"}),
    MOVE(new String[]{"MOVE","GO","WALK","HEAD"}),
    ADD(new String[]{"PICK UP","TAKE","GRAB"}),
    DROP(new String[]{"DROP","PLACE","EXAMINE"}),
    CUSTOM(null);
    private String[] verbArray;
    Action(String[] actionArray){
        this.verbArray = actionArray;
    }
    public String[] getVerbArray(){
        return verbArray;
    }
    public void setVerbArray(String[] verbs){
        this.verbArray = verbs;
    }
    public String actionParse(String command){
        for (int i = 0; i < verbArray.length; i++) {
            //searches the players command for that particular verb
            if (command.contains(verbArray[i])) {
                //sets textFriendlyAction to that verb so it can be used later
                return verbArray[i].toLowerCase();
                //returns the action
            }
        }
        return null;
    }
}