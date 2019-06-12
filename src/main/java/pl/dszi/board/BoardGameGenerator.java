package pl.dszi.board;

import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardGameGenerator {
    private BoardGame boardGame;
    private CrateGenerator crateGenerator;
    private int beatenGens = 0;
    private int beatPop = 0;

    private List<Integer> redundantScore = new ArrayList<>();

    public boolean generated = false;

    public BoardGameGenerator(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.crateGenerator = new CrateGenerator(75);
    }

    public void initializeCrates() {
        this.crateGenerator.randomizeCrateCells(boardGame.getCells(), boardGame.getInfo(), beatPop);
    }

    public boolean checkIfPlayersOnSamePosition(Player player1, Player player2) {
        return boardGame.getPlayerPosition(player1).equals(boardGame.getPlayerPosition(player2));
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    private void setAllCellsEmpty() {
        for (int i = 0; i < boardGame.getInfo().getCells().length; i++) {
            for (int j = 0; j < boardGame.getInfo().getCells()[i].length; j++) {
                if (boardGame.getInfo().getCells()[i][j].getType() != CellType.CELL_WALL)
                    boardGame.getInfo().getCells()[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i, j));
            }
        }
    }


    public void generateFinalCrates() {
        crateGenerator.createCrates(boardGame.getCells(), crateGenerator.getBestGen());
    }

    public int[] resetGameWithNewCrates() {
        Player player1 = boardGame.getPlayerByName(Constant.PLAYER_1_NAME);
        Player player2 = boardGame.getPlayerByName(Constant.PLAYER_2_NAME);

        if (beatenGens > 20 || (redundantScore.size() >= 3 && beatenGens>0)) {
            Game.gameStatus = GameStatus.RUNNING;
            generated = true;
            return crateGenerator.getBestGen();
        }
        if (beatPop > 9) {
            crateGenerator.reproduction(crateGenerator.makeSelectionWithRanking());
            beatPop = 0;
            beatenGens++;
        }
        if (checkIfPlayersOnSamePosition(boardGame.getPlayerByName(Constant.PLAYER_1_NAME), boardGame.getPlayerByName(Constant.PLAYER_2_NAME))) {
            boardGame.getMap().replace(player1, Constant.PLAYER_1_STARTINGLOCATION);
            boardGame.getMap().replace(player2, Constant.PLAYER_2_STARTINGLOCATION);
            setAllCellsEmpty();
            if (redundantScore.size() == 0 || redundantScore.contains(player2.getNoobPlayerController().getActionCounter())) {
                redundantScore.add(player2.getNoobPlayerController().getActionCounter());
            } else {
                redundantScore.clear();
            }
            crateGenerator.setPopulationScore(beatenGens, player2.getNoobPlayerController().getActionCounter());
            initializeCrates();
            player2.getNoobPlayerController().setActionCounter(0);
            beatPop++;
        }
        return crateGenerator.returnRandomCrates();
    }
}
