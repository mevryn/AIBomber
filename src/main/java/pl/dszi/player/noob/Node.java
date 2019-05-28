package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.constant.Constants;

public class Node {
    private Cell cell;
    private int g;
    private int h;
    private int f;
    private Direction direction;
    private Node parent;

    int getF() {
        return f;
    }

    private void setG(int g) {
        this.g = g;
    }

    private int getG() {
        return g;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode);
            return true;
        }
        return false;
    }

    Node getParent() {
        return parent;
    }

    void setNodeData(Node currentNode) {
        int gCost = currentNode.getG() + Constants.DEFAULT_NODE_COST;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    void setH(Node destinationNode) {
        Distances distances = new Distances(this, destinationNode);
        this.h = distances.returnManhattanDistance();
    }

    public Cell getCell() {
        return cell;
    }

    private void calculateFinalCost() {
        this.f = g + h;
    }

    public Node(Direction direction) {
        this.direction = direction;
    }

    public void setDirection(Direction direction) {

        this.direction = direction;
    }

    public Direction getDirection() {

        return direction;
    }

    Node(Cell cell) {
        this.cell = cell;
        this.direction = Direction.DEFAULT;

        this.g = 1;
    }

    @Override
    public String toString() {
        return "Node{" +
                "cell=" + cell +
                '}';
    }
}
