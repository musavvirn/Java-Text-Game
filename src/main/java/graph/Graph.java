package graph;

import java.util.*;

public class Graph {
    private Map<Vertex, HashSet<Vertex>> map;

    public Graph() {
        this.map = new HashMap<>();
    }

    public Set<Vertex> get(Vertex v) {
        return this.map.get(v);
    }

    public void addNode(Vertex v, List<Vertex> list) {
        this.map.put(v, new HashSet<>(list));
    }

    public void removeNode(Vertex v) {
        this.map.remove(v);
    }
    public void addVertex(Vertex source, Vertex v) {
        HashSet<Vertex> list = this.map.get(source);
        list.add(v);
        this.map.put(source, list);
   }

   public void removeVertex(Vertex source, Vertex v) {
       HashSet<Vertex> list = this.map.get(source);
       list.remove(v);
       this.map.put(source, list);
   }


}
