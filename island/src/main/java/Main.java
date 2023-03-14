import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import configuration.*;
import shapes.ShapeSpecificationFactory;
import shapes.Shapes;
public class Main{
    public static void main(String[] args) throws Exception{
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        lagoon newLagoon = new lagoon(aMesh,config);
        aMesh=newLagoon.identify();
        new MeshFactory().write(aMesh, "img/island.mesh");
    }
}