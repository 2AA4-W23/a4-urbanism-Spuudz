package shapes;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;


import IslandADT.*;
import shapes.*;
public class CircleTest {
    @Test
    public void testDistance(){
        final Circle myCircle = new Circle();
        assertEquals(10.0, myCircle.distanceFromCentre(0,0,10.0,0));
        assertNotEquals(100.0, myCircle.distanceFromCentre(100,100,100,50));
        
    }

    @Test
    public void testOcean(){
        final Circle myCircle = new Circle();
        assertEquals(0, myCircle.assignType(25, 20));
        assertEquals(-1, myCircle.assignType(50,100));
        Tile t = new Tile();
        t=myCircle.assignOcean(t);
        assertEquals("Ocean",t.getProperties().get("tile_type") );
    }

}
