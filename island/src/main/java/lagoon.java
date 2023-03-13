import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class lagoon {
    private int radius = 200;
    private double xMid = 250;
    private double yMid = 250;
    public Structs.Mesh ogMesh;
    public lagoon(Structs.Mesh oldMesh){
        ogMesh = oldMesh;
        xMid = xDimension()/2;
        yMid = yDimension()/2;
    }

    public double xDimension(){
        double max_x = Double.MIN_VALUE;
        for (Structs.Vertex v: ogMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
        }
        return Math.ceil(max_x);
    }

    public double yDimension(){
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: ogMesh.getVerticesList()) {
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        return Math.ceil(max_y);
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
                color ="231,215,201";
            }else{
                color = "50,74,178";
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
            double x=ogMesh.getVertices(p.getCentroidIdx()).getX();
            double y = ogMesh.getVertices(p.getCentroidIdx()).getY();
            double distance = Math.sqrt(Math.pow(x-centerX,2) + Math.pow(y-centerY,2));
            if(distance>radius){
                color = "50,74,178";
            }else{
                color = "231,215,201";
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
