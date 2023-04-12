package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.util.List;
import java.util.Optional;

public class CityProperty implements PropertyAccess<Integer>{

    @Override
    public Optional<Integer> extract(List<Property> props) {
        String value = new Reader(props).get("city");

        if (value == null)
            return Optional.of(-1);

        return Optional.of(Integer.parseInt(value));
        
    }

    
}
