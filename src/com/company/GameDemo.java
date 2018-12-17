package com.company;

import javax.swing.*;
import java.io.IOException;

public class GameDemo {
    public GameDemo(){

    }

    public static void main(String args[]) throws IOException {
        //create the rooms
        String[] hack = new String[]{"HACK","CUT","CHOP","OPEN","SLICE"};
        String[] bust = new String[]{"BASH","BUST","BREAK","SWING","HIT","FORCE"};

        TextReader descriptionReader = new TextReader();


        Location inv = new Location("Inventory");
        Location bathroom = new Location("Bathroom");
        Location kitchen = new Location("Kitchen");
        Location bedroom = new Location("Bedroom");
        Location foyer = new Location("Foyer");
        Location yard = new Location("Yard");

        //create the items

        //Items in bathroom
        Item pipe = new Item(new String[]{"pipe"});
        Item keycard = new Item(new String[]{"card", "code"});

        //Items in kitchen
        Item knife = new Item(new String[]{"knife","blade"});
        Item paper = new Item(new String[]{"paper","riddle"});
        Item key = new Item(new String[]{"key"});

        //Items in Bedroom
        Item axe = new Item(new String[]{"axe"});
        //create the objects

        //Objects in bathroom
        Item bathroomDoor = new Item(new String[]{"door","lock","handle"},false);
        Item body = new Item(new String[]{"body","corpse","man"},false);

        //Objects in kitchen
        Item kitchenDoor = new Item(new String[]{"door","lock","handle"},false);
        Item pig = new Item(new String[]{"pig","platter"},false);

        //Objects in bedroom
        Item bedroomDoor = new Item(new String[]{"door","lock","handle"},false);
        Item mattress = new Item(new String[]{"bed","mattress"},false);

        //Objects in foyer
        Item foyerDoor = new Item(new String[]{"door","lock","handle"},false);
        Item powerBox = new Item(new String[]{"box","panel","lock"},false);
        //create the triggers

        //Triggers in the bathroom
        Trigger gameStart = new Trigger(inv.getDefaultItem(), bathroom.getDefaultItem(), Action.EXAMINE);
        Trigger cutBody = new Trigger(knife,body,hack);
        Trigger hackBody = new Trigger(axe,body,hack);
        Trigger takePipe = new Trigger(inv.getDefaultItem(),pipe,Action.ADD);
        Trigger bathroomDoorBust = new Trigger(pipe,bathroomDoor,bust);

        //Triggers in the Kitchen
        Trigger enterKitchen = new Trigger(inv.getDefaultItem(),kitchen.getDefaultItem(),Action.EXAMINE);
        Trigger cutPig = new Trigger(knife,pig,hack);
        Trigger takeKnife = new Trigger(inv.getDefaultItem(),knife,Action.ADD);
        Trigger kitchenDoorUnlock = new Trigger(key,kitchenDoor,new String[]{"UNLOCK","OPEN"});

        //Triggers in the Bedroom
        Trigger enterBedroom = new Trigger(inv.getDefaultItem(),bedroom.getDefaultItem(),Action.EXAMINE);
        Trigger cutMattress = new Trigger(knife,mattress,hack);
        Trigger takeAxe = new Trigger(inv.getDefaultItem(),axe,Action.ADD);
        Trigger bedroomDoorBust = new Trigger(axe,bedroomDoor,bust);

        //Triggers in the Foyer
        Trigger enterFoyer = new Trigger(inv.getDefaultItem(),foyer.getDefaultItem(),Action.EXAMINE);
        Trigger unlockBox = new Trigger(keycard,powerBox,new String[]{"ENTER","UNLOCK","USE"});
        //create the subtriggers

        //bathroom subtriggers
        ItemTrigger pipeRemove = new ItemTrigger(pipe, ItemTrigger.ItemTriggerTypes.REMOVE,null);
        ItemTrigger placardAdd = new ItemTrigger(keycard, ItemTrigger.ItemTriggerTypes.ADD,bathroom);
        DoorTrigger bathroomDoorExit = new DoorTrigger(bathroomDoor, bathroom,DoorTrigger.DoorTriggerTypes.ADD,new Exit(Exit.Direction.NORTH,kitchen));


        //kitchen subtriggers
        ItemTrigger keyAdd = new ItemTrigger(key, ItemTrigger.ItemTriggerTypes.ADD,kitchen);
        DoorTrigger kitchenDoorExit = new DoorTrigger(kitchenDoor, kitchen,DoorTrigger.DoorTriggerTypes.ADD, new Exit(Exit.Direction.WEST,bedroom));

        //bedroom subtriggers
        ItemTrigger axeAdd = new ItemTrigger(axe, ItemTrigger.ItemTriggerTypes.ADD,bedroom);
        DoorTrigger bedroomDoorExit = new DoorTrigger(bedroomDoor,bedroom, DoorTrigger.DoorTriggerTypes.ADD, new Exit(Exit.Direction.SOUTH,foyer));

        //foyer subtriggers
        DoorTrigger foyerDoorExit = new DoorTrigger(foyerDoor,foyer, DoorTrigger.DoorTriggerTypes.ADD,new Exit(Exit.Direction.WEST,yard));


        //set up the rooms
        bathroom.setLocationDescription(descriptionReader.description("bathrooom1"));
        kitchen.setLocationDescription(descriptionReader.description("kitchen1"));
        bedroom.setLocationDescription(descriptionReader.description("bedroom1"));
        foyer.setLocationDescription(descriptionReader.description("foyer1"));
        yard.setLocationDescription(descriptionReader.description("ending"));
        //set up the items

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
        powerBox.setItemDescription(descriptionReader.description("powerBox"));
        //set up the triggers

        //set result text (if any)
        bathroomDoorBust.setResult(descriptionReader.description("bathroomDoorBreak"));
        cutBody.setResult(descriptionReader.description("cutBody"));
        hackBody.setResult(descriptionReader.description("hackBody"));

        cutPig.setResult(descriptionReader.description("cutPig"));
        kitchenDoorUnlock.setResult(descriptionReader.description("kitchenDoorUnlock"));

        cutMattress.setResult(descriptionReader.description("cutMattress"));
        bedroomDoorBust.setResult(descriptionReader.description("bedroomDoorBust"));

        unlockBox.setResult(descriptionReader.description("unlockBox"));

        //set update text (if any)
        gameStart.setLockUpdate(descriptionReader.description("bathroom2"));
        takePipe.setLockUpdate(descriptionReader.description("pipe2"));
        cutBody.setLockUpdate(descriptionReader.description("body2"));
        hackBody.setLockUpdate(descriptionReader.description("body2"));
        bathroomDoorBust.setLockUpdate(descriptionReader.description("bathroomDoor2"));

        enterKitchen.setLockUpdate(descriptionReader.description("kitchen2"));
        takeKnife.setLockUpdate(descriptionReader.description("knife2"));
        cutPig.setLockUpdate("pig2");
        kitchenDoorUnlock.setLockUpdate(descriptionReader.description("kitchenDoor2"));

        cutMattress.setLockUpdate(descriptionReader.description("mattress2"));
        takeAxe.setLockUpdate(descriptionReader.description("axe2"));
        bedroomDoorBust.setLockUpdate(descriptionReader.description("bedroomDoor2"));

        enterFoyer.setLockUpdate(descriptionReader.description("foyer2"));
        unlockBox.setLockUpdate("place holder description");





        //set up subtriggers

        //set update text (if any)
        foyerDoorExit.setLockUpdate(descriptionReader.description("foyerDoor2"));

        //add subtriggers to triggers
        bathroomDoorBust.addSubTrigger(pipeRemove);
        bathroomDoorBust.addSubTrigger(bathroomDoorExit);
        cutBody.addSubTrigger(placardAdd);
        hackBody.addSubTrigger(placardAdd);

        cutPig.addSubTrigger(keyAdd);
        kitchenDoorUnlock.addSubTrigger(kitchenDoorExit);

        cutMattress.addSubTrigger(axeAdd);
        bedroomDoorBust.addSubTrigger(bedroomDoorExit);

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

        //add items to rooms
        bathroom.addItem(pipe);

        kitchen.addItem(knife);
        kitchen.addItem(paper);

        //add objects to rooms
        bathroom.addItem(bathroomDoor);
        bathroom.addItem(body);

        kitchen.addItem(kitchenDoor);
        kitchen.addItem(pig);

        bedroom.addItem(bedroomDoor);
        bedroom.addItem(mattress);

        foyer.addItem(powerBox);
        //add triggers to room.
        bathroom.addTrigger(gameStart);
        kitchen.addTrigger(enterKitchen);
        bedroom.addTrigger(enterBedroom);
        foyer.addTrigger(enterFoyer);

        //add exits linking the rooms together.

        kitchen.addExit(new Exit(Exit.Direction.SOUTH,bathroom ));
        bedroom.addExit(new Exit(Exit.Direction.EAST,kitchen));
        foyer.addExit(new Exit(Exit.Direction.NORTH,bedroom));
        new Game("Alone",bathroom,yard,inv);
    }
}
