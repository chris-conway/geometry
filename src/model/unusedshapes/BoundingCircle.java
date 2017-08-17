package model.unusedshapes;


import controller.Controller;
import model.BoundingShape;
import model.DoublePoint;

/**
 * Created by Chris on 15/05/2017.
 */
public class BoundingCircle implements BoundingShape {

    Controller cont;
    DoublePoint origin;
    double radius;

    public BoundingCircle (Controller c, DoublePoint origin, double radius) {
        this.cont = c;
        this.origin = origin;
        this.radius = radius;
    }

    public DoublePoint getCenter() {
        return origin;
    }

    public DoublePoint getPointFromAngleAtCenter(double degAngle){
        double radAngle = Math.toRadians(degAngle);
        double x = origin.x + this.radius * Math.cos(radAngle);
        double y = origin.y + this.radius * Math.sin(radAngle);
        return new DoublePoint(x, y);
    }

}
