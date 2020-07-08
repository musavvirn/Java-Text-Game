package graph;

public abstract class Vertex implements Coordinates{
    private String label;
    private int x = 0; private int y = 0;

    public Vertex(String label) {
        this.label = label;
    }

    public String getName() {
        return this.label;
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x; this.y = y;

    }

    @Override
    public int[] getXY() {
        return new int[]{this.x, this.y};
    }
}
