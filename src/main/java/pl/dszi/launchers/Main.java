package pl.dszi.launchers;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.engine.Constants;
import pl.dszi.engine.Game;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;

import java.awt.*;

public class Main {
    public static void main(final String[] args) {
        int maxHp = 3;

       // Player p3 = new Player("Player 3", 3);
      //  Player p4 = new Player("Player 4", 3);
        BoardGame boardGame = new BoardGame(Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT, new Cell[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY]);
        Player pl = new Player(Constants.player1Name, 3,new ManualPlayerController());
        Player p2 = new Player(Constants.player2Name, 3,new NoobPlayerController(boardGame));
        int spawnOffset = Constants.DEFAULT_OFFSET * 2;
        boardGame.put(pl, new Point(Constants.DEFAULT_CELL_SIZE, Constants.DEFAULT_CELL_SIZE));
        boardGame.put(p2, new Point(Constants.DEFAULT_GAME_WIDTH - spawnOffset, Constants.DEFAULT_GAME_HEIGHT - spawnOffset));
        Game game = new Game(boardGame);
    }
}
