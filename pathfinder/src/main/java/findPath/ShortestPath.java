package findPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GraphADT.*;

public class ShortestPath implements FindPath{


    public double pathBetweenNodes(Node n1, Node n2){
        return Math.sqrt(Math.pow(n1.getX()-n2.getX(),2) + Math.pow(n1.getY()-n2.getY(),2));
    }

    public void assignWeight(Graph G){
        double distance;
        for(Edge e : G.getEdgesList()){
            int n1Idx = e.V1IDX();
            int n2Idx = e.V2IDX();

            double n1X = G.getNodeList().get(n1Idx).getX();
            double n2X = G.getNodeList().get(n2Idx).getX();
            double n1Y = G.getNodeList().get(n1Idx).getY();
            double n2Y = G.getNodeList().get(n2Idx).getY();

            distance = Math.sqrt(Math.pow((n1X - n2X),2) + Math.pow((n1Y - n2Y),2));

            e.setProperty("weight", Double.toString(distance));
        }
    }

    public List<Integer> shortestPath(Graph G, Node n1, Node n2){
        ArrayList<Node> path = new ArrayList<Node>();
        ArrayList<Double> cost = new ArrayList<Double>();
        ArrayList<Node> Q = new ArrayList<Node>();


        for(int i = 0; i <  G.getNodeList().size(); i++){
            cost.add(Double.MAX_VALUE);
            path.add(null);
        }

        path.set(n1.getIndex(),n1);
        cost.set(n1.getIndex(),0.0);

        Q.add(n1);

        Node minNode = n1;

        while(Q.size()!=0){
            for(Node n : Q){
                double min = Double.MAX_VALUE;
                if (cost.get(n.getIndex()) < min){
                    min = cost.get(n.getIndex());
                    minNode = n;
                }
            }
            Q.remove(minNode);

            for(int idx : minNode.getNeighborList()){
                if((cost.get(minNode.getIndex()) + pathBetweenNodes(minNode, G.getNode(idx))) < cost.get(idx)){
                    path.set(G.getNode(idx).getIndex(),minNode);
                    cost.set(G.getNode(idx).getIndex(),cost.get(minNode.getIndex()) + pathBetweenNodes(minNode, G.getNode(idx)));
                    Q.add(G.getNode(idx));
                }
            }
        }
        
        List<Integer> shortestPath = new ArrayList<Integer>();
        Node currentNode = n2;

        while(currentNode != n1){
            shortestPath.add(currentNode.getIndex());
            currentNode = path.get(currentNode.getIndex());
        }
        shortestPath.add(n1.getIndex());
        Collections.reverse(shortestPath);
        return shortestPath;
    }
}