package shapes;
import IslandADT.*;
import org.locationtech.jts.geom.Geometry;


public interface Shapes {

    public Island identifyLand(Island currentIsland, double centerX, double centerY,double width, double height);

}
