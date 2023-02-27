package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import java.util.ArrayList;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import java.util.Random;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.util.GeometricShapeFactory;

public class MeshADT {
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(10.00));
    List<Coordinate> pointCoordinates = new ArrayList<>();
    List<Coordinate> randomCoords = new ArrayList<>();
    List<Coordinate> squareCoords = new ArrayList<>();
    List<Coordinate> centerCoords = new ArrayList<>();
    private final Double width = 500.0;
    private final Double height = 500.0;
    Envelope boundary = new Envelope(0,width,0,height);
    private int squareSize = 20; //list to represent the indices of segments for squares generated not in odd x-y pairs
    private List<Vertex> verticesWithColors = new ArrayList<>();
    private List<Vertex> irregVertex = new ArrayList<>();
    private List<Segment> irregSegments = new ArrayList<>();
    private List<Polygon> irregPolygons = new ArrayList<>();
    
    public Mesh generate(String gridType, int numPolygons, int numRelax){
        
        if( gridType.equals("Irregular") ){
            List<org.locationtech.jts.geom.Polygon> polygonGen = lloydRelaxation(randomGen(numPolygons), numRelax);
            convert(polygonGen);
            colorVertices(gridType, numPolygons);
            neighborRelation("Irregular");
            
        }
        else if(gridType.equals("Grid")){
            List<org.locationtech.jts.geom.Polygon> polygonGen = squareGen(numPolygons);
            convert(polygonGen);
            colorVertices(gridType, numPolygons);
            neighborRelation("Grid");
        }
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(irregSegments).addAllPolygons(irregPolygons).build();
        
    }
    private void colorVertices(String gridType, int numPolygons){
        Random bag = new Random();
        Property centroid;
        for(Vertex v: irregVertex){
            if(gridType.equals("Grid")){
                if (irregVertex.indexOf(v) < Math.pow((int)Math.sqrt(numPolygons),2)){
                    centroid = Property.newBuilder().setKey("centroid").setValue("True").build();
                }
                else {
                    centroid = Property.newBuilder().setKey("centroid").setValue("False").build();
                }
            }
            else {
                if (irregVertex.indexOf(v) < numPolygons){
                    centroid = Property.newBuilder().setKey("centroid").setValue("True").build();
                }
                else {
                    centroid = Property.newBuilder().setKey("centroid").setValue("False").build();
                }
            }
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).addProperties(centroid).build();
            verticesWithColors.add(colored);
        }
    }
    //For generating the square grid
    private List<org.locationtech.jts.geom.Polygon> squareGen(int numPolygons){
        VoronoiDiagramBuilder diagramBuilder = new VoronoiDiagramBuilder();
        diagramBuilder.setClipEnvelope(boundary);
        int row = (int)Math.sqrt(numPolygons);
        Double x;
        Double y = squareSize/2.0;
        Coordinate newCoord;
        for (int i = 0; i < row; i++) { //generate points
            x = squareSize/2.0;
            for (int j = 0; j < row; j++) { //generate points
                newCoord = new Coordinate(x,y);
                System.out.println(newCoord);
                squareCoords.add(newCoord);
                x += squareSize;
            }
            y += squareSize;
        }
        List<org.locationtech.jts.geom.Polygon> producedPolygons = new ArrayList<>(); //list of produced polygons
        GeometricShapeFactory squareGenFactory = new GeometricShapeFactory();

        for (int i = 0; i < squareCoords.size(); i++) {
            squareGenFactory.setCentre(squareCoords.get(i));
            squareGenFactory.setWidth(squareSize);
            squareGenFactory.setHeight(squareSize);
            producedPolygons.add(squareGenFactory.createRectangle());
        }

        for(Coordinate coord:squareCoords){
                Vertex newVertex = Vertex.newBuilder().setX(coord.getX()).setY(coord.getY()).build();
                irregVertex.add(newVertex);
        } 
        centerCoords = squareCoords;

        return producedPolygons;
    }
    private List<org.locationtech.jts.geom.Polygon> randomGen(int numPolygons){
        VoronoiDiagramBuilder diagramBuilder = new VoronoiDiagramBuilder();
        diagramBuilder.setClipEnvelope(boundary);
        Random rand = new Random();
        Double x;
        Double y;
        Coordinate newCoord;
        for (int i = 0; i < numPolygons; i++) { //generate random points
            x=rand.nextDouble()*width;
            y=rand.nextDouble()*height;
            newCoord=new Coordinate(x,y);

            randomCoords.add(newCoord);
        }
       
        diagramBuilder.setSites(randomCoords); //set voronoi diagram coordinates
        diagramBuilder.setClipEnvelope(boundary);
        Geometry polygonCollection = diagramBuilder.getDiagram(geometryFactory); //generate voronoi diagram
        
        List<org.locationtech.jts.geom.Polygon> producedPolygons = new ArrayList<>(); //list of produced polygons

        if(polygonCollection instanceof GeometryCollection) {
            GeometryCollection geometryCollection = (GeometryCollection) polygonCollection;
            System.out.println("Produced polygon count: " + geometryCollection.getNumGeometries());
            
            for(int j = 0; j < geometryCollection.getNumGeometries(); j++) {
                org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) geometryCollection.getGeometryN(j);
                producedPolygons.add(polygon);
            }
        }
        return producedPolygons;
    }

    public List<org.locationtech.jts.geom.Polygon> lloydRelaxation(List<org.locationtech.jts.geom.Polygon> producedPolygons, int numRelax){
        VoronoiDiagramBuilder diagramBuilder = new VoronoiDiagramBuilder();
        diagramBuilder.setClipEnvelope(boundary);
        
        for(int i=0;i<numRelax;i++){
            int counter=0;
            
            if(i==0){
                for(org.locationtech.jts.geom.Polygon polygon:producedPolygons){
                    Coordinate tempCord = new Coordinate(polygon.getCentroid().getX(),polygon.getCentroid().getY());
                    Vertex newVertex = Vertex.newBuilder().setX((polygon.getCentroid().getX())).setY(polygon.getCentroid().getY()).build();
                    irregVertex.add(newVertex);
                    centerCoords.add(tempCord);
                    counter++;
                }
            }else{
                for(org.locationtech.jts.geom.Polygon polygon:producedPolygons){
                    Coordinate tempCord = new Coordinate(polygon.getCentroid().getX(),polygon.getCentroid().getY());
                    Vertex newVertex = Vertex.newBuilder().setX((polygon.getCentroid().getX())).setY(polygon.getCentroid().getY()).build();
                    irregVertex.set(counter,newVertex);
                    
                    centerCoords.set(counter,tempCord);
                    counter++;
                }
            }
            diagramBuilder.setSites(centerCoords); //set voronoi diagram coordinates
            diagramBuilder.setClipEnvelope(boundary);
            Geometry polygonCollection = diagramBuilder.getDiagram(geometryFactory); //generate voronoi diagram

            if(polygonCollection instanceof GeometryCollection) {
                GeometryCollection geometryCollection = (GeometryCollection) polygonCollection;
                for(int j = 0; j < geometryCollection.getNumGeometries(); j++) {
                    org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) geometryCollection.getGeometryN(j);
                    producedPolygons.set(j,polygon);
                }
            }
        }
        return producedPolygons;
    }

    public void neighborRelation(String gridType){
        List<Vertex> neighborVertex = new ArrayList<>();
        DelaunayTriangulationBuilder neighbor = new DelaunayTriangulationBuilder();
        double vx,vy;
        neighbor.setSites(centerCoords);
        Geometry neighborTrig = neighbor.getTriangles(geometryFactory);
        
        for (int i = 0; i < neighborTrig.getNumPoints(); i++) {
            vx = neighborTrig.getCoordinates()[i].getX();
            vy = neighborTrig.getCoordinates()[i].getY();
            Vertex newVertex= Vertex.newBuilder().setX((double) vx).setY((double) vy).build();
            neighborVertex.add(newVertex);
        }

        for(int i = 0; i < irregPolygons.size(); i++){
            Polygon tempPoly = irregPolygons.get(i);
            if(gridType.equals("Grid")){
                for(int j = 0; j < neighborVertex.size(); j+=4){
                    if(irregVertex.get(irregPolygons.get(i).getCentroidIdx()).equals( neighborVertex.get(j))){
            
                        double comp1x = Math.abs(neighborVertex.get(j).getX()-neighborVertex.get(j+1).getX());
                        double comp1y = Math.abs(neighborVertex.get(j).getY()-neighborVertex.get(j+1).getY());
                        double comp2x = Math.abs(neighborVertex.get(j+1).getX()-neighborVertex.get(j+2).getX());
                        double comp2y = Math.abs(neighborVertex.get(j+1).getY()-neighborVertex.get(j+2).getY());
                        double comp3x = Math.abs(neighborVertex.get(j).getX()-neighborVertex.get(j+2).getX());
                        double comp3y = Math.abs(neighborVertex.get(j).getY()-neighborVertex.get(j+2).getY());

                        int v1 = findIndex(neighborVertex.get(j));
                        int v2 = findIndex(neighborVertex.get(j+1));
                        int v3 = findIndex(neighborVertex.get(j+2));
        
                        Double sqaureSide = (double) squareSize;
                        if(comp1x != sqaureSide || comp1y != sqaureSide){
                            tempPoly = irregPolygons.get(v1);
                            tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v2).build();
                            irregPolygons.set(v1,tempPoly);
                        }
                        if(comp2x != sqaureSide || comp2y != sqaureSide){
                            tempPoly = irregPolygons.get(v2);
                            tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v3).build();
                            irregPolygons.set(v2,tempPoly);
                        }
                        if(comp3x != sqaureSide || comp3y != sqaureSide){
                            tempPoly = irregPolygons.get(v3);
                            tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v1).build();
                            irregPolygons.set(v3,tempPoly);
                        }
                    }
        
                }
            }else{
                for (int j = 0; j < neighborVertex.size()-3; j+=4) {
                    int v1 = findIndex(neighborVertex.get(j));
                    int v2 = findIndex(neighborVertex.get(j+1));
                    int v3 = findIndex(neighborVertex.get(j+2));

                    tempPoly = irregPolygons.get(v1);
                    tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v2).addNeighborIdxs(v3).build();
                    irregPolygons.set(v1,tempPoly);

                    tempPoly = irregPolygons.get(v2);
                    tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v1).addNeighborIdxs(v3).build();
                    irregPolygons.set(v2,tempPoly);

                    tempPoly = irregPolygons.get(v3);
                    tempPoly = Polygon.newBuilder(tempPoly).addNeighborIdxs(v1).addNeighborIdxs(v2).build();
                    irregPolygons.set(v3,tempPoly);
 
                }
            }
        }
   
    }

    private int findIndex(Vertex neighbor){
        for(int i = 0; i < irregPolygons.size(); i++){
            Polygon tempPoly = irregPolygons.get(i);
            if(neighbor.equals(irregVertex.get(tempPoly.getCentroidIdx()))){
                return i;
            }
        }
        return 0;
    }

    public void convert(List<org.locationtech.jts.geom.Polygon> producedPolygons){
        Double v1x, v1y;
        List<Integer> polySides = new ArrayList<>();

        for(org.locationtech.jts.geom.Polygon polygon:producedPolygons){
            Geometry line = polygon.convexHull();
            polySides.add(line.getNumPoints());
            for (int i = 0; i < line.getNumPoints(); i++) {
                v1x =  line.getCoordinates()[i].getX();
                v1y = line.getCoordinates()[i].getY();
                Vertex newVertex= Vertex.newBuilder().setX((double) v1x).setY((double) v1y).build();
                irregVertex.add(newVertex);

                if(i!=0){
                    irregSegments.add(Segment.newBuilder().setV1Idx(irregVertex.size()-1).setV2Idx(irregVertex.size()-2).build());
                }
            }
            
        }

        int segCounter = 0;
        for (int i = 0; i < polySides.size(); i++) {
            Polygon tempPoly = Polygon.newBuilder().build();
            for (int j = 0; j < polySides.get(i)-1; j++) {
                tempPoly = Polygon.newBuilder(tempPoly).addSegmentIdxs(segCounter).build();
                segCounter++;
            }
            tempPoly=Polygon.newBuilder(tempPoly).setCentroidIdx(i).build();

            irregPolygons.add(tempPoly);
        }
    }
}


