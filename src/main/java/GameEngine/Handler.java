package GameEngine;

import BoardGame.BoardGame;
import BoardGame.Obstacle.Obstacle;
import BoardGame.Obstacle.SolidObstacle;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    private LinkedList<GameObject> gameObjects = new LinkedList<>();
    private LinkedList<SolidObstacle> solidObstacles = new LinkedList<>();
    private BoardGame boardGame;
    public void tick(){
        for(int i = 0 ; i<gameObjects.size();i++){
            GameObject gameObject = gameObjects.get(i);
            gameObject.tick();
        }
        boardGame.getBoardGameTileSize();
    }
    public void render(Graphics graphics){
        boardGame.render(graphics);

        for(int i = 0 ; i<gameObjects.size();i++){
            GameObject gameObject = gameObjects.get(i);
            gameObject.render(graphics);
        }
    }

    public void setBoardGame(BoardGame boardGame){
        this.boardGame = boardGame;
    }

    public void addObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public void removeObject(GameObject gameObject){
        gameObjects.remove(gameObject);
    }

    public void addObstacle(SolidObstacle solidObstacle){solidObstacles.add(solidObstacle);}

    public void removeObstacle (SolidObstacle solidObstacle){solidObstacles.remove(solidObstacle);}

}
