package whittaker.desert;

import java.util.Map;
import IslandADT.Island;
import whittaker.desert.desertBiomes.*;
import IslandADT.Tile;
import whittaker.Whittaker;
import whittaker.desert.desertBiomes.Dunes;

public class Desert implements Whittaker{
    
    public Desert(Map<String, String> params){}

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
                Dunes dunes = new Dunes();
                Mesa mesa = new Mesa();
                Oasis oasis = new Oasis();
                SaltFlats saltflats = new SaltFlats();
                Savanna savanna = new Savanna();
                
                if((elevation >= 200.0)){
                    if(humid >= 80){
                        color = oasis.getColor();
                        type = oasis.getBiome();
                    }else{
                        color = mesa.getColor();
                        type = mesa.getBiome();
                    }
                }else if((elevation < 200.0 && elevation >= 140.0)){
                    if(humid >= 70){
                        color = oasis.getColor();
                        type = oasis.getBiome();
                    }else if(humid < 70 && humid >= 20){
                        color = saltflats.getColor();
                        type = saltflats.getBiome();
                    }else{
                        color = dunes.getColor();
                        type = dunes.getBiome(); 
                    }
                }else if((elevation < 140.0 && elevation >= 100.0)){
                    if(humid >= 50){
                        color = oasis.getColor();
                        type = oasis.getBiome();
                    }else{
                        color = savanna.getColor();
                        type = savanna.getBiome();
                    }
                }else if((elevation < 100.0 && elevation >= 60.0)){
                    if(humid >= 50){
                        color = savanna.getColor();
                        type = savanna.getBiome();
                    }else{
                        color = dunes.getColor();
                        type = dunes.getBiome(); 
                    }                
                }else{
                    color = dunes.getColor();
                    type = dunes.getBiome();
                }

                t.setProperty("rgb_color",color);
                t.setProperty("tile_type", type);
            }
        }
        
        return clone;
    }
}
