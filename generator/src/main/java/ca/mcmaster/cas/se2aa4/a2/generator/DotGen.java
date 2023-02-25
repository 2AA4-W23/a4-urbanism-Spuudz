package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;


public class DotGen {
    MeshADT mesh = new MeshADT();
    public Mesh generate(String gridType, int numPolygons, int numRelax){
        return mesh.generate(gridType, numPolygons, numRelax);
    }
    
    

}
