package pl.dszi.engine;

import pl.dszi.board.*;
import pl.dszi.engine.constant.Constants;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.List;

public class Game implements Runnable {


    private Boolean running = false;

    private Renderer renderer;
    private BoardGame boardGame;

    private BoardGame getBoardGame() {
        return boardGame;
    }

    public Game(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.renderer = new Renderer2D();
        new Window(Constants.DEFAULT_GAME_WIDTH, Constants.DEFAULT_GAME_HEIGHT, "AiBomber", this.boardGame, this.renderer);
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

    private void start() {
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop() {
        try {
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render() {
        renderer.render();
        renderer.renderBoardGame(boardGame.getInfo().getCells());
        boardGame.getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
        renderer.showGraphic();
    }

    private void tick() {
        this.checkIfPlayersAreAlive();
        this.aiMovement();
        this.checkForExplosionCollideWithPlayer();


        if(boardGame.getPlayerByName(Constants.PLAYER_2_NAME).getCurrentHp()<=0){
            System.out.println("dupa");
        }
    }

    private void checkForExplosionCollideWithPlayer(){
        for (Cell[] cells:getBoardGame().getInfo().getCells()) {
            for(Cell aCell:cells){
                if(aCell.getType()==CellType.CELL_BOOM_CENTER){
                    for(Player player: boardGame.getMap().keySet()) {
                        Rectangle playerBody = new Rectangle(boardGame.getPlayerPosition(player).x,boardGame.getPlayerPosition(player).y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
                        if(aCell.getBody().intersects(playerBody) && player.isMortal()){
                            player.damagePlayer();
                        }
                    }
                }
            }
        }
    }

    private void checkIfPlayersAreAlive(){
        for(Player player:boardGame.getMap().keySet()){
            player.setAlive();
        }
    }
    private void aiMovement() {
        List<Player> autoPlayers = boardGame.getAllNonManualPlayers();
        autoPlayers.stream().filter(Player::isAlive).forEach(player -> player.getPlayerController().pathFinding());
    }
}