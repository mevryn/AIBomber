package pl.dszi.engine;

import pl.dszi.board.BoardGame;
import pl.dszi.board.BombCell;
import pl.dszi.board.Cell;
import pl.dszi.board.ExplosionCell;
import pl.dszi.gui.Window;
import pl.dszi.gui.renderer.Renderer;
import pl.dszi.gui.renderer.Renderer2D;
import pl.dszi.player.Player;
import pl.dszi.player.noob.NoobPlayerController;

import java.awt.*;
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
        renderer.renderBoardGame(boardGame.getInfo().getCells());
        renderer.renderCrates(boardGame.getInfo().getCrates());
        renderer.renderBomb(boardGame.getBombs());
        renderer.renderExplosions(boardGame.getExplosionCells());
        boardGame.getMap().forEach((player, point) -> renderer.renderPlayer(player, point));
        renderer.showGraphic();
    }

    private void tick() {
        this.aiMovement();
        this.checkForBombsToDetonate();
        this.checkForExplosionCollideWithPlayer();
        this.checkForExplosionToEstinguish();
        this.checkIfPlayersAreAlive();
        this.boardGame.deleteCrateOnExplosion();
        if(boardGame.getPlayerByName(Constants.PLAYER_2_NAME).getCurrentHp()<=0){
            System.out.println("dupa");
        }
        // System.out.println(boardGame.getPlayerPosition(boardGame.getPlayerByName(Constants.PLAYER_1_NAME)));
    }

    private void checkForExplosionCollideWithPlayer(){
        for(int i=0;i<boardGame.getExplosionCells().size();i++){
            for(Player player: boardGame.getMap().keySet()){
                Rectangle playerBody = new Rectangle(boardGame.getPlayerPosition(player).x,boardGame.getPlayerPosition(player).y,Constants.DEFAULT_CELL_SIZE,Constants.DEFAULT_CELL_SIZE);
                if(boardGame.getExplosionCells().get(i).getBody().intersects(playerBody) &&
                        !boardGame.getExplosionCells().get(i).checkIfPlayerWasAlreadyDamaged(player)&&
                            player.isMortal()){
                    boardGame.getExplosionCells().get(i).addAlreadyDamagedPlayer(player);
                    player.damagePlayer();
                }
            }
        }
    }

    private void checkIfPlayersAreAlive(){
        for(Player player:boardGame.getMap().keySet()){
            if(player.getCurrentHp()<=0){
                boardGame.getMap().remove(player);
            }
        }
    }
    private void aiMovement() {
        List<Player> autoPlayers = boardGame.getAllNonManualPlayers();
        for (Player autoPlayer : autoPlayers) {
            autoPlayer.getPlayerController().pathFinding();
        }
    }

    private void checkForExplosionToEstinguish(){
        for(ExplosionCell explosionCell:boardGame.getExplosionCells()){
            if (!explosionCell.isBurning()){
                boardGame.getExplosionCells().remove(explosionCell);
            }
        }
    }

    private void checkForBombsToDetonate() {
        for (BombCell entry : boardGame.getBombs()) {
            if (entry.isReadyToExplode()) {
                entry.getPlayer().detonateBomb();
                boardGame.detonateBomb(entry);
                boardGame.getBombs().remove(entry);
            }
        }
    }
}