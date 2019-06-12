package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constant;

public class Node {
    private Cell cell;
    private int g;//heuristic cost
    private int h; //default node cost
    private int f; //final cost
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

    boolean checkBetterPath(Node currentNode) {
        int gCost = currentNode.getG() + 1;
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
        int gCost = currentNode.getG() + Constant.DEFAULT_NODE_COST;
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


    Node(Cell cell) {
        this.cell = cell;
        this.g = 1;
    }
}
