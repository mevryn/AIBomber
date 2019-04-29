package pl.dszi.player.noob;

import java.awt.*;

class Distances {
    private Point point1;
    private Point point2;

    public Distances(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }
    protected int returnManhattanDistance(){
        return Math.abs(point1.x-point2.x)+Math.abs(point1.y-point2.y);
    }
}
