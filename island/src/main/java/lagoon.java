import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class lagoon {
    private int radius = 200;
    private int lagoonRad = 100;
    private double xMid = 250;
    private double yMid = 250;
    public Structs.Mesh ogMesh;
    public lagoon(Structs.Mesh oldMesh){
        ogMesh = oldMesh;
    }

    public int findCenter(){
        int middleIndex=0;
        double smallestDistance=500;
        int counter =0;
        for(Structs.Polygon poly : ogMesh.getPolygonsList()){
            int tempCentroid = poly.getCentroidIdx();
            double x=ogMesh.getVertices(tempCentroid).getX();
            double y = ogMesh.getVertices(tempCentroid).getY();
            double distance = Math.sqrt(Math.pow(x-xMid,2) + Math.pow(y-yMid,2));
            
            if (distance < smallestDistance){
                smallestDistance=distance;
                middleIndex=counter;
            }
            counter++;
        }
        return middleIndex;
    }
    
    public Mesh color(){
        int center = findCenter();
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(ogMesh.getVerticesList());
        clone.addAllSegments(ogMesh.getSegmentsList());

        int counter=0;
        for(Structs.Polygon poly: ogMesh.getPolygonsList()) {
            String color;
            if(counter==center){
                color ="250,10,250";
            }else{
                color = "10,250,10";
            }
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);

                
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(color)
                        .build();
                pc.addProperties(p);
                clone.addPolygons(pc);
            counter++;
        }
        return clone.build();
    }
        
    public Mesh identifyTiles(){
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(ogMesh.getVerticesList());
        clone.addAllSegments(ogMesh.getSegmentsList());
        Structs.Polygon center = ogMesh.getPolygons(findCenter());
        double centerX=ogMesh.getVertices(ogMesh.getPolygons(findCenter()).getCentroidIdx()).getX();
        double centerY=ogMesh.getVertices(ogMesh.getPolygons(findCenter()).getCentroidIdx()).getY();

        for(Structs.Polygon p : ogMesh.getPolygonsList()){
            String color;
            double x = ogMesh.getVertices(p.getCentroidIdx()).getX();
            double y = ogMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
            if(distance>radius){
                color = "0,0,255";
            }else if(distance<radius && distance>lagoonRad){
                Boolean oceanNeighbor = false;
                List <Integer> neighbors = p.getNeighborIdxsList();
                for(int neighbor : neighbors){
                    Polygon tempNeighbor = ogMesh.getPolygons(neighbor);
                    int tempCentroid = tempNeighbor.getCentroidIdx();
                    double tempX = ogMesh.getVertices(tempCentroid).getX();
                    double tempY = ogMesh.getVertices(tempCentroid).getY();
                    double tempDistance = Math.sqrt(Math.pow(tempX-centerX,2) + Math.pow(tempY-centerY,2));
                    if(tempDistance > radius || tempDistance < lagoonRad){
                        oceanNeighbor = true;
                    }
                }
                if(oceanNeighbor){
                    color = "252,244,163";
                }else{
                    color = "0,255,0";
                }
            }else if(distance<lagoonRad){
                color = "115,215,255";
            }else{
                color = "100,100,100";
            }
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(p);

                
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
