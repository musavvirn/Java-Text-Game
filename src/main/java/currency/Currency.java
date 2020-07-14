package currency;

public abstract class Currency {
    protected int amount;

    public void increase(int i) {
        this.amount += i;
    }

    public void decrease(int i) throws Exception {
        if (this.amount - i >= 0) {
            this.amount -= i;
        } else {
            throw new Exception("Negative balance");
        }

    }

    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int i) {
        this.amount = i;
    }
}
