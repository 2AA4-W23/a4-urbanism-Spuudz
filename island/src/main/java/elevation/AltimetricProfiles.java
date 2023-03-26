package elevation;

import IslandADT.*;
import seeds.Seed;

public interface AltimetricProfiles {
    Island assignElevation (Island island,Seed seed);
    int findStartIdx (Island island);
    int findStartIdx(Seed seed);
    Seed returnSeed();
    

}
