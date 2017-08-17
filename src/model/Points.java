package model;

import controller.Controller;
import jts.geom.LineString;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by Chris on 15/05/2017.
 */
public class Points {

    Controller cont;
    ArrayList<Point2D> points;
    ArrayList<Double> angles;
    BoundingShape boundingShape;


    public Points(Controller controller, BoundingShape boundingShape) {
        this.cont = controller;
        this.points = new ArrayList<>();
        this.boundingShape = boundingShape;
        this.angles = new ArrayList<>();
    }

    public void addPoint() {
        this.points.add(new Point2D.Double(0, 0));
        this.angles.add(0.0);
        evenlySpacePoints();
    }

    public void removePoint() {
        if (this.points.size() > 0) {
            this.points.remove(this.points.size() - 1);
        }
        evenlySpacePoints();
    }

    public ArrayList<Point2D> getPointsArray() {
        return points;
    }

    public void updatePoints() {
        for (int i = 0; i < points.size(); i++) {
            points.get(i).setLocation(boundingShape.getPointFromAngleAtCenter(angles.get(i)));
        }
    }

    private void evenlySpacePoints() {
        double spacing = (double) 360 / (double) points.size();
        double currentSpacing = 0;

        for (int i = 0; i < points.size(); i++) {
            Point2D newPoint = boundingShape.getPointFromAngleAtCenter(currentSpacing);
            points.get(i).setLocation(newPoint);
            angles.set(i, currentSpacing);
            currentSpacing += spacing;
            if (currentSpacing > 360) {
                currentSpacing = currentSpacing - 360;
            }
        }
    }

    public void rotatePoints(boolean clockwise) {

        double rotationSpeed = 1;

        for (int i = 0; i < angles.size(); i++) {
            double currentSpacing = angles.get(i);
            if (clockwise) {
                currentSpacing += rotationSpeed;
            } else {
                currentSpacing -= rotationSpeed;
            }
            if (currentSpacing > 360) {
                currentSpacing -= (double) 360;
            } else if (currentSpacing < 0) {
                currentSpacing = (double) 360 - currentSpacing;
            }
            angles.set(i, currentSpacing);
        }

        cont.getPoints().updatePoints();
    }

    public ArrayList<Line2D> getAllPointToPointLinesFromIntersectionPoints(){
        ArrayList<Line2D> lines = new ArrayList<>();
        ArrayList<Point2D> intersectionPoints = getAllIntersectionPoints();
        for(int i = 0; i < intersectionPoints.size(); i++){
            for(int j = i + 1; j < intersectionPoints.size(); j++){
                lines.add(new Line2D.Double(intersectionPoints.get(i), intersectionPoints.get(j)));
            }
        }
        return lines;
    }

    public ArrayList<Line2D> getAllPointToPointLines() {
        int counter = 0;
        ArrayList<Line2D> allLines = new ArrayList<>();
        while (counter < points.size()) {
            Point2D point1 = points.get(counter);
            for (int j = counter + 1; j < points.size(); j++) {
                Point2D point2 = points.get(j);
                allLines.add(new Line2D.Double(point1.getX(), point1.getY(), point2.getX(), point2.getY()));
            }
            counter++;
        }
        return allLines;
    }


    public ArrayList<Point2D> getAllIntersectionPoints() {
        ArrayList<Line2D> allLines = getAllPointToPointLines();
        ArrayList<Point2D> allIntersectionPoints = new ArrayList<>();
        for (int i = 0; i < allLines.size(); i++) {
            for (int j = 1; j < allLines.size(); j++) {
                Point2D possiblePoint = getLineLineIntersection(allLines.get(i), allLines.get(j));
                if (possiblePoint != null) {
                    allIntersectionPoints.add(possiblePoint);
                }
            }
        }
        return allIntersectionPoints;
    }

    private Stream allIntersectionPoints(Line2D currentLine){
        ArrayList<Line2D> allLines = getAllPointToPointLines();
        allLines.remove(currentLine);
        return allLines.stream().map(lines -> getLineLineIntersection(currentLine, lines));
    }

    public ArrayList<Line2D> getAllNonIntersectingLinesFromInstersectionPointLines(){
        Comparator<Line2D> sortByDescendingLength = (Line2D lineOne, Line2D lineTwo) ->
                Double.compare(distanceBetween(lineTwo.getP1(),lineTwo.getP2()), distanceBetween(lineOne.getP1(),lineOne.getP2()));
        ArrayList<Line2D> lines = cont.getPoints().getAllPointToPointLinesFromIntersectionPoints();
        Collections.sort(lines, sortByDescendingLength);
        ArrayList<Line2D> filteredLines = new ArrayList<>();
        while(lines.size() > 0){
            Line2D line = lines.remove(0);
            if(!intersects(line, lines)){
                filteredLines.add(line);
            }
        }
        return filteredLines;
    }

    public Point2D getLineLineIntersection(Line2D line1, Line2D line2) {
        double det1And2 = det(line1.getP1().getX(), line1.getP1().getY(), line1.getP2().getX(), line1.getP2().getY());
        double det3And4 = det(line2.getP1().getX(), line2.getP1().getY(), line2.getP2().getX(), line2.getP2().getY());
        double x1LessX2 = line1.getP1().getX() - line1.getP2().getX();
        double y1LessY2 = line1.getP1().getY() - line1.getP2().getY();
        double x3LessX4 = line2.getP1().getX() - line2.getP2().getX();
        double y3LessY4 = line2.getP1().getY() - line2.getP2().getY();
        double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
        if (det1Less2And3Less4 == 0) {
            // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
            return null;
        }
        double x = (det(det1And2, x1LessX2,
                det3And4, x3LessX4) /
                det1Less2And3Less4);
        double y = (det(det1And2, y1LessY2,
                det3And4, y3LessY4) /
                det1Less2And3Less4);

        Point2D point = new Point2D.Double(x, y);
        if(boundingShape.containsPoint(point)){
            return point;
        }
        return null;
    }

    private double distanceBetween(Point2D p1, Point2D p2){
        return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX()) + (p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
    }

    public boolean intersects(Line2D candidateLine, ArrayList<Line2D> allLines){
        for (Line2D line : allLines) {
            if(candidateLine.getP1().equals(line.getP1()) ||
                    candidateLine.getP1().equals(line.getP2())||
                    candidateLine.getP2().equals(line.getP1())||
                    candidateLine.getP2().equals(line.getP2())){
                continue;
            }
            if(candidateLine.intersectsLine(line))return true;
        }
        return false;
    }


    protected static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }


}
