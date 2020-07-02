package graph;

public abstract class Vertex implements Coordinates{
    private String label;

    public Vertex(String label) {
        this.label = label;
    }

    public String getName() {
        return this.label;
    }
}
