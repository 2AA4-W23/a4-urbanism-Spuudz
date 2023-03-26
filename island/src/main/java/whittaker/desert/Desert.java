package whittaker.desert;

import java.util.Map;
import IslandADT.Island;
import IslandADT.Tile;
import whittaker.Whittaker;

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

                if((humid >= 80.0) || (elevation >= 80.0)){
                    
                }else if((humid < 80.0 && humid >= 60.0) || (elevation < 80.0 && elevation >= 60.0)){
                    
                }else if((humid < 60.0 && humid >= 40.0) || (elevation < 60.0 && elevation >= 40.0)){
                    
                }else if((humid < 40.0 && humid >= 20.0) || (elevation < 40.0 && elevation >= 20.0)){
                    
                }else{
                    
                }

                t.setProperty("rgb_color",color);
                t.setProperty("tile_type", type);
            }
        }
        
        return clone;
    }
}
