package graph;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Village V1 = new Village("Village 1");
        City C1 = new City("City 1");
        Village V2 = new Village("Village 2");
        Village V3 = new Village("Abandoned Village");

        Location L1 = new Location();
        L1.addNode(C1, Arrays.asList(V1, V2));
        L1.addNode(V1, Arrays.asList(V2, V3));

        System.out.println(L1.getSize());
    }



}
