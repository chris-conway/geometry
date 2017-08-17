package controller;

import kn.uni.voronoitreemap.j2d.PolygonSimple;

public class TestController {

    public TestController() {
        PolygonSimple polygonSimple = initialisePolygonWithPoints();

    }

    private PolygonSimple initialisePolygonWithPoints(){
        double xPoints[] =  new double[8];
        double yPoints[] = new double[8];
        xPoints[0] =  100; yPoints[0] =  100;
        xPoints[1] =  500; yPoints[1] =  200;
        xPoints[2] =  900; yPoints[2] =  100;
        xPoints[3] =  700; yPoints[3] =  500;
        xPoints[4] =  900; yPoints[4] =  900;
        xPoints[5] =  500; yPoints[5] =  600;
        xPoints[6] =  100; yPoints[6] =  900;
        xPoints[7] =  300; yPoints[7] =  500;

        return new PolygonSimple(xPoints, yPoints);
    }



}
