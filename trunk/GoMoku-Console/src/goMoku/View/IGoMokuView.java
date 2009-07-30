package goMoku.View;

import goMoku.Model.GameBoard;
import java.awt.Point;

/**
 *
 */
public interface IGoMokuView {

    public int getBoardSize(int minValue, int maxValue);
    public void showWelcome();
    public void showGoodbye();
    public void showStart();
    public void showRepeatMoveMessage();
    public void printBoard(GameBoard board);
    public void setPlayersTitle(String blackPlayerTitle, String whitePlayerTitle);
    public Point readMove(String playerName);
}
