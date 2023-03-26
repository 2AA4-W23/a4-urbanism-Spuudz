import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import configuration.*;
import elevation.AltimetricProfileFactory;
import elevation.AltimetricProfiles;
import exporter.export;
import importer.Importer;
import importer.importInterface;
import seeds.Seed;
import shapes.ShapeSpecificationFactory;
import shapes.Shapes;
import soil.*;
import water.*;
import whittaker.Whittaker;
import whittaker.WhittakerSpecificationFactory;
import IslandADT.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Set.*;
public class Main{
    public static void main(String[] args) throws Exception{

        Configuration config = new Configuration(args);
        Seed seed = new Seed(config);

        Structs.Mesh aMesh = new MeshFactory().read(config.input());
        //lagoon newLagoon = new lagoon(aMesh,config);
        //aMesh=newLagoon.identify();
        
        Island newIsland = new Island();
        importInterface importMesh = new Importer(aMesh);
        newIsland=importMesh.convert();
        lagoon newLagoon = new lagoon(newIsland,config);
        newIsland = newLagoon.identify();

        AltimetricProfiles profile = new AltimetricProfileFactory().create(config);
        newIsland = profile.assignElevation(newIsland,seed);
        seed=profile.returnSeed();
        System.out.println(seed.getSeed());
        

        Lakes newLake = new Lakes(config);
        newIsland = newLake.generateLakes(newIsland);


        Aquifers newAquifer = new Aquifers(config);
        newIsland = newAquifer.generateAquifers(newIsland);


        SoilProfiles soil_profile = new SoilProfileFactory().create(config);
        newIsland = soil_profile.assignHumidity(newIsland);

        River addRivers = new River(config);
        newIsland = addRivers.generateRivers(newIsland);

        Whittaker newWhittaker = new WhittakerSpecificationFactory().create(config);
        newIsland = newWhittaker.genWhittaker(newIsland);
        
        export exporter = new export();
        aMesh=exporter.run(newIsland);
        new MeshFactory().write(aMesh, "img/island.mesh");
    }
}