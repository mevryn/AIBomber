package pl.dszi.board.crategenerator;

import pl.dszi.board.BoardGame;
import pl.dszi.engine.Game;
import pl.dszi.engine.GameStatus;
import pl.dszi.engine.constant.Constant;
import pl.dszi.player.Player;

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
        this.crateGenerator = new CrateGenerator(Constant.DEFAULT_CRATE_AMOUNT);
    }

    public void initializeCrates() {
        getBoardGame().getInfo().setAllCellsToEmpty();
        this.crateGenerator.randomizeCrateCells(boardGame.getCells(), boardGame.getInfo(), beatPop);
    }


    public BoardGame getBoardGame() {
        return boardGame;
    }

    private void setAllCellsEmpty() {
        boardGame.getInfo().setAllCellsToEmpty();
    }


    public void generateFinalCrates() {
        crateGenerator.createCrates(boardGame.getCells(), crateGenerator.getBestGen());
    }

    public void resetGameWithNewCrates() {
        Player player1 = boardGame.getPlayerByName(Constant.PLAYER_1_NAME);
        Player player2 = boardGame.getPlayerByName(Constant.PLAYER_2_NAME);

        if (beatenGens > Constant.GENERATIONS_TO_BEAT_TO_GENERATE_CRATES || (redundantScore.size() >= Constant.MAXIMUM_REDUNDAND_SCORE_OF_POPULATION && beatenGens > 0)) {
            Game.gameStatus = GameStatus.RUNNING;
            generated = true;
            crateGenerator.getBestGen();
            return;
        }
        if (beatPop > Constant.POPULATIONS_TO_POP_NEW_GENERATION - 1) {
            crateGenerator.reproduction(crateGenerator.makeSelectionWithRanking());
            beatPop = 0;
            beatenGens++;
        }
        if (boardGame.checkIfPlayersOnSamePosition(boardGame.getPlayerByName(Constant.PLAYER_1_NAME), boardGame.getPlayerByName(Constant.PLAYER_2_NAME))) {
            boardGame.getMap().replace(player1, Constant.PLAYER_1_STARTINGLOCATION);
            boardGame.getMap().replace(player2, Constant.PLAYER_2_STARTINGLOCATION);
            setAllCellsEmpty();
            if (redundantScore.size() == 0 || redundantScore.contains(player2.getAIController().getActionCounter())) {
                redundantScore.add(player2.getAIController().getActionCounter());
            } else {
                redundantScore.clear();
            }
            crateGenerator.setPopulationScore(beatenGens, player2.getAIController().getActionCounter());
            initializeCrates();
            player2.getAIController().setActionCounter(0);
            beatPop++;
        }
        crateGenerator.returnRandomCrates();
    }
}
