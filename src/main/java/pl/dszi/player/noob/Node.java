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
    public int getF() {
        return f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getG() {
        return g;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode);
            return true;
        }
        return false;
    }

    public Node getParent() {
        return parent;
    }

    public void setNodeData(Node currentNode){
        int gCost = currentNode.getG() + Constants.DEFAULT_NODE_COST;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }
    public void setH(Node destinationNode) {
        Distances distances = new Distances(this,destinationNode);
        this.h = distances.returnManhattanDistance();
    }

    public Cell getCell() {
        return cell;
    }

    public void calculateFinalCost(){
        this.f = g+h;
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

    public Node(Cell cell) {
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
