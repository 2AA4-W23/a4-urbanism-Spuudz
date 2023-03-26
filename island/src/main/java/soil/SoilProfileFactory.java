package soil;
import java.util.HashMap;
import java.util.Map;
import IslandADT.*;
import configuration.Configuration;

public class SoilProfileFactory {
    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("arid", Arid.class);
     }

    public static SoilProfiles create(Configuration configuration) {
        Map<String, String> options = configuration.export();
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class klass = bindings.get(options.get(Configuration.SOILPROFILE));
            return (SoilProfiles) klass.getDeclaredConstructor(Map.class).newInstance(options);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Island defaultHumidity(Island anIsland){
        for(Tile t : anIsland.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("humidity","99");
            }
            else if(!(t.getProperties().get("tile_type").equals("Lake") || t.getProperties().get("tile_type").equals("Aquifer"))){
                t.setProperty("humidity","0");
            }
            else {
                t.setProperty("humidity","100");
            }
        }
        return anIsland;
    }
}