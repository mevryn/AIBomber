package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.Collection;

public class BoardGameController {
    private BoardGame boardGame;
    private CrateGenerator crateGenerator;
    private int beatenPopulation=0;
    public BoardGameController(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.crateGenerator= new CrateGenerator(75);
    }

    public void initializeCrates(){
        this.crateGenerator.randomizeCrateCells(boardGame.getCells(),boardGame.getInfo(),beatenPopulation);
    }

    public boolean checkIfPlayersOnSamePosition(Player player1, Player player2){
        return boardGame.getPlayerPosition(player1).equals(boardGame.getPlayerPosition(player2));
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }

    private void setAllCellsEmpty(){
        for (int i = 0; i < boardGame.getInfo().getCells().length; i++) {
            for (int j = 0; j < boardGame.getInfo().getCells()[i].length; j++) {
                if(boardGame.getInfo().getCells()[i][j].getType()!=CellType.CELL_WALL)
                boardGame.getInfo().getCells()[i][j] = new Cell(CellType.CELL_EMPTY, new Point(i, j));
            }
        }
    }
    public void resetGameWithNewCrates(){
        Player player1=boardGame.getPlayerByName(Constants.PLAYER_1_NAME);
        Player player2=boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        if(beatenPopulation>=9){
            crateGenerator.reproduction(crateGenerator.makeSelectionWithRanking());
            beatenPopulation=0;
        }
        if(checkIfPlayersOnSamePosition(boardGame.getPlayerByName(Constants.PLAYER_1_NAME),boardGame.getPlayerByName(Constants.PLAYER_2_NAME)))
        {
            boardGame.getMap().replace(player1,Constants.PLAYER_1_STARTINGLOCATION);
            boardGame.getMap().replace(player2,Constants.PLAYER_2_STARTINGLOCATION);
          setAllCellsEmpty();
          crateGenerator.setPopulationScore(beatenPopulation,player2.getPlayerController().getActionCounter());
          System.out.println(beatenPopulation+": "+player2.getPlayerController().getActionCounter());
          initializeCrates();
            player2.getPlayerController().setActionCounter(0);
            beatenPopulation++;
        }
    }
}
