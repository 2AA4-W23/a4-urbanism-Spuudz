package cities;

import configuration.Configuration;
import java.util.List;
import java.util.Map;
import java.util.Random;

import GraphADT.*;
import java.util.ArrayList;

import IslandADT.*;
import IslandADT.Edge;

public class Cities {
    private int numOfCities;
    private List<Integer> landEdges = new ArrayList<Integer>();
    private List<Integer> landVertices = new ArrayList<Integer>();
    private List<Integer> cityIndex = new ArrayList<Integer>();
    private int capitalIndex;

    public Cities(Configuration config){
        Map<String,String> options = config.export();
        numOfCities = Integer.parseInt(options.get(Configuration.CITIES));
    }

    public int getCapitalIndex(){
        return capitalIndex;
    }

    private void getLandVertices(Island anIsland){
        for(int landIndex : anIsland.getLandTiles()){
            Tile landTile = anIsland.getTiles(landIndex);
            for(int index : landTile.getEdgeIdxs()){
                int v1 = anIsland.getEdge(index).V1IDX();
                int v2 = anIsland.getEdge(index).V2IDX();

                landEdges.add(index);
                if(!(landVertices.contains(v1))){
                    landVertices.add(v1);
                }
                if(!(landVertices.contains(v2))){
                    landVertices.add(v2);
                }   
            }
        }
    }

    public Island generateCities(Island anIsland){
        Random rand = new Random();
        List<Integer> cityList = new ArrayList<Integer>();
        getLandVertices(anIsland);
        cityList = generateCityList();
        int count = 0;

        for(int index : cityList){
            Vertex v = anIsland.getVertices(index);
            int citySize = rand.nextInt(4)+1;

            if(count == 0){
                v.setProperty("city", "5");
                this.capitalIndex = index;
            }
            else{
                v.setProperty("city", Integer.toString(citySize));
            }
            cityIndex.add(index);
            count++;
        }

        for(List<Integer> L : findCityPaths(anIsland)){
            System.out.println(L);
            for(int x = 0; x < L.size()-1; x++){
                IslandADT.Vertex v1 = anIsland.getVertices(landVertices.get(L.get(x)));
                IslandADT.Vertex v2 = anIsland.getVertices(landVertices.get(L.get(x+1)));

                IslandADT.Edge e = new Edge(v1, v2);
                e.setProperty("city", "1");

                anIsland.addEdge(e);
            }
        }
        return anIsland;

    }

    private List<Integer> generateCityList(){
        Random rand = new Random();
        List<Integer> cityIndexList = new ArrayList<Integer>();

        for(int i = 0; i < numOfCities; i++){
            int randomInt = rand.nextInt(landVertices.size()+1);

            if(!(cityIndexList.contains(landVertices.get(randomInt)))){
                cityIndexList.add(landVertices.get(randomInt));
            }
            else{
                i--;
            }
        }
        return cityIndexList;
    }

    private List<List<Integer>> findCityPaths(Island anIsland){
        Graph G = new Graph();
        List<List<Integer>> pathList = new ArrayList<List<Integer>>();

        StarNetwork network = new StarNetwork();

        G = network.convertToGraph(landVertices,landEdges,anIsland);

        pathList = network.shortestPath(G, cityIndex, capitalIndex);

        return pathList;
    }
}
