package bla;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Manager {
    protected String title = "";
    protected ArrayList listArray = new ArrayList();
    protected HashMap listMap = new HashMap();

    public void add(Addable a) throws Exception {
        if (this.listArray.contains(a)) {
            throw new Exception(this.title + " already contains " + a.getName());
        } else {
            this.listArray.add(a);

        }
    }
    public void remove(Addable a) throws Exception {
        if (!this.listArray.contains(a)) {
            throw new Exception(this.title + " already contains " + a.getName());
        } else {
            this.listArray.remove(a);

        }
    }
    public void print() {
        if (!this.isEmpty()) {
            System.out.println(this.listArray.size());
        } else {
            System.out.println(this.title + " is empty");
        }
    }
    public boolean isEmpty() {
        return (this.listArray.isEmpty());
    }


}
