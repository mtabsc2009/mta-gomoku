package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

public class GoMokuConsoleGame extends GoMokuGame {

    private String m_blackTitle;
    private String m_whiteTitle;

    public GoMokuConsoleGame(GoMokuGameType gameType, IGoMokuView view) {
        super(gameType, view);

        switch (gameType) {
            case ComputerVSComputer: m_blackTitle = COMPUTER_TITLE; m_whiteTitle = COMPUTER_TITLE;
            case ComputerVSUser:     m_blackTitle = COMPUTER_TITLE; m_whiteTitle = USER_TITLE;
            case UserVSComputer:     m_blackTitle = USER_TITLE; m_whiteTitle = COMPUTER_TITLE;
            case UserVSUser:         m_blackTitle = USER_TITLE; m_whiteTitle = USER_TITLE;
        }
    }


    public void Start()
    {
        m_view.ShowWelcome();
        if (!InitGame()) {
            m_view.ShowGoodbye();
        } else {
           m_view.SetPlayersTitle(m_blackTitle, m_whiteTitle);
           m_view.ShowStart();

           boolean isGameOver = false;
           String  move = null;

           // Play the game untill its over (quit, full board, or winning)
           while (!isGameOver) {

               m_view.PrintBoard(m_gameBoard);

               // TODO : Check if player/compuer, send player type+color to the method
               // Get the next move
               move = m_view.GetMove();

               // If the user wanted to quit
               if (move == null) {
                   isGameOver = true;

               // Still playing
               } else{
                    // TODO : implement movement
               }
           }

           // Game is over, check why:
           // user wanted to quit
           if (move == null) {
               m_view.ShowGoodbye();
           }
           // TODO : add more checks

        }
    }

    private boolean InitGame() {
        int boardSize = m_view.GetBoardSize(GoMokuGame.MIN_BOARD_SIZE, GoMokuGame.MAX_BOARD_SIZE);
        if (boardSize <= 0) {
            return false;
        } else {
            m_gameBoard = new GameBoard(boardSize);
            return true;
        }
    }
}
