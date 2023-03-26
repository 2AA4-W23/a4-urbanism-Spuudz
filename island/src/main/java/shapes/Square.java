package shapes;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.util.GeometricShapeFactory;
import Tiles.TileTypeChoose;
import Tiles.TileType;
import java.util.List;
import java.util.Map;
import IslandADT.*;

public class Square implements Shapes {

    public Square(Map<String, String> params){}

    public Island identifyLand(Island currentIsland, double centerX, double centerY,double width, double height){
        
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile = "";
        double squareWidth;
        Island clone = new Island();
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());

        if(width>height){
            squareWidth = width/3.14; 
        }else{
            squareWidth =height/3.14;
        }
        double xLeft = centerX - squareWidth;
        double xRight = centerX + squareWidth;
        double yTop = centerY - squareWidth;
        double yBottom = centerY + squareWidth;

        int idx = 0;
        for(Tile t : currentIsland.getTileList()){
            String color = "";
            double x = currentIsland.getVertices(t.getCentroidIdx()).getX();
            double y = currentIsland.getVertices(t.getCentroidIdx()).getY();

            if((xLeft < x && xRight > x) && (yTop < y && yBottom > y)){
                color = chooseTile.getColor(TileType.Forest);
                tile = chooseTile.getTile(TileType.Forest);
                currentIsland.addLandTile(idx);
                     
            }else{
                color = chooseTile.getColor(TileType.Ocean);
                tile = chooseTile.getTile(TileType.Ocean);
            }

            t.setProperty("tile_type",tile);
            t.setProperty("rgb_color",color);

            idx++;
        }   
        return clone;
    }
}
