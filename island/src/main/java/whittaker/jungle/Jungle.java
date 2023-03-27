package whittaker.jungle;

import java.util.Map;
import whittaker.jungle.jungleBiomes.*;
import IslandADT.Island;
import IslandADT.Tile;
import whittaker.Whittaker;

public class Jungle implements Whittaker{
    
    public Jungle(Map<String, String> params){}
    public Jungle(){}

    @Override
    public Island genWhittaker(Island currentIsland) {
        String color ="";
        String type = "";
        Island clone = new Island();
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());
        clone.setLandTiles(currentIsland.getLandTiles());

        for(Tile t : currentIsland){
            Map<String, String> tile = t.getProperties();
            double elevation = Double.parseDouble(tile.get("elevation"));
            double humid = Double.parseDouble(tile.get("humidity"));

            if(!(tile.get("tile_type").equals("Ocean") || tile.get("tile_type").equals("Lake"))){
                int biome = (int)Math.floor(Math.random() * (5 - 1 + 1) + 1);
                Beach beach = new Beach();
                Tropical tropical = new Tropical();
                Montane montane = new Montane();
                Temperate temperate = new Temperate();
                Flooded flooded = new Flooded();

                if((elevation >= 200.0)){
                    if(humid >= 60){
                        color = montane.getColor();
                        type = montane.getBiome();
                    }else{
                        color = tropical.getColor();
                        type = tropical.getBiome();
                    }
                }else if((elevation < 200.0 && elevation >= 140.0)){
                    if(humid >= 50){
                        color = flooded.getColor();
                        type = flooded.getBiome();
                    }else if(humid < 50 && humid >= 20){
                        color = tropical.getColor();
                        type = tropical.getBiome();
                    }else{
                        color = temperate.getColor();
                        type = temperate.getBiome(); 
                    }
                }else if((elevation < 140.0 && elevation >= 100.0)){
                    if(humid >= 50){
                        color = flooded.getColor();
                        type = flooded.getBiome();
                    }else{
                        color = tropical.getColor();
                        type = tropical.getBiome();
                    }
                }else if((elevation < 100.0 && elevation >= 60.0)){
                    if(humid >= 50){
                        color = tropical.getColor();
                        type = tropical.getBiome();
                    }else{
                        color = beach.getColor();
                        type = beach.getBiome(); 
                    }                
                }else{
                    color = beach.getColor();
                    type = beach.getBiome();
                }

                t.setProperty("rgb_color",color);
                t.setProperty("tile_type", type);
            }
        }
        
        return clone;
    }
}
