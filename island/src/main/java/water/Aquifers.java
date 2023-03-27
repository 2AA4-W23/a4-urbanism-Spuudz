package water;

import java.util.Map;
import java.util.Random;
import IslandADT.*;
import Tiles.*;
import configuration.Configuration;
import seeds.Seed;

public class Aquifers {
    private int numOfAquifers;
    private TileTypeChoose tile = new TileTypeChoose();
    private Seed newSeed = new Seed();

    public Aquifers(Configuration config){
        Map<String, String> options = config.export();
        numOfAquifers = Integer.parseInt(options.get(Configuration.AQUIFERS));
    }
    public Aquifers(int num){numOfAquifers = num;}

    public Island generateAquifers(Island anIsland, Seed seed){
        Island clone = new Island();
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());

        for(int i = 0; i < numOfAquifers; i++){
            int index = seed.returnCurrent();
            Tile t = anIsland.getTiles(index);
            t.setProperty("tile_type", tile.getTile(TileType.Aquifer));

            clone.addTile(t);
        }
        newSeed = seed;
        return clone;
    }

    public Island generateAquifers(Island anIsland){
        Island clone = new Island();
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());
        Random rand = new Random();
        int currentNum = 0;
        int index = 0;

        for(Tile t : anIsland.getTileList()){
            if(t.getProperties().get("tile_type").equals("Land")){
                if(currentNum < numOfAquifers){
                    if((rand.nextInt(20)+1) == 1){
                        t.setProperty("tile_type", tile.getTile(TileType.Aquifer));
                        currentNum++;
                        newSeed.addToSeed(Integer.toString(index));
                    }
                }
            }
            clone.addTile(t);
            index++;
        }

        return clone;
    }

    public Seed returnSeed(){
        return newSeed;
    }
}
