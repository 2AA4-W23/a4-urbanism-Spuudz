package elevation;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public interface AltimetricProfiles {
    double findVariance(Mesh aMesh);
    Mesh assignElevation (Mesh aMesh);
    

}
