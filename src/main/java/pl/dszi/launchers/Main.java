package pl.dszi.launchers;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;
import pl.dszi.engine.Game;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;

import java.awt.*;

public class Main {
    public static void main(final String[] args){
        int maxHp = 3;
        Player pl = new Player("Player 1",3);
        Player p2 = new Player("Player 2",3);
        Player p3 = new Player("Player 3",3);
        Player p4 = new Player("Player 4",3);
        BoardGame boardGame = new BoardGame(Constants.DEFAULT_GAME_WIDTH,Constants.DEFAULT_GAME_HEIGHT,new Cell[13][11]);
        int spawnOffset=Constants.DEFAULT_OFFSET*2;
        boardGame.put(pl,new Point(Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE));
        boardGame.put(p2,new Point(Constants.DEFAULT_GAME_WIDTH-spawnOffset,Constants.DEFAULT_GAME_HEIGHT-spawnOffset));
        Game game = new Game(boardGame);
    }
}
