package goMoku.View;

import goMoku.Model.GameBoard;

/**
 *
 */
public interface IGoMokuView {

    public int GetBoardSize(int minSize, int maxSize);
    public void ShowWelcome();
    public void ShowGoodbye();
    public void ShowStart();
    public void PrintBoard(GameBoard board);
    public void SetPlayersTitle(String blackPlayerTitle, String whitePlayerTitle);
    public String GetMove();
}
