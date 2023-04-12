import GraphADT.*;
import findPath.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class shortestPathTest {

    @Test
    public void testShortestPath(){
        Graph g = new Graph();
        g = createTestGraph();

        ShortestPath d = new ShortestPath();

        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        path.add(1);
        path.add(3);
        path.add(5);
        path.add(6);

        assertEquals(path, d.shortestPath(g, g.getNode(0), g.getNode(6)));
    }

    @Test
    public void testShortestPathAdjacentNodes(){
        Graph g = new Graph();
        g = createTestGraph();

        ShortestPath d = new ShortestPath();

        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        path.add(1);

        assertEquals(path, d.shortestPath(g, g.getNode(0), g.getNode(1)));
    }

    @Test
    public void testShortestPathSameNode(){
        Graph g = new Graph();
        g = createTestGraph();

        ShortestPath d = new ShortestPath();

        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        
        assertEquals(path, d.shortestPath(g, g.getNode(0), g.getNode(0)));
    }


    public Graph createTestGraph(){
        Node n1 = new Node(0, 0,0);
        Node n2 = new Node(20,10,1);
        Node n3 = new Node(40, 30,2);
        Node n4 = new Node(50, 50,3);
        Node n5 = new Node(60, 40, 4);
        Node n6 = new Node(70, 70,5);
        Node n7 = new Node(80, 100,6);

        List<Node> nodes = new ArrayList<Node>();
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        nodes.add(n7);

        Edge e1 = new Edge(n1,n2);
        Edge e2 = new Edge(n2,n3);
        Edge e3 = new Edge(n2,n4);
        Edge e4 = new Edge(n3,n4);
        Edge e5 = new Edge(n3,n5);
        Edge e6 = new Edge(n4,n5);
        Edge e7 = new Edge(n4,n6);
        Edge e8 = new Edge(n5,n6);
        Edge e9 = new Edge(n6,n7);

        List<Edge> edges = new ArrayList<Edge>();
        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        edges.add(e6);
        edges.add(e7);
        edges.add(e8);
        edges.add(e9);

        for(GraphADT.Node node : nodes){
            List<Integer> neighbors = new ArrayList<Integer>();
            for(GraphADT.Edge edge : edges){
                if (node.getIndex() == edge.V1IDX()){
                    neighbors.add(edge.V2IDX());
                }else if(node.getIndex() == edge.V2IDX()){
                    neighbors.add(edge.V1IDX());
                }
                node.setNeighbors(neighbors);
                nodes.set(node.getIndex(),node);
            }
        }


        Graph g = new Graph();
        g.assignGraph(nodes, edges);

        return g;
    }
}
