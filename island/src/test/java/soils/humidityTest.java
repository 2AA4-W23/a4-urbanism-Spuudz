package soils;

import IslandADT.*;
import soil.SoilProfileFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class humidityTest{

    @Test
    public void defaultHumidityTest(){
        Island testIsland = registerTestIsland();
        SoilProfileFactory newSoil = new SoilProfileFactory();

        newSoil.defaultHumidity(testIsland);

        assertEquals("100", testIsland.getTiles(0).getProperties().get("humidity"));
        assertEquals("0", testIsland.getTiles(1).getProperties().get("humidity"));
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