package whittaker;
import whittaker.jungle.Jungle;

import java.util.HashMap;
import java.util.Map;
import configuration.Configuration;

public class WhittakerSpecificationFactory {
    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("jungle", Jungle.class);  
        /*bindings.put("wasteland", Wasteland.class);
        bindings.put("square", Square.class);*/
     }

    public static Whittaker create(Configuration configuration) {
        Map<String, String> options = configuration.export();
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class klass = bindings.get(options.get(Configuration.WHITTAKER));
            return (Whittaker) klass.getDeclaredConstructor(Map.class).newInstance(options);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
