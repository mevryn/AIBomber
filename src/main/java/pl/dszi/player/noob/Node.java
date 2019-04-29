package pl.dszi.player.noob;

import pl.dszi.board.Cell;

public class Node {
    private Cell cell;
    private Cell cellA;
    private Cell cellB;
    private Cell cellC;
    private boolean closed;
    private boolean visited;
    public Node(Cell cell) {
        this.cell = cell;
        if(cell.getType().walkable)
            closed = true;
        else
            closed = false;
        visited = false;
    }

}
