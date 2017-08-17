package model;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Chris on 17/05/2017.
 */
public interface BoundingShape {



    Point2D getPointFromAngleAtCenter(double degAngle);

    Point2D getCenter();

    boolean containsPoint(Point2D point);

}
