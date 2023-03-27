import IslandADT.*;
import water.Aquifers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class aquiferTests {
    
    public void generateAquifersTest(){
        Island testIsland = registerTestIsland();
        Aquifers newAq = new Aquifers(1);

        testIsland = newAq.generateAquifers(testIsland);
        assertEquals("Aquifer", testIsland.getTiles(0).getProperties().get("tile_type"));
    }

    public static Island registerTestIsland(){
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        t.setProperty("tile_type", "Land");
        tiles.add(t);

        island.register(tiles, vertices, null);

        return island;
    } 
}
