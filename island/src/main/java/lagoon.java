import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class lagoon {
    private int radius = 100;
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
        /*for(Structs.Polygon poly: ogMesh.getPolygonsList()) {
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);
            String color = "100,21,100";
            Structs.Property p = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue(color)
            .build();
            pc.addProperties(p);

            clone.addPolygons(pc);
        }*/
        
    

}
