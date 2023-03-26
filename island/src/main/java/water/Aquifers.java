package water;

import java.util.Map;
import java.util.Random;
import IslandADT.*;
import Tiles.*;
import configuration.Configuration;

public class Aquifers {
    private int numOfAquifers;

    public Aquifers(Configuration config){
        Map<String, String> options = config.export();
        numOfAquifers = Integer.parseInt(options.get(Configuration.AQUIFERS));
    }

    public Island generateAquifers(Island anIsland){
        Island clone = new Island();
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());
        Random rand = new Random();
        TileTypeChoose tile = new TileTypeChoose();
        int currentNum = 0;

        for(Tile t : anIsland.getTileList()){
            if(t.getProperties().get("tile_type").equals("Forest")){
                if(currentNum < numOfAquifers){
                    if((rand.nextInt(20)+1) == 1){
                        t.setProperty("tile_type", tile.getTile(TileType.Aquifer));
                        currentNum++;
                    }
                }
            }
            clone.addTile(t);
        }

        return clone;
    }
}
