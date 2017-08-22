package controller;

import jts.geom.*;
import jts.geom.Polygon;
import jts.operation.polygonize.Polygonizer;
import model.*;
import view.ViewFrame;
import view.ViewPanel;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Chris on 15/05/2017.
 */
public class Controller {

    private BoundingShape boundingShape;
    private Polygon boundingShapeAsPolygon;
    private Points points;
    private ViewPanel viewPanel;
    private AnimationTimer at;
    private InputListener inListener;

    public Controller() {
        ArrayList<Point2D> points = new ArrayList<Point2D>();

        points.add(new Point2D.Double(100,100));
        points.add(new Point2D.Double(500,200));
        points.add(new Point2D.Double(900,100));
        points.add(new Point2D.Double(700,500));
        points.add(new Point2D.Double(900,900));
        points.add(new Point2D.Double(500,600));
        points.add(new Point2D.Double(100,900));
        points.add(new Point2D.Double(300,500));

        boundingShape = new BoundingPolygon(points);
        this.boundingShapeAsPolygon = createJTSPolygonBoundingShape(points);
        this.points = new Points(this, boundingShape);
        for(int i = 0; i < 3; i++) {
            this.points.addPoint();
        }

        at = new AnimationTimer(this);
        inListener = new InputListener(this);
        ViewFrame viewFrame = new ViewFrame();
        viewPanel = new ViewPanel(this);
        viewFrame.add(viewPanel);
        viewPanel.repaint();
        viewFrame.addKeyListener(inListener);
        at.startTimer();

        viewFrame.setVisible(true);
        viewPanel.setVisible(true);
    }

    private jts.geom.Polygon createJTSPolygonBoundingShape(ArrayList<Point2D> pointList){
        if(pointList.size() > 0) {
            Polygonizer polygonizer = new Polygonizer();
            GeometryFactory geometryFactory = new GeometryFactory();
            CoordinateSequenceFactory coordinateSequenceFactory = geometryFactory.getCoordinateSequenceFactory();
            ArrayList<LineString> lineStrings = new ArrayList<>();
            Coordinate[] coords = new Coordinate[2];
            coords[0] = new Coordinate(pointList.get(0).getX(), pointList.get(0).getY());
            coords[1] = new Coordinate(pointList.get(pointList.size() - 1).getX(), pointList.get(pointList.size() - 1).getY());
            lineStrings.add(new LineString(coordinateSequenceFactory.create(coords), geometryFactory));
            for (int i = 1; i < pointList.size(); i++) {
                coords[0] = new Coordinate(pointList.get(i - 1).getX(), pointList.get(i - 1).getY());
                coords[1] = new Coordinate(pointList.get(i).getX(), pointList.get(i).getY());
                lineStrings.add(new LineString(coordinateSequenceFactory.create(coords), geometryFactory));
            }
            lineStrings.add(new LineString(coordinateSequenceFactory.create(coords), geometryFactory));
            LineString[] lineStringArray = geometryFactory.toLineStringArray(lineStrings);
            MultiLineString multiLineString = geometryFactory.createMultiLineString(lineStringArray);
            polygonizer.add(multiLineString);
            ArrayList<Polygon> polygons = (ArrayList<Polygon>) polygonizer.getPolygons();
            return polygons.get(0);
        }
        return null;
    }

    public AnimationTimer getAnimationTimer() {
        return at;
    }

    public void repaintPanel(){
        viewPanel.repaint();
    }

    public BoundingShape getBoundingShape() {
        return boundingShape;
    }

    public Points getPoints() {
        return points;
    }

    public Polygon getBoundingShapeAsPolygon() {
        return boundingShapeAsPolygon;
    }
}

