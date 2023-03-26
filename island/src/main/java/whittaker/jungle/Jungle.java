package whittaker.jungle;

import java.util.Map;
import whittaker.jungle.jungleBiomes.*;
import IslandADT.Island;
import IslandADT.Tile;
import whittaker.Whittaker;

public class Jungle implements Whittaker{
    
    public Jungle(Map<String, String> params){}

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

                if((elevation >= 95.0)){
                    color = montane.getColor();
                    type = montane.getBiome();
                }else if((humid >= 80.0) || (elevation < 95.0 && elevation >= 60.0)){
                    color = flooded.getColor();
                    type = flooded.getBiome();
                }else if((humid < 80.0 && humid >= 60.0) || (elevation < 60.0 && elevation >= 40.0)){
                    color = tropical.getColor();
                    type = tropical.getBiome();
                }else if((humid < 40.0 && humid >= 20.0) || (elevation < 40.0 && elevation >= 20.0)){
                    color = temperate.getColor();
                    type = temperate.getBiome();
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
