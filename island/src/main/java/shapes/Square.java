package shapes;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.util.GeometricShapeFactory;
import Tiles.TileTypeChoose;
import Tiles.TileType;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;

public class Square implements Shapes {

    public Square(Map<String, String> params){}

    public Mesh identifyLand(Mesh currentMesh, double centerX, double centerY,double width, double height){
        
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile = "";
        double squareWidth;
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(currentMesh.getVerticesList());
        clone.addAllSegments(currentMesh.getSegmentsList());

        if(width>height){
            squareWidth = width/3.14; 
        }else{
            squareWidth =height/3.14;
        }
        double xLeft = centerX - squareWidth;
        double xRight = centerX + squareWidth;
        double yTop = centerY - squareWidth;
        double yBottom = centerY + squareWidth;

        for(Structs.Polygon p : currentMesh.getPolygonsList()){
            String color = "";
            double x = currentMesh.getVertices(p.getCentroidIdx()).getX();
            double y = currentMesh.getVertices(p.getCentroidIdx()).getY();

            if((xLeft < x && xRight > x) && (yTop < y && yBottom > y)){
                Boolean oceanNeighbor = false;
                List <Integer> neighbors = p.getNeighborIdxsList();
                for(int neighbor : neighbors){
                    Polygon tempNeighbor = currentMesh.getPolygons(neighbor);
                    int tempCentroid = tempNeighbor.getCentroidIdx();
                    double tempX = currentMesh.getVertices(tempCentroid).getX();
                    double tempY = currentMesh.getVertices(tempCentroid).getY();
                    if((xLeft > tempX || xRight < tempX) || (yTop > tempY || yBottom < tempY)){
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
                     
            }else{
                color = chooseTile.getColor(TileType.Ocean);
                tile = chooseTile.getTile(TileType.Ocean);
            }

            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(p);

                Structs.Property tt = Structs.Property.newBuilder()
                        .setKey("tile_type")
                        .setValue(tile)
                        .build();
                pc.addProperties(tt);

                
                Structs.Property pr = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(color)
                        .build();
                pc.addProperties(pr);
                clone.addPolygons(pc);
        }

        return clone.build();    
    }
}
