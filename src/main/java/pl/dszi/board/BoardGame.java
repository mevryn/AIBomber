package pl.dszi.board;

import pl.dszi.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class BoardGame {
    private Map<Player,Point> map = new HashMap<>();

    public boolean put(Player player,Point point){
        if(checkIfFieldIsEmpty(point)){
            map.put(player,point);
            return true;
        }else{
            System.err.println("Point is not empty");
            return false;
        }

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
        if(point.x > 0 || point.y > 0 ) {
            System.err.println("Target space is out of boarda");
            return true;
        }
        else
            return false;
    }
    boolean checkIfFieldIsEmpty(Point point){
        if(map.containsValue(point)){
            return false;
        }else
            return true;
    }
}
