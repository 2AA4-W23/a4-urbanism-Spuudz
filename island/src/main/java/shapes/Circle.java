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
        Island clone = new Island();
        clone.register(currentIsland.getTileList(), currentIsland.getVerticesList(),currentIsland.getEdgesList());
        System.out.println(width+" Circles "+height);
        if(width>height){
            radius = height/2.5; 
        }else{
            radius=width/2.5;
        }
        System.out.println(radius);
        //Structs.Polygon center = currentIsland.getPolygons(findCenter());
        //double centerX=currentIsland.getVertices(currentIsland.getPolygons(findCenter()).getCentroidIdx()).getX();
        //double centerY=currentIsland.getVertices(currentIsland.getPolygons(findCenter()).getCentroidIdx()).getY();

        for(Tile t : currentIsland.getTileList()){

            String color;
            double x = currentIsland.getVertices(t.getCentroidIdx()).getX();
            double y = currentIsland.getVertices(t.getCentroidIdx()).getY();
            double distance = distanceFromCentre(x, y, centerX, centerY);
            t=assignType(t, radius, distance);
        }
        return clone;
    }
    public double distanceFromCentre(double x, double y, double centerX, double centerY){
        return Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));

    }
    public Tile assignType(Tile t,double radius, double distance){
        String color;
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile;
        if(distance>=radius){
            color = chooseTile.getColor(TileType.Ocean);
            tile = chooseTile.getTile(TileType.Ocean);
        }else if(distance<radius){
                color = chooseTile.getColor(TileType.Forest);
                tile = chooseTile.getTile(TileType.Forest);
        }else{
            color = chooseTile.getColor(null);
            tile = chooseTile.getTile(null);
        }
        t.setProperty("tile_type", tile);
        t.setProperty("rgb_color",color);
    

    return t;

    }
}
