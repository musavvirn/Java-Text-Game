import choice.Choice;
import choice.State;
import graph.City;
import graph.MapManager;
import graph.Village;
import inventory.Item;
import mission.Mission;
import mission.MissionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static choice.State.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Game started.. ");
        Scanner input = new Scanner(System.in);
        Thread.sleep(2000);

        /* initiating Locations */
        MapManager M1 = MapManager.getInstance();

        City C1 = new City("Sasau"), C2 = new City("Rattay"), C3 = new City("Ledetchko");
        Village V1 = new Village("Sasau Farms"), V2 = new Village("South Road Farms"),
                V3 = new Village("Rattay Countryside"), V4 = new Village("Terak Village");

        M1.addNode(C1, Arrays.asList(C2, V1, V2, V3));
        M1.addNode(C3, Arrays.asList(C2, V2, V4));
        M1.addVertex(V1, V2);
        M1.addVertex(V1, V3);

        /* initiating Missions and Choices */
        Mission MAIN_MISSION = new Mission("Main objective");

        Choice c1 = new Choice("Explore the city", "..");
        c1.setLinkedChoice(null);
        Choice c2 = new Choice("Head outside city", "Heading to the east gate..");

        Choice c1a = new Choice("Go to the Market place");
        Choice c1b = new Choice("Head to the Gardens");
        Choice c1c = new Choice("Head to the Inn");
        Choice c1d = new Choice("Visit the Mosque courtyard");

        c1.addChoice(Arrays.asList(c1a, c1b, c1c, c1d));
        Choice c1aa = new Choice("Approach the fruit seller");
        Choice c1ab = new Choice("Give alms to the beggar");
        c1ab.setState(FINAL);
        c1aa.setEndText("The fruit seller barely has any fruits..");
        c1a.addChoice(Arrays.asList(c1aa, c1ab));
        Choice c1aaa = new Choice("Ask him where is all his fruits", "Some folks at the garden don't like him and he can't get his fruits..");
        Choice c1aab = new Choice("Offer to help him with any task", "He asks you to fetch him some fruits from the gardens");
        Choice c1aaba = new Choice("Agree to do it!");
        Choice c1aabb = new Choice("May be later, not now");

        Mission DELIVER_FRUITS = new Mission("Deliver 3 baskets of fruits from the gardens to the seller");
        c1aaba.setState(State.LINKED_FINAL);
        c1aaba.setInitiateMission(DELIVER_FRUITS);

        Choice c1cc = new Choice("Inspect the inn", "Travellers rest at the inn for 2-3 days usually, mostly traders");
        c1c.addChoice(c1cc);

        Choice c1ba = new Choice("Pick up fruits in baskets", "How many baskets to fill..?");
        c1b.addChoice(c1ba);
        Choice c1baa = new Choice("Fill 3 baskets"); Choice c1bab = new Choice("Fill 4 baskets");
        c1baa.setState(FINAL); c1bab.setState(FINAL);
        c1ba.addChoice(c1baa); c1ba.addChoice(c1bab);

        Item fruitBasket3 = new Item("3 Fruit baskets"); Item fruitBasket4 = new Item("4 Fruit baskets");
        c1baa.setGainItem(fruitBasket3); c1bab.setGainItem(fruitBasket4);

        Choice c1aac = new Choice("Hand over 3 fruit baskets"), c1aad = new Choice("Hand over 4 fruit baskets");
        c1baa.setLinkedChoice(c1aac);c1bab.setLinkedChoice(c1aad);
        c1aac.setState(LINKED_FINAL); c1aad.setState(LINKED_FINAL);
        c1aac.setParentChoice(c1aa); c1aad.setParentChoice(c1aa);c1aac.setUseItem(fruitBasket3);c1aad.setUseItem(fruitBasket4);
        c1aac.setCompleteMission(DELIVER_FRUITS); c1aad.setCompleteMission(DELIVER_FRUITS);

        c1aa.addChoice(Arrays.asList(c1aaa, c1aab));
        c1aab.addChoice(Arrays.asList(c1aaba, c1aabb));

        Choice c2a = new Choice("Speak to group of soldiers", "Approaching soldiers..");
        Choice c2aa = new Choice("Ask them how their job is", "Well, not much going on here..");
        Choice c2ab = new Choice("Give them a nasty look", "One of them gets mad and chases you!..");
        Choice c2ac = new Choice("Ask them about the bag you found", "I found this abandoned bag by the gate..");
        c2ac.setParentChoice(c2a);
        Choice c2aca = new Choice("It belongs to one of them, return it gracefully");
        c2aca.setState(State.LINKED_FINAL);
        Item abandoned_bag = new Item("Abandoned bag");
        c2aca.setUseItem(abandoned_bag);

        Choice c2acb = new Choice("It belongs to one of them, ask for a payment to return it");
        c2aca.setLinkedDeactivateChoice(c2acb);
        c2ac.addChoice(Arrays.asList(c2aca, c2acb));
        Choice c2aba = new Choice("Run off to the farm close-by", "Running away..");


        Choice c2abb = new Choice("Surrender and apologize", "I'm sorry, I'll do whatever you ask!..");
        c2abb.setState(FINAL);
        c2a.addChoice(Arrays.asList(c2aa, c2ab));
        c2ab.addChoice(Arrays.asList(c2aba, c2abb));

        Choice c2b = new Choice("Take the West path into the farm", "The farmer and a man who looks like not from around here are conversing.. the traveller looks weary and exhausted..");
        Choice c2ba = new Choice("Ask the farmer whats going on", "'This traveller looks like he needs some help but he won't explain much.");
        Choice c2bb = new Choice("Inquire the traveller where he is coming from", "'I'm a trader from Ayvan.. attacked by bandits on the way and barely escaped.");
        Choice c2bba = new Choice("Ambushed...? Where were you ambushed?", "In the edge of the forest beyond the road.");
        Choice c2bbaa = new Choice("So you have your belongings still there..?", "I only had a few things and bandits probably made off with it.");
        Choice c2bbb = new Choice("What is your name? Where were you travelling to?", "I'm Malik. I was on a journey to Soqan for trade.");
        Choice c2bbba = new Choice("Alright, I'll bring you to the city inn, you can rest there a bit and get back your strength.", "Thank you, I'll be grateful.");
        Mission HELP_TRAVELLER = new Mission("Take the traveller Malik to the city inn.");
        Mission INSPECT_AMBUSH = new Mission("Visit the edge of the forest where Malik was attacked.");
        c2bbba.setState(LINKED_FINAL); c2bbba.setLinkedDeactivateChoice(c2bb);c2bbba.setInitiateMission(HELP_TRAVELLER);
        c2bbb.addChoice(c2bbba);
        Choice c1ca = new Choice("Drop off Malik at the inn.", "Here it is. Rest here and I'll be back soon. 'Thank you.'");
        c1ca.setInitiateMission(INSPECT_AMBUSH);

        Choice c2bc = new Choice("Get on the off-path to the edge of Sheba Forest", "The clearing has tracks of boots and some destroyed branches and leaves. There is a blood stain on the ground and some torn pieces belonging to a cloth. You also find a decorative band, with red and yellow stripes.");

        Mission VISIT_MALIK = new Mission("Pay Malik a visit at the inn.");
        c2bc.setCompleteMission(INSPECT_AMBUSH);
        c1ca.setLinkedChoice(c2bc);c2bc.setParentChoice(c2b); c2bc.setState(LINKED_FINAL);
        Choice c1cb = new Choice("Visit Malik at the Inn", "Surprisingly Malik seems to have left, his room is empty.");
        c1cb.setCompleteMission(VISIT_MALIK); c1cb.setParentChoice(c1c);
        c2bc.setLinkedChoice(c1cb);
        c2bc.setInitiateMission(VISIT_MALIK);
        c1ca.setState(LINKED_FINAL); c1ca.setParentChoice(c1c);c1ca.setCompleteMission(HELP_TRAVELLER);
        c2bbb.setLinkedChoice(c1ca);
        c2bb.addChoice(c2bba); c2bb.addChoice(c2bbb);
        c2bbaa.setState(FINAL);
        c2bba.addChoice(c2bbaa);
        c2b.addChoice(Arrays.asList(c2ba, c2bb));

        Choice c1cba = new Choice("Ask the innkeeper where he is", "'A bit ago, another stranger came in and inquired about any out-of-town travellers recently. Malik saw the man, and seemed to be alarmed.. After the not-so-friendly stranger left, Malik also left secretively and asked the innkeeper to not mention him to anyone else.");
        Mission IDENTITY_OF_MALIK = new Mission("Discover the indentity of Malik and find him. Something seems strange about him.");
        c1cba.setState(LINKED_FINAL); c1cba.setInitiateMission(IDENTITY_OF_MALIK);c1cb.addChoice(c1cba);



        Choice c2c = new Choice("Inspect abandoned bag near the gate");
        Choice c2ca = new Choice("Put it in inventory");
        c2ca.setLinkedChoice(c2ac);
        c2ca.setGainItem(abandoned_bag);
        Mission ABANDONED_BAG = new Mission("Find the owner of the bag.");
        c2ca.setInitiateMission(ABANDONED_BAG);
        c2aca.setCompleteMission(ABANDONED_BAG);
        Choice c2cb = new Choice("Leave it where it was");
        c2c.addChoice(Arrays.asList(c2ca, c2cb));
        c2ca.setState(State.LINKED_FINAL);
        c2ca.setLinkedDeactivateChoice(c2cb);

        ArrayList<Choice> lc1 = new ArrayList<Choice>(Arrays.asList(c1, c2));
        c2.addChoice(Arrays.asList(c2a, c2b, c2c));

        MAIN_MISSION.getStartingChoice().addChoice(lc1);

        MissionManager.getInstance().addQuest(MAIN_MISSION);
        MAIN_MISSION.activate();



    }

}
