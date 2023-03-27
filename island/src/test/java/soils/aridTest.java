package soils;

import IslandADT.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import soil.*;

public class aridTest {

    public void waterDistanceTest(){
        Island testIsland = registerTestIsland();
        Arid newSoil = new Arid();  

        Tile t1 = testIsland.getTiles(0);
        Tile t2 = testIsland.getTiles(1);
        double distance = Math.sqrt(Math.pow((t1.getCentroid().getX() - t2.getCentroid().getX()),2) + Math.pow((t1.getCentroid().getY() - t2.getCentroid().getY()),2));
        
        assertEquals(distance, newSoil.distanceFromWater(testIsland, testIsland.getTiles(1)));
    }


    public static Island registerTestIsland(){
        Island island = new Island();
        List<Vertex> vertices= new ArrayList<>();
        List<Tile> tiles= new ArrayList<>();
        vertices.add( new Vertex(0, 0, 0));
        vertices.add( new Vertex(10, 0, 1));
        
        Tile t = new Tile();
        t.addCentroid(vertices.get(0));
        t.setProperty("tile_type", "Lake");
        tiles.add(t);
        Tile t2 = new Tile();
        t2.addCentroid(vertices.get(1));
        t2.setProperty("tile_type", "land");
        tiles.add(t2);

        island.register(tiles, vertices, null);
        return island;
    }
    
}
