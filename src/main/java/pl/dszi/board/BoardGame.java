package pl.dszi.board;

import pl.dszi.engine.Constants;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoardGame {
    private Map<Player,Point> map = new HashMap<>();
    public final int  width;
    public final int height;

    public Cell[][] getCells() {
        return cells;
    }

   public final Cell[][] cells;

    public BoardGame(int width, int height,Cell[][] cells) {
        this.width = width;
        this.height = height;
        this.cells= cells;
        setBoard();
    }

    private void setBoard(){
        boolean even = true;
        for(int i = 0; i<cells.length;i++){
            for(int j = 0; j<cells[i].length;j++){
                this.cells[i][j] = new Cell(CellType.CELL_EMPTY,new Point(i*Constants.DEFAULT_CELL_SIZE,j*Constants.DEFAULT_CELL_SIZE),even);
                if(even)
                    even = false;
                    else
                    even = true;
            }
        }
    }
    public boolean put(Player player, Point point){
        if(checkIfFieldIsEmpty(point)){
            map.put(player,point);
            return true;
        }else{
            System.err.println("Point is not empty");
            return false;
        }

    }

    public Map<Player, Point> getMap() {
        return map;
    }

    public Point getPlayerPosition(Player player){
        return map.get(player);
    }

    public Point move(Player player,Direction direction){
        Point newPoint= new Point(getPlayerPosition(player).x+direction.x,getPlayerPosition(player).y+direction.y);
        if(map.containsValue(newPoint) || !targetSpaceIsInside(newPoint)){
            System.err.println("Can't move here. Field is not empty");
            return getPlayerPosition(player);
        }else {
            map.replace(player, newPoint);
            return getPlayerPosition(player);
        }
    }

    public boolean targetSpaceIsInside(Point point){
        if((point.x > 0 || point.y > 0) && (point.x < Constants.DEFAULT_GAME_WIDTH || point.y < Constants.DEFAULT_GAME_HEIGHT)) {
            System.err.println("Target space is out of boarda");
            return true;
        }
        else
            return false;
    }
    boolean checkIfFieldIsEmpty(Point point){
        return !map.containsValue(point);
    }
}
