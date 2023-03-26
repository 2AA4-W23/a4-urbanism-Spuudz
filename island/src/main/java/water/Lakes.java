package water;

import java.util.List;
import java.util.Map;
import configuration.Configuration;
import java.util.Random;

import Tiles.TileTypeChoose;
import Tiles.TileType;
import IslandADT.*;

public class Lakes {
    private int numOfLakes;

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
            if(tile.get("tile_type").equals("Forest")){count++;}
        }
        return count;
    }

    public Island generateLakes(Island anIsland){
        Random rand = new Random();
        String tileType;
        TileTypeChoose tile = new TileTypeChoose();
        Island clone = new Island();
        int landTiles = numOfLandTiles(anIsland);
        clone.register(anIsland.getTileList(), anIsland.getVerticesList(), anIsland.getEdgesList());
        clone.setLandTiles(anIsland.getLandTiles());

        int currentNum = 0;

        while(currentNum < numOfLakes && currentNum < landTiles){
            for(Tile t : anIsland.getTileList()){
                tileType = t.getProperties().get("tile_type");
                if(tileType.equals("Forest")){
                    int random = rand.nextInt(10)+1;
                    if(currentNum < numOfLakes){
                        if(random == 1){
                            t.setProperty("tile_type", tile.getTile(TileType.Lake));
                            t.setProperty("rgb_color", tile.getColor(TileType.Lake));
                            clone.addTile(t);
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
}