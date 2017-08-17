package model.unusedshapes;

import model.BoundingShape;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Vector;

/**
 * Created by Chris on 17/05/2017.
 */
public class BoundingTriangle implements BoundingShape {

    Point2D[] points;
    Point2D center;


    public BoundingTriangle(Point2D[] points) {
        this.points = points;
        center = findCenter();
    }

    public Point2D getCenter() {
        return center;
    }

    private Point2D findCenter(){
        double totalX = 0;
        double totalY = 0;
        for(Point2D p : points){
            totalX += p.getX();
            totalY += p.getY();
        }
        return new Point2D.Double(totalX/points.length, totalY/points.length);
    }

    private double findMaxWidth(){
        double maxDistance = 0;
        double currentDistance = 0;
        for(Point2D i : points){
            for(Point2D j : points){
                currentDistance = Math.sqrt(Math.pow(j.getX() - i.getX(), 2) + Math.pow(j.getY() - i.getY(), 2));
                if(currentDistance > maxDistance){
                    maxDistance = currentDistance;
                }
            }
        }
        return maxDistance;
    }

    public Point2D getPointFromAngleAtCenter(double degAngle){

        //new DoublePoint(Math.cos(Math.toRadians(degAngle)), Math.sin(Math.toRadians(degAngle)));

return null;
    }
}
