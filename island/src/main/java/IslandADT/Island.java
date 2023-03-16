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

    public List<Vertex> getVerticesList(){
        return java.util.Collections.unmodifiableList(vertices);
    }

    public Vertex getVertices(int index){
        return vertices.get(index);
    }

    public List<Edge> getEdgesList(){
        return java.util.Collections.unmodifiableList(edges);
    }
    

    public List<Tile> getTileList(){
        return java.util.Collections.unmodifiableList(Tiles);
    }

    public Tile getTiles(int index){
        return Tiles.get(index);
    }


    @Override
    public Iterator<Tile> iterator() {
        return this.Tiles.iterator();
    }
}
