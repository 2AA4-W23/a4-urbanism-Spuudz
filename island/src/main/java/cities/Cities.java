package cities;

import configuration.Configuration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import GraphADT.*;
import findPath.*;
import IslandADT.*;

public class Cities {
    private int numOfCities;
    private List<Integer> landEdges = new ArrayList<Integer>();
    private List<Integer> landVertices = new ArrayList<Integer>();
    private List<Integer> cityIndex = new ArrayList<Integer>();

    public Cities(Configuration config){
        Map<String,String> options = config.export();
        numOfCities = Integer.parseInt(options.get(Configuration.CITIES));
    }

    public void getLandVertices(Island anIsland){
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
            }
            else{
                v.setProperty("city", Integer.toString(citySize));
            }
            cityIndex.add(index);
            count++;
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
}
