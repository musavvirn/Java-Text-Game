package graph;

import printer.PrintItems;

public class MapManager extends Graph implements PrintItems {
    private static final MapManager instance = new MapManager();

    public static MapManager getInstance() {
        return instance;
    }

    @Override
    public void print() {
        System.out.println("<< Location List >>");
        for (Vertex v : this.getMap().keySet()) {
            System.out.println(String.format("- %s", v.getName()));
        }
    }



}
