import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import configuration.*;
import exporter.export;
import importMesh.Importer;
import shapes.ShapeSpecificationFactory;
import shapes.Shapes;
import water.*;
import IslandADT.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Set.*;
public class Main{
    public static void main(String[] args) throws Exception{
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        //lagoon newLagoon = new lagoon(aMesh,config);
        //aMesh=newLagoon.identify();
        
        Island newIsland = new Island();
        Importer importMesh = new Importer(aMesh);
        newIsland=importMesh.convert();
        lagoon newLagoon = new lagoon(newIsland,config);
        newIsland = newLagoon.identify();
        System.out.println(newIsland);
        
        export exporter = new export();
        aMesh=exporter.run(newIsland);
        new MeshFactory().write(aMesh, "img/island.mesh");
    }
}