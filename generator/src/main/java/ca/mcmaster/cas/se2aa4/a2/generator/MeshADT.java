package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import java.util.ArrayList;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import java.util.Random;

public class MeshADT {
    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private List<Polygon> PolygonList = new ArrayList<Polygon>();
    private List<Vertex> vertices = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();
    private List<Integer> segmentIndices = new ArrayList<Integer>();
    private List<Integer> inBetweenIndices= new ArrayList<Integer>(); //list to represent the indices of segments for squares generated not in odd x-y pairs
    private List<Vertex> verticesWithColors = new ArrayList<>();
    
    public Mesh generate(){
        generateVertices();
        generateSegments();
        colorVertices();
        polygonGenerator();

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).addAllPolygons(PolygonList).build();
    }
    private void generateVertices(){
        for(int x = 0; x <= width; x += 2*square_size) {
            for(int y = 0; y <= height; y += 2*square_size) {
                Property centroid = Property.newBuilder().setKey("centroid").setValue("False").build();
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).addProperties(centroid).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).addProperties(centroid).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).addProperties(centroid).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).addProperties(centroid).build());
            }
        }
    }

    private void generateSegments(){
        //First for loop will create segments that create squares on every odd x & y square ie in 3x3 grid squares will be made (1,1), (3,1) etc
        for(int x = 0; x < vertices.size()-1;x+=4){
            int v1 = x;
            int v2 = x+1;
            int v3 = x+2;
            int v4 = x+3;
            segments.add(Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build());
            segments.add(Segment.newBuilder().setV1Idx(v1).setV2Idx(v3).build());
            segments.add(Segment.newBuilder().setV1Idx(v2).setV2Idx(v4).build());
            segments.add(Segment.newBuilder().setV1Idx(v3).setV2Idx(v4).build());
            
            segmentIndices.add(v1);
            segmentIndices.add(v2);
            segmentIndices.add(v4);
            segmentIndices.add(v3);
            
        }


        int differenceOdd=3+4*((height/square_size)-((height/square_size)/2)-1); //difference between left and right lines in terms of inBetweenIndices
        int differenceEven = 2*(((height/square_size)-((height/square_size)/2)-1))-1;  
        boolean endColumn= true;
        int lastColumnSquareCount=0; //variable to store the number of squares created on the last column
        int columns=0; //number of columns finished
        int tempSegmentSize=segments.size()-3; //store the second last inBetweenIndices from segment generaton //remove -3?
        for(int x=2; x<vertices.size()-2;x+=4){//vertical lines

            double end = vertices.get(x+3).getY();
            int v1 = x;
            int v2 = x+1;
            int v3 = x+2;
            int v4 = x+3;
            
            if(tempSegmentSize>=(x+differenceOdd)){
                if(endColumn){

                    inBetweenIndices.add(x);
                    inBetweenIndices.add(x+differenceOdd);
                    inBetweenIndices.add(segments.size()+1-2*columns);
                    inBetweenIndices.add(segments.size() + differenceEven+1-2*columns);
                    

                }else{
                    inBetweenIndices.add(x);
                    inBetweenIndices.add(x+differenceOdd);
                    
                }
            }    
            if((int)end==height||vertices.get(v1).getY()==height){//if we reach the end of one column we dont add the segments
                endColumn=true;
                continue;
            }
            segments.add(Segment.newBuilder().setV1Idx(v1).setV2Idx(v3).build());
            segments.add(Segment.newBuilder().setV1Idx(v2).setV2Idx(v4).build());

            if(vertices.get(v4).getY()+square_size==height || vertices.get(v2).getY()+square_size==height){ //reaching the end of one column of squares in the next loop
                endColumn=false;
                columns++;
            }else{ 
                //y=1;//redundant?
            }
            if(vertices.get(v4).getX()==width||vertices.get(v3).getX()==width||vertices.get(v2).getX()==width){ //reached the end of the width given therefore, number of squares left to generate is known which is what count represents
                lastColumnSquareCount++;
            }
            if(lastColumnSquareCount==(height/square_size)/2){ //if we finish the last square break the loop
                break;
            }


        }

        tempSegmentSize += 3;
        for(int x=0; x<vertices.size()-3;x+=4){ 
            int v4 = x+3;
            if(vertices.get(v4).getY()+square_size<height){
                segmentIndices.add(v4);
                segmentIndices.add(tempSegmentSize);
                segmentIndices.add(v4+1);
                segmentIndices.add(tempSegmentSize+1);
                
            }else{
                tempSegmentSize -= 2;
            }
            tempSegmentSize += 2;

        }

        int v2 = height/square_size*2 +2;
        for (int x=1,i=0; x<vertices.size(); x+=4){//horizontal lines
            int v1 = x;
            if(vertices.get(x).getX()>=width){
                System.out.println("width break");
                v2+=2;
                break;
            }
            segments.add(Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build());
            segments.add(Segment.newBuilder().setV1Idx(v1+2).setV2Idx(v2+2).build());
            v2+=4;

            if(i==inBetweenIndices.size()){
                break;
            }

            if(vertices.get(segments.get(segments.size()-2).getV1Idx()).getY()==0){
                System.out.println("true");
                segmentIndices.add(segments.size()-2);
                segmentIndices.add(inBetweenIndices.get(i));
                segmentIndices.add(segments.size()-1);
                segmentIndices.add(inBetweenIndices.get(i+1));
                i+=2;
            }else{
                segmentIndices.add(segments.size()-3);
                segmentIndices.add(inBetweenIndices.get(i));
                segmentIndices.add(segments.size()-2);
                segmentIndices.add(inBetweenIndices.get(i+1));

                segmentIndices.add(segments.size()-2);
                segmentIndices.add(inBetweenIndices.get(i+2));
                segmentIndices.add(segments.size()-1);
                segmentIndices.add(inBetweenIndices.get(i+3));
                i+=4;
            }

        }

    }
    private void colorVertices(){
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
    }
    
    private void polygonGenerator(){
        int centCounter = vertices.size();
        int vertBefore = vertices.size();
        ArrayList<Vertex> centroids = new ArrayList<>();
        for(int i = 0; i < segmentIndices.size(); i+=4){
            
            double xVal = 0;
            double yVal = 0;

            for (int j = 0; j < 4; j++) {
                xVal += vertices.get(segments.get(segmentIndices.get(i)).getV1Idx()).getX();
                xVal += vertices.get(segments.get(segmentIndices.get(i)).getV2Idx()).getX();

                yVal = vertices.get(segments.get(segmentIndices.get(i)).getV1Idx()).getY();
                yVal += vertices.get(segments.get(segmentIndices.get(i)).getV2Idx()).getY();
            }

            xVal = xVal/8.0;
            yVal = yVal/2.0 + square_size/2;

            vertices.add(Vertex.newBuilder().setX((double) xVal).setY((double) yVal).build());
            PolygonList.add(Polygon.newBuilder().addSegmentIdxs(segmentIndices.get(i)).addSegmentIdxs(segmentIndices.get(i+1)).addSegmentIdxs(segmentIndices.get(i+2)).addSegmentIdxs(segmentIndices.get(i+3)).setCentroidIdx(centCounter).build());
            centCounter++;
        }

        for (int i = vertBefore; i < centCounter; i++) {
            int red = 0;
            int green = 0;
            int blue = 0;
            
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Property centroid = Property.newBuilder().setKey("centroid").setValue("True").build();
            Vertex colored = Vertex.newBuilder(vertices.get(i)).addProperties(color).addProperties(centroid).build();
            verticesWithColors.add(colored);
        }
    }
}