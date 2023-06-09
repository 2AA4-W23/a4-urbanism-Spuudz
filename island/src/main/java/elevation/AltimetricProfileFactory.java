package elevation;
import java.util.HashMap;
import java.util.Map;
import configuration.Configuration;

public class AltimetricProfileFactory {
    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("volcano", Volcano.class);
        bindings.put("hills",Hills.class);
        bindings.put("plateau",Plateau.class);

     }

    public static AltimetricProfiles create(Configuration configuration) {
        Map<String, String> options = configuration.export();
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class klass = bindings.get(options.get(Configuration.PROFILE));
            return (AltimetricProfiles) klass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    


}
