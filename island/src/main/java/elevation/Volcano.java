package elevation;

import java.util.Random;
import java.util.Map;

import IslandADT.*;

public class Volcano implements AltimetricProfiles {

    public Volcano(Map<String, String> params){

    }
    public Island assignElevation(Island island){
        double maxAltitude;
        double width=width(island);
        double height = height(island);
        if(width>height){
            maxAltitude = height/2;
        }else{
            maxAltitude = width/2;
        }

        int centreIDX = findStartIdx(island);
        for(Tile t : island.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("elevation", "0");
            }else{
                System.out.println("distance centre: "+distanceFromCentre(centreIDX, island, t));
                int elevationValue = (int) (maxAltitude-((distanceFromCentre(centreIDX, island, t))));
                System.out.println("normal elevations:"+elevationValue);
                if(elevationValue<=3){
                    Random rand = new Random();
                    int eValue  = rand.nextInt(3)+1;
                    t.setProperty("elevation", Integer.toString(eValue));
                }else{
                    t.setProperty("elevation", Integer.toString(elevationValue));
                }
            }
            


        }
        return island;

    }



    private double distanceFromCentre(int centerIDX, Island island, Tile t){
        double centerX = island.getTiles(centerIDX).getCentroid().getX();
        //System.out.println(centerX);
        double centerY = island.getTiles(centerIDX).getCentroid().getY();
        //System.out.println(centerY);

        return Math.sqrt(Math.pow(t.getCentroid().getX()-centerX,2)+Math.pow(t.getCentroid().getY()-centerY,2));

        

    }
    private double width(Island island){
        double max_x = Double.MIN_VALUE;
        for (IslandADT.Vertex v: island.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
        }
        System.out.println(Math.ceil(max_x));
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
        System.out.println("center x: "+centerX+" center y: "+centerY);
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
}
