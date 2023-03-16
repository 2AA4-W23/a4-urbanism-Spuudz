package IslandADT;

import java.util.*;

public class Tile implements Iterable<Vertex> {
    private final List<Vertex> hull;

    private List<Integer> neighborsIdx;
    private List<Integer> edgeIdxs;
    private Vertex centroid;
    private int centroidIdx;
    Map<String, String> properties=new HashMap<>();
    public Tile() {
        this(new ArrayList<>());
    }
    public Map<String,String> getProperties(){
        return java.util.Collections.unmodifiableMap(properties);

    }

    public void setEdgeIdxs(List<Integer> edge){
        edgeIdxs=edge;
    }

    public List<Integer> getEdgeIdxs(){
        return java.util.Collections.unmodifiableList(edgeIdxs);
    }

    public void setNeighborIdx(List<Integer> idxs){
        neighborsIdx = idxs;
    }

    public Tile(List<Vertex> hull) {
        this.hull = hull;
        this.neighborsIdx = new ArrayList<>();
    }

    public void add(Vertex v) {
        this.hull.add(v);
    }

    public void addCentroidIdx( int index){
        centroidIdx = index;
    }

    public int getCentroidIdx(){
        return centroidIdx;
    }

    public Vertex getCentroid(){
        return centroid;
    }

    public void registerAsNeighbourIdx(int n) {
        this.neighborsIdx.add(n);
    }

    public void addCentroid(Vertex center){
        centroid=center;
    }

    public List<Integer> getNeighborsIdxList() {
        return java.util.Collections.unmodifiableList(neighborsIdx);
    }

    public void setProperty(String key, String value){
        properties.put(key,value);
    }

    public List<Edge> hull(){
        List<Edge> result = new ArrayList<>();
        Iterator<Vertex> it = this.hull.iterator();
        Vertex start = it.next();
        Vertex current = start;
        while(it.hasNext()) {
            Vertex next = it.next();
            result.add(new Edge(current, next));
            current = next;
        }
        result.add(new Edge(current, start));

        return result;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile Tile = (Tile) o;
        return hull.equals(Tile.hull);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hull);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return this.hull.iterator();
    }

    @Override
    public String toString() {
        return "Tile(" +centroid+ "," + hull + ", "+ this.neighborsIdx.size() +")";
    }
}
