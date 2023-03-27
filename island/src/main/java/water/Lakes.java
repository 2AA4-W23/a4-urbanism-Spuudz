package water;

import java.util.List;
import java.util.Map;
import configuration.Configuration;
import java.util.Random;

import Tiles.TileTypeChoose;
import Tiles.TileType;
import IslandADT.*;
import seeds.*;

public class Lakes {
    private int numOfLakes;
    private Seed newSeed = new Seed();
    private TileTypeChoose tile = new TileTypeChoose();

    public Lakes(Configuration config){
        Map<String, String> options = config.export();
        numOfLakes = Integer.parseInt(options.get(Configuration.LAKES));
    }
    public Lakes(int num){numOfLakes = num;}

    public int numOfLandTiles(Island anIsland){
        Map<String, String> tile;
        int count = 0;
        for(Tile t : anIsland.getTileList()){
            tile = t.getProperties();
            if(tile.get("tile_type").equals("Land")){count++;}
        }
        return count;
    }

    public Island generateLakes(Island anIsland, Seed seed){
        Island clone = new Island();
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());

        for(int i = 0; i < numOfLakes; i++){
            int index = seed.returnCurrent();
            Tile t = anIsland.getTiles(index);
            t.setProperty("tile_type", tile.getTile(TileType.Lake));
            t.setProperty("rgb_color", tile.getColor(TileType.Lake));
            lakeSpread(t, clone);

            clone.addTile(t);
        }
        newSeed = seed;
        return clone;
    }

    public Island generateLakes(Island anIsland){
        Random rand = new Random();
        String tileType;
        Island clone = new Island();
        int landTiles = numOfLandTiles(anIsland);
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());

        int currentNum = 0;
        int index = 0;

        while(currentNum < numOfLakes && currentNum < landTiles){
            for(Tile t : anIsland.getTileList()){
                tileType = t.getProperties().get("tile_type");
                if(tileType.equals("Land")){
                    int random = rand.nextInt(10)+1;
                    if(currentNum < numOfLakes){
                        if(random == 1){
                            t.setProperty("tile_type", tile.getTile(TileType.Lake));
                            t.setProperty("rgb_color", tile.getColor(TileType.Lake));
                            clone.addTile(t);
                            newSeed.addToSeed(Integer.toString(index));
                            lakeSpread(t, clone);
                            currentNum++;
                        }
                    }
                }
                else if(tileType.equals("Lake")){
                    continue;
                }
                else{
                    clone.addTile(t);
                }
                index++;
            }
        }
        return clone;
     }

     public void lakeSpread(Tile lake, Island clone){
        List<Integer> neighbors = lake.getNeighborsIdxList();
        TileTypeChoose tile = new TileTypeChoose();
        for(int idx : neighbors){
            Tile t = clone.getTiles(idx);
            if(t.getProperties().get("elevation").equals(lake.getProperties().get("elevation"))){
                t.setProperty("tile_type", tile.getTile(TileType.Lake));
                t.setProperty("rgb_color", tile.getColor(TileType.Lake));
                clone.addTile(t);
            }
        }
     }
     public Seed returnSeed(){
        return newSeed;
     }
}