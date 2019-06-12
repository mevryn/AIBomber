package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constant;

import java.util.*;

class Astar {
    private Node[][] nodes;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node nodeEnd;


    Astar(Cell[][] cells) {
        this.nodes = new Node[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY];
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.nodes[i][j] = new Node(cells[i][j]);
            }
        }
        closedSet = new HashSet<>();
    }

    private Node getNodeFromCell(Cell cell) {
        return nodes[cell.getPoint().x][cell.getPoint().y];
    }

    private void setAllHeuristic(Node destinationNode) {
        for (Node[] node : nodes) {
            for (Node aNode : node) {
                aNode.setH(destinationNode);
            }
        }
    }


    List<Cell> chooseBestWay(Cell start, Cell end) {
        closedSet.clear();
        Node nodeStart = getNodeFromCell(start);
        nodeEnd = getNodeFromCell(end);
        setAllHeuristic(nodeEnd);
        openList.add(nodeStart);
        while (!isEmpty(openList)) {
            Node currentNode = openList.poll();
            closedSet.add(currentNode);
            assert currentNode != null;
            if (isFinalNode(currentNode)) {
                return getPath(currentNode);
            } else
                getNeighbors(currentNode);
        }
        return new ArrayList<>();
    }

    private List<Cell> getPath(Node currentNode) {
        List<Node> path = new LinkedList<>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        List<Cell> parsedPath = new ArrayList<>();
        for (Node node : path) {
            parsedPath.add(node.getCell());
        }
        return parsedPath;
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(nodeEnd);
    }


    private void getNeighbors(Node aNode) {
        int row;
        int col;
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                if (nodes[i][j].equals(aNode)) {
                    row = i;
                    col = j;
                    if (row - 1 >= 0) {
                        checkNode(aNode, row - 1, col);
                    }
                    if (col - 1 >= 0) {
                        checkNode(aNode, row, col - 1);
                    }
                    if (row + 1 < Constant.DEFAULT_GAME_TILES_HORIZONTALLY) {
                        checkNode(aNode, row + 1, col);
                    }
                    if (col + 1 < Constant.DEFAULT_GAME_TILES_VERTICALLY) {
                        checkNode(aNode, row, col + 1);
                    }
                }
            }
        }
    }

    private void checkNode(Node currentNode, int row, int col) {
        Node adjacentNode = nodes[row][col];
        if (adjacentNode.getCell().getType().walkable && !getClosedSet().contains(adjacentNode)) {
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode);
                if (changed) {
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private PriorityQueue<Node> getOpenList() {
        return openList;
    }

    Set<Node> getClosedSet() {
        return closedSet;

    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

}