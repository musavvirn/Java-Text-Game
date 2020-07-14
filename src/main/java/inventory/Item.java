package inventory;

public class Item {
    protected String name;
    protected int price;
    protected ItemState state;

    public Item(String name, int price) {
        this.name = name;
        this.state = ItemState.TRADEABLE;
        this.price = price;
    }

    public Item(String name, ItemState state) {
        this.name = name;
        this.state = state;
        this.price = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }

    public Item(String name) {
        this.name = name;
        this.state = ItemState.TRADEABLE;
        this.price = 0;
    }




}
