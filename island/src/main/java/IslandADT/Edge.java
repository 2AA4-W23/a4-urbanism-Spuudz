package IslandADT;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Edge {
    private final List<Vertex> vertices;
    private Map<String, String> properties=new HashMap<>();

    public Edge(Vertex v1, Vertex v2) {
        this.vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
    }

    public void setProperty(String key, String value){
        properties.put(key,value);
    }
    public Map<String,String> getProperties(){
        return java.util.Collections.unmodifiableMap(properties);

    }

    public int V1IDX(){
        return vertices.get(0).getIndex();
    }
    public int V2IDX(){
        return vertices.get(1).getIndex();
    }
    public Vertex[] contents() {
        return this.vertices.toArray(new Vertex[]{});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge that = (Edge) o;
        return vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }
}
