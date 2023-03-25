import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import IslandADT.*;
import elevation.Volcano;
import water.River;
public class riverTests {
    @BeforeAll
    public static void startTesting(){
        System.out.println("Beginning River Testing");
    }

    @AfterAll
    public static void endTesting(){
        System.out.println("Finishing River testing");
    }

    @Test
    public static void riverTest(){
        Island island = registerTestIsland();
        River newRiver = new River();

        island = newRiver.generateARiver(island);

        assertTrue(island.getEdgesList().size()>0);

        int idx1 = island.getEdgesList().get(0).V1IDX();
        int idx2 = island.getEdgesList().get(0).V2IDX();
        int e1 = Integer.parseInt(island.getTiles(idx1).getProperties().get("elevation"));
        int e2 = Integer.parseInt(island.getTiles(idx2).getProperties().get("elevation"));
        assertTrue(e1>e2);
    }

    public static Island registerTestIsland(){
        Island island = new Island();
        Volcano volcano = new Volcano();
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

        return volcano.assignElevation(island);
    } 
}
