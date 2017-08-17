package model.unusedshapes;

import controller.Controller;
import model.BoundingShape;
import model.DoublePoint;

import java.awt.geom.Point2D;

/**
 * Created by Chris on 17/05/2017.
 */
public class BoundingRectangle implements BoundingShape {

    double width;
    double height;
    Point2D topLeftPoint;
    Controller cont;
    Point2D center;

    public BoundingRectangle(Controller c, double width, double height, Point2D topLeftPoint) {
        super();
        cont = c;
        this.width = width;
        this.height = height;
        this.topLeftPoint = topLeftPoint;
        center = new Point2D.Double(topLeftPoint.getX() + width/(double)2, topLeftPoint.getY() + height/(double)2);
    }

    public Point2D getCenter() {
        return center;
    }

    public Point2D getPointFromAngleAtCenter(double deg) {
        double twoPI = Math.PI*2;
        double theta = deg * Math.PI / 180;

        while (theta < -Math.PI) {
            theta += twoPI;
        }

        while (theta > Math.PI) {
            theta -= twoPI;
        }

        double rectAtan = Math.atan2(height, width);
        double tanTheta = Math.tan(theta);
        int region;

        if ((theta > -rectAtan) && (theta <= rectAtan)) {
            region = 1;
        } else if ((theta > rectAtan) && (theta <= (Math.PI - rectAtan))) {
            region = 2;
        } else if ((theta > (Math.PI - rectAtan)) || (theta <= -(Math.PI - rectAtan))) {
            region = 3;
        } else {
            region = 4;
        }

        Point2D edgePoint = new Point2D.Double(width/(double) 2, height/(double)2);
        double xFactor = 1;
        double yFactor = 1;

        switch (region) {
            case 1: yFactor = -1; break;
            case 2: yFactor = -1; break;
            case 3: xFactor = -1; break;
            case 4: xFactor = -1; break;
        }

        if ((region == 1) || (region == 3)) {
            edgePoint.setLocation(edgePoint.getX() + xFactor * (width / 2.0), edgePoint.getY() + yFactor * (width / 2.) * tanTheta);
        } else {
            edgePoint.setLocation(edgePoint.getX() + xFactor * (height / (2. * tanTheta)), edgePoint.getY() + yFactor * (height /  2.));
        }

        return edgePoint;
    };

    public Point2D getPointFromAngleAtCenter3(double angleInDegrees){
        Point2D p = new Point2D.Double(0, 0);
        double r = height/(double) 2;
        double angle = (angleInDegrees % 360) * Math.PI /180;

        double angleModPiOverTwo = angle % (Math.PI/4);

        if (angle >= 0 && angle < Math.PI / 4)
        {
            p.setLocation(r, r * Math.tan(angle));
        }
        else if (angle >= Math.PI / 4 && angle < Math.PI / 2)
        {
            p.setLocation(r * Math.tan(Math.PI/2 - angle), r);
        }
        else if (angle >= Math.PI / 2 && angle < 3*Math.PI/4)
        {
            p.setLocation(-1 * r * Math.tan(angle % (Math.PI/4)), r);
        }
        else if (angle >= 3*Math.PI/4 && angle < Math.PI)
        {
            p.setLocation(-1 * r, r * Math.tan(Math.PI - angle));
        }
        else if (angle >= Math.PI && angle < 5*Math.PI/4)
        {
            p.setLocation(-1 * r, -1 * r * Math.tan(angle % (Math.PI/4)));
        }
        else if (angle >= 5*Math.PI/4 && angle < 3*Math.PI/2)
        {
            p.setLocation(-1 * r * Math.tan(3*Math.PI/2 - angle),-1 * r);
        }
        else if (angle >= 3*Math.PI/2 && angle < 7*Math.PI/4)
        {
            p.setLocation(r * Math.tan(angle % (Math.PI/4)),-1 * r);
        }
        else
        {
            p.setLocation(r, -1 * r * Math.tan(2 * Math.PI - angle));

        }

        return p;
    }


    public DoublePoint getPointFromAngleAtCenter1(double degAngle) {
        double radAngle = Math.toRadians(degAngle);
        double x;
        double y;
        double rectDiagAngle = Math.atan2(center.y - topLeftPoint.y, topLeftPoint.x + width - center.x);

        if (degAngle <= Math.PI) {
            if (degAngle < (Math.PI / (double) 2)) {
                if (degAngle < rectDiagAngle) {
                    x = topLeftPoint.x + width;
                    double oppositeLegLength = Math.tan(degAngle) * (width / (double) 2);
                    y = center.y - oppositeLegLength;
                } else {
                    y = topLeftPoint.y;
                    double adjacentLegLength = (height / (double) 2) / Math.tan(degAngle);
                    x = center.x + adjacentLegLength;
                }
            } else {
                if (degAngle < (Math.PI - rectDiagAngle)) {
                    y = topLeftPoint.y;
                    double adjacentLegLength = (height / (double) 2) / Math.tan(Math.PI - degAngle);
                    x = center.x - adjacentLegLength;
                } else {
                    x = topLeftPoint.x;
                    double oppositeLegLength = Math.tan(Math.PI - degAngle) * (width / (double) 2);
                    y = center.y - oppositeLegLength;
                }
            }
        } else {
            if (degAngle < ((double) 3 * Math.PI / (double) 2)) {
                if (degAngle < (rectDiagAngle + Math.PI)) {
                    x = topLeftPoint.x;
                    double oppositeLegLength = Math.tan(degAngle - Math.PI) * (width / (double) 2);
                    y = center.y + oppositeLegLength;
                } else {
                    y = topLeftPoint.y + height;
                    double adjacentLegLength = (height / (double) 2) / Math.tan(degAngle - Math.PI);
                    x = center.x - adjacentLegLength;
                }
            } else {
                if (degAngle < ((double) 2 * Math.PI - rectDiagAngle)) {
                    y = topLeftPoint.y + height;
                    double adjacentLegLength = (height / (double) 2) / Math.tan((double) 2 * Math.PI - degAngle);
                    x = center.x + adjacentLegLength;
                } else {
                    x = topLeftPoint.x + width;
                    double oppositeLegLength = Math.tan((double) 2 * Math.PI - degAngle) * (width / (double) 2);
                    y = center.y + oppositeLegLength;
                }
            }
        }
        return new DoublePoint(x, y);
    }

    public DoublePoint getPointFromAngleAtCenter2(double degAngle){
        double rectDiagAngle = Math.atan2(center.y - topLeftPoint.y, topLeftPoint.x + width - center.x);
        DoublePoint collision = new DoublePoint(0,0);
        if (degAngle <= Math.PI) {
            // The collision is between the top and the right edge    
            if (degAngle < (Math.PI / 2.0)) {
                // The line collides with the right edge            
                if (degAngle < rectDiagAngle) {
                    // For this collision you have the x coordinate, is the same as the right edge x coordinate
                    collision.x = topLeftPoint.x + width;
                    // Now you need to find the y coordinate for the collision, to do that you just need the opposite leg
                    double oppositeLegLength = Math.tan(degAngle) * (width / 2);
                    collision.y = center.y - oppositeLegLength;
                } else {
                    // The line collides with the top edge
                    // 
                    // For this collision you have the y coordinate, is the same as the top edge y coordinate
                    collision.y = topLeftPoint.y;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    double adjacentLegLength = (height / 2) / Math.tan(degAngle);
                    collision.x = center.x + adjacentLegLength;
                }
            } else {
                // // The collision is between the top and the left edge    
                // 
                // The line collides with the top edge            
                if (degAngle < (Math.PI - rectDiagAngle)) {
                    // For this collision you have the y coordinate, is the same as the top edge y coordinate
                    collision.y = topLeftPoint.y;
                    double adjacentLegLength = (height / 2) / Math.tan(Math.PI - degAngle);
                    collision.x = center.x - adjacentLegLength;
                } else {
                    // The line collides with the left edge
                    // 
                    // For this collision you have the x coordinate, is the same as the left edge x coordinate
                    collision.x = topLeftPoint.x;
                    double oppositeLegLength = Math.tan(Math.PI - degAngle) * (width / 2);
                    collision.y = center.y - oppositeLegLength;
                }
            }
        } else {
            // angle > 180°
            //
            // The collision is between the lower and the left edge
            if (degAngle < (3.0 * Math.PI / 2.0)) {
                //  The line collides with the left edge
                if (degAngle < (rectDiagAngle + Math.PI)) {
                    // For this collision you have the x coordinate, is the same as the left edge x coordinate
                    collision.x = topLeftPoint.x;
                    double oppositeLegLength = Math.tan(degAngle - Math.PI) * (width / 2);
                    collision.y = center.y + oppositeLegLength;
                } else {
                    // The line collides with the lower edge
                    // 
                    // For this collision you have the y coordinate, is the same as the lower edge y coordinate
                    collision.y = topLeftPoint.y + height;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    double adjacentLegLength = (height / 2) / Math.tan(degAngle - Math.PI);
                    collision.x = center.x - adjacentLegLength;
                }
            } else {
                // The collision is between the lower and the right edge
                // 
                // The line collides with the lower edge
                if (degAngle < (2.0 * Math.PI - rectDiagAngle)) {
                    // For this collision you have the y coordinate, is the same as the lower edge y coordinate
                    collision.y = topLeftPoint.y + height;
                    // Now you need to find the x coordinate for the collision, to do that you just need the adjacent leg
                    double adjacentLegLength = (height / 2) / Math.tan(2.0 * Math.PI - degAngle);
                    collision.x = center.x + adjacentLegLength;
                } else {
                    // The line collides with the lower right
                    // 
                    // For this collision you have the x coordinate, is the same as the right edge x coordinate
                    collision.x = topLeftPoint.x + width;
                    // Now you need to find the y coordinate for the collision, to do that you just need the opposite leg
                    double oppositeLegLength = Math.tan(2.0 * Math.PI - degAngle) * (width / 2);
                    collision.y = center.y + oppositeLegLength;
                }
            }
        }
        return collision;
    }



}
