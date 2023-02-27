package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas, String cmdArg) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        for (Structs.Polygon p : aMesh.getPolygonsList()){
            int count=0;
            for(int polySegmentIDX : p.getSegmentIdxsList()){
                System.out.println(p.getSegmentIdxsList().size());
                canvas.setColor(averageColor(aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV1Idx()).getPropertiesList(), aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV2Idx()).getPropertiesList(), cmdArg));
                canvas.draw(new Line2D.Double(aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV1Idx()).getX(), aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV1Idx()).getY(), aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV2Idx()).getX(), aMesh.getVertices(aMesh.getSegments(polySegmentIDX).getV2Idx()).getY()));
                count++;
            }
            if(cmdArg.equals("-X")){
                for(int polyNeighborIDX : p.getNeighborIdxsList()){
                    canvas.setColor(Color.LIGHT_GRAY);
                    canvas.draw(new Line2D.Double(aMesh.getVertices(p.getCentroidIdx()).getX(), aMesh.getVertices(p.getCentroidIdx()).getY(), aMesh.getVertices(polyNeighborIDX).getX(), aMesh.getVertices(polyNeighborIDX).getY()));
                }
            }   
            System.out.println(count);
        }
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList(), cmdArg));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }    
    }
    
    private Color averageColor(List<Property> properties1, List<Property> properties2, String cmdArg){
        String val1 = null;
        String val2 = null;
        for(Property p: properties1) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val1 = p.getValue();
            }
        }
        for (Property p :properties2){
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val2 = p.getValue();
            }
        }

        if(cmdArg.equals("-X")){
            return Color.BLACK;
        }

        String[] raw = val1.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);

        raw=val2.split(",");
        red = (red+Integer.parseInt(raw[0]))/2;
        green = (green+Integer.parseInt(raw[1]))/2;
        blue = (green+Integer.parseInt(raw[2]))/2;

        return new Color (red,green,blue);

    }

    private Color extractColor(List<Property> properties, String cmdArg) {
        String val = null;
        String cnt = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
            if(cmdArg.equals("-X")){
                if(p.getKey().equals("centroid")){
                    if(p.getValue().equals("True")){
                        return Color.RED;
                    }
                    else{
                        return Color.BLACK;
                    }
                }
            }
            if(p.getKey().equals("centroid")){
                cnt = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        if(cnt.equals("False")){
            return new Color(red, green, blue);
        }
        else{
            return new Color(red, green, blue,0);
        }
    }

}
