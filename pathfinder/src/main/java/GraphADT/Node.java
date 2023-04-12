package GraphADT;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private static final int precision = 2;
    private final int x, y;
    private final int index;
    private List<Integer> neighborList = new ArrayList<Integer>();
    private int refIndex;

    public Node(float x, float y,int index){
        this.x=convert(x);
        this.y=convert(y);
        this.index=index;
    }

    public void setRefIndex(int index){
        this.refIndex = index;
    }

    public int getRefIndex(){
        return refIndex;
    }

    public void setNeighbors(List<Integer> neighbors){
        this.neighborList = neighbors;
    }

    public float x() {
        return this.x / (float) Math.pow(10, precision);
    }
    public int getIndex(){
        return index;
    }

    public float y() {
        return this.y / (float) Math.pow(10, precision);
    }

    private int convert(float x) {
        return (int) Math.round(x*Math.pow(10, precision));
    }

    /*public Vertex makeVertex(float maxX, float maxY) {
        float newX = Math.max(0.0f, Math.min(this.x(), maxX));
        float newY = Math.max(0.0f, Math.min(this.y(), maxY));
        return new Vertex(newX, newY);
    }*/

    public List<Integer> getNeighborList(){
        return neighborList;
    }

    public double getX(){
        return (double) x();
    }

    public double getY(){
        return (double) y();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node vertex = (Node) o;
        return x == vertex.x && y == vertex.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x() +
                ", " + y() +
                ')';
    }
}
