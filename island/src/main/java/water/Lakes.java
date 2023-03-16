package water;

import java.util.Map;
import configuration.Configuration;
import java.util.Random;

import Tiles.TileType;
import Tiles.TileTypeChoose;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

public class Lakes {
    private int numOfLakes;

    public Lakes(Configuration config){
        Map<String, String> options = config.export();
        numOfLakes = Integer.parseInt(options.get(Configuration.LAKES));
    }

    public int numOfLandTiles(Mesh aMesh){
        String tile = "";
        int count = 0;
        for(Polygon p : aMesh.getPolygonsList()){
            for(Property property : p.getPropertiesList()){
                if((property.getKey()).equals("tile_type")){
                    tile = property.getValue();
                }
            }
            if(tile.equals("Forest")){count++;}
        }
        return count;
    }

    public Mesh generateLakes(Mesh aMesh){
        Random rand = new Random();
        String tileType = "";
        TileTypeChoose tile = new TileTypeChoose();
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(aMesh.getVerticesList());
        clone.addAllSegments(aMesh.getSegmentsList());
        int currentNum = 0;

        while(currentNum < numOfLakes){
            for(Polygon p : aMesh.getPolygonsList()){
                Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(p);
                for(Property property : p.getPropertiesList()){
                    if(property.getKey().equals("tile_type")){
                        tileType = property.getValue();
                    }
                }
                if(tileType.equals("Forest")){
                    int random = rand.nextInt(5)+1;
                    if(currentNum < numOfLakes){
                        if(random == 1){
                            Structs.Property tt = Structs.Property.newBuilder()
                                    .setKey("tile_type")
                                    .setValue(tile.getColor(TileType.Lake))
                                    .build();
                            pc.setProperties(0,tt);

                            Structs.Property pr = Structs.Property.newBuilder()
                                    .setKey("rgb_color")
                                    .setValue(tile.getColor(TileType.Lake))
                                    .build();
                            pc.setProperties(1,pr);

                            currentNum++;
                        }
                    }
                }
                System.out.println(currentNum);
                clone.addPolygons(pc);
            }
        }
        return clone.build();
     }
}
