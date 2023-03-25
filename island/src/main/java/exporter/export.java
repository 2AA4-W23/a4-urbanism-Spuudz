package exporter;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import IslandADT.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class export {

    public export() {}

    public Structs.Mesh run(Island island) {
        Structs.Mesh.Builder result = Structs.Mesh.newBuilder();
        /*Map<Vertex, Integer> vertices = registerVertices(island, result);
        System.out.println(island.getVerticesList().size());
        System.out.println(vertices.keySet().size());

        Map<Edge, Integer> segments = registerSegments(island, result, vertices);
        registerPolygons(island, result, segments, vertices);*/
        makeVertices(island, result);
        System.out.println(1);
        makeSegments(island, result);
        System.out.println(2);
        makePolygons(island, result);
        return result.build();
    }

    private void makeVertices(Island island, Structs.Mesh.Builder result){
        Structs.Vertex[] vertices = new Structs.Vertex[island.getVerticesList().size()];
        int count=0;
        for(Vertex v : island.getVerticesList()){
            Structs.Vertex ve = Structs.Vertex.newBuilder().setX(v.x()).setY(v.y()).build();
            vertices[count]= ve;
            count++;
        }
        result.addAllVertices(List.of(vertices));
        
    }

    private void makeSegments(Island island, Structs.Mesh.Builder result ){
        Structs.Segment[] exported = new Structs.Segment[island.getEdgesList().size()];
        int count=0;
        for (Edge e : island.getEdgesList()){
            Structs.Segment s = Structs.Segment.newBuilder()
                    .setV1Idx(e.V1IDX())
                    .setV2Idx(e.V2IDX()).build();
            Structs.Segment.Builder pc = Structs.Segment.newBuilder(s);
            for(Map.Entry<String, String> entry : e.getProperties().entrySet()){
                Structs.Property tt = Structs.Property.newBuilder()
                        .setKey(entry.getKey())
                        .setValue(entry.getValue())
                        .build();
                pc.addProperties(tt);
            }
            s=pc.build();
            exported[count]=s;
            count++;
        }
        result.addAllSegments(List.of(exported));

    }

    private void makePolygons(Island island, Structs.Mesh.Builder result){
        for(Tile t : island){
            int centroidIdx = t.getCentroidIdx();
            List<Integer> segmentIdxs = new ArrayList<>();
            segmentIdxs=t.getEdgeIdxs();
            List<Integer> neighborIdx = t.getNeighborsIdxList();

            Structs.Polygon exported = Structs.Polygon.newBuilder()
                    .setCentroidIdx(centroidIdx)
                    .addAllSegmentIdxs(segmentIdxs)
                    .addAllNeighborIdxs(neighborIdx).build();
            
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(exported);
            for(Map.Entry<String, String> entry : t.getProperties().entrySet()){
                Structs.Property tt = Structs.Property.newBuilder()
                        .setKey(entry.getKey())
                        .setValue(entry.getValue())
                        .build();
                pc.addProperties(tt);
            }
                
            result.addPolygons(pc);
        }

    }


    private void registerPolygons(Island island, Structs.Mesh.Builder result,
                                  Map<Edge, Integer> segments, Map<Vertex, Integer> vertices) {
        Map<Tile, Integer> polygons = buildPolygonRegistry(island);
        for(Tile p: island) {
            //System.out.println(vertices.keySet().size());
           // System.out.println(vertices.values().size());
            //System.out.println(p.getCentroid()+" "+p.getCentroidIdx());
            int centroidIdx = p.getCentroidIdx();
            List<Integer> segmentIdxs = new ArrayList<>();
            for(Edge pov: p.hull()) {
                segmentIdxs.add(segments.get(pov));
            }
            List<Integer> neigbhoursIdx = new ArrayList<>();
            for(int i : p.getNeighborsIdxList()){
                int neighborIdx = i;
                neigbhoursIdx.add(neighborIdx);
            }            
            
            Structs.Polygon exported = Structs.Polygon.newBuilder()
                    .setCentroidIdx(centroidIdx)
                    .addAllSegmentIdxs(segmentIdxs)
                    .addAllNeighborIdxs(neigbhoursIdx).build();
            

            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(exported);
            for(Map.Entry<String, String> entry : p.getProperties().entrySet()){
                Structs.Property tt = Structs.Property.newBuilder()
                        .setKey(entry.getKey())
                        .setValue(entry.getValue())
                        .build();
                pc.addProperties(tt);
            }
                
            result.addPolygons(pc);
                
        }
    }

    private Map<Edge, Integer> registerSegments(Island island, Structs.Mesh.Builder result,
                                                        Map<Vertex, Integer> vertices ) {
        Map<Edge, Integer> segments = buildSegmentRegistry(island);
        Structs.Segment[] exported = new Structs.Segment[island.getEdgesList().size()];
        int count = 0;
        for(Edge key: island.getEdgesList()){
            Vertex[] contents = key.contents();
            Structs.Segment s = Structs.Segment.newBuilder()
                    .setV1Idx(vertices.get(contents[0]))
                    .setV2Idx(vertices.get(contents[1])).build();
            exported[count] = s;
            count++;
        }
        System.out.println("island edge list: "+island.getEdgesList().size());

        result.addAllSegments(List.of(exported));
        return segments;
    }
    private Map<Vertex, Integer>  registerVertices(Island island, Structs.Mesh.Builder result) {
        Map<Vertex, Integer> vertices = buildVertexRegistry(island);
        Structs.Vertex[] exported = new Structs.Vertex[island.getVerticesList().size()];
        int count=0;
        System.out.println("num island vertices:"+island.getVerticesList().size());
        for(Vertex key: island.getVerticesList()){
            Structs.Vertex v = Structs.Vertex.newBuilder().setX(key.x()).setY(key.y()).build();
            exported[count] = v;
            count++;
        }
        result.addAllVertices(List.of(exported));
        return vertices;
    }

    private Map<Vertex, Integer> buildVertexRegistry(Island island) {
        // Creating the set of all vertices
        List<Vertex> vertices = new ArrayList<>();
        int count=0;
        for (Tile p: island) {
            for(Vertex v: p) {
                vertices.add(v);
                count++;
            }
        }
        System.out.println("count:"+count);
        // building the inverse structure (vertex -> index)
        Map<Vertex, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Vertex v: vertices) {
            registry.put(v, counter++);
        }
        return registry;
    }

    private Map<Edge, Integer> buildSegmentRegistry(Island island) {
        Set<Edge> segments = new HashSet<>();
        for(Tile t : island){
            segments.addAll(t.hull());
        }
        Map<Edge, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Edge pov: segments) {
            registry.put(pov, counter++);
        }
        return registry;
    }

    private Map<Tile, Integer> buildPolygonRegistry(Island island) {
        Map<Tile, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Tile p: island ) {
            registry.put(p, counter++);
        }
        return registry;
    }

}
