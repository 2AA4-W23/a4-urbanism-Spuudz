package soil;

import java.util.Map;
import IslandADT.*;

public class Arid implements SoilProfiles {
    public Arid(Map <String,String> params){}

    public Island assignHumidity(Island anIsland){
        double distance = 0.0;
        int humidity;
        anIsland = defaultHumidity(anIsland);
        for(Tile t : anIsland.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("humidity", "0,71,100");
            }
            else if(t.getProperties().get("humidity").equals("0")){
                distance = distanceFromWater(anIsland, t);
                System.out.println(distance);
                humidity = (int)(100 - distance*3);
                if(humidity >= 100){
                    humidity = 99;
                }
                else if (humidity <= 0){
                    humidity = 0;
                }
                t.setProperty("humidity", Integer.toString(humidity) + "," + Integer.toString(humidity) + "," + Integer.toString(humidity));
            }
            else {
                t.setProperty("humidity", "255,255,255");
            }
        }
        return anIsland;
    }

    public double distanceFromWater(Island anIsland, Tile t){
        double distance = 0.0;
        double minDistance = Integer.MAX_VALUE;
        for(Tile waterBody : anIsland.getTileList()){
            if(waterBody.getProperties().get("tile_type").equals("Lake") || waterBody.getProperties().get("tile_type").equals("Aquifer")){
                distance = Math.sqrt(Math.pow((waterBody.getCentroid().getX() - t.getCentroid().getX()),2) + Math.pow((waterBody.getCentroid().getY() - t.getCentroid().getY()),2));
                if(distance <= minDistance){
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    public Island defaultHumidity(Island anIsland){
        for(Tile t : anIsland.getTileList()){
            if(!(t.getProperties().get("tile_type").equals("Lake") || t.getProperties().get("tile_type").equals("Aquifer"))){
                t.setProperty("humidity","0");
            }
            else {
                t.setProperty("humidity","100");
            }
        }
        return anIsland;
    }
}
