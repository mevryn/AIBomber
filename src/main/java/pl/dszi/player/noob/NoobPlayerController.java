package pl.dszi.player.noob;

import pl.dszi.board.BoardGame;
import pl.dszi.board.Cell;
import pl.dszi.board.Direction;
import pl.dszi.player.Player;
import pl.dszi.player.PlayerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NoobPlayerController implements PlayerController {
    private boolean manual = false;
    private BoardGame boardGame;
    private Astar astar;
    private Point playerLocation;
    private Player player;
    private Direction direction;
    private List<Cell> way = new ArrayList<>();
    private NoobAI noobAI;

    public NoobPlayerController(BoardGame boardGame, NoobAI noobAI) {
        this.noobAI = noobAI;
        this.boardGame = boardGame;
        this.direction = Direction.SOUTH;
    }

    @Override
    public void pathFinding() {
        setPlayer();
        playerLocation = boardGame.getPlayerPosition(player);
        this.astar = new Astar(boardGame.getCells());
        if (noobAI.makeDecision(boardGame)) {
            boardGame.plantBomb(player);
        }
        if (way.size() == 0)
            way = astar.chooseBestWay(boardGame.getPlayerPositionCell(player), boardGame.getPlayerPositionCell(getClosestPlayer()));
        if (way.size() > 0 && playerLocation.equals(way.get(0).getBody().getLocation())) {
            way.remove(0);
        } else if (way.size() > 0)
            makeAMove(way.get(0));
    }

    private void setPlayer() {
        Optional<Player> playerOptional = boardGame.getMap().keySet().stream().filter(player1 -> player1.getPlayerController().equals(this)).findAny();
        playerOptional.ifPresent(this::setPlayer);
    }

    private Player getClosestPlayer() {
        Optional<Map.Entry<Player, Point>> playerOptional = boardGame.getMap().entrySet().stream().filter(playerPointEntry -> !playerPointEntry.getKey().equals(player)).min((o1, o2) -> (int) Math.round(Point.distance(o1.getValue().x, o1.getValue().y, o2.getValue().x, o2.getValue().y)));
        if (playerOptional.isPresent()) {
            return playerOptional.get().getKey();
        } else {
            System.err.println("Any other player not present for some reason. Programmer fault.");
            return null;
        }
    }

    public void makeAMove(Cell cell) {
        if (playerLocation.x > cell.getBody().x) {
            boardGame.move(player, Direction.WEST);
        } else if (playerLocation.x < cell.getBody().x) {
            boardGame.move(player, Direction.EAST);
        } else if (playerLocation.y > cell.getBody().y) {
            boardGame.move(player, Direction.NORTH);
        } else if (playerLocation.y < cell.getBody().y) {
            boardGame.move(player, Direction.SOUTH);
        }
    }

    @Override
    public void plantBomb() {
        boardGame.plantBomb(player);
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean checkIfManual() {
        return manual;
    }

}

