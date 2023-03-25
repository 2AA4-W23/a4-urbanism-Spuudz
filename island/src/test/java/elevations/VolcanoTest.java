package elevations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import IslandADT.*;
import elevation.Volcano;

public class VolcanoTest {
    @Test
    public void centreDistance(){
        Volcano volcano = new Volcano();
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        vertices.add( new Vertex(10, 0, 1));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        tiles.add(t2);
        island.register(tiles, vertices, null);
        assertEquals(10, volcano.distanceFromCentre(0, island, t2));
        

    }
    @Test
    public void testCentrePolyon(){
        Volcano volcano = new Volcano();
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        vertices.add( new Vertex(10, 0, 1));
        vertices.add(new Vertex(20, 0, 2));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        t.setProperty("tile_type", "land");
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        t2.setProperty("tile_type", "land");
        tiles.add(t2);
        Tile t3 = new Tile();
        t3.addCentroid(vertices.get(2));
        t3.setProperty("tile_type", "land");
        tiles.add(t3);

        island.register(tiles, vertices, null);
        assertEquals(1, volcano.findStartIdx(island));
    }
}
