package model;

/**
 * Created by Chris on 10/04/2017.
 */
public class DoublePoint {

    public double x;
    public double y;
    public double rotationAroundCircle;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.rotationAroundCircle = 0;
    }

    public void setPoint(DoublePoint point){
        this.x = point.x;
        this.y = point.y;
    }

    public double getRotationAroundCircle() {
        return rotationAroundCircle;
    }

    public void setRotationAroundCircle(double rotationAroundCircle) {
        this.rotationAroundCircle = rotationAroundCircle;
    }

    public double getX(){
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void subtract(DoublePoint point){
        x -= point.x;
        y -= point.y;
    }

    public void add(DoublePoint point){
        x += point.x;
        y += point.y;
    }

    public void multiply(int times){
        x*=times;
        y*=times;
    }

    public double distanceFrom(DoublePoint point){
        return Math.abs(Math.sqrt(Math.pow(point.x - this.x, 2) + Math.pow(point.y - this.y, 2)));
    }

    public DoublePoint getUnitVector(DoublePoint point){
        DoublePoint direction = new DoublePoint(point.x - this.x, point.y - this.y);
        double magnitude = Math.sqrt(Math.pow(direction.x, 2) + Math.pow(direction.y, 2));
        return new DoublePoint(direction.x/magnitude, direction.y/magnitude);
    }

}
