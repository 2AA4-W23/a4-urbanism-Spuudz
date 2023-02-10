package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.SegmentOrBuilder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 100;
    private final int height = 100;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        List<Structs.Segment> segments = new ArrayList<>();
        List<Polygon> PolygonList = new ArrayList<Polygon>();
        List<Integer> index = new ArrayList<Integer>();
        int count = 0;
        int temp = 0;
        List<Integer> indexOrderVertical = new ArrayList<Integer>();
        List<Integer> indexOrderHorizontal = new ArrayList<Integer>();
        // Create all the vertices
        for(int x = 0; x <= width; x += 2*square_size) {
            for(int y = 0; y <= height; y += 2*square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
                


            }
        }

        for(int x = 0; x < vertices.size()-1;x+=4){

            int v1 = x;
            int v2 = x+1;
            int v3 = x+2;
            int v4 = x+3;
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v3).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2).setV2Idx(v4).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v3).setV2Idx(v4).build());
            
            
            System.out.println(vertices.get(v4).getY());
            if(vertices.get(v4).getY()+square_size<height){
                indexOrderVertical.add(v4);
                indexOrderVertical.add(v4+1);
            }
            
            

            index.add(v1);
            index.add(v2);
            index.add(v3);
            index.add(v4);
            
        }
        int differenceOdd=3+4*((height/square_size)-((height/square_size)/2)-1); //difference between left and right lines in terms of index
        int differenceEven = 2*(((height/square_size)-((height/square_size)/2)-1))-1;
        int y=1;
        int counter=0;
        int columns=0;
        int tempSegmentSize=segments.size()-3;
        for(int x=2; x<vertices.size()-3;x+=4){//vertical lines
            int v1 = x;
            int v2 = x+1;
            int v3 = x+2;
            int v4 = x+3;
            double end = vertices.get(v1).getY();
            //System.out.println(end);

            if(tempSegmentSize>=(x+differenceOdd)){
                if(y==1){
                    indexOrderHorizontal.add(x);
                    indexOrderHorizontal.add(x+differenceOdd);
                    indexOrderHorizontal.add(segments.size()+1-2*columns);
                    indexOrderHorizontal.add(segments.size() + differenceEven+1-2*columns);
                }else if(y!=1){
                    indexOrderHorizontal.add(x);
                    indexOrderHorizontal.add(x+differenceOdd);
                    
                }
            }
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v3).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2).setV2Idx(v4).build());
            if(vertices.get(v4).getY()+square_size==height){
                y=0;
                columns++;
            }else{
                y=1;
            }
            counter++;
                
            if((int)end==height){
                continue;
            }

        }
        int v2 = height/square_size*2 +2;

        for (int x=1; x<vertices.size()-1; x+=2){//horizontal lines
            int v1 = x;
            
            if(vertices.get(x).getX()==width){
                continue;
            }
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build());
            v2+=2;
            

            
        }
        System.out.println(indexOrderHorizontal);

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }
        for(Vertex v : vertices){
           // System.out.println(v.getX());
            //System.out.println(v.getY());
        }

        for(int i = 0; i < index.size(); i+=4){
            PolygonList.add(Polygon.newBuilder().addSegmentIdxs(index.get(i)).addSegmentIdxs(index.get(i+1)).addSegmentIdxs(index.get(i+2)).addSegmentIdxs(index.get(i+3)).build());
        }


        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).addAllPolygons(PolygonList).build();
    }

}
