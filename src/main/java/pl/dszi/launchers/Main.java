package pl.dszi.launchers;

import pl.dszi.board.BoardGame;
import pl.dszi.board.BoardGameGenerator;
import pl.dszi.board.Cell;
import pl.dszi.engine.constant.Constant;
import pl.dszi.engine.Game;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;
import pl.dszi.player.noob.NoobRossaAI;

public class Main {
    public static void main(final String[] args) {
        BoardGame boardGame = new BoardGame(new Cell[Constant.DEFAULT_GAME_TILES_HORIZONTALLY][Constant.DEFAULT_GAME_TILES_VERTICALLY]);
        Constant.BASIC_BOMB_EXPLOSION_TIMER = 0;
        Constant.BASIC_BOMB_EXPLOSION_BURNING_TIMER = 0;
        Player pl = new Player(Constant.PLAYER_1_NAME, 3);
        Player p2 = new Player(Constant.PLAYER_2_NAME, 3, new NoobPlayerController(boardGame, new NoobRossaAI()));
        boardGame.put(pl, Constant.PLAYER_1_STARTINGLOCATION);
        boardGame.put(p2, Constant.PLAYER_2_STARTINGLOCATION);
        BoardGameGenerator boardGameController = new BoardGameGenerator(boardGame);
        boardGameController.initializeCrates();
        new Game(boardGameController);
    }
}
