import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class lagoon {
    private int radius = 100;
    private double xMid = 250;
    private double yMid = 250;
    private Structs.Mesh ogMesh;
    public lagoon(Structs.Mesh oldMesh){
        ogMesh = oldMesh;
    }

    public int findCenter(){
        int middleIndex=0;
        int smallestDistance=500;
        for(Structs.Polygon poly : ogMesh.getPolygonsList()){
            int tempCentroid = poly.getCentroidIdx();
            double x=ogMesh.getVertices(tempCentroid).getX();
            double y = ogMesh.getVertices(tempCentroid).getY();
            double distance = Math.sqrt(Math.pow(x-xMid,2) + Math.pow(y-yMid,2));
            
            if (distance < smallestDistance){
                middleIndex=tempCentroid;
            }
        }
        return middleIndex;
    }
    
    public void color(){
        for(Structs.Polygon poly: ogMesh.getPolygonsList()) {
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);
            String color = "208,21,243";
            Structs.Property p = Structs.Property.newBuilder()
            .setKey("rgb_color")
            .setValue(color)
            .build();
            pc.addProperties(p);
        }
    }

}
