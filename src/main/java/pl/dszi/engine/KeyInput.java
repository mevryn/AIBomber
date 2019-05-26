package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Direction;
import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private BoardGame boardGame;

    public KeyInput(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public void keyPressed(KeyEvent e) {
        Player player = boardGame.getPlayerByName(Constants.PLAYER_1_NAME);
        int key = e.getKeyCode();
        try {
            if (key == KeyEvent.VK_W) {
                boardGame.move(player, Direction.NORTH);
            } else if (key == KeyEvent.VK_S) {
                boardGame.move(player, Direction.SOUTH);
            } else if (key == KeyEvent.VK_A) {
                boardGame.move(player, Direction.WEST);
            } else if (key == KeyEvent.VK_D) {
                boardGame.move(player, Direction.EAST);
            }
            if (key == KeyEvent.VK_SPACE) {
                if (player.getBombAmount() > player.getBombActualyTicking())
                    boardGame.plantBomb(player);
            }
        }catch(NullPointerException nullPointerException){
            System.out.println("You are dead");
        }
    }
}
