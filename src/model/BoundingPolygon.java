package model;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Chris on 17/05/2017.
 */
public class BoundingPolygon implements BoundingShape{

    private ArrayList<Point2D> points;
    private Point2D center;
    private double maxDistance;
    private Path2D shapePath;

    public BoundingPolygon(ArrayList<Point2D> points) {
        this.points = points;
        this.shapePath = convertPointsToPath(this.points);
        center = findCenter();
        maxDistance = findMaxDistance();
    }
    public Point2D getCenter() {
        return center;
    }

    private Path2D convertPointsToPath(ArrayList<Point2D> points){
        if(points.size() > 3) {
            Path2D path = new Path2D.Double();
            path.moveTo(points.get(0).getX(), points.get(0).getY());
            for(int i = 1; i < points.size(); i++){
                path.lineTo(points.get(i).getX(), points.get(i).getY());
            }
            path.closePath();
            return path;
        }
        System.out.println("Cannot convert to Path2D - too few points to create polygon.");
        return null;
    }

    public boolean containsPoint(Point2D point){
        return shapePath.contains(point);
    }

    private Point2D findCenter(){
        double totalX = 0;
        double totalY = 0;
        for(Point2D p : points){
            totalX += p.getX();
            totalY += p.getY();
        }
        return new Point2D.Double(totalX/points.size(), totalY/points.size());
    }

    private double findMaxDistance(){
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

    private boolean doLinesIntersect(AbstractLine line1, AbstractLine line2){
        DoublePoint CmP = new DoublePoint(line2.getX1() - line1.getX1(), line2.getY1() - line1.getY1());
        DoublePoint r = new DoublePoint(line1.getX2() - line1.getX1(), line1.getY2() - line1.getY1());
        DoublePoint s = new DoublePoint(line2.getX2() - line2.getX1(), line2.getY2() - line2.getY1());

        double CmPxr = CmP.x * r.y - CmP.y * r.x;
        double CmPxs = CmP.x * s.y - CmP.y * s.x;
        double rxs = r.x * s.y - r.y * s.x;

        if (CmPxr == 0f)
        {
            // Lines are collinear, and so intersect if they have any overlap

            return ((line2.getX1() - line1.getX1() < 0f) != (line2.getX1()- line1.getX2() < 0f))
                    || ((line2.getY1() - line1.getY1() < 0f) != (line2.getY1() - line1.getY2() < 0f));
        }

        if (rxs == 0f)
            return false; // Lines are parallel.

        double rxsr = 1f / rxs;
        double t = CmPxs * rxsr;
        double u = CmPxr * rxsr;

        return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
    }

    public Point2D getPointFromAngleAtCenter(double degAngle){
        Point2D outerPoint = new Point2D.Double(center.getX() + (maxDistance*Math.cos(degAngle)), center.getY() + (maxDistance*Math.sin(degAngle)));
        Line2D centerLine = new Line2D.Double(center, outerPoint);


        for(int i = 0; i < points.size(); i++){
            Line2D shapeSegment;
            if(i == points.size() - 1){
                shapeSegment = new Line2D.Double(points.get(0), points.get(i));
            }else {
                shapeSegment = new Line2D.Double(points.get(i), points.get(i + 1));
            }
            if(Line2D.linesIntersect(centerLine.getP1().getX(), centerLine.getP1().getY(),
                    centerLine.getP2().getX(), centerLine.getP2().getY(),
                    shapeSegment.getP1().getX(), shapeSegment.getP1().getY(),
                    shapeSegment.getP2().getX(), shapeSegment.getP2().getY())){
                return getLineLineIntersection(centerLine, shapeSegment);

            }
        }
        return null;
    }

    public Point2D getLineLineIntersection(Line2D line1, Line2D line2) {
        double det1And2 = det(line1.getP1().getX(), line1.getP1().getY(), line1.getP2().getX(), line1.getP2().getY());
        double det3And4 = det(line2.getP1().getX(), line2.getP1().getY(), line2.getP2().getX(), line2.getP2().getY());
        double x1LessX2 = line1.getP1().getX() - line1.getP2().getX();
        double y1LessY2 = line1.getP1().getY() - line1.getP2().getY();
        double x3LessX4 = line2.getP1().getX() - line2.getP2().getX();
        double y3LessY4 = line2.getP1().getY() - line2.getP2().getY();
        double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
        if (det1Less2And3Less4 == 0){
            // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
            return null;
        }
        double x = (det(det1And2, x1LessX2,
                det3And4, x3LessX4) /
                det1Less2And3Less4);
        double y = (det(det1And2, y1LessY2,
                det3And4, y3LessY4) /
                det1Less2And3Less4);
        return new Point2D.Double(x, y);
    }

    public ArrayList<Line2D> getAllPointToPointLines(){
        int counter = 0;
        ArrayList<Line2D> allLines = new ArrayList<>();
        while(counter < points.size()){
            Point2D point1 = points.get(counter);
            for(int j = counter + 1; j < points.size(); j++){
                Point2D point2 = points.get(j);
                allLines.add(new Line2D.Double(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
            }
            counter++;
        }
        return allLines;
    }

    public ArrayList<Point2D> getAllIntersectionPoints(){
        ArrayList<Line2D> allLines = getAllPointToPointLines();
        ArrayList<Point2D> allIntersectionPoints = new ArrayList<>();
        for(int i = 0; i < allLines.size(); i++){
            for(int j = 1; j < allLines.size(); j++){
                Point2D possiblePoint = getLineLineIntersection(allLines.get(i), allLines.get(j));
                if(possiblePoint != null){
                    allIntersectionPoints.add(possiblePoint);
                }
            }
        }
        return allIntersectionPoints;
    }


    protected static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }
}
