import IslandADT.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import water.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class lakesTest {

    @Test
    public void testLandIdentification(){
        Island island = new Island();
        island = registerTestIsland();
        Lakes myLake = new Lakes(0);

        assertEquals(3, myLake.numOfLandTiles(island));
    }
    @Test
    public void generatingLakes(){
        Island island = new Island();
        island = registerTestIsland();
        Lakes myLake = new Lakes(1);

        myLake.generateLakes(island);
        assertEquals(2, myLake.numOfLandTiles(island));
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
        t.setProperty("tile_type", "Forest");
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        t2.setProperty("tile_type", "Forest");
        tiles.add(t2);
        Tile t3 = new Tile();
        t3.addCentroid(vertices.get(2));
        t3.setProperty("tile_type", "Forest");
        tiles.add(t3);

        island.register(tiles, vertices, null);

        return island;
    } 
}
