package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.List;

import javax.sound.sampled.Line;

import org.apache.batik.ext.awt.geom.Polygon2D;
import org.apache.batik.ext.awt.geom.Polyline2D;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
/*         for(Structs.Segment s: aMesh.getSegmentsList()){
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            System.out.println(extractColor(aMesh.getVertices(v1).getPropertiesList()));
            canvas.setColor(averageColor(aMesh.getVertices(v1).getPropertiesList(), aMesh.getVertices(v2).getPropertiesList()));
            canvas.draw(new Line2D.Double(aMesh.getVertices(v1).getX(), aMesh.getVertices(v1).getY(),aMesh.getVertices(v2).getX(),aMesh.getVertices(v2).getY()));
        } */
        for (Structs.Polygon p : aMesh.getPolygonsList()){
            int i1 = p.getSegmentIdxs(0);
            int i2 = p.getSegmentIdxs(1);
            int i3 = p.getSegmentIdxs(2);
            int i4 = p.getSegmentIdxs(3);

            Structs.Segment s1 = aMesh.getSegments(i1);
            Structs.Segment s2 = aMesh.getSegments(i2);
            Structs.Segment s3 = aMesh.getSegments(i3);
            Structs.Segment s4 = aMesh.getSegments(i4);

            canvas.setColor(averageColor(aMesh.getVertices(s1.getV1Idx()).getPropertiesList(), aMesh.getVertices(s1.getV2Idx()).getPropertiesList()));
            canvas.draw(new Line2D.Double(aMesh.getVertices(s1.getV1Idx()).getX(), aMesh.getVertices(s1.getV1Idx()).getY(), aMesh.getVertices(s1.getV2Idx()).getX(), aMesh.getVertices(s1.getV2Idx()).getY()));

            canvas.setColor(averageColor(aMesh.getVertices(s2.getV1Idx()).getPropertiesList(), aMesh.getVertices(s2.getV2Idx()).getPropertiesList()));
            canvas.draw(new Line2D.Double(aMesh.getVertices(s2.getV1Idx()).getX(), aMesh.getVertices(s2.getV1Idx()).getY(), aMesh.getVertices(s2.getV2Idx()).getX(), aMesh.getVertices(s2.getV2Idx()).getY()));

            canvas.setColor(averageColor(aMesh.getVertices(s3.getV1Idx()).getPropertiesList(), aMesh.getVertices(s3.getV2Idx()).getPropertiesList()));
            canvas.draw(new Line2D.Double(aMesh.getVertices(s3.getV1Idx()).getX(), aMesh.getVertices(s3.getV1Idx()).getY(), aMesh.getVertices(s3.getV2Idx()).getX(), aMesh.getVertices(s3.getV2Idx()).getY()));

            canvas.setColor(averageColor(aMesh.getVertices(s4.getV1Idx()).getPropertiesList(), aMesh.getVertices(s4.getV2Idx()).getPropertiesList()));
            canvas.draw(new Line2D.Double(aMesh.getVertices(s4.getV1Idx()).getX(), aMesh.getVertices(s4.getV1Idx()).getY(), aMesh.getVertices(s4.getV2Idx()).getX(), aMesh.getVertices(s4.getV2Idx()).getY()));
            
            
            
            

        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }
    private Color averageColor(List<Property> properties1, List<Property> properties2 ){
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

}
