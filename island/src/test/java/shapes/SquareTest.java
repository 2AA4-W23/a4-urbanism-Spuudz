package shapes;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import IslandADT.*;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


public class SquareTest {
    @BeforeAll
    public static void startTesting(){
        System.out.println("Beginning Jungle Biome Testing");
    }

    @AfterAll
    public static void endTesting(){
        System.out.println("Finishing Jungle Biome testing");
    }

    @Test
    public void genWhittaker(){
        Square square = new Square(null);
        Island island = new Island();
        
        island=registerTestIsland();
        island=square.identifyLand(island,180, 180, 314, 314);
        assertEquals("Land",(island.getTiles(0).getProperties().get("tile_type")));
        assertEquals("Land",island.getTiles(1).getProperties().get("tile_type"));

    }

    public static Island registerTestIsland(){
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(90, 90, 0));
        vertices.add( new Vertex(200, 200, 1));
        vertices.add(new Vertex(150, 150, 2));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        tiles.add(t2);

        island.register(tiles, vertices, null);
        return island;

       
    }
}
