package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.engine.Constants;

import java.util.*;

public class Astar {
    private Node[][] nodes;
    private Cell[][] cells;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node nodeStart;
    private Node nodeEnd;


    public Astar(Cell[][] cells) {
        this.cells = cells;
        this.nodes = new Node[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.nodes[i][j] = new Node(cells[i][j]);
            }
        }
        closedSet = new HashSet<>();
    }

    public Node getNodeFromCell(Cell cell) {
        return nodes[cell.getIndexX()][cell.getIndexY()];
    }

    public void setAllHeuristic(Node destinationNode) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                nodes[i][j].setH(destinationNode);
            }
        }
    }

    public Node getNodeEnd() {
        return nodeEnd;
    }

    public List<Node> chooseBestWay(Cell start,Direction direction, Cell end) {
        nodeStart = getNodeFromCell(start);
        nodeEnd = getNodeFromCell(end);
        nodeStart.setDirection(direction);
        setAllHeuristic(nodeEnd);
        openList.add(nodeStart);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            }
            else
                getNeighbors(currentNode);
        }
        return new ArrayList<>();
    }

    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(nodeEnd);
    }


    public List<Node> getNeighbors(Node aNode) {
        List<Node> neighbors = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                if (nodes[i][j].equals(aNode)) {
                    row = i;
                    col = j;
                    if (row - 1 >= 0) {
                        checkNode(aNode,row-1,col,1);
                    }
                    if (col - 1 >= 0) {
                        checkNode(aNode,row,col-1,1);
                    }
                    if (row + 1 < Constants.DEFAULT_GAME_TILES_HORIZONTALLY) {
                        checkNode(aNode,row+1,col,1);
                    }
                    if (col + 1 < Constants.DEFAULT_GAME_TILES_VERTICALLY) {
                        checkNode(aNode,row,col+1,1);
                    }
                }
            }
        }
        return neighbors;
    }

    private void checkNode(Node currentNode, int row, int col, int cost) {
        Node adjacentNode = nodes[row][col];
        if (adjacentNode.getCell().getType().walkable && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;

    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

}