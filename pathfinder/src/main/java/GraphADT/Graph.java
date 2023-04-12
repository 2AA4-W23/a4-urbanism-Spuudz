package GraphADT;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> vertices;
    private List<Edge> edges;

    public Graph(){
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    public void assignGraph(List<Node> n, List<Edge> e){
        this.vertices = n;
        this.edges = e;
    }

    public List<Edge> getEdgesList(){
        return edges;
    }
    public List<Node> getNodeList(){
        return vertices;
    }
    public Node getNode(int index){
        return vertices.get(index);
    }
}
