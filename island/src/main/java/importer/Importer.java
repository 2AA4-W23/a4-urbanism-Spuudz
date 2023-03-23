package importer;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import IslandADT.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Set.*;
import java.util.List;

public class Importer implements importInterface {

    private Mesh aMesh;
    private List<IslandADT.Vertex> verticeList;
    private List<IslandADT.Edge> edgeList;
    private List<IslandADT.Tile> tileList;
    

    public Importer(Mesh oldMesh){
        aMesh = oldMesh;
    }

    public Island convert(){
        buildVertices();
        buildEdges();
        buildTiles();
        //buildNeighbours();
        Island newIsland = new Island();
        System.out.println(aMesh.getVerticesList().size());
        System.out.println(verticeList.size());
        newIsland.register(tileList, verticeList,edgeList);
        return newIsland;
    }

    public void buildVertices(){
        verticeList=new ArrayList<IslandADT.Vertex>();
        int count = 0;
        for(Vertex v : aMesh.getVerticesList()){
            IslandADT.Vertex vertex = new IslandADT.Vertex((float)v.getX(), (float)v.getY(),count);
            verticeList.add(vertex);
            count++;
        }
    }

    public void buildEdges(){
        edgeList = new ArrayList<IslandADT.Edge>();
        for (Segment s : aMesh.getSegmentsList()){
            IslandADT.Edge newEdge = new IslandADT.Edge(verticeList.get(s.getV1Idx()), verticeList.get(s.getV2Idx()));
            edgeList.add(newEdge);
        }

    }

    public void buildTiles(){
        tileList = new ArrayList<IslandADT.Tile>();
        for (Polygon p : aMesh.getPolygonsList()){
            List<IslandADT.Vertex> vertices = new ArrayList<IslandADT.Vertex>();
            for(int s : p.getSegmentIdxsList()){
                IslandADT.Vertex [] polyVerticeList =(edgeList.get(s).contents());
                vertices.add(polyVerticeList[0]);
                vertices.add(polyVerticeList[1]);
            }
            

            IslandADT.Tile tile = new IslandADT.Tile(vertices);

            tile.addCentroidIdx(p.getCentroidIdx());
            tile.addCentroid(verticeList.get(p.getCentroidIdx()));
            tile.setEdgeIdxs(p.getSegmentIdxsList());
            tile.setNeighborIdx(p.getNeighborIdxsList());
            tileList.add(tile);
        }
    }

    private void buildNeighbours(){
        int tileIndex = 0;
        for(Polygon p : aMesh.getPolygonsList()){
            IslandADT.Tile tile = tileList.get(tileIndex);
            for(int n : p.getNeighborIdxsList()){
                tile.registerAsNeighbourIdx(n);
            }
            tileList.set(tileIndex, tile);
        }
    }


    
}
