package pl.dszi.board;

import pl.dszi.engine.constant.Constants;
import pl.dszi.player.Player;

import java.awt.*;
import java.util.Collection;

public class BoardGameController {
    private BoardGame boardGame;
    private CrateGenerator crateGenerator;
    private int beatenGens=0;
    private int beatPop=0;

    public BoardGameController(BoardGame boardGame) {
        this.boardGame = boardGame;
        this.crateGenerator= new CrateGenerator(50);
    }

    public void initializeCrates(){
        this.crateGenerator.randomizeCrateCells(boardGame.getCells(),boardGame.getInfo(),beatenGens);
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


    public int[] resetGameWithNewCrates(){
        Player player1=boardGame.getPlayerByName(Constants.PLAYER_1_NAME);
        Player player2=boardGame.getPlayerByName(Constants.PLAYER_2_NAME);
        if(beatPop>=3){
            return crateGenerator.getBestGen();
        }
        if(beatenGens>9){
            crateGenerator.reproduction(crateGenerator.makeSelectionWithRanking());
            beatenGens=0;
            beatPop++;
        }
        if(checkIfPlayersOnSamePosition(boardGame.getPlayerByName(Constants.PLAYER_1_NAME),boardGame.getPlayerByName(Constants.PLAYER_2_NAME)))
        {
            boardGame.getMap().replace(player1,Constants.PLAYER_1_STARTINGLOCATION);
            boardGame.getMap().replace(player2,Constants.PLAYER_2_STARTINGLOCATION);
          setAllCellsEmpty();
          crateGenerator.setPopulationScore(beatenGens,player2.getPlayerController().getActionCounter());
          System.out.println(beatenGens+": "+player2.getPlayerController().getActionCounter());
          initializeCrates();
            player2.getPlayerController().setActionCounter(0);
            beatenGens++;
        }
        return crateGenerator.returnRandomCrates();
    }
}
