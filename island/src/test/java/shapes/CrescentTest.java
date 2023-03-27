package shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import IslandADT.*;


public class CrescentTest {

    @Test
    public void testOcean(){
        Crescent shape = new Crescent();
        Island testIsland = new Island();
        testIsland = registerTestIsland();
        Tile t = testIsland.getTiles(0);

        assertEquals("Ocean", shape.assignType(t, 1, 0, 0, testIsland, 0).getProperties().get("tile_type"));
        assertEquals("Ocean", shape.assignType(t, 0, 1, 2, testIsland, 0).getProperties().get("tile_type"));
    }

    @Test
    public void testForest(){
        Crescent shape = new Crescent();
        Island testIsland = new Island();
        testIsland = registerTestIsland();
        Tile t = testIsland.getTiles(0);

        assertEquals("Land", shape.assignType(t, 0, 2, 1, testIsland, 0).getProperties().get("tile_type"));
    }


    public static Island registerTestIsland(){
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        tiles.add(t);

        island.register(tiles, vertices, null);

        return island;
    } 
}
