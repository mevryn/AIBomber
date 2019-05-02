package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Astar {
    private Node[][] nodes;
    private Cell[][] cells;
    private int cost = 1;
    private boolean visited = false;

    private PriorityQueue<Node> openList;
    private List<Node> closedList;

    public Astar(Cell[][] cells) {
        this.cells = cells;
        this.nodes = new Node[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY];
        this.openList = new PriorityQueue<Node>(Comparator.comparingInt(Node::getF));
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.nodes[i][j] = new Node(cells[i][j]);
            }
        }
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

    public List<Cell> chooseBestWay(Cell start, Cell end) {
        List<Node> bestWay = new ArrayList<>();
        Node nodeStart = getNodeFromCell(start);
        Node nodeEnd = getNodeFromCell(end);
        setAllHeuristic(nodeEnd);

        return null;
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
                        neighbors.add(nodes[row - 1][col]);
                    }
                    if (col - 1 >= 0) {
                        neighbors.add(nodes[row][col - 1]);
                    }
                    if (row + 1 < Constants.DEFAULT_GAME_TILES_HORIZONTALLY) {
                        neighbors.add(nodes[row + 1][col]);
                    }
                    if (col + 1 < Constants.DEFAULT_GAME_TILES_VERTICALLY) {
                        neighbors.add(nodes[row][col + 1]);
                    }
                }
            }
        }
        return neighbors;
    }
}