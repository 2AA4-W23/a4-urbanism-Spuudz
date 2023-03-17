package shapes;
import Tiles.TileTypeChoose;
import Tiles.TileType;
import java.util.List;
import java.util.Map;
import IslandADT.*;

public class Crescent implements Shapes {

    public Crescent(Map<String, String> params){}

    public Island identifyLand(Island currentIsland, double centerX, double centerY,double width, double height){
        
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile = "";
        double radius;
        Island clone = new Island();
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());

        if(width>height){
            radius = height/2.5; 
        }else{
            radius=width/2.5;
        }

        for(Tile t : currentIsland.getTileList()){
            String color = "";
            double x = currentIsland.getVertices(t.getCentroidIdx()).getX();
            double y = currentIsland.getVertices(t.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
            double crescentDistance = Math.sqrt(Math.pow(x-(centerX-(radius*0.8)),2) + Math.pow(y-centerY,2));
            if(distance>=radius){
                color = chooseTile.getColor(TileType.Ocean);
                tile = chooseTile.getTile(TileType.Ocean);
            }else if(distance<radius){
                if(crescentDistance > radius){
                    Boolean oceanNeighbor = false;
                    List <Integer> neighbors = t.getNeighborsIdxList();
                    for(int neighbor : neighbors){
                        Tile tempNeighbor = currentIsland.getTiles(neighbor);
                        int tempCentroid = tempNeighbor.getCentroidIdx();
                        double tempX = currentIsland.getVertices(tempCentroid).getX();
                        double tempY = currentIsland.getVertices(tempCentroid).getY();
                        double tempDistance = Math.sqrt(Math.pow(tempX-centerX,2) + Math.pow(tempY-centerY,2));
                        double tempCrescentDistance = Math.sqrt(Math.pow(tempX-(centerX-radius*0.8),2) + Math.pow(tempY-centerY,2));
                        if(tempDistance > radius || tempCrescentDistance <= radius){
                            oceanNeighbor = true;
                        }
                    }
                    if(oceanNeighbor){
                        color = chooseTile.getColor(TileType.Beach);
                        tile = chooseTile.getTile(TileType.Beach);
                    }else{
                        color = chooseTile.getColor(TileType.Forest);
                        tile = chooseTile.getTile(TileType.Forest);
                    }
                }
                else {
                    color = chooseTile.getColor(TileType.Ocean);
                    tile = chooseTile.getTile(TileType.Ocean);
                }
            }
            t.setProperty("tile_type",tile);
            t.setProperty("rgb_color",color);
        }

        return clone;    
    }
}
