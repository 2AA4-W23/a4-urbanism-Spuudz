package shapes;
import Tiles.TileTypeChoose;
import Tiles.TileType;
import java.util.Map;
import IslandADT.*;

public class Crescent implements Shapes {

    public Crescent(Map<String, String> params){}

    public Island identifyLand(Island currentIsland, double centerX, double centerY,double width, double height){
        double radius;
        Island clone = new Island();
        int idx = 0;
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());

        if(width>height){
            radius = height/2.5; 
        }else{
            radius=width/2.5;
        }

        for(Tile t : currentIsland.getTileList()){
            double x = currentIsland.getVertices(t.getCentroidIdx()).getX();
            double y = currentIsland.getVertices(t.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
            double crescentDistance = Math.sqrt(Math.pow(x-(centerX-(radius*0.8)),2) + Math.pow(y-centerY,2));
            t = assignType(t, distance, crescentDistance, radius, clone, idx);
            idx++;
        }

        return clone;    
    }
    public Tile assignType(Tile t, double distance, double crescentDistance, double radius, Island anIsland, int idx){
        String color = "";
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile = "";
        if(distance>=radius){
            color = chooseTile.getColor(TileType.Ocean);
            tile = chooseTile.getTile(TileType.Ocean);
        }else if(distance<radius){
            if(crescentDistance > radius){
                    color = chooseTile.getColor(TileType.Land);
                    tile = chooseTile.getTile(TileType.Land);
                    anIsland.addLandTile(idx);
            }
            else {
                color = chooseTile.getColor(TileType.Ocean);
                tile = chooseTile.getTile(TileType.Ocean);
            }
        }
        t.setProperty("tile_type",tile);
        t.setProperty("rgb_color",color);
        return t;
    }
}
