package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
An attempt at creating a text adventure game engine using object oriented principles that could in theory be used
for many games. Game runs using the JFrame setup.
 */
public class Game extends JFrame {
    /*
    Setting up important variables
     */
    //Player's current location, where you "are" in the game.
    private Location currentLocation;

     //The location you're trying to get to. Ends the game when you arrive.
     private Location ending;

    //creates an empty location to store the players items.
    private Location inventory;

    //the string used to hold the players text input
    private String command;

    //a string used to isolate the players action and feed it back in some response messages
    private String textFriendlyAction;

    //A text field that simulates the command line interface
    private JTextField commandInput;

    //A text area that holds all the displayed text in the game.
    private JTextArea displayOutput;


/*
Constructor for the game.
Created with a title, a starting location, an ending location, and an inventory.
 */

    public Game(String title, Location startingLocation, Location endLocation,Location inventory) {
        //Setting up the Jframe

        //sets the title of the game
        setTitle(title);
        //Jframesetup;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setForeground(Color.black);
        setPreferredSize(new Dimension(1080,720));


        //initializing the text area.
        displayOutput = new JTextArea();
        //set the text to wrap and to not be editable by the player.
        displayOutput.setEditable(false);
        displayOutput.setLineWrap(true);

        //adds the text area to a scrollPane.
        JScrollPane scrollOutput = new JScrollPane(displayOutput);
        //set up the Command Line interface
        commandInput = new JTextField();

        //adding an action listener to activate whenever the player hits enter.
        commandInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets the text in the Command Line interface and assigns it to the command string
                command = commandInput.getText();
                //clears the text in the Command Line interface
                commandInput.setText("");
                //mirrors the players commands in the main output so they can remember it.
                outputAppend(command);
                //runs the text interpreter on the command
                interpreter(command);
                //sets the caret to the bottom of the jtext area every time the area is updated.
                //This ensures the scroll panel automatically scrolls to the bottom, while still allowing you
                //To scroll up.
                displayOutput.setCaretPosition(displayOutput.getDocument().getLength());
            }
        });




        //adds the scrollPane and command line interface into the JFrame
        add(scrollOutput, BorderLayout.CENTER);
        add(commandInput,BorderLayout.SOUTH);

        //create the inventory for the player
        this.inventory = inventory;


        //set the players current location
        currentLocation = startingLocation;

        //sets the ending location.
        ending = endLocation;

        //call showLocation to load the into text for the first room without the player having to move there.
        interpreter("look");
        //pack the JFrame and let the player see it.
        pack();
        setVisible(true);
    }

    /*
    Action parse takes the players input and searches for any known verbs in the Action enum.
    If it finds a verb, it returns the action that verb is connected to.
    If it can't, it returns the null action to let the interpreter know that it couldn't find a valid action.
    */


    private Action actionParse(String command){
        //for each unique value in Action,
        for(Action e : Action.values()){
            //copies the verb array for the action
            //skips the null & custom actions to prevent them from being assigned prematurely
            if(e.getVerbArray() != null) {
                String action = e.actionParse(command);
                if(action != null){
                    //also updates the textFriendlyAction to hold the precise verb used for the action,
                    //as it is used in a few failure/success messages.
                    textFriendlyAction = action;
                    return e;
                }
            }
        }
        //if a verb isn't found, returns the null action.
        return Action.NULL;
    }
    /*
    Item parse searches a location for any items the player may have typed.
     */
    private Item itemParse(String command, Location location) {

        for (Object o : location.getItems()) {
            Item an_item = (Item) o;
            //checks if the the item and the command match
            if (an_item.itemMatch(command))
                //returns the item if it is.
                return an_item;
        }
        //if an item isn't found, it returns the "framework" item of the location.
        return location.getFrameworkItem();
    }


    /*
    move is used to check if the player specified a valid direction to move in.
    If the player specified a valid location, it moves the player there and returns true.
    Otherwise, it returns false;
     */
    private boolean move(String command){
        //For each loop to progress through the vector of all exits in the current location
        for (Object exit : currentLocation.getExits()) {
            Exit an_exit = (Exit) exit;
            //checks if the command and the exit match
            if (an_exit.exitMatch(command)) {
                //if they do, moves them through that exit to the new location, and shows the player the location title.
                currentLocation = an_exit.getLeadsTo();
                outputAppend(currentLocation.toString());
                //if its the first time visiting the room, it displays a description.
                if(currentLocation.isFirstTime()){
                    interpreter("look");
                    //then sets the room so it is no longer the first time.
                    currentLocation.setFirstTime(false);
                }
                //returns true
                return true;


            }
        }
        //otherwise returns false
            return false;
    }

    /*
    add takes the item the player specified in their command and tries to add it to the player's inventory.
     */
    private void add(Item currentItem) {
        //Checks to see if the item can be moved and that the item is not already in the player's inventory.
        if (currentItem.isMoveable() && currentItem.getItemLocation() == currentLocation) {
            //if it isn't, it sets the item to show it now is in the player's inventory
            //adds the item to the player's inventory

            //and removes it from the room the player is in.
            currentLocation.removeItem(currentItem);
            inventory.addItem(currentItem);
            //it then informs the player that the item has been added to their inventory.
            outputAppend("Added " + currentItem + " to your inventory.");

        } else if (currentItem.getItemLocation()==inventory) {
            //if the item is already in the players inventory, they'll be informed of that.
            outputAppend("The " + currentItem + " is already in your inventory.");

        } else if (!currentItem.isMoveable()) {
            //if the item cannot be be moved, it tells the player that.
            outputAppend("The " + currentItem + " cannot be moved.");

        }
    }

    /*
    drop takes the item the player specified in their command and tries to remove it from the player's inventory.
     */
    private void drop(Item currentItem){
            //checks if the item in question is in their inventory.
            if (currentItem.getItemLocation()==inventory)
            {
                //if so the item is set to show that is no longer the case
                //adds the item to the current location

                //removes it from the players inventory
                inventory.removeItem(currentItem);
                currentLocation.addItem(currentItem);
                //informs the player of their success
                outputAppend("You " +textFriendlyAction +" the "+ currentItem + " on the floor.");
            }
            //if the item isn't in the inventory, snarkily informs the player of this
            else{
                outputAppend("You can't " +textFriendlyAction + " what you haven't taken.");
                //if the item can't be moved, it gives them a heads up on that.
                if(!currentItem.isMoveable()){
                    outputAppend("Also, you can't take that anyway.");
                }
            }
    }

    /*
    Output append is a quick shortcut method used to format the displayed text correctly and append it to the
    text area.
     */
    private void outputAppend(String appendString){
        displayOutput.append("\n"+appendString+"\n");
    }


    /*
    Inventory check runs a trigger check for each item in the players inventory.
     */

    private MasterTrigger inventoryCheck(Item currentObject, Action currentAction, String interpreterCommand){
        MasterTrigger trigger;
        for (Object o : inventory.getItems()) {
            Item an_item = (Item) o;
            trigger = currentObject.checkTriggers(currentAction,an_item,interpreterCommand);
            if (trigger!= null)
                return trigger;
        }
        return null;
    }
    /*
    The text interpreter, AKA the actual game. Interprets the player's commands and responds to tell the story
    of the game.
     */
    private void interpreter(String command){
        MasterTrigger currentTrigger;
        //set the players to command to upper case to avoid Case Sensitive Errors.
        String interpreterCommand = command.toUpperCase();
        //parses the command for any actions to take
        Action currentAction = actionParse(interpreterCommand);
        //parses the command for any items the player has in their inventory.
        Item currentItem = itemParse(interpreterCommand,inventory);
        //parses the command for any objects in the current room
        Item currentObject = itemParse(interpreterCommand,currentLocation);

        //checks to see if the current object has valid triggers
        currentTrigger = currentObject.checkTriggers(currentAction, currentItem,interpreterCommand);
        if(currentTrigger == null)
            //if not, it checks to see if there are valid triggers for the current item
            currentTrigger = currentItem.checkTriggers(currentAction, currentObject,interpreterCommand);
        //if there were no successes but the player didn't specify an item, it will check for triggers on
        //all items in the player inventory.
        if(currentTrigger == null&& currentItem.isFramework())
            currentTrigger = inventoryCheck(currentObject,currentAction,interpreterCommand);


        //if a trigger was found, it sets the current action to the action the trigger specifies.
        //this is used to change the action from NULL to CUSTOM for any custom triggers so
        //error messages are not displayed.
        if(currentTrigger != null)
            currentAction = currentTrigger.getTriggerAction();
        //sets up a series of cases for each possible action the player can take
        switch (currentAction){

            //Null: when no action is found, the interpreter informs the player of this.
            case NULL:
                outputAppend("I don't understand.");
            break;

            //Move: calls the move method to move the player. If the move fails, it informs the player.
            case MOVE:
                    if(!move(interpreterCommand))
                        outputAppend("Could not move that way!");

                break;

            //Examine: Used to get the description of the specified item.
            // If no item is specified, it gets the description of the room.
            case EXAMINE:
                if(!currentItem.isFramework())
                    outputAppend(currentItem.getItemDescription());
                else
                    outputAppend(currentObject.getItemDescription());
                break;

                //Brings up the guide if help is requested.
            case HELP:
                    outputAppend(inventory.getLocationDescription());
                break;

            //Add: adds an item to the player's inventory.
            case ADD:
                //if the current object isn't a framework object, it tries to add it to the inventory.
                if(!currentObject.isFramework())
                    add(currentObject);
                //if the current item isn't a framework object it tries (unsuccessfully) to add it to the inventory.
                else if(!currentItem.isFramework())
                    add(currentItem);
                //otherwise, it requests you specify an item to add.
                else
                    outputAppend("Please specify item to "+textFriendlyAction+".");
                break;

            //Drop: drops an item from the player's inventory.
            //functions similarly to add.
            case DROP:
                if(!currentItem.isFramework())
                    drop(currentItem);
                else if(!currentObject.isFramework())
                    drop(currentObject);
                else{
                    outputAppend("Please specify item to "+textFriendlyAction+".");
                }
            }
            /*
            After actions are taken, any valid triggers are triggered
            If the action was a custom action, it skips right to here.
             */


            if(currentTrigger != null){
                //if there is result text to be displayed, that will be called.
                //this was used due to issues with directly updating the textArea from the trigger class.
                if(currentTrigger.getResult()!=null)
                    outputAppend(currentTrigger.getResult());

                //regardless of the above, it will then trigger the trigger.
                    currentTrigger.triggerResult();

            }

            /*
            Josh's code contribution aside from the textReader and the script.
            Checks for the ending. When the game ends, it displays the ending in a JOPtionPane.
            It then quits out.
            I was planning on working on a restart option, but ran out of time.
             */

        if(currentLocation==ending){
            commandInput.setEditable(false);
                JOptionPane.showMessageDialog(null,ending.getLocationDescription());
                System.exit(0);
            //hit cancel is saem as no so cloes gui.
        }

    }
}


