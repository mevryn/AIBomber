package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Direction;
import pl.dszi.player.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput extends KeyAdapter {

    BoardGame boardGame;

    public KeyInput(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public void keyPressed(KeyEvent e){
        Player player = boardGame.getPlayerByName(Constants.player1Name);
        int key = e.getKeyCode();
        if( key == KeyEvent.VK_W){
            boardGame.move(player,Direction.NORTH);
        }else if (key == KeyEvent.VK_S){
            boardGame.move(player,Direction.SOUTH);
        }else if (key == KeyEvent.VK_A){
            boardGame.move(player,Direction.WEST);
        }else if (key == KeyEvent.VK_D){
            boardGame.move(player,Direction.EAST);
        }
    }
}
