package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.board.BoardGameController;
import pl.dszi.engine.constant.Constants;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;

import java.util.List;

public class Game implements Runnable {


    private GameStatus gameStatus = GameStatus.STOP;

    private Renderer renderer;
    private BoardGameController boardGameController;
    public Game(BoardGameController boardGameController) {
        this.boardGameController = boardGameController;
        this.renderer = new Renderer2D();
        new Window("AiBomber", this.boardGameController.getBoardGame(), this.renderer);
        this.start();
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (gameStatus==GameStatus.RUNNING || gameStatus == GameStatus.TESTING) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (gameStatus == GameStatus.RUNNING || gameStatus == GameStatus.TESTING) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //   System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.start();
        gameStatus=GameStatus.TESTING;
    }

    private void stop() {
            gameStatus= GameStatus.STOP;
    }

    private void render() {
        renderer.render();
        renderer.renderBoardGame(boardGameController.getBoardGame().getInfo().getCells());
        boardGameController.getBoardGame().getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
        renderer.showGraphic();
    }

    private void tick() {
        this.aiMovement();
        if(gameStatus==GameStatus.RUNNING)
        boardGameController.getBoardGame().damageAllPlayersIntersectingWithExplosion();
        if(boardGameController.checkIfPlayersOnSamePosition(boardGameController.getBoardGame().getPlayerByName(Constants.PLAYER_1_NAME),boardGameController.getBoardGame().getPlayerByName(Constants.PLAYER_2_NAME))){
            boardGameController.resetGameWithNewCrates();
        }
    }


    private void aiMovement() {
        List<Player> autoPlayers = boardGameController.getBoardGame().getAllNonManualPlayers();
        autoPlayers.stream().filter(Player::isAlive).forEach(player -> player.getPlayerController().AIPlaning(player));
    }
}