package pl.dszi.engine;

import pl.dszi.board.crategenerator.BoardGameGenerator;
import pl.dszi.engine.constant.Constant;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;
import pl.dszi.player.noob.AIController;

import java.util.List;

public class Game implements Runnable {


    public static GameStatus gameStatus = GameStatus.STOP;

    private Renderer renderer;
    private BoardGameGenerator boardGameGen;

    public Game(BoardGameGenerator boardGameController) {

        this.boardGameGen = boardGameController;
        this.renderer = new Renderer2D();
        new Window("AiBomber", this.boardGameGen.getBoardGame(), this.renderer);
        this.start();
    }


    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = Double.MAX_VALUE;
        double ns = 1000000000 / amountOfTicks;
        if (gameStatus == GameStatus.RUNNING) {
            ns = 1000000000 / 60.0;
            Constant.DEFAULT_SPEED = 4;
        }
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (gameStatus != GameStatus.STOP) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
                if (gameStatus == GameStatus.GENERATING) {
                    renderer.renderLauncherWhileLoading();
                }
            }
            render();

            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void resetGame() {
        Player pl = new Player(Constant.PLAYER_1_NAME, 3);
        Player p2 = new Player(Constant.PLAYER_2_NAME, 3, new AIController(boardGameGen.getBoardGame()));
        boardGameGen.getBoardGame().getMap().clear();
        boardGameGen.getBoardGame().put(pl, Constant.PLAYER_1_STARTINGLOCATION);
        boardGameGen.getBoardGame().put(p2, Constant.PLAYER_2_STARTINGLOCATION);
        boardGameGen.generateFinalCrates();

        run();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.start();
        gameStatus = GameStatus.GENERATING;
    }

    private void stop() {
        gameStatus = GameStatus.STOP;
    }

    private void render() {
        if (Game.gameStatus != GameStatus.GENERATING) {
            renderer.render();
            renderer.renderBoardGame(boardGameGen.getBoardGame().getInfo().getCells());
            boardGameGen.getBoardGame().getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
            renderer.showGraphic();
        }
    }

    private void tick() {
        this.aiMovement();
        if (gameStatus == GameStatus.RUNNING)
            boardGameGen.getBoardGame().damageAllPlayersIntersectingWithExplosion();
        if (gameStatus == GameStatus.GENERATING && boardGameGen.getBoardGame().checkIfPlayersOnSamePosition(boardGameGen.getBoardGame().getPlayerByName(Constant.PLAYER_1_NAME), boardGameGen.getBoardGame().getPlayerByName(Constant.PLAYER_2_NAME))) {
            boardGameGen.resetGameWithNewCrates();
        }
        if (boardGameGen.generated) {
            boardGameGen.generated = false;
            Constant.BASIC_BOMB_EXPLOSION_TIMER = 4;
            Constant.BASIC_BOMB_EXPLOSION_BURNING_TIMER = 2;
            resetGame();
        }
    }


    private void aiMovement() {
        List<Player> autoPlayers = boardGameGen.getBoardGame().getAllNonManualPlayers();
        autoPlayers.stream().filter(Player::isAlive).forEach(player -> player.getAIController().AIPlaning(player));
    }
}