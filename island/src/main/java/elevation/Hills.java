package elevation;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import IslandADT.*;

public class Hills implements AltimetricProfiles {
    private static final double maxAltitude=50;
    private static final int numHills=5;

    public Hills(Map<String, String> params){

    }

    public Island assignElevation(Island island){
        Set<Integer> hilltops = new HashSet<>();
        while(hilltops.size()<numHills){
            hilltops.add(findStartIdx(island));
        }
    
        for(int i : hilltops){
            island.getTiles(i).setProperty("elevation", (Integer.toString((int)maxAltitude)));
        }

        for(Tile t : island.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("elevation", "0");
            }else{
                double elevation = maxAltitude-(nearestDistance(hilltops, island, t)/5);
                if(elevation<0){
                    elevation=1;
                }
                t.setProperty("elevation", Integer.toString((int)elevation));
            }

        }
        return island;

    }
    private double nearestDistance(Set<Integer> hilltop, Island island, Tile t){
        double minDistance = Double.MAX_VALUE;
        for(Integer i : hilltop){
            double distance = Math.sqrt(Math.pow(t.getCentroid().getX()-island.getTiles(i).getCentroid().getX(),2) + Math.pow(t.getCentroid().getY()-island.getTiles(i).getCentroid().getX(),2));
            if(distance<minDistance){
                minDistance=distance;
            }
        }
        return minDistance;

    }

    public int findStartIdx(Island island){
        Random rand = new Random();
        while(true){
            int randomIdx = rand.nextInt(island.getTileList().size());
            if(island.getTiles(randomIdx).getProperties().get("tile_type").equals("Ocean")){
                continue;
            }else{
                return randomIdx;
            }

        }
        
    }
    

}
