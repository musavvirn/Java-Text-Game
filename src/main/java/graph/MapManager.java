package graph;

import printer.PrintItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapManager extends Graph implements PrintItems {
    private static final MapManager instance = new MapManager();
    private Vertex current;
    private List<Vertex> visitedPointsList = new ArrayList<>();

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

    /** Methods for travelling between locations
     *
     *
     *
     *
    * */


    /**  @return Vertex: current
     */
    public Vertex getCurrent() {
        return this.current;
    }

    public void setCurrent(Vertex v) {
        this.current = v;
        this.addVisitedLocation(v);
    }


    /** travel to vertex
     * can only travel to locations that are discovered and connected to current vertex
     *        throw exception if can't travel
     *  @return Vertex: destination
     */
    public Vertex travelTo(Vertex v) {
        if (this.getTravelPointsList().contains(v) && this.current != v) {
            // TODO "travel" to v;
            this.current = v;
            this.addVisitedLocation(v);
        }

        return v;
    }

    /**  @return List: possible list of travel vertices from current vertex
     */
    public Set<Vertex> getTravelPointsList() {
        return this.get(current);
    }


    /**  @return List: past visited locations
     */
    public List<Vertex> getVisitedLocations() {
        return this.visitedPointsList;
    }

    /**
     * add location to list of visited locations
     * @return void
     */
    public void addVisitedLocation(Vertex v) {
        if (!this.visitedPointsList.contains(v)) {
            this.visitedPointsList.add(v);
        }
    }

    /**
     *
     * @return double: explored percentage of total locations available;
     */
    public double getExploredPercent() {
        double n = this.visitedPointsList.size() / this.getSize();
        return n;
    }
}
