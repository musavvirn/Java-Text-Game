package inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private final static Inventory instance = new Inventory();
    private HashMap<Item, Integer> listOfItems;

    private Inventory() {
        this.listOfItems = new HashMap<Item, Integer>();
    }

    public static Inventory getInstance() {
        return instance;
    }
    public void add(Item item) throws Exception {
        if (this.listOfItems.containsKey(item)) {
            throw new Exception("Can't add duplicate item..");
        } else {
            this.listOfItems.put(item, 1);
            System.out.println(String.format("Gained new item - %s", item.getName()));
        }
    }

    public void remove(Item item) throws Exception {
        if (!this.listOfItems.containsKey(item)) {
            throw new Exception("Item does not exist in inventory..");
        } else {
            this.listOfItems.remove(item);
            System.out.println(String.format("• Got rid of %s", item.getName()));
        }
    }

    public void printInventory() {
        System.out.println("<< Inventory >>");
        if (this.listOfItems.isEmpty()) {
            System.out.println("Inventory is empty..");
        } else {
            ArrayList<Item> list = new ArrayList<>(this.listOfItems.keySet());
            for (Item item: list) {
                System.out.println(String.format("• %s - %d", item.getName(), this.listOfItems.get(item)));
            }
        }
    }

}
