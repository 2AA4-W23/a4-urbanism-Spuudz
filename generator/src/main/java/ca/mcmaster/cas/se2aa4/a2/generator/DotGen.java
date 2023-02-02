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

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        List<Structs.Segment> segments = new ArrayList<>();
        int count = 0;
        int temp = 0;
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());

/*                 segments.add(Structs.Segment.newBuilder().setV1Idx(count).setV2Idx(count+1).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(count).setV2Idx(count+2).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(count+3).setV2Idx(count+1).build());
                segments.add(Structs.Segment.newBuilder().setV1Idx(count+3).setV2Idx(count+2).build());

                count+=2; */
            }
        }
/*         Vertex temp;
        for(int i = 0; i < vertices.size(); i++){
            for(int j = 0; j < vertices.size(); j++){
                if(vertices.get(j).getX() < vertices.get(i).getX()){
                    temp = vertices.get(j);
                    vertices.set(j,vertices.get(i));
                    vertices.set(i,temp);
                }
                else if(vertices.get(j).getX() == vertices.get(i).getX() && vertices.get(j).getY() < vertices.get(i).getY()){
                    temp = vertices.get(j);
                    vertices.set(j,vertices.get(i));
                    vertices.set(i,temp);
                }
            }
        } */
        for(int x = 0; x < vertices.size()-3;x+=4){
            int v1 = x;
            int v2 = x+1;
            int v3 = x+2;
            int v4 = x+3;
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v3).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v2).setV2Idx(v4).build());
            segments.add(Structs.Segment.newBuilder().setV1Idx(v3).setV2Idx(v4).build());
        }
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
            System.out.println(v.getX());
            System.out.println(v.getY());
        }


        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
