package com.company;

import java.io.IOException;
/*
When this project started out, it was originally going to be a simple Text-Based Adventure.
However, as some of the key points of object oriented programming are modularity and re-usability,
We instead decided to make a game engine that would enable the quick and easy creation of text based games.
The script for this particular adventure was written by Josh, while I worked on implementation of the Classes
that make up the game engine.

This GameDemo class is currently setup to run "Alone," a horror-themed escape-the-room game.
It draws inspiration from a bunch of spooky video-games, with resident evil being a major source.
The final room of this game, the foyer, is a homage to the first room in the mansion from Resident Evil 1,
though it's seen a lot of use in many games since then. If you google Resident Evil mansion and check images,
you'll quickly see what We're talking about.

This is the largest java project I've worked on, and I learned a lot about GUI, Inheritance, abstraction
& enumerated types while making it. While I'm not entirely satisfied with the final scope of the project,
I'm glad it came out as well as it did.

I hope you enjoy playing!

-Luca Negroponte
 */
public class GameDemo {
    public GameDemo(){

    }

    public static void main(String args[]) throws IOException {
        //The two strings here are used for specific custom actions that are reused throughout certain triggers.
        String[] hack = new String[]{"HACK","CUT","CHOP","OPEN","SLICE"};
        String[] bust = new String[]{"BASH","BUST","BREAK","SWING","HIT","FORCE"};

        //Sets up the text reader to read files into certain strings.

        /*
        Originally, the plan was to have a more advanced text reader that could create the entire game from plaintext,
        but we ran out of time.
         */
        TextReader descriptionReader = new TextReader();

        /*
        With the text reader set up, the first step of game creation is to
        create all the locations that will be in the game.
         */

        /*
        Inventory: This isn't really a location per-se, more an area to hold all the items the player has on-hand.
        It's framework item is also used for any triggers where no item is needed to set off the trigger.
         */
        Location inv = new Location("Inventory");

        //Bathroom. First location in the game. Where the player Starts.
        Location bathroom = new Location("Bathroom");
        //The Kitchen
        Location kitchen = new Location("Kitchen");
        //The Bedroom
        Location bedroom = new Location("Bedroom");
        //The Foyer.
        Location foyer = new Location("Foyer");
        //The Yard. This is the location the game ends, and thus isn't really fleshed out like the other locations.
        Location yard = new Location("Yard");

        /*
        The next step of creation is to create all the items that will be in the game.
        Organized by the locations they will end up in.
         */

        //Items in bathroom

        //The pipe. Used to break the bathroom door down.
        Item pipe = new Item(new String[]{"pipe"});

        //The keycard. Used to unlock the door in the last location.
        //While the keycard isn't in the bathroom at the start, I create it here as that is where it will go
        //When the trigger for it is activated.
        Item keycard = new Item(new String[]{"card", "code"});

        //Items in kitchen

        //The knife. Used to cut open the pig and retrieve the key, and cut open the mattress and retrieve the axe.
        //Can be used to cut up something else too...
        Item knife = new Item(new String[]{"knife","blade"});
        //A riddle. Josh wrote this. I wrote the snarky comment about it.
        Item paper = new Item(new String[]{"paper","riddle"});
        //The key used to unlock the kitchen door. Like the card, I create it here for the sake of clarity.
        Item key = new Item(new String[]{"key"});

        //Items in Bedroom

        //The axe is used to break down the bedroom door. Created here for the same reasons as the card and the key.
        Item axe = new Item(new String[]{"axe"});

        //Items in the Foyer.

        //In this case, no items ended up being added to the foyer.

        /*
        Object creation. While the class being used are items, Objects here refer to any items that can't
        be moved from the location they are in. These are things like furniture, doors, etc.
         */

        //Objects in bathroom
        //The bathroom door. Needs to be broken to escape the first location.
        Item bathroomDoor = new Item(new String[]{"door","lock","handle"},false);
        //The body. Poor sap.
        Item body = new Item(new String[]{"body","corpse","man"},false);

        //Objects in kitchen

        //Kitchen door. Needs to be unlocked.
        Item kitchenDoor = new Item(new String[]{"door","lock","handle"},false);
        //The pig. A horror classic.
        Item pig = new Item(new String[]{"pig","platter"},false);

        //Objects in bedroom

        //Bedroom door. Don't ask me why it doesn't have a handle, because I don't know.
        Item bedroomDoor = new Item(new String[]{"door","lock","handle"},false);

        //Mattress. You ever see those mattresses on the side of the road filled with bed-bugs? Gross.
        Item mattress = new Item(new String[]{"bed","mattress"},false);

        //Objects in foyer

        //The foyer Doors. Electrically locked.
        Item foyerDoor = new Item(new String[]{"door","lock","handle"},false);

        //The power box. KeyCards puzzles, while not strictly resident evil, are a video-game past time, so we
        //opted to include them here.
        Item powerBox = new Item(new String[]{"box","panel","lock"},false);

        //While the stairs mentioned in the foyer descriptor make for nice scenery dressing, we realized that
        //People might try to climb them, so we included them here to tell people that they couldn't.
        Item stairs = new Item(new String[]{"stairs"},false);
        //create the triggers

        /*
        Trigger creation.
        Any event in the game that involves progressing in any way is created here.
         */
        //Triggers in the bathroom

        //This is a trigger used to change the bathrooms description after it is viewed the first time.
        //It's called game start because the game starts by automatically reading the description text for the
        //Location you start in. We Actually add the intro text as the description for that location, then use this
        //Trigger to update the location when the description is read, so that it now has the locations normal description.
        Trigger gameStart = new Trigger(inv.getFrameworkItem(), bathroom.getFrameworkItem(), Action.EXAMINE);

        //This was my contribution to the story. The body was originally just supposed to have a piece of paper
        //with the code to exit on it, but due to the game confusing that and the riddle paper in the kitchen,
        //I decided that this macabre solution was more fun.
        //There are actually two triggers here: One if you cut the body open with the knife,
        //and one if you hack it open with the axe.
        Trigger cutBody = new Trigger(knife,body,hack);
        Trigger hackBody = new Trigger(axe,body,hack);
        //This is used to change the pipes description upon picking it up.
        Trigger takePipe = new Trigger(inv.getFrameworkItem(),pipe,Action.ADD);
        //The first puzzle: Take the pipe, break the door down with it.
        Trigger bathroomDoorBust = new Trigger(pipe,bathroomDoor,bust);

        //Triggers in the Kitchen

        //Most locations have a bit of first time dialogue to read when you enter,
        // meaning they have to be updated with their normal description afterwords.
        Trigger enterKitchen = new Trigger(inv.getFrameworkItem(),kitchen.getFrameworkItem(),Action.EXAMINE);
        //The solution to the second puzzle. Cut the pig open with the knife.
        Trigger cutPig = new Trigger(knife,pig,hack);
        //Updates the description of the knife when you pick it up, much like the pipe.
        Trigger takeKnife = new Trigger(inv.getFrameworkItem(),knife,Action.ADD);

        //Once you get the key, this is used to check for unlocking the kitchen door.
        Trigger kitchenDoorUnlock = new Trigger(key,kitchenDoor,new String[]{"UNLOCK","OPEN"});

        //Triggers in the Bedroom

        //First time description.
        Trigger enterBedroom = new Trigger(inv.getFrameworkItem(),bedroom.getFrameworkItem(),Action.EXAMINE);

        //Cut the mattress open.
        Trigger cutMattress = new Trigger(knife,mattress,hack);
        //Upate the axe text.
        Trigger takeAxe = new Trigger(inv.getFrameworkItem(),axe,Action.ADD);
        //Break the bedroom door down.
        Trigger bedroomDoorBust = new Trigger(axe,bedroomDoor,bust);

        //Triggers in the Foyer

        //First time description.
        Trigger enterFoyer = new Trigger(inv.getFrameworkItem(),foyer.getFrameworkItem(),Action.EXAMINE);

        //Check to see if you unlocked the box with the keycard.
        Trigger unlockBox = new Trigger(keycard,powerBox,new String[]{"ENTER","UNLOCK","USE"});


        //create the subtriggers

        /*
        SubTriggers ended up being used for most of the actions that updated parts of the game, as can be seen below.
         */

        //bathroom subtriggers

        //When you break the bathroom door down, the pipe shatters, and is removed from the game.
        ItemTrigger pipeRemove = new ItemTrigger(pipe, ItemTrigger.ItemTriggerTypes.REMOVE,null);

        //The grisly final solution. Cutting or hacking the body open will reveal the keyCard needed to leave.
        ItemTrigger keycardAdd = new ItemTrigger(keycard, ItemTrigger.ItemTriggerTypes.ADD,bathroom);
        //Upon breaking the door down, this trigger is the one that adds the exit to the kitchen and allows you to procede.
        DoorTrigger bathroomDoorExit = new DoorTrigger(bathroomDoor, bathroom,DoorTrigger.DoorTriggerTypes.ADD,new Exit(Exit.Direction.NORTH,kitchen));


        //kitchen subtriggers

        //Add the key when you cut the pig open and "pull" the key out. This combined with the riddle end up being the hint for the last puzzle.
        //This paticular trigger is of note because it adds the key straight to your inventory, meaning that you "automatically" pick it up.
        ItemTrigger keyAdd = new ItemTrigger(key, ItemTrigger.ItemTriggerTypes.ADD,inv);

        //Adds the exit to the bedroom when you unlock the kitchen door.
        DoorTrigger kitchenDoorExit = new DoorTrigger(kitchenDoor, kitchen,DoorTrigger.DoorTriggerTypes.ADD, new Exit(Exit.Direction.WEST,bedroom));

        //bedroom subtriggers

        //Adds the axe to the location when you cut open the mattress and "reveal" it.
        ItemTrigger axeAdd = new ItemTrigger(axe, ItemTrigger.ItemTriggerTypes.ADD,bedroom);

        //Adds the exit to the foyer when you break down the door.
        DoorTrigger bedroomDoorExit = new DoorTrigger(bedroomDoor,bedroom, DoorTrigger.DoorTriggerTypes.ADD, new Exit(Exit.Direction.SOUTH,foyer));

        //foyer subtriggers

        //Final part, adds the exit to the yard and the end of the game when you unlock the box.
        DoorTrigger foyerDoorExit = new DoorTrigger(foyerDoor,foyer, DoorTrigger.DoorTriggerTypes.ADD,new Exit(Exit.Direction.WEST,yard));


        //set up the locations

        /*
        This just reads in the first time descriptions to the description for each location.
         */
        inv.setLocationDescription(descriptionReader.description("guide"));

        bathroom.setLocationDescription(descriptionReader.description("bathrooom1"));
        kitchen.setLocationDescription(descriptionReader.description("kitchen1"));
        bedroom.setLocationDescription(descriptionReader.description("bedroom1"));
        foyer.setLocationDescription(descriptionReader.description("foyer1"));
        yard.setLocationDescription(descriptionReader.description("ending"));
        //set up the items

        /*
        Likewise, this sets the description for all the items.
         */
        //set up bathroom items
        pipe.setItemDescription(descriptionReader.description("pipe1"));
        keycard.setItemDescription("a keyCard. Was it worth it?");

        //set up kitchen items
        knife.setItemDescription(descriptionReader.description("knife1"));
        key.setItemDescription(descriptionReader.description("key1"));
        paper.setItemDescription(descriptionReader.description("paper1"));

        //set up bedroom items
        axe.setItemDescription(descriptionReader.description("axe1"));

        //set up the foyer items



        //set up the objects
        //Again, object setup is grouped separately for my own peace of mind.


        //set up the bathroom objects
        bathroomDoor.setItemDescription(descriptionReader.description("bathroomDoor1"));
        body.setItemDescription(descriptionReader.description("body1"));

        //set up the kitchen objects
        kitchenDoor.setItemDescription(descriptionReader.description("kitchenDoor1"));
        pig.setItemDescription(descriptionReader.description("pig1"));

        //set up bedroom objects
        bedroomDoor.setItemDescription(descriptionReader.description("bedroomDoor1"));
        mattress.setItemDescription(descriptionReader.description("mattress1"));

        //set up foyer objects
        foyerDoor.setItemDescription(descriptionReader.description("foyerDoor1"));
        powerBox.setItemDescription(descriptionReader.description("powerBox1"));
        stairs.setItemDescription(descriptionReader.description("stairs"));

        //set up the triggers
        //Triggers have two parts that need to be set;
        //The text to be displayed when the trigger is activated
        //And the text used to update the item the trigger was assigned to.

        //set result text (if any)

        //Bathroom triggers
        bathroomDoorBust.setResult(descriptionReader.description("bathroomDoorBreak"));
        cutBody.setResult(descriptionReader.description("cutBody"));
        hackBody.setResult(descriptionReader.description("hackBody"));

        //Kitchen triggers
        cutPig.setResult(descriptionReader.description("cutPig"));
        kitchenDoorUnlock.setResult(descriptionReader.description("kitchenDoorUnlock"));

        //Bedroom triggers
        cutMattress.setResult(descriptionReader.description("cutMattress"));
        bedroomDoorBust.setResult(descriptionReader.description("bedroomDoorBust"));

        //Foyer triggers
        unlockBox.setResult(descriptionReader.description("unlockBox"));

        //set update text (if any)

        //all the triggers here update the item they were locked to with alternate descriptions.
        //some of these update the framework for the location, causing the location description to change.

        //Bathroom triggers
        gameStart.setLockUpdate(descriptionReader.description("bathroom2"));
        takePipe.setLockUpdate(descriptionReader.description("pipe2"));
        cutBody.setLockUpdate(descriptionReader.description("body2"));
        hackBody.setLockUpdate(descriptionReader.description("body2"));
        bathroomDoorBust.setLockUpdate(descriptionReader.description("bathroomDoor2"));

        //kitchen triggers
        enterKitchen.setLockUpdate(descriptionReader.description("kitchen2"));
        takeKnife.setLockUpdate(descriptionReader.description("knife2"));
        cutPig.setLockUpdate(descriptionReader.description("pig2"));
        kitchenDoorUnlock.setLockUpdate(descriptionReader.description("kitchenDoor2"));

        //bedroom triggers
        cutMattress.setLockUpdate(descriptionReader.description("mattress2"));
        takeAxe.setLockUpdate(descriptionReader.description("axe2"));
        bedroomDoorBust.setLockUpdate(descriptionReader.description("bedroomDoor2"));

        //foyer triggers
        enterFoyer.setLockUpdate(descriptionReader.description("foyer2"));
        unlockBox.setLockUpdate("powerBox2");


        //set up subtriggers
        //most of the subtriggers don't update any actual text.
        // The only one that does is the one for the foyer doors, as the main trigger is locked to the power box.

        //set update text (if any)
        foyerDoorExit.setLockUpdate(descriptionReader.description("foyerDoor2"));



        /*
        With this, the game is more or less created. All that remains is to package everything up and get run the game.
         */



        //add subtriggers to triggers
        //here, we assign every single subtrigger to the trigger that it should be set off by.
        //breaking the bathroom door does two things, remove the pipe and add the exit.
        bathroomDoorBust.addSubTrigger(pipeRemove);
        bathroomDoorBust.addSubTrigger(bathroomDoorExit);

        //I think this may allow the keycard to be added twice, but I decided it wasn't worth worrying about.
        cutBody.addSubTrigger(keycardAdd);
        hackBody.addSubTrigger(keycardAdd);

        //this causes the key to be added to the kitchen when the pig is cut open.
        cutPig.addSubTrigger(keyAdd);
        //this causes the exit to the bedroom to be added when you unlock the door.
        kitchenDoorUnlock.addSubTrigger(kitchenDoorExit);

        //this adds the axe when you cut open the mattress.
        cutMattress.addSubTrigger(axeAdd);

        //this adds an exit to the foyer when you break the bedroom door.
        bedroomDoorBust.addSubTrigger(bedroomDoorExit);

        //this adds an exit to the yard when you unlock the powerbox with the keycard.
        unlockBox.addSubTrigger(foyerDoorExit);


        //add triggers to items
        pipe.addTrigger(takePipe);
        knife.addTrigger(takeKnife);
        axe.addTrigger(takeAxe);


        //add triggers to objects
        bathroomDoor.addTrigger(bathroomDoorBust);
        body.addTrigger(cutBody);
        body.addTrigger(hackBody);

        kitchenDoor.addTrigger(kitchenDoorUnlock);
        pig.addTrigger(cutPig);

        bedroomDoor.addTrigger(bedroomDoorBust);
        mattress.addTrigger(cutMattress);

        powerBox.addTrigger(unlockBox);

        //add items to locations
        bathroom.addItem(pipe);
        //note that we are not adding the items that are added by triggers or subtriggers.
        kitchen.addItem(knife);
        kitchen.addItem(paper);

        //add objects to locations
        bathroom.addItem(bathroomDoor);
        bathroom.addItem(body);

        kitchen.addItem(kitchenDoor);
        kitchen.addItem(pig);

        bedroom.addItem(bedroomDoor);
        bedroom.addItem(mattress);

        foyer.addItem(powerBox);
        foyer.addItem(foyerDoor);
        foyer.addItem(stairs);


        //add triggers to locations.
        //Again, this is done by adding the trigger to the framework item for each location.
        bathroom.addTrigger(gameStart);
        kitchen.addTrigger(enterKitchen);
        bedroom.addTrigger(enterBedroom);
        foyer.addTrigger(enterFoyer);

        //add exits linking the locations together.
        //These are the exits that lead backwards to each location.
        //They get to exist from the beginning because you can't access them without adding the corresponding exit.

        kitchen.addExit(new Exit(Exit.Direction.SOUTH,bathroom ));
        bedroom.addExit(new Exit(Exit.Direction.EAST,kitchen));
        foyer.addExit(new Exit(Exit.Direction.NORTH,bedroom));

        //Finally, the demo initiates an instance of the game, with the appropriate start location and end location.
        //The inventory is also added to allow for triggers to work in the Game class correctly,
        //though it gives the added bonus of being able to specify items the player starts with.
        //And with that, we are done! I had a blast working on this project, and I hope you enjoy it,
        //or at least appreciate the effort put into it.
        new Game("Alone",bathroom,yard,inv);
    }
}
