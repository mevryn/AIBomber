package pl.dszi.launchers;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constants;
import pl.dszi.engine.Game;
import pl.dszi.player.ManualPlayerController;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;
import pl.dszi.player.noob.NoobRossaAI;

import java.awt.*;

public class Main {
    public static void main(final String[] args) {
        int maxHp = 3;
        BoardGame boardGame = new BoardGame(new Cell[Constants.DEFAULT_GAME_TILES_HORIZONTALLY][Constants.DEFAULT_GAME_TILES_VERTICALLY]);
        Player pl = new Player(Constants.PLAYER_1_NAME, 3,new ManualPlayerController());
        Player p2 = new Player(Constants.PLAYER_2_NAME, 3,new NoobPlayerController(boardGame,new NoobRossaAI()));
        //Player p3 = new Player(Constants.PLAYER_2_NAME, 3,new NoobPlayerController(boardGame,new NoobRossaAI()));
        //Player p4 = new Player(Constants.PLAYER_2_NAME, 3,new NoobPlayerController(boardGame,new NoobRossaAI()));
        boardGame.put(pl, Constants.player1StartingLocation);
        boardGame.put(p2,Constants.player2StartingLocation);
        //boardGame.put(p3,Constants.player3StartingLocation);
        //aaboardGame.put(p4,Constants.player4StartingLocation);
        Game game = new Game(boardGame);
    }
}
