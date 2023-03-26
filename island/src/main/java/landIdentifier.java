import java.util.List;

import IslandADT.Island;
import IslandADT.Tile;
import Tiles.TileType;
import Tiles.TileTypeChoose;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import configuration.Configuration;
import shapes.*;
import water.*;

public class landIdentifier {
    private double xMid;
    private double yMid;
    private Configuration config;
    private Island island;
    public landIdentifier(Island oldIsland, Configuration config){
        island = oldIsland;
        xMid = xDimension()/2;
        yMid = yDimension()/2;
        
        this.config=config;
    }

    public Island identify(){
        Shapes shape = ShapeSpecificationFactory.create(config);
        Island islandShape = shape.identifyLand(island, island.getVertices(island.getTiles(findCenter()).getCentroidIdx()).getX(), island.getVertices(island.getTiles(findCenter()).getCentroidIdx()).getY(), xMid*2, yMid*2);
        return islandShape;
    }

    public double xDimension(){
        double max_x = Double.MIN_VALUE;
        for (IslandADT.Vertex v: island.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
        }
        System.out.println(Math.ceil(max_x));
        return Math.ceil(max_x);
    }

    public double yDimension(){
        double max_y = Double.MIN_VALUE;
        for (IslandADT.Vertex v: island.getVerticesList()) {
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        return Math.ceil(max_y);
    }

    public int findCenter(){
        int middleIndex=0;
        double smallestDistance=500;
        int counter =0;
        for(Tile t : island.getTileList()){
            int tempCentroid = t.getCentroidIdx();
            double x=island.getVertices(tempCentroid).getX();
            double y = island.getVertices(tempCentroid).getY();
            double distance = Math.sqrt(Math.pow(x-xMid,2) + Math.pow(y-yMid,2));
            
            if (distance < smallestDistance){
                smallestDistance=distance;
                middleIndex=counter;
            }
            counter++;
        }
        return middleIndex;
    }

}
