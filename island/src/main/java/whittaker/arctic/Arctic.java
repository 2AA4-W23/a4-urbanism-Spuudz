package whittaker.arctic;

import java.util.Map;
import IslandADT.Island;
import IslandADT.Tile;
import whittaker.Whittaker;
import whittaker.arctic.articBiomes.*;

public class Arctic implements Whittaker{
    
    public Arctic(Map<String, String> params){}
    public Arctic(){}

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
                Alpine alpine = new Alpine();
                Glacier glacier = new Glacier();
                Boreal boreal = new Boreal();
                subArtic subArtic = new subArtic();
                Tundra tundra = new Tundra();

                if((elevation >= 200.0)){
                    if(humid >= 80){
                        color = boreal.getColor();
                        type = boreal.getBiome();
                    }else{
                        color = glacier.getColor();
                        type = glacier.getBiome();
                    }
                }else if((elevation < 200.0 && elevation >= 140.0)){
                    if(humid >= 70){
                        color = boreal.getColor();
                        type = boreal.getBiome();
                    }else if(humid < 70 && humid >= 20){
                        color = alpine.getColor();
                        type = alpine.getBiome();
                    }else{
                        color = subArtic.getColor();
                        type = subArtic.getBiome(); 
                    }
                }else if((elevation < 140.0 && elevation >= 100.0)){
                    if(humid >= 50){
                        color = boreal.getColor();
                        type = boreal.getBiome();
                    }else{
                        color = alpine.getColor();
                        type = alpine.getBiome();
                    }
                }else if((elevation < 100.0 && elevation >= 60.0)){
                    if(humid >= 50){
                        color = alpine.getColor();
                        type = alpine.getBiome();
                    }else{
                        color = subArtic.getColor();
                        type = subArtic.getBiome(); 
                    }                
                }else{
                    color = tundra.getColor();
                    type = tundra.getBiome();
                }

                t.setProperty("rgb_color",color);
                t.setProperty("tile_type", type);
            }
        }
        
        return clone;
    }
}

