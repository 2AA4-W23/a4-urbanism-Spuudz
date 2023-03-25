package elevations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import IslandADT.*;
import elevation.Hills;
import elevation.Volcano;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class HillsTest {
    @BeforeAll
    public static void startTesting(){
        System.out.println("Beginning Hills Profile Testing");
    }

    @AfterAll
    public static void endTesting(){
        System.out.println("Finishing Hills profile testing");
    }

    @Test
    public void randomStartIDX(){
        Hills hills = new Hills();
        Island island = new Island();
        
        island=registerTestIsland();
        int x = hills.findStartIdx(island);
        assertTrue(x>=0);
        assertTrue(x<=2);
    }

    @Test
    public void assignElevation(){
        Hills hills = new Hills();
        Island island = new Island();
        
        island=registerTestIsland();
        island=hills.assignElevation(island);
        assertFalse(Integer.parseInt(island.getTiles(0).getProperties().get("elevation"))==(Integer.parseInt(island.getTiles(1).getProperties().get("elevation")))&&(Integer.parseInt(island.getTiles(2).getProperties().get("elevation")))==Integer.parseInt(island.getTiles(0).getProperties().get("elevation")));

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
        return island;

       
    }
}
