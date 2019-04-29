package pl.dszi.player.noob;

import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;

import java.util.ArrayList;
import java.util.List;

public class Astar {
    private Node[][] nodes;
    private Cell[][] cells;
    private int cost = 1;
    private boolean visited = false;

    public Astar(Cell[][] cells) {
        this.cells = cells;
        this.nodes = new Node[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                this.nodes[i][j]= new Node(cells[i][j]);
            }
        }
    }
}