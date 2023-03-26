package elevation;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import IslandADT.*;
import seeds.Seed;

public class Hills implements AltimetricProfiles {
    private static final double maxAltitude=150;
    private static int numHills=5;
    private Seed newSeed = new Seed();

    public Hills(){

    }

    public Seed returnSeed(){
        return newSeed;
    }
    public Island assignElevation(Island island,Seed seed){
        
        if(numHills>island.getLandTiles().size()){
            numHills=island.getLandTiles().size()/3;
            if(numHills==0){
                numHills=1;
            }
        }
        Set<Integer> hilltops = new HashSet<>();
        
        if(seed.input()){ //if there is an input seed
            System.out.println("hello");
            for(int i=0;i<numHills;i++){
                int idx = findStartIdx(seed);
                System.out.println("hills idx"+idx);
                hilltops.add(idx);
            }
            newSeed=seed;
        }else{
            while(hilltops.size()<numHills){
                int idx=findStartIdx(island);
                newSeed.addToSeed(Integer.toString(idx));
                hilltops.add(idx);
            }
        }
    
        for(int i : hilltops){
            island.getTiles(i).setProperty("elevation", (Integer.toString((int)maxAltitude)));
        }

        for(Tile t : island.getTileList()){
            if(t.getProperties().get("tile_type").equals("Ocean")){
                t.setProperty("elevation", "0");
            }else{
                double elevation = maxAltitude-(nearestDistance(hilltops, island, t)/3);
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
    public int findStartIdx(Seed seed){
        return seed.returnCurrent();
        
    }
    

}
