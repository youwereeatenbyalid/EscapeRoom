package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Vector;

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

    //creates an empty location to store the players items.
    Location inventory;

    //might use this for something at some point?
    Panel gamePanel;

    //the string used to hold the players text input
    String command;

    //a string used to isolate the players action and feed it back in some response messages
    public String textFriendlyAction;

    //A text field that simulates the command line interface
    CommandLine commandInput;

    //The main output for everything the player is told. Need to get it to scroll somehow.
    JTextArea displayOutput;

    /*enumerator for each of the actions that can be taken in the game. built with a String array to hold all
    Possible typed verbs (or as many as seem pertinent)
     */
    enum Action{
        NULL(null),
        LOOK(new String[]{"LOOK","VIEW"}),
        EXAMINE(new String[]{"EXAMINE", "INSPECT"}),
        MOVE(new String[]{"MOVE","GO","WALK","HEAD"}),
        ADD(new String[]{"PICK UP","TAKE","GRAB"}),
        DROP(new String[]{"DROP","PLACE","EXAMINE"});
        private String[] verbArray;
        Action(String[] actionArray){
            this.verbArray = actionArray;
        }
    }
    public Game(){
        //Setting up the Jframe
        setTitle("Alone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setForeground(Color.black);
        setSize(1080,720);

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
                displayOutput.append(command+"\n");
                //runs the text interpreter on the command
                interpreter(command);
            }
        });
        //initializing the game output
        displayOutput = new JTextArea();

        //set the text to wrap and to not be editable by the player.
        displayOutput.setEditable(false);
        displayOutput.setLineWrap(true);


        //gamePanel = new Panel();

        //adds the display and command line interface into the JFrame
        add(displayOutput, BorderLayout.CENTER);
        add(commandInput,BorderLayout.SOUTH);


        //create the inventory for the player
        inventory = new Location("Inventory","Player Inventory");

        /*
        This is the actuall setup for the specifics of the game.
        Everything below here can be replaced to create a custom game.
        Work on creating an easy input?
         */

        //create the rooms
        Location l1 = new Location("Room one","description for room one");
        Location l2 = new Location("Room two","description for room two");
        Location l3 = new Location("Room three","description for room three");
        Location l4 = new Location("Room four","description for room four");
        //add items to the rooms
        l1.addItem(new Item(new String[]{"Test Item one","Item one"},"Item description one"));
        l1.addItem(new Item(new String[]{"Test Object one","Object one"},"Object description one",false));

        l2.addItem(new Item(new String[]{"Test Item two","Item two"},"Item description two"));
        l2.addItem(new Item(new String[]{"Test Object two","Object two"},"Object description two",false));

        l3.addItem(new Item(new String[]{"Test Item three","Item three"},"Item description three"));
        l3.addItem(new Item(new String[]{"Test Object three","Object three"},"Object description three",false));

        l4.addItem(new Item(new String[]{"Test Item four","Item four"},"Item description four"));
        l4.addItem(new Item(new String[]{"Test Object four","Object four"},"Object description four",false));

        //add exits linking the rooms together.
        l1.addExit(new Exit(Exit.Direction.EAST, l2));
        l1.addExit(new Exit(Exit.Direction.SOUTH,l4));

        l2.addExit(new Exit(Exit.Direction.WEST,l1 ));
        l2.addExit(new Exit(Exit.Direction.SOUTH,l3 ));

        l3.addExit(new Exit(Exit.Direction.WEST,l4));
        l3.addExit(new Exit(Exit.Direction.NORTH,l2));

        l4.addExit(new Exit(Exit.Direction.EAST,l3));
        l4.addExit(new Exit(Exit.Direction.NORTH,l1));

        //set the players current location
        currentLocation = l1;

        //call showLocation to load the into text for the first room without the player having to move there.
        showLocation();

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
            String[] actionParse = e.verbArray;
            //skips the null action prevent it from being assigned prematurely
            if(actionParse != null) {
                //for each value in the verb array
                for (int i = 0; i < e.verbArray.length; i++) {
                    //searches the players command for that particular verb
                    if (command.contains(e.verbArray[i])) {
                        //sets textFriendlyAction to that verb so it can be used later
                        textFriendlyAction = e.verbArray[i].toLowerCase();
                        //returns the action
                        return e;
                    }
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
        //Creates an iterator out of the items vector
        // which is used to progress through each item in the location being searched
        for (Object o : location.getItems()) {
            Item an_item = (Item) o;
            //checks if the the item and the command match
            if (an_item.itemMatch(command))
                //returns the item if it is.
                return an_item;
        }
        //if an item isn't found, it returns a null to indicate no item was found.
        return null;
    }

    /*
    Show Location displays the room title and, if it's the player's the first time visiting, the room description.
     */

    private  void showLocation(){
        //displays room title
        displayOutput.append("\n" + currentLocation.getLocationTitle() +"\n");
        //checks if it's the first time visiting.
        if(currentLocation.isFirstTime()) {
            //if it is, it will display the room description, then set firstTime to false.
            displayOutput.append("\n" + currentLocation.getLocationDescription() + "\n");
            currentLocation.setFirstTime(false);
        }
    }

    /*
    move is used to check if the player specified a valid direction to move in.
    If the player specified a valid location, it moves the player there.
    Otherwise, it tells the player that they couldn't move there.
     */
    private void move(String command){
        //Creates an iterator out of the exits vector
        // which is used to progress through each exit in the location being searched
        for (Object exit : currentLocation.getExits()) {
            Exit an_exit = (Exit) exit;
            //checks if the command and the exit match
            if (an_exit.exitMatch(command)) {
                //if they do, moves them through that exit to the new location, and shows the player that location.
                currentLocation = an_exit.getLeadsTo();
                showLocation();
                return;

            }
        }
            //otherwise, it tells the player they couldn't move.
                displayOutput.append("Could not move that way!\n");
    }

    /*
    add takes the item the player specified in their command and tries to add it to the player's inventory.
     */
    private void add(Item currentItem) {
        //Checks to see if the item can be moved and that the item is not already in the player's inventory.
        if (currentItem.isMoveable() && !currentItem.isInInventory()) {
            //if it isn't, it sets the item to show it now is in the player's inventory
            currentItem.setInInventory(true);
            //adds the item to the player's inventory
            inventory.addItem(currentItem);
            //and removes it from the room the player is in.
            currentLocation.removeItem(currentItem);
            //it then informs the player that the item has been added to their inventory.
            displayOutput.append("Added " + currentItem + " to your inventory.\n");

        } else if (currentItem.isInInventory()) {
            //if the item is already in the players inventory, they'll be informed of that.
            displayOutput.append("The " + currentItem + " is already in your inventory.\n");

        } else if (!currentItem.isMoveable()) {
            //if the item cannot be be moved, it tells the player that.
            displayOutput.append("The " + currentItem + " cannot be moved.\n");

        }
    }

    /*
    drop takes the item the player specified in their command and tries to remove it from the player's inventory.
     */
    private void drop(Item currentItem){
            //checks if the item in question is in their inventory.
            if (currentItem.isInInventory())
            {
                //if so the item is set to show that is no longer the case
                currentItem.setInInventory(false);
                //adds the item to the current location
                currentLocation.addItem(currentItem);
                //removes it from the players inventory
                inventory.removeItem(currentItem);
                //informs the player of their success
                displayOutput.append("You " +textFriendlyAction +" the "+ currentItem + " on the floor.\n");
            }
            //if the item isn't in the inventory, snarkily informs the player of this
            else{
                displayOutput.append("You can't " +textFriendlyAction + " what you haven't taken.");
                //if the item can't be moved, it gives them a heads up on that.
                if(!currentItem.isMoveable()){
                    displayOutput.append("Also, you can't take that anyway.\n");
                }
            }
    }

    /*
    The text interpreter, AKA the actual game. Interprets the player's commands and responds to tell the story
    of the game.
     */
    private void interpreter(String command){
        //set the players to command to upper case to avoid Case Sensitive Errors.
        String interpreterCommand = command.toUpperCase();
        //parses the command for any actions to take
        Action currentAction = actionParse(interpreterCommand);
        //parses the command for any items the player has in their inventory.
        Item currentItem = itemParse(interpreterCommand,inventory);
        //if nothing is found, it will parse the current location for those items.
        if(currentItem == null){
            currentItem = itemParse(interpreterCommand,currentLocation);
        }
        //sets up a series of cases for each possible action the player can take
        switch (currentAction){

            //Null: when no action is found, the interpreter informs the player of this.
            case NULL:
                displayOutput.append("I'm sorry, I didn't understand.\n");
                break;

            //Move: calls the move method to move the player.
            case MOVE:
                    move(interpreterCommand);
                break;

            //Look: used to get the description of the current location.
            case LOOK:

                displayOutput.append("\n"+currentLocation.getLocationDescription()+"\n");
                break;

            //Examine: Used to get the description of the specified item.
            case EXAMINE:
                if(currentItem != null)
                    displayOutput.append("\n"+currentItem.getItemDescription()+"\n");
                else
                    //if there is no item, it requests that the player specify an object to examine.
                    displayOutput.append("Specify object to examine.\n");
                break;

            //Add: adds an item to the player's inventory.
            case ADD:
                if(currentItem != null)
                    add(currentItem);
                else
                    displayOutput.append("Please specify item to "+textFriendlyAction+".\n");
                break;

            //Drop: drops an item from the player's inventory.
            case DROP:
                if(currentItem != null )
                    drop(currentItem);
                else{
                    displayOutput.append("Please specify item to "+textFriendlyAction+".\n");
                }
            }
    }

    public static void main(String[] args) {
	new Game();
    }
}
