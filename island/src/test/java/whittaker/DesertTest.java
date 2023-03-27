package whittaker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import IslandADT.*;
import whittaker.desert.Desert;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class DesertTest {
    
    @BeforeAll
    public static void startTesting(){
        System.out.println("Beginning Arctic Biome Testing");
    }

    @AfterAll
    public static void endTesting(){
        System.out.println("Finishing Arctic Biome testing");
    }

    @Test
    public void genWhittaker(){
        Desert desert = new Desert();
        Island island = new Island();
        
        island=registerTestIsland();
        island=desert.genWhittaker(island);
        assertEquals("oasis",(island.getTiles(0).getProperties().get("tile_type")));
        assertEquals("savanna",island.getTiles(1).getProperties().get("tile_type"));
        assertEquals("savanna",(island.getTiles(2).getProperties().get("tile_type")));

    }

    public static Island registerTestIsland(){
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        vertices.add( new Vertex(10, 0, 1));
        vertices.add(new Vertex(20, 0, 2));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        t.setProperty("tile_type", "land");
        t.setProperty("elevation", "230.0");
        t.setProperty("humidity", "90.0");
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        t2.setProperty("tile_type", "land");
        t2.setProperty("elevation", "120.0");
        t2.setProperty("humidity", "20.0");
        tiles.add(t2);
        Tile t3 = new Tile();
        t3.addCentroid(vertices.get(2));
        t3.setProperty("tile_type", "land");
        t3.setProperty("elevation", "80.0");
        t3.setProperty("humidity", "60.0");
        tiles.add(t3);

        island.register(tiles, vertices, null);
        return island;

       
    }
}
