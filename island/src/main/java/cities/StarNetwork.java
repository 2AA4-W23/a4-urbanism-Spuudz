package cities;

import java.util.List;
import java.util.ArrayList;

import GraphADT.*;
import GraphADT.Edge;
import IslandADT.*;
import findPath.ShortestPath;

public class StarNetwork {

    public StarNetwork(){}

    public Graph convertToGraph(List<Integer> nodeIdxList, List<Integer> edgeIdxList, Island anIsland){
        int count = 0;
        Graph G = new Graph();

        List<GraphADT.Node> nodeList = new ArrayList<GraphADT.Node>();
        List<GraphADT.Edge> edgeList = new ArrayList<GraphADT.Edge>();

        for(int index : nodeIdxList){
            IslandADT.Vertex currentVertex = anIsland.getVertices(index);

            Node n = new Node((float)currentVertex.getX(),(float)currentVertex.getY(),count);
            n.setRefIndex(index);
            nodeList.add(n);

            count++;
        }

        for(int index : edgeIdxList){
            IslandADT.Edge currentEdge = anIsland.getEdge(index);
            IslandADT.Vertex currentV1 = anIsland.getVertices(currentEdge.V1IDX());
            IslandADT.Vertex currentV2 = anIsland.getVertices(currentEdge.V2IDX());

            Node n1 = null;
            Node n2 = null;

            for(Node n : nodeList){
                if(n.getRefIndex() == currentV1.getIndex()){
                    n1 = n;
                }

                if(n.getRefIndex() == currentV2.getIndex()){
                    n2 = n;
                }
            }


            GraphADT.Edge e = new Edge(n1,n2);

            edgeList.add(e);
        }

        for(GraphADT.Node node : nodeList){
            List<Integer> neighbors = new ArrayList<Integer>();
            for(GraphADT.Edge edge : edgeList){
                if (node.getIndex() == edge.V1IDX()){
                    neighbors.add(edge.V2IDX());
                }else if(node.getIndex() == edge.V2IDX()){
                    neighbors.add(edge.V1IDX());
                }
                node.setNeighbors(neighbors);
                nodeList.set(node.getIndex(),node);
            }
        }
        G.assignGraph(nodeList, edgeList);
        return G;
    }

    public List<List<Integer>> shortestPath(Graph G, List<Integer> cityList, int capitalIndex){
        ShortestPath path = new ShortestPath();

        List<List<Integer>> pathList = new ArrayList<List<Integer>>();
        
        Node capital = null;
        Node city = null;

        for(Node n : G.getNodeList()){
            if(n.getRefIndex() == capitalIndex){
                capital = n;
            }
        }

        for(int index : cityList){
            if(index == capitalIndex){
                continue;
            }
            for(Node n : G.getNodeList()){
                if(n.getRefIndex() == index){
                    city = n;
                }
            }
            pathList.add(path.shortestPath(G, capital, city));
        }

        return pathList;
    }
}