package elevation;

import java.util.Map;
import IslandADT.*;

public class Plateau implements AltimetricProfiles {
    private static final double maxAltitude = 250;
    public Plateau(){

    }

    public Island assignElevation(Island island){
        Island clone = new Island();
        clone.register(island.getTileList(), island.getVerticesList(), island.getEdgesList());
        clone.setLandTiles(island.getLandTiles());
        int centerIDX = findStartIdx(clone);
        Tile t = clone.getTiles(centerIDX);
        t.setProperty("elevation", Integer.toString((int)maxAltitude));
        for(Integer i : t.getNeighborsIdxList()){
            if(clone.getTiles(i).getProperties().get("tile_type").equals("Ocean")){
                clone.getTiles(i).setProperty("elevation", "0");
            }else if(!clone.getTiles(i).getProperties().containsKey("elevation")){
                double elevation=distanceFromCentre(centerIDX, island, clone.getTiles(i));
                elevation=maxAltitude-elevation/10;
                clone.getTiles(i).setProperty("elevation",Integer.toString((int)elevation));

            }
            for(Integer j : clone.getTiles(i).getNeighborsIdxList()){
                if(clone.getTiles(j).getProperties().get("tile_type").equals("Ocean")){
                    clone.getTiles(j).setProperty("elevation", "0");
                }else if(!clone.getTiles(j).getProperties().containsKey("elevation")){
                    double elevation=distanceFromCentre(centerIDX, island, clone.getTiles(j));
                    elevation=maxAltitude-elevation/10;
                    clone.getTiles(j).setProperty("elevation",Integer.toString((int)elevation));
    
                }
            }

            
        }
        for(Tile tile : clone.getTileList()){
            if(!tile.getProperties().containsKey("elevation")){
                if(tile.getProperties().get("tile_type").equals("Ocean")){
                    tile.setProperty("elevation", "0");
                }else{
                    double elevation=maxAltitude-distanceFromCentre(centerIDX, island, tile);
                    if(elevation<0){
                        elevation=1;
                    }
                    tile.setProperty("elevation",Integer.toString((int)elevation));

                }

            }
        }
        return clone;

    }

    public int findStartIdx(Island island){
        int islandTile=0;
        double totalX=0;
        double totalY=0;

        for(Tile t : island.getTileList()){
            if(!(t.getProperties().get("tile_type").equals("Ocean"))){
                islandTile++;
                totalX+=t.getCentroid().getX();
                totalY+=t.getCentroid().getY();


            }

        }

        double centerX = totalX/islandTile;
        double centerY = totalY/islandTile;
        System.out.println("center x: "+centerX+" center y: "+centerY);
        double minDistance=Double.MAX_VALUE;
        int count=0;
        int centerIDX=0;
        for(Tile t : island.getTileList()){
            double newDistance = Math.sqrt(Math.pow(t.getCentroid().getX()-centerX,2)+Math.pow(t.getCentroid().getY()-centerY,2));
            if(newDistance<minDistance){
                minDistance=newDistance;
                centerIDX=count;
            }
            count++;

        }
        return centerIDX;
    }

    private double distanceFromCentre(int centerIDX, Island island, Tile t){
        double centerX = island.getTiles(centerIDX).getCentroid().getX();
        //System.out.println(centerX);
        double centerY = island.getTiles(centerIDX).getCentroid().getY();
        //System.out.println(centerY);

        return Math.sqrt(Math.pow(t.getCentroid().getX()-centerX,2)+Math.pow(t.getCentroid().getY()-centerY,2));

        

    }



}
