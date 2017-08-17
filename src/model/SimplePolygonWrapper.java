package model;

import kn.uni.voronoitreemap.j2d.PolygonSimple;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class SimplePolygonWrapper {

    PolygonSimple polygonSimple;

    public SimplePolygonWrapper(Point2D[] points) {
        double xPoints[] = new double[points.length];
        double yPoints[] = new double[points.length];
        int i = 0;
        for(Point2D point : points){
            xPoints[i] = point.getX();
            yPoints[i] = point.getY();
            i++;
        }
        polygonSimple = new PolygonSimple(xPoints, yPoints);
    }


}
