package shapes;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.geom.Geometry;


public interface Shapes {

    public Mesh identifyLand(Mesh currentMesh, double centerX, double centerY,double width, double height);

}
