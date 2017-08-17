package controller;

import kn.uni.voronoitreemap.j2d.PolygonSimple;
import model.*;
import view.ViewFrame;
import view.ViewPanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Chris on 15/05/2017.
 */
public class Controller {

    private BoundingShape bShape;
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

        bShape = new BoundingPolygon(points);
        this.points = new Points(this, bShape);
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

    public AnimationTimer getAnimationTimer() {
        return at;
    }

    public void repaintPanel(){
        viewPanel.repaint();
    }

    public BoundingShape getBoundingShape() {
        return bShape;
    }

    public Points getPoints() {
        return points;
    }

}

