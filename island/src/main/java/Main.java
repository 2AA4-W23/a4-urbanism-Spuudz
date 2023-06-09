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
import cities.*;

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
        landIdentifier newShape = new landIdentifier(newIsland,config);
        newIsland = newShape.identify();

        AltimetricProfiles profile = new AltimetricProfileFactory().create(config);
        newIsland = profile.assignElevation(newIsland,seed);
        seed=profile.returnSeed();
        

        Lakes newLake = new Lakes(config);
        if(seed.input()){
            newIsland = newLake.generateLakes(newIsland, seed);
            seed = newLake.returnSeed();
        }
        else{
            newIsland = newLake.generateLakes(newIsland);
            seed.addToSeed(newLake.returnSeed().getSeed());
        }


        Aquifers newAquifer = new Aquifers(config);
        if(seed.input()){
            newIsland = newAquifer.generateAquifers(newIsland, seed);
            seed = newAquifer.returnSeed();
        }
        else{
            newIsland = newAquifer.generateAquifers(newIsland);
            seed.addToSeed(newAquifer.returnSeed().getSeed());
        }


        SoilProfiles soil_profile = new SoilProfileFactory().create(config);
        newIsland = soil_profile.assignHumidity(newIsland);

        River addRivers = new River(config);
        newIsland = addRivers.generateRivers(newIsland,seed);
        if(seed.input()){
            seed=addRivers.returnSeed();
        }else{
            seed.addToSeed(addRivers.returnSeed().getSeed());
        }
        
        System.out.println(seed.getSeed());

        Whittaker newWhittaker = new WhittakerSpecificationFactory().create(config);
        newIsland = newWhittaker.genWhittaker(newIsland);

        Cities city = new Cities(config);
        newIsland = city.generateCities(newIsland);
        
        export exporter = new export();
        aMesh=exporter.run(newIsland);
        new MeshFactory().write(aMesh, "img/island.mesh");
    }
}