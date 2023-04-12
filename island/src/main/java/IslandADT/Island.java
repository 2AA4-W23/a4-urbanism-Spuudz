package IslandADT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class Island implements Iterable<Tile>{
    private List<Tile> Tiles;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private List<Integer> landTiles = new ArrayList<>();

    public Island() {
        this.Tiles = new ArrayList<>();
        this.vertices=new ArrayList<>();
        this.edges=new ArrayList<>();
    }

    public void register(List<Tile> tiles, List<Vertex> vertices, List<Edge> edges) {
        for(Tile t : tiles){
            Tiles.add(t);
        }
        this.vertices=vertices;
        this.edges=edges;
    }

    public void addTile(Tile newTile){

    }
    public void setLandTiles( List<Integer> landTiles){
        this.landTiles=landTiles;
    }

    public List<Integer> getLandTiles(){
        return java.util.Collections.unmodifiableList(landTiles);
    }

    public void addEdge(Edge e){
        edges.add(e);
    }

    public void setVertex(Vertex v){
        vertices.set(v.getIndex(),v);
    }

    public List<Vertex> getVerticesList(){
        return java.util.Collections.unmodifiableList(vertices);
    }

    public Vertex getVertices(int index){
        return vertices.get(index);
    }

    public List<Edge> getEdgesList(){
        return edges;
    }

    public Edge getEdge(int index){
        return edges.get(index);
    }
    

    public List<Tile> getTileList(){
        return java.util.Collections.unmodifiableList(Tiles);
    }

    public Tile getTiles(int index){
        return Tiles.get(index);
    }

    public void addLandTile(int t){
        landTiles.add(t);
    }

    @Override
    public Iterator<Tile> iterator() {
        return this.Tiles.iterator();
    }
}
