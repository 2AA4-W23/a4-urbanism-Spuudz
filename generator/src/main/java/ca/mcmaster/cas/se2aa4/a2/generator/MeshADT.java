package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import java.util.ArrayList;
import java.util.List;

public class MeshADT {
    List<Polygon> PolygonList = new ArrayList<Polygon>();

    public void createPolygons(){
        //fill polygon arraylist with consecutive segments
        PolygonList.add(Polygon.newBuilder().setSegmentIdxs(0, 0).setSegmentIdxs(1, 1).build());
    }

}