package elevation;

import java.util.Random;
import java.util.Map;

import IslandADT.*;
import seeds.Seed;

public class Volcano implements AltimetricProfiles {
    private Seed newSeed=new Seed();
    public Volcano(Map<String, String> params){

    }
    public Volcano(){}
    public Island assignElevation(Island island, Seed seed){
        double maxAltitude;
        double width=width(island);
        double height = height(island);
        if(width>height){
            maxAltitude = height/2;
        }else{
            maxAltitude = width/2;
        }
        if(maxAltitude<200){
            maxAltitude=250;
        }

        int centreIDX = findStartIdx(island);
        for(Tile t : island.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("elevation", "0");
            }else{
                int elevationValue = (int) (maxAltitude-((distanceFromCentre(centreIDX, island, t))));
                if(elevationValue<=0){
                    t.setProperty("elevation", "1");
                }else{
                    t.setProperty("elevation", Integer.toString(elevationValue));
                }
            }
            


        }
        return island;

    }

    public Seed returnSeed(){
        String seed="";
        for(int i=0;i<30;i++){
            seed+="0";
        }
        newSeed.addToSeed(seed);
        return newSeed;
    }

    public double distanceFromCentre(int centerIDX, Island island, Tile t){
        double centerX = island.getTiles(centerIDX).getCentroid().getX();
        double centerY = island.getTiles(centerIDX).getCentroid().getY();

        return Math.sqrt(Math.pow(t.getCentroid().getX()-centerX,2)+Math.pow(t.getCentroid().getY()-centerY,2));

        

    }
    private double width(Island island){
        double max_x = Double.MIN_VALUE;
        for (IslandADT.Vertex v: island.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
        }
        return Math.ceil(max_x);

    } 

    private double height(Island island){
        double max_y = Double.MIN_VALUE;
        for (IslandADT.Vertex v: island.getVerticesList()) {
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        return Math.ceil(max_y);

    }
    public int findStartIdx(Island island){
        int islandTile=0;
        double totalX=0;
        double totalY=0;

        for(Tile t : island.getTileList()){
            if(!(t.getProperties().get("tile_type").equals("Ocean"))){
                islandTile++;
                totalX+=t.getCentroid().getX();
                totalY+=t.getCentroid().getY();


            }

        }

        double centerX = totalX/islandTile;
        double centerY = totalY/islandTile;
        double minDistance=Double.MAX_VALUE;
        int count=0;
        int centerIDX=0;
        for(Tile t : island.getTileList()){
            double newDistance = Math.sqrt(Math.pow(t.getCentroid().getX()-centerX,2)+Math.pow(t.getCentroid().getY()-centerY,2));
            if(newDistance<minDistance){
                minDistance=newDistance;
                centerIDX=count;
            }
            count++;

        }
        return centerIDX;
    }
    public int findStartIdx(Seed seed){
        return -1;
    }
}
