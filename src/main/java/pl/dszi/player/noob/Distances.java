package pl.dszi.player.noob;

import pl.dszi.board.Cell;

import java.awt.*;

class Distances {
    private Node Node1;
    private Node Node2;

    Distances(Node Node1, Node Node2) {
        this.Node1 = Node1;
        this.Node2 = Node2;
    }

    int returnManhattanDistance() {
        return Math.abs(Node1.getCell().getPoint().x - Node2.getCell().getPoint().x) + Math.abs(Node1.getCell().getPoint().y - Node2.getCell().getPoint().y);
    }


    static int returnManhattaDistanceOfTwoCells(Point point1, Point point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }
}
