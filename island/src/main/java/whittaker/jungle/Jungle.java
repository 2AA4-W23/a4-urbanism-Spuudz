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
        Island clone = new Island();
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());

        for(Tile t : currentIsland){
            Map<String, String> tile = t.getProperties();
            double elevation = Double.parseDouble(tile.get("elevation"));
            double humid = Double.parseDouble(tile.get(""));
            if(!(tile.get("tile_type").equals("Ocean"))){
                int biome = (int)Math.floor(Math.random() * (5 - 1 + 1) + 1);
                Beach beach = new Beach();
                Tropical tropical = new Tropical();
                Montane montane = new Montane();
                Temperate temperate = new Temperate();
                Flooded flooded = new Flooded();

                if(biome == 1){
                    color = beach.getColor();
                }else if(biome == 2){
                    color = tropical.getColor();
                }else if(biome == 3){
                    color = montane.getColor();
                }else if(biome == 4){
                    color = temperate.getColor();
                }else{
                    color = flooded.getColor();
                }

                t.setProperty("rgb_color",color);
            }
        }
        
        return clone;
    }
}
