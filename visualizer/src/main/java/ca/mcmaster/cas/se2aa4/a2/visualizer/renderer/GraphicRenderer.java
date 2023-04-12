package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.CityProperty;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.RiverProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.Optional;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawRiverSegments(aMesh, canvas);
        drawCities(aMesh, canvas);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawRiverSegments(Mesh aMesh, Graphics2D canvas){
        canvas.setColor(new Color(0,71,100));
        int count=0;
        for(Segment s : aMesh.getSegmentsList()){
            Path2D path = new Path2D.Float();
            Optional<Integer> thickness = new RiverProperty().extract(s.getPropertiesList());
            Integer thic = Integer.valueOf(thickness.get());
            if(thickness.equals(Optional.of(-1))){
                continue;
            }else if(count==0){
                Stroke stroke = new BasicStroke(0.4f*thic);
                canvas.setStroke(stroke);
                path.moveTo(aMesh.getVertices(s.getV1Idx()).getX(), aMesh.getVertices(s.getV1Idx()).getY());
                path.lineTo(aMesh.getVertices(s.getV2Idx()).getX(), aMesh.getVertices(s.getV2Idx()).getY());
                path.closePath();
                canvas.draw(path);
            }
            
        }
    }

    private void drawCities(Mesh aMesh, Graphics2D canvas){
        canvas.setColor(new Color(255,0,0));

        for(Vertex v : aMesh.getVerticesList()){
            Optional<Integer> citySize = new CityProperty().extract(v.getPropertiesList());
            Integer thickInteger = Integer.valueOf(citySize.get());

            if(citySize.equals(Optional.of(-1))){
                continue;
            }
            else {
                System.out.println(thickInteger);
                Stroke stroke = new BasicStroke(0.2f*(thickInteger*5));
                canvas.setStroke(stroke);
                Ellipse2D.Double circle = new Ellipse2D.Double(v.getX() - (0.2f*(thickInteger*2)/2),v.getY() - (0.2f*(thickInteger*5)/2), 0.2f*(thickInteger*5), 0.2f*(thickInteger*5));
                canvas.fill(circle);
                canvas.draw(circle);
            }
        }
    }


    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if(fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }

}
