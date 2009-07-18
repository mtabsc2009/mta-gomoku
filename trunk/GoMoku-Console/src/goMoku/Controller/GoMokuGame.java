package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

public abstract class GoMokuGame {

    // Constants
    public static final int MIN_BOARD_SIZE = 15;
    public static final int MAX_BOARD_SIZE = 19;
    public static final String COMPUTER_TITLE = "Computer";
    public static final String USER_TITLE = "Human";


    public GoMokuGame(GoMokuGameType gameType, IGoMokuView view) {
        this.m_view = view;
        m_gameType = gameType;
    }

    public void InitGameBoard(int size) {
        m_gameBoard = new GameBoard(size);
    }

    protected IGoMokuView m_view;
    protected GoMokuGameType m_gameType;
    protected GameBoard m_gameBoard;

}
