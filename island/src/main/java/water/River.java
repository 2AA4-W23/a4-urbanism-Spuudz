package water;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import configuration.Configuration;
import java.util.Random;
import java.util.Set;

import IslandADT.*;
import Tiles.TileType;
import Tiles.TileTypeChoose;
public class River {
    private Set<Integer>sourceIdxs = new HashSet<>();
    private int numRivers;
    private static final int riverHumidity=10;
    public River(Configuration config){
        Map<String, String> options = config.export();
        numRivers = Integer.parseInt(options.get(Configuration.RIVERS));
    }
    public River(){
        
    }

    public Island generateRivers(Island island){
        Island clone = new Island();
        clone.register(island.getTileList(), island.getVerticesList(), island.getEdgesList());
        clone.setLandTiles(island.getLandTiles());

        for(int i=0;i<numRivers;i++){
            clone = generateARiver(clone);
        }
        return clone;
        


    }

    private int assignStart(List<Integer> landTiles){
        Random rand = new Random();
        int count=0;
        for(int i : landTiles){
            int chance = rand.nextInt(landTiles.size()-count)+1;
            System.out.println(chance);
            if(chance<=2){
                if(sourceIdxs.contains(i)){
                    System.out.println(i);
                    continue;
                }
                sourceIdxs.add(i);
                return i;
            }
            count++;
        }
        return -1;
    }

    public Island generateARiver(Island island){
        Island clone = new Island();
        clone.register(island.getTileList(), island.getVerticesList(), island.getEdgesList());
        clone.setLandTiles(island.getLandTiles());
        System.out.println(clone.getLandTiles().size());
        Random rand = new Random();
        int startIDX = assignStart(clone.getLandTiles());
        while(true){
            if(clone.getTiles(startIDX).getProperties().get("humidity").equals("100")){
                startIDX = assignStart(clone.getLandTiles());
            }else{
                break;
            }
        }
        
        System.out.println("starting: "+startIDX);
        int thickness = rand.nextInt(3)+1;
        boolean riverSource=true;
        TileTypeChoose tile = new TileTypeChoose();
        while(true){
            if(startIDX==-1){
                
                return clone;
            }
            Tile t = clone.getTiles(startIDX);
            List<Integer> neighbour = t.getNeighborsIdxList();
            int nextIDX = lowestElevationIDX(t, neighbour, clone.getTileList());
            if(nextIDX==-1){
                t.setProperty("tile_type", tile.getTile(TileType.Lake));
                t.setProperty("rgb_color", tile.getColor(TileType.Lake));
                return clone;

            }
            if(clone.getTiles(startIDX).getProperties().get("humidity").equals("100")){ //if on a water source end the river
                return clone;
            }else if(clone.getTiles(nextIDX).getProperties().get("humidity").equals("100")){ //the segment will end on a water source
                
                Edge newEdge = new Edge(clone.getTiles(startIDX).getCentroid(), clone.getTiles(nextIDX).getCentroid());
                sourceIdxs.add(nextIDX);
                sourceIdxs.add(startIDX);
                double humidity1 = Double.parseDouble(t.getProperties().get("humidity"));
                humidity1+= thickness*riverHumidity;
                if(humidity1>=100){
                    humidity1=99;
                }
                newEdge.setProperty("river",Integer.toString(thickness));
                if(riverSource){
                    newEdge.setProperty("riverSource", "true");
                    riverSource=false;
                }
                t.setProperty("humidity", Double.toString(humidity1));
                clone.addEdge(newEdge);
                return clone;
            }

            //normal river segment
            
            double humidity1 = Double.parseDouble(t.getProperties().get("humidity"));
            Edge newEdge = new Edge(clone.getTiles(startIDX).getCentroid(), clone.getTiles(nextIDX).getCentroid());
            humidity1+= thickness*riverHumidity;
            if(humidity1>=100){
                humidity1=99;
            }
            t.setProperty("humidity", Double.toString(humidity1));

            double humidity2 = Double.parseDouble(clone.getTiles(nextIDX).getProperties().get("humidity"));
            humidity2+= thickness*riverHumidity;
            if(humidity2>=100){
                humidity2=99;
            }
            t.setProperty("humidity", Double.toString(humidity2));
            if(riverSource){
                newEdge.setProperty("riverSource", "true");
                riverSource=false;
            }
            if(island.getEdgesList().contains(newEdge)){
                int idx = island.getEdgesList().indexOf(newEdge);
                int originalThickness=Integer.parseInt(island.getEdgesList().get(idx).getProperties().get("river"));
                thickness+=originalThickness;

            }
            System.out.println(thickness);
            newEdge.setProperty("river",Integer.toString(thickness));
            clone.addEdge(newEdge);  
            sourceIdxs.add(nextIDX);
            sourceIdxs.add(startIDX);
            
            startIDX=nextIDX;


        }
        
    }
    private int lowestElevationIDX(Tile t, List<Integer> neighbours, List<Tile> tileList){
        Double tileElevation=Double.parseDouble(t.getProperties().get("elevation"));
        Double minElevation = tileElevation;
        int idx = -1;  
        for(int i : neighbours){
            Double neighbourElevation=Double.parseDouble(tileList.get(i).getProperties().get("elevation"));
            if(neighbourElevation<minElevation){
                idx=i;
                minElevation=neighbourElevation;
            }
        }
        if(minElevation==tileElevation){
            return -1;
        }
        return idx;

        
    }
}
