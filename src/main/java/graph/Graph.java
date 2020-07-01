package graph;

import java.util.*;

public abstract class Graph {
    private HashMap<Vertex, HashSet<Vertex>> map;
    private int size;

    public Graph() {
        this.map = new HashMap<>();
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public Set<Vertex> get(Vertex v) {
        return this.map.get(v);
    }

    public void addNode(Vertex source, List<Vertex> list) throws Exception {
        if (!this.map.containsKey(source)) {
            this.map.put(source, new HashSet<>(list));
            for (Vertex v : list) {
                if (this.map.containsKey(v)) {
                    this.addVertex(v, source);

                } else {
                    this.map.put(v, new HashSet<>(Arrays.asList(source)));
                }
            }
        }

        this.size = this.map.keySet().size();
    }

    public void removeNode(Vertex source) throws Exception {
        for (Vertex v : this.map.get(source)) {
            this.removeVertex(v, source);
        }
        this.map.remove(source);

        /* remove a Node should also remove the connections to this node from others */
        this.size = this.map.keySet().size();
    }
    public void addVertex(Vertex source, Vertex v) throws Exception {
        if (source.equals(v)) {
            throw new Exception("Source node and vertex are the same..");
        } else {
            this.map.get(source).add(v);
            this.addNode(v, Arrays.asList(source));
        }
   }

   public void removeVertex(Vertex source, Vertex v) throws Exception {
       if (source.equals(v)) {
           throw new Exception("Source node and vertex are the same..");
       } else {
           this.map.get(source).remove(v);
           this.map.get(v).remove(source);
       }
   }

   public int getNodeSize(Vertex v) {
        return this.get(v).size();
   }

}
