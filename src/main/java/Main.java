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

import static choice.State.FINAL;
import static choice.State.LINKED_FINAL;

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
        Mission q1 = new Mission("Main objective");

        Choice c1 = new Choice("Explore the city", "..");
        c1.setLinkedChoice(null);
        Choice c2 = new Choice("Head outside city", "Heading to the east gate..");

        Choice c1a = new Choice("Go to the market place");
        Choice c1b = new Choice("Head to the gardens");
        Choice c1c = new Choice("Head to the docks");
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

        Mission m3 = new Mission("Deliver 3 baskets of fruits from the gardens to the seller");
        c1aaba.setState(State.LINKED_FINAL);
        c1aaba.setInitiateMission(m3);

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
        c1aac.setCompleteMission(m3); c1aad.setCompleteMission(m3);

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
        c2ac.addChoice(Arrays.asList(c2aca, c2acb));
        Choice c2aba = new Choice("Run off to the farm close-by", "Running away..");


        Choice c2abb = new Choice("Surrender and apologize", "I'm sorry, I'll do whatever you ask!..");
        c2abb.setState(FINAL);
        c2a.addChoice(Arrays.asList(c2aa, c2ab));
        c2ab.addChoice(Arrays.asList(c2aba, c2abb));

        Choice c2b = new Choice("Take the West path into the farm");
        Choice c2ba = new Choice("Inspect sheep", "Inspecting..");
        c2ba.setState(FINAL);
        c2b.addChoice(Arrays.asList(c2ba));

        Choice c2c = new Choice("Inspect abandoned bag near the gate");
        Choice c2ca = new Choice("Put it in inventory");
        c2ca.setLinkedChoice(c2ac);
        c2ca.setGainItem(abandoned_bag);
        Mission q2 = new Mission("Find the owner of the bag.");
        c2ca.setInitiateMission(q2);
        c2aca.setCompleteMission(q2);
        Choice c2cb = new Choice("Leave it where it was");
        c2c.addChoice(Arrays.asList(c2ca, c2cb));
        c2ca.setState(State.LINKED_FINAL);

        ArrayList<Choice> lc1 = new ArrayList<Choice>(Arrays.asList(c1, c2));
        c2.addChoice(Arrays.asList(c2a, c2b, c2c));

        q1.getStartingChoice().addChoice(lc1);

        MissionManager.getInstance().addQuest(q1);
        q1.activate();



    }

}
