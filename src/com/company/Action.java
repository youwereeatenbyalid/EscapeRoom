package com.company;

/*
The action enum holds the list of all default commands used by the game.
Each enum holds a list of strings that can activate the default option.
There are also two empty actions used for no action and custom actions respectively.
Some thought was put into updating the "Custom" Action with a set of verbs,
But I didn't have time to properly implement it,
as it would have meant refactoring large parts of the code.
 */

enum Action{
    NULL(null),
    EXAMINE(new String[]{"LOOK","VIEW","EXAMINE", "INSPECT"}),
    MOVE(new String[]{"MOVE","GO","WALK","HEAD"}),
    ADD(new String[]{"PICK UP","TAKE","GRAB"}),
    DROP(new String[]{"DROP","PLACE","EXAMINE"}),
    HELP(new String[]{"HELP","MAN"}),
    CUSTOM(null);
    private String[] verbArray;
    /*
    Constructor
     */
    Action(String[] actionArray){
        this.verbArray = actionArray;
    }

    /*
    Methods.
     */
    public String[] getVerbArray(){
        return verbArray;
    }
    //Action parse
    public String actionParse(String command){
        for (int i = 0; i < verbArray.length; i++) {
            //searches the players command for that particular verb
            if (command.contains(verbArray[i])) {
                //sets textFriendlyAction to that verb so it can be used later
                return verbArray[i].toLowerCase();
                //returns the action
            }
        }
        //returns a null string if no action was found.
        return null;
    }
}