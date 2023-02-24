import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.generator.MeshADT;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DotGen generator = new DotGen();
        Mesh myMesh;
        if(args[1].equals("-h") || args[1].equals("--help")){
            System.out.println("Please enter the information in the command line in the following order (with spaces in between): GridType PolygonNumber RelaxationLevel");
            System.out.println("If any information is missing, default values will be supplied");
        }
        else {
            try {
                myMesh = generator.generate(args[1], Integer.parseInt(args[2]),Integer.parseInt(args[3]));
            } catch (Exception e) {
                myMesh = generator.generate("Irregular", 100, 300);
            }
            MeshFactory factory = new MeshFactory();
            factory.write(myMesh, args[0]);
        }
        
    }

}
