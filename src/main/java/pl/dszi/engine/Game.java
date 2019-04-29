package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;

import java.util.List;

public class Game implements Runnable {


    private Thread thread;
    private Boolean running = false;

    private Renderer renderer;
    private BoardGame boardGame;

    public BoardGame getBoardGame() {
        return boardGame;
    }

    public Game(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.renderer = new Renderer2D();
        new Window(Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT, "AiBomber",this.boardGame, this.renderer);
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
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    void stop() {
        try {
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        renderer.render();
        renderer.renderBoardGame(boardGame.getCells());
        renderer.renderBomb(boardGame.getCells());
        boardGame.getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
        renderer.showGraphic();
    }

    private void tick() {
        List<Player> autoPlayers = boardGame.getAllNonManualPlayers();
        Cell[][] sampleCell = boardGame.getCells();
        for(int i = 0; i<autoPlayers.size();i++){
            autoPlayers.get(i).getPlayerController().makeAMove(sampleCell[0][2]);
        }

       // System.out.println(boardGame.getPlayerPosition(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
    }
}
