package shapes;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.util.GeometricShapeFactory;

import IslandADT.Island;
import Tiles.TileTypeChoose;
import Tiles.TileType;
import java.util.List;
import java.util.Map;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;

public class Crescent implements Shapes {

    public Crescent(Map<String, String> params){}
    public Island identifyLand(Island currentMesh, double centerX, double centerY,double width, double height){
        return currentMesh;
    }

    /*public Mesh identifyLand(Island currentMesh, double centerX, double centerY,double width, double height){
        
        TileTypeChoose chooseTile = new TileTypeChoose();
        String tile = "";
        double radius;
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(currentMesh.getVerticesList());
        clone.addAllSegments(currentMesh.getSegmentsList());
        if(width>height){
            radius = height/2.5; 
        }else{
            radius=width/2.5;
        }
        for(Structs.Polygon p : currentMesh.getPolygonsList()){
            String color = "";
            double x = currentMesh.getVertices(p.getCentroidIdx()).getX();
            double y = currentMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
            double crescentDistance = Math.sqrt(Math.pow(x-(centerX-(radius*0.8)),2) + Math.pow(y-centerY,2));
            if(distance>=radius){
                color = chooseTile.getColor(TileType.Ocean);
                tile = chooseTile.getTile(TileType.Ocean);
            }else if(distance<radius){
                if(crescentDistance > radius){
                    Boolean oceanNeighbor = false;
                    List <Integer> neighbors = p.getNeighborIdxsList();
                    for(int neighbor : neighbors){
                        Polygon tempNeighbor = currentMesh.getPolygons(neighbor);
                        int tempCentroid = tempNeighbor.getCentroidIdx();
                        double tempX = currentMesh.getVertices(tempCentroid).getX();
                        double tempY = currentMesh.getVertices(tempCentroid).getY();
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
    */
}
