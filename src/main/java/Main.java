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
        Thread.sleep(500);

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
        //c1.addLinkedChoice(null);
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
        c1baa.setLinkedDeactivateChoice(c1bab);c1bab.setLinkedDeactivateChoice(c1baa);
        c1baa.setState(FINAL); c1bab.setState(FINAL);
        c1ba.addChoice(c1baa); c1ba.addChoice(c1bab);

        Item fruitBasket3 = new Item("3 Fruit baskets"); Item fruitBasket4 = new Item("4 Fruit baskets");
        c1baa.setGainItem(fruitBasket3); c1bab.setGainItem(fruitBasket4);

        Choice c1aac = new Choice("Hand over 3 fruit baskets"), c1aad = new Choice("Hand over 4 fruit baskets");
        c1baa.addLinkedChoice(c1aac);c1bab.addLinkedChoice(c1aad);
        c1aac.setState(LINKED_FINAL); c1aad.setState(LINKED_FINAL);
        c1aac.setParent(c1aa); c1aad.setParent(c1aa);c1aac.setUseItem(fruitBasket3);c1aad.setUseItem(fruitBasket4);
        c1aac.setCompleteMission(DELIVER_FRUITS); c1aad.setCompleteMission(DELIVER_FRUITS);

        c1aa.addChoice(Arrays.asList(c1aaa, c1aab));
        c1aab.addChoice(Arrays.asList(c1aaba, c1aabb));

        Choice c2a = new Choice("Speak to group of soldiers", "Approaching soldiers..");
        Choice c2aa = new Choice("Ask them how their job is", "Well, not much going on here..");
        Choice c2ab = new Choice("Give them a nasty look", "One of them gets mad and chases you!..");
        Choice c2ac = new Choice("Ask them about the bag you found", "I found this abandoned bag by the gate..");
        c2ac.setParent(c2a);
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

        Choice c2c = new Choice("Inspect abandoned bag near the gate");
        Choice c2ca = new Choice("Put it in inventory");
        c2ca.addLinkedChoice(c2ac);
        c2ca.setGainItem(abandoned_bag);
        Mission ABANDONED_BAG = new Mission("Find the owner of the bag.");
        c2ca.setInitiateMission(ABANDONED_BAG);
        c2aca.setCompleteMission(ABANDONED_BAG);
        c2c.addChoice(Arrays.asList(c2ca));
        c2ca.setState(State.LINKED_FINAL);

        Choice c2b = new Choice("Take the West path into the farm", "The farmer and a man who looks like not from around here are conversing.. the traveller looks weary and exhausted..");
        Choice c2ba = new Choice("Ask the farmer whats going on", "'This traveller looks like he needs some help but he won't explain much.");
        Choice c2bb = new Choice("Inquire the traveller where he is coming from", "'I'm a trader from Ayvan.. attacked by bandits on the way and barely escaped.");
        Choice c2bba = new Choice("Ambushed...? Where were you ambushed?", "In the edge of the forest beyond the road.");
        Choice c2bbaa = new Choice("So you have your belongings still there..?", "I only had a few things and bandits probably made off with it.");
        Choice c2bbb = new Choice("What is your name? Where were you travelling to?", "I'm Malik. I was on a journey to Soqan for trade.");
        Choice c2bc = new Choice("Alright traveller, I'll bring you to the city inn, you can rest there a bit and get back your strength.", "Thank you, I'll be grateful.");
        c2bbb.setState(FINAL);
        Mission HELP_TRAVELLER = new Mission("Take the traveller Malik to the city inn.");
        HELP_TRAVELLER.addUpdate("Visit the traveller sometime later.");
        HELP_TRAVELLER.addUpdate("Find Malik in the city.");
        HELP_TRAVELLER.addUpdate("Meet Malik when he leaves at first light in the morning.");
        HELP_TRAVELLER.addUpdate("Malik has postponed leaving - meet up with him later.");
        Mission INSPECT_AMBUSH = new Mission("Visit the edge of the forest where Malik was attacked.");
        c2bc.setState(LINKED_FINAL); c2bc.setInitiateMission(HELP_TRAVELLER);
        c2bc.setParent(c2b); c2bc.setLinkedDeactivateChoice(c2bb);
        c2bbb.addLinkedChoice(c2bc);

        Choice c1ca = new Choice("Drop off Malik at the inn.", "Here it is. Rest here and I'll be back soon. 'Thank you.'");
        c1ca.setInitiateMission(INSPECT_AMBUSH);

        Choice c2bd = new Choice("Get on the off-path to the edge of Sheba Forest", "The clearing has tracks of boots and some destroyed branches and leaves. There is a blood stain on the ground and some torn pieces belonging to a cloth. You also find a decorative band, with red and yellow stripes.");
        Item DECOR_BAND = new Item("Decorative band with red & yellow stripes");
        c2bd.setGainItem(DECOR_BAND);

        Mission VISIT_MALIK = new Mission("Pay Malik a visit at the inn.");
        c2bd.setCompleteMission(INSPECT_AMBUSH);
        c1ca.addLinkedChoice(c2bd);c2bd.setParent(c2b); c2bd.setState(LINKED_FINAL);
        Choice c1cb = new Choice("Visit Malik at the Inn", "Surprisingly Malik seems to have left, his room is empty.");
        c1cb.setParent(c1c);
        c2bd.addLinkedChoice(c1cb);
        c2bd.setInitiateMission(VISIT_MALIK);
        c1ca.setState(LINKED_FINAL); c1ca.setParent(c1c);

        c1ca.setCompleteMission(HELP_TRAVELLER);
        c2bbb.addLinkedChoice(c1ca);
        c2bb.addChoice(c2bba); c2bb.addChoice(c2bbb);
        c2bbaa.setState(FINAL);
        c2bba.addChoice(c2bbaa);
        c2b.addChoice(Arrays.asList(c2ba, c2bb));

        Choice c1cba = new Choice("Ask the innkeeper where he is", "'A bit ago, another stranger came in and inquired about any out-of-town travellers recently. Malik saw the man, and seemed to be alarmed.. After the not-so-friendly stranger left, Malik also left secretively and asked the innkeeper to not mention him to anyone else.");
        Mission IDENTITY_OF_MALIK = new Mission("Discover the indentity of Malik and find him. Something seems strange about him.");
        c1cba.setState(LINKED_FINAL); c1cba.setInitiateMission(IDENTITY_OF_MALIK);c1cb.addChoice(c1cba);
        Mission VISIT_MALIK_BEFORE_DEPART = new Mission("Visit Malik when he leaves the city at first light.");
        Choice c1da = new Choice("You find Malik at the courtyard, approach towards him.", "He is speaking with the Imam and hands over a small leather pouch to him.");
        Choice c1daa = new Choice("Ask him if he is alright..", "'I'm good, just walked over here for prayers.");
        Choice c1dab = new Choice("Inquire about the pouch he handed over.. ", "On second thoughts you don't mention that you saw it.");
        Choice c1daba = new Choice("Show him the decorative item you fond on the attack site.", "'Its from one of my possesions, got ripped off.' However you are unconvinced as the band seems to be of a women's head gear. Malik departs to get back to the inn for the night." +
                "You decide to spend some time at the courtyard. As you feel the cool water from the fountain on your face and you see people slowly leaving to their homes for the evening. Only a few remain, amongst them the Imam, and a man with distinctive black head gear and rings on his finger, whom you recognize as an Officer of the Sultan's advisors.");
        c1daa.setState(FINAL); c1daba.setState(LINKED_FINAL);
        c1daba.setInitiateMission(VISIT_MALIK_BEFORE_DEPART);
        c1dab.addChoice(c1daba);
        c1da.addChoice(Arrays.asList(c1daa, c1dab));
        c1da.setParent(c1d);
        c1cba.addLinkedChoice(c1da);

        Choice c1db = new Choice("Inquire to the Imam about what Malik entrusted to him.", "The Imam refuses to divulge and says it's not of your concern.");
        c1db.setParent(c1d); c1daba.addLinkedChoice(c1db);
        c1db.setState(FINAL);

        Choice c1dc = new Choice("You get approached by the Officer.", "'I have a task for your, out of the city. Meet me here in the morning, ready to travel for some days.'");
        Mission MEET_OFFICER = new Mission("The sultan's officer Arkut will be waiting for you in the Mosque courtyard next morning");
        c1dc.setState(FINAL);c1dc.setParent(c1d); c1dc.setInitiateMission(MEET_OFFICER);
        c1daba.addLinkedChoice(c1dc);

        Choice c1cd = new Choice("Meet Malik before he departs", "Malik and the Innkeeper seem devastated. There was an attempted burglary last night, and it seems the burglars were looking for somthing or someone. Nothing was actually stolen.");
        Choice c1cda = new Choice("'Innkeeper - do you suspect someone?", "I .. don't. I don't what to say.. they broke in to some guests rooms upstairs and that's when guards were alerted and they escaped.");
        Choice c1cdb = new Choice("Advise Malik not to leave the city, it may not be safe.", "You will meet up with him later again.");
        Mission MEET_MALIK_LATER = new Mission("Malik has postponed his departure - meet up with him later.");
        c1cd.setCompleteMission(VISIT_MALIK_BEFORE_DEPART);
        c1cdb.setInitiateMission(MEET_MALIK_LATER);
        c1cda.setState(FINAL);
        c1cdb.setState(FINAL);

        c1cd.addChoice(Arrays.asList(c1cda, c1cdb));


        ArrayList<Choice> lc1 = new ArrayList<Choice>(Arrays.asList(c1, c2));
        c2.addChoice(Arrays.asList(c2a, c2b, c2c));
        MAIN_MISSION.getStartingChoice().addChoice(lc1);
        MissionManager.getInstance().addQuest(MAIN_MISSION);
        MAIN_MISSION.activate();



    }

}
