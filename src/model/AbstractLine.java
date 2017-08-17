package model;

import java.awt.geom.Line2D;

/**
 * Created by Chris on 17/05/2017.
 */
public abstract class AbstractLine extends Line2D{

    public boolean equals(Line2D line){
        return this.getX1() == line.getX1() &&
                this.getY1() == line.getY1() &&
                this.getX2() == line.getX2() &&
                this.getY2() == line.getY2();
    }
}
