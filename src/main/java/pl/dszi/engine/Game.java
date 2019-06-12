package pl.dszi.engine;

import pl.dszi.board.BoardGameGenerator;
import pl.dszi.engine.constant.Constant;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;
import pl.dszi.player.noob.NoobRossaAI;

import java.util.List;

public class Game implements Runnable {


    public static GameStatus gameStatus = GameStatus.STOP;

    private Renderer renderer;
    private BoardGameGenerator boardGameController;
    private int[] crates;

    public Game(BoardGameGenerator boardGameController) {

        this.boardGameController = boardGameController;
        this.renderer = new Renderer2D();
        new Window("AiBomber", this.boardGameController.getBoardGame(), this.renderer);
        this.start();
    }


    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks =Double.MAX_VALUE;
        double ns = 1000000000 / amountOfTicks;
        if(gameStatus== GameStatus.RUNNING){
            ns = 1000000000 / 60.0;
            Constant.DEFAULT_SPEED=4;
        }
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (gameStatus!= GameStatus.STOP) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
                if(gameStatus==GameStatus.GENERATING){
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

    private void resetGame(){
        Player pl = new Player(Constant.PLAYER_1_NAME, 3);
        Player p2 = new Player(Constant.PLAYER_2_NAME, 3,new NoobPlayerController(boardGameController.getBoardGame(), new NoobRossaAI( )));
        boardGameController.getBoardGame().getMap().clear();
        boardGameController.getBoardGame().put(pl, Constant.PLAYER_1_STARTINGLOCATION);
        boardGameController.getBoardGame().put(p2, Constant.PLAYER_2_STARTINGLOCATION);
        boardGameController.getBoardGame().getInfo().setAllCellsToEmpty();
        boardGameController.generateFinalCrates();
        run();
    }
    private void start() {
        Thread thread = new Thread(this);
        thread.start();
        gameStatus=GameStatus.GENERATING;
    }

    private void stop() {
            gameStatus= GameStatus.STOP;
    }

    private void render() {
        if(Game.gameStatus!=GameStatus.GENERATING){
        renderer.render();
        renderer.renderBoardGame(boardGameController.getBoardGame().getInfo().getCells());
        boardGameController.getBoardGame().getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
        renderer.showGraphic();
        }
    }

    private void tick() {
        this.aiMovement();
        if(gameStatus==GameStatus.RUNNING)
        boardGameController.getBoardGame().damageAllPlayersIntersectingWithExplosion();
        if(gameStatus==GameStatus.GENERATING&&boardGameController.checkIfPlayersOnSamePosition(boardGameController.getBoardGame().getPlayerByName(Constant.PLAYER_1_NAME),boardGameController.getBoardGame().getPlayerByName(Constant.PLAYER_2_NAME))){
           crates= boardGameController.resetGameWithNewCrates();
        }
        if(boardGameController.generated){
            boardGameController.generated=false;
            Constant.BASIC_BOMB_EXPLOSION_TIMER=4;
            Constant.BASIC_BOMB_EXPLOSION_BURNING_TIMER=2;
            resetGame();
        }
    }


    private void aiMovement() {
        List<Player> autoPlayers = boardGameController.getBoardGame().getAllNonManualPlayers();
        autoPlayers.stream().filter(Player::isAlive).forEach(player -> player.getNoobPlayerController().AIPlaning(player));
    }
}