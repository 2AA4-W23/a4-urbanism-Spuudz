package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    public List<Structs.Segment> PolygonSegments = new ArrayList<Structs.Segment>(); 
    public int centroid;

    public void constructPolygonSegments(List<Structs.Segment> segments){
        for(int i = 0; i < segments.size(); i++){
            for(int j = i+1; j < segments.size(); j++){
                if(segments.get(i).getV1Idx() == segments.get(j).getV1Idx() || segments.get(i).getV1Idx() == segments.get(j).getV2Idx() || segments.get(i).getV2Idx() == segments.get(j).getV1Idx() || segments.get(i).getV2Idx() == segments.get(j).getV2Idx()){
                    PolygonSegments.add(segments.get(i));
                }
            }
        }
    }

}
