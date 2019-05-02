package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;

public class Node {
    private Cell cell;
    private int g;
    private int h;
    private int f;
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

    public Node(Cell cell) {
        this.cell = cell;
        this.g = 1;
    }

}
