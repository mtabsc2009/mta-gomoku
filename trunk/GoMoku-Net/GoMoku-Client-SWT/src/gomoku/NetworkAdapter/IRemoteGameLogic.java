package gomoku.NetworkAdapter;

import gomoku.Model.*;
import java.awt.Point;

public interface IRemoteGameLogic {

    Player getCurrPlayer();
    Player getWinner();

    GameBoard getGameBoard();
    GoMokuGameType getGameType();

    /**
     *
     * @return true if [at least one of] the game-over conditions are met:
     * <li> no more free cells on the board
     * <li> one of the player achieved victory
     * <br>
     * otherwise returns false
     *
     */
    boolean isGameOver();
    boolean getVictoryAchieved();

    void makeFirstComputerMove();
    void makeMove(Point move);
}
