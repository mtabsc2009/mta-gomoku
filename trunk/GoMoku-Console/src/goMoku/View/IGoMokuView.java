package goMoku.View;

import goMoku.Model.GameBoard;
import java.awt.Point;
import goMoku.Controller.UserAbortException;

/**
 * This interface defines the requirements for a Gomoku game view implementation 
 */
public interface IGoMokuView {

    public int getBoardSize(int minValue, int maxValue) throws UserAbortException;
    public void showWelcome();
    public void showGoodbye();
    public void showStart();
    public void showOccupiedMoveMessage();
    public void showIllegalMoveMessage();
    public void printBoard(GameBoard board);
    public void setPlayersTitle(String blackPlayerTitle, String whitePlayerTitle);
    public Point readMove(String playerName, char playerMark) throws UserAbortException;
    public void showNutralGameOver();
    public void showVictoryGameOver(boolean whiteWon, Point lastMove);
}
