package bla;

public class Man extends Manager {

    private static Man instance = new Man();

    public static Manager getInstance() {
        return instance;
    }

    private Man() {
        this.title = "Man";
    }

    public static void main(String[] args) throws Exception {
        Man.getInstance().print();
        Man.getInstance().add(new Addable()); Man.getInstance().add(new Addable());
        Man.getInstance().print();

    }
}
