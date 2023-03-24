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


import configuration.Configuration;


public class Circle implements Shapes {

    private Island island;
    public Circle(Map<String, String> params){
        
    }

    public Island identifyLand(Island currentIsland, double centerX, double centerY,double width, double height){
        String tile = "";
        double radius;
        if(width>height){
            radius = height/2.5; 
        }else{
            radius=width/2.5;
        }
        //Structs.Polygon center = currentIsland.getPolygons(findCenter());
        //double centerX=currentIsland.getVertices(currentIsland.getPolygons(findCenter()).getCentroidIdx()).getX();
        //double centerY=currentIsland.getVertices(currentIsland.getPolygons(findCenter()).getCentroidIdx()).getY();
        int idx=0;
        for(Tile t : currentIsland.getTileList()){

            String color;
            double x = currentIsland.getVertices(t.getCentroidIdx()).getX();
            double y = currentIsland.getVertices(t.getCentroidIdx()).getY();
            double distance = distanceFromCentre(x, y, centerX, centerY);
            int type = assignType(radius, distance);
            if(type==0){

                TileTypeChoose chooseTile = new TileTypeChoose();
                color = chooseTile.getColor(TileType.Forest);
                tile = chooseTile.getTile(TileType.Forest);
                t.setProperty("tile_type", tile);
                t.setProperty("rgb_color",color);

                currentIsland.addLandTile(idx);
            }else{
                assignOcean(t);
            }
            idx++;
        }
        System.out.println("land tile size:"+currentIsland.getLandTiles().size());
        return currentIsland;
    }
    public double distanceFromCentre(double x, double y, double centerX, double centerY){
        return Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));

    }
    public int assignType(double radius, double distance){
        if(distance>=radius){
            return -1;
        }else{
            return 0;
        }
    



    }
    public Tile assignOcean(Tile t){
        TileTypeChoose chooseTile = new TileTypeChoose();
        String color = chooseTile.getColor(TileType.Ocean);
        String tile = chooseTile.getTile(TileType.Ocean);
        t.setProperty("tile_type", tile);
        t.setProperty("rgb_color",color);
        return t;

    }
}
