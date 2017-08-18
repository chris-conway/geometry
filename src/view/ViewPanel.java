package view;

import controller.Controller;
import jts.geom.*;
import jts.geom.Polygon;
import jts.operation.polygonize.Polygonizer;
import kn.uni.voronoitreemap.datastructure.OpenList;
import kn.uni.voronoitreemap.diagram.PowerDiagram;
import kn.uni.voronoitreemap.j2d.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.geom.*;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewPanel extends JPanel {

    Controller cont;
    int redrawFillCounter;
    ArrayList<Color> previousColors;

    public ViewPanel(Controller c) {
        this.cont = c;
        Dimension dim = new Dimension(1000, 1000);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        this.setMinimumSize(dim);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        redrawFillCounter = 51;
        previousColors = new ArrayList<>();
    }

    private Color randomColor() {
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        return new Color(r, g, b);
    }

    private ArrayList<Path2D> getFillSpaces() {
        ArrayList<Point2D> points = cont.getPoints().getPointsArray();
        ArrayList<Path2D> paths = new ArrayList<>();
        Random rand = new Random();

        int numOfPoints = points.size();
        ArrayList<Point2D> currentShapePoints = new ArrayList<>();
        while (numOfPoints > 3) {
            while (currentShapePoints.size() < 3) {
                Point2D possiblePoint = points.get(rand.nextInt(points.size()));
                if (!currentShapePoints.contains(possiblePoint)) {
                    currentShapePoints.add(possiblePoint);
                }
            }
            numOfPoints -= 3;
            Path2D path = new Path2D.Double();
            path.moveTo(currentShapePoints.get(0).getX(), currentShapePoints.get(0).getY());
            path.lineTo(currentShapePoints.get(1).getX(), currentShapePoints.get(1).getY());
            path.lineTo(currentShapePoints.get(2).getX(), currentShapePoints.get(2).getY());
            paths.add(path);
            currentShapePoints.clear();
        }
        return paths;
    }

    private void myPolygonDrawer(Graphics2D g){
        PowerDiagram diagram = new PowerDiagram();

// normal list based on an array
        OpenList sites = new OpenList();

        Random rand = new Random();
// create a root polygon which limits the voronoi diagram.
// here it is just a rectangle.

        PolygonSimple rootPolygon = new PolygonSimple();
        int width = 1000;
        int height = 1000;
        rootPolygon.add(0, 0);
        rootPolygon.add(width, 0);
        rootPolygon.add(width, height);
        rootPolygon.add(0, height);

// create 100 points (sites) and set random positions in the rectangle defined above.
        ArrayList<Point2D> intersectionPoints = cont.getPoints().getAllIntersectionPoints();

        for (int i = 0; i < intersectionPoints.size(); i++) {
            Site site = new Site(intersectionPoints.get(i).getX(), intersectionPoints.get(i).getY());
            // we could also set a different weighting to some sites
            // site.setWeight(30)
            sites.add(site);
        }

// set the list of points (sites), necessary for the power diagram
        diagram.setSites(sites);
// set the clipping polygon, which limits the power voronoi diagram
        diagram.setClipPoly(rootPolygon);

// do the computation
        diagram.computeDiagram();

// for each site we can no get the resulting polygon of its cell.
// note that the cell can also be empty, in this case there is no polygon for the corresponding site.
        for (int i=0;i<sites.size;i++){
            Site site=sites.array[i];
            g.draw(site.getPolygon());

        }
    }

    private void findPolygons(Graphics2D g){
        Polygonizer polygonizer = new Polygonizer();
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Line2D> lines = cont.getPoints().getAllNonIntersectingLinesFromIntersectionPointLines();
        lines = lines.stream().distinct().collect(Collectors.toList());
        CoordinateSequenceFactory coordinateSequenceFactory = geometryFactory.getCoordinateSequenceFactory();
        ArrayList<LineString> lineStrings = new ArrayList<>();
        for (Line2D line : lines) {
            Coordinate[] coords = new Coordinate[2];
            coords[0] = new Coordinate(line.getX1(), line.getY1());
            coords[1] = new Coordinate(line.getX2(), line.getY2());
            lineStrings.add(new LineString(coordinateSequenceFactory.create(coords), geometryFactory));
        }

        LineString[] lineStringArray = geometryFactory.toLineStringArray(lineStrings);
        MultiLineString multiLineString = geometryFactory.createMultiLineString(lineStringArray);
        polygonizer.add(multiLineString);
        ArrayList<Polygon> polygons = (ArrayList<Polygon>) polygonizer.getPolygons();
        g.setColor(Color.CYAN);
        drawJTSPolygonsAsPath2D(g, polygons);
        g.setColor(Color.orange);
        lines.forEach(g::draw);
    }

    private void drawJTSPolygonsAsPath2D(Graphics2D g, List<Polygon> polygons){
        boolean maintainColor = previousColors.size() == polygons.size();
        if(!maintainColor) previousColors.clear();
        int colorCounter = 0;

        for (Polygon polygon : polygons) {
            Path2D finalShape = new Path2D.Double();
            Coordinate[] coords = polygon.getCoordinates();
            finalShape.moveTo(coords[0].x, coords[0].y);
            for(int i = 1; i < coords.length; i++){
                finalShape.lineTo(coords[i].x, coords[i].y);
            }
            if(previousColors.size() == 0 && maintainColor == false){
                Color newColor = generateRandomColor();
                previousColors.add(newColor);
                g.setColor(newColor);
            }else if(maintainColor){
                previousColors.set(colorCounter, smudgeColor(previousColors.get(colorCounter)));
                g.setColor(previousColors.get(colorCounter));
            }else{
                previousColors.add(colorCounter, generateRandomColor());
            }
            g.fill(finalShape);
            colorCounter++;
        }
    }

    private Color smudgeColor(Color color){
        Random rand = new Random();

        int newRed = color.getRed() + rand.nextInt(20) - 10;
        int newGreen = color.getGreen() + rand.nextInt(20) - 10;
        int newBlue = color.getBlue() + rand.nextInt(20) - 10;
        if(newRed > 255)newRed=255;
        if(newRed < 0)newRed=0;
        if(newGreen > 255)newGreen=255;
        if(newGreen < 0)newGreen=0;
        if(newBlue > 255)newBlue=255;
        if(newBlue < 0)newBlue=0;
        return new Color(newRed, newGreen, newBlue);
    }

    private Color generateRandomColor() {
        Color mix = new Color(0, 155, 255);
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }

        Color color = new Color(red, green, blue);
        return color;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.red);

        g.setStroke(new BasicStroke(1));
        findPolygons(g);
        this.repaint();
    }

}
