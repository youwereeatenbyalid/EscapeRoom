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
    //Player's current location
     Location currentLocation;

     Location ending;

    //creates an empty location to store the players items.
    Location inventory;

    Panel gamePanel;

    //the string used to hold the players text input
    String command;

    //a string used to isolate the players action and feed it back in some response messages
    public String textFriendlyAction;

    //A text field that simulates the command line interface
    CommandLine commandInput;

    //The main output for everything the player is told. Need to get it to scroll somehow.
    JTextArea displayOutput;

    JScrollPane scrollOutput;

    /*enumerator for each of the actions that can be taken in the game. built with a String array to hold all
    Possible typed verbs (or as many as seem pertinent)
     */

    public Game(String title, Location startinglocation, Location endLocation,Location inventory) {
        //Setting up the Jframe
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setForeground(Color.black);
        setPreferredSize(new Dimension(1080,720));

        //set up the Command Line interface
        commandInput = new CommandLine();

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

                displayOutput.setCaretPosition(displayOutput.getDocument().getLength());


            }
        });
        //initializing the game output
        displayOutput = new JTextArea();
        scrollOutput = new JScrollPane(displayOutput);

        //set the text to wrap and to not be editable by the player.
        displayOutput.setEditable(false);
        displayOutput.setLineWrap(true);


        //gamePanel = new Panel();

        //adds the display and command line interface into the JFrame
        add(scrollOutput, BorderLayout.CENTER);
        add(commandInput,BorderLayout.SOUTH);

        //create the inventory for the player
        this.inventory = inventory;
        /*
        This is the actual setup for the specifics of the game.
        Everything below here can be replaced to create a custom game.
        Work on creating an easy input?
         */



        //set the players current location
        currentLocation = startinglocation;
        ending = endLocation;

        //call showLocation to load the into text for the first room without the player having to move there.
        interpreter("look");
        //pack the JFrame and let the player see it.
        pack();
        setVisible(true);
    }

    /*
    Action parse takes the players input and searches for any known verbs.
    If it finds a verb, it returns the action that verb is connected to.
    If it can't, it returns the null action to let the interpreter know that it couldn't find a valid action.
     */


    private Action actionParse(String command){
        //for each unique value in Action,
        for(Action e : Action.values()){
            //copies the verb array for the action
            //skips the null action prevent it from being assigned prematurely
            if(e.getVerbArray() != null) {
                String action = e.actionParse(command);
                if(action != null){
                    textFriendlyAction = action;
                    return e;
                }
            }
        }
        //if a verb isn't found, returns the null action.
        return Action.NULL;
    }
    /*
    Item parse takes the players input and searches for any items in either the players inventory or the room.
    If it finds an item in either, it returns the item.
     */
    private Item itemParse(String command, Location location) {

        for (Object o : location.getItems()) {
            Item an_item = (Item) o;
            //checks if the the item and the command match
            if (an_item.itemMatch(command))
                //returns the item if it is.
                return an_item;
        }
        //if an item isn't found, it returns a null to indicate no item was found.
        return location.getDefaultItem();
    }


    /*
    move is used to check if the player specified a valid direction to move in.
    If the player specified a valid location, it moves the player there.
    Otherwise, it tells the player that they couldn't move there.
     */
    private boolean move(String command){
        boolean moveSuccess = false;
        //Creates an iterator out of the exits vector
        // which is used to progress through each exit in the location being searched
        for (Object exit : currentLocation.getExits()) {
            Exit an_exit = (Exit) exit;
            //checks if the command and the exit match
            if (an_exit.exitMatch(command)) {
                //if they do, moves them through that exit to the new location, and shows the player that location.
                currentLocation = an_exit.getLeadsTo();
                outputAppend(currentLocation.getLocationTitle());
                moveSuccess = true;
                break;


            }
        }
            return moveSuccess;
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

    public void outputAppend(String appendString){
        displayOutput.append("\n"+appendString+"\n");
    }



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
        MasterTrigger currentTrigger = null;
        //set the players to command to upper case to avoid Case Sensitive Errors.
        String interpreterCommand = command.toUpperCase();
        //parses the command for any actions to take
        Action currentAction = actionParse(interpreterCommand);
        //parses the command for any items the player has in their inventory.
        Item currentItem = itemParse(interpreterCommand,inventory);
        //if nothing is found, it will parse the current location for those items.
        Item currentObject = itemParse(interpreterCommand,currentLocation);

        MasterTrigger success = currentObject.checkTriggers(currentAction, currentItem,interpreterCommand);
        if(success == null)
            success = currentItem.checkTriggers(currentAction, currentObject,interpreterCommand);
        if(success == null&& currentItem.isFramework())
            success = inventoryCheck(currentObject,currentAction,interpreterCommand);


        if(success != null)
            currentAction = success.getTriggerAction();
        //sets up a series of cases for each possible action the player can take
        switch (currentAction){

            //Null: when no action is found, the interpreter informs the player of this.
            case NULL:
                outputAppend("I don't understand.");
            break;

            //Move: calls the move method to move the player.
            case MOVE:
                    if(move(interpreterCommand)&& currentLocation.isFirstTime())
                            interpreter("look");
                    else
                        outputAppend("Could not move that way!");

                break;

            //Examine: Used to get the description of the specified item. If no item is specified, it gets the description of the room.
            case EXAMINE:
                if(!currentItem.isFramework())
                    outputAppend(currentItem.getItemDescription());
                else
                    outputAppend(currentObject.getItemDescription());
                    //if there is no item, it requests that the player specify an object to examine.
                break;

            //Add: adds an item to the player's inventory.
            case ADD:
                if(!currentObject.isFramework())
                    add(currentObject);
                else if(!currentItem.isFramework())
                    add(currentItem);
                else
                    outputAppend("Please specify item to "+textFriendlyAction+".");
                break;

            //Drop: drops an item from the player's inventory.
            case DROP:
                if(!currentItem.isFramework())
                    drop(currentItem);
                else if(!currentObject.isFramework())
                    drop(currentObject);
                else{
                    outputAppend("Please specify item to "+textFriendlyAction+".");
                }
            }
            if(success != null){
                if(success.getResult()!=null)

                    outputAppend(success.getResult());
                    success.triggerResult();

            }
            if(currentLocation==ending){
                commandInput.setEditable(false);
                int playAgain= JOptionPane.showConfirmDialog(null,ending.getLocationDescription());
                    //end here somehow?
            }

            //Check for Triggers on Item mentioned

            //check for triggers on Object mentioned

    }
}


