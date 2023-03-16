package IslandADT;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class Edge {
    private final List<Vertex> vertices;

    public Edge(Vertex v1, Vertex v2) {
        this.vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
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
