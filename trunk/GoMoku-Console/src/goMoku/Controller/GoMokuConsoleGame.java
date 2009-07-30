package goMoku.Controller;

////import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;
import java.awt.Point;

public class GoMokuConsoleGame extends GoMokuGame {
    private String m_blackTitle;
    private String m_whiteTitle;

    public GoMokuConsoleGame(GoMokuGameType gameType, IGoMokuView view) {
        super(gameType, view);

        switch (gameType) {
            case ComputerVSComputer: 
            	m_blackTitle = COMPUTER_TITLE; 
            	m_whiteTitle = COMPUTER_TITLE;
            	break;
            case ComputerVSUser:     
            	m_blackTitle = COMPUTER_TITLE; 
            	m_whiteTitle = USER_TITLE;
            	break;
            case UserVSComputer:     
            	m_blackTitle = USER_TITLE; 
            	m_whiteTitle = COMPUTER_TITLE;
            	break;
            case UserVSUser:         
            	m_blackTitle = USER_TITLE; 
            	m_whiteTitle = USER_TITLE;
        }
    }


    public void Start()
    {
        m_view.showWelcome();
        if (!initGame()) {
            m_view.showGoodbye();
            return;
        }
        
        m_view.setPlayersTitle(m_blackTitle, m_whiteTitle);
        m_view.showStart();

        Point move = null;

        m_view.printBoard(m_gameBoard);
        
        // Play the game until its over (quit, full board, or winning)
        while (!isGameOver()) {

        	// Get the next move
        	if (isWhiteTurn) {
        		move = m_players[WHITE_PLAYER_INDEX].makeMove();
        	} else {
        		move = m_players[BLACK_PLAYER_INDEX].makeMove();
        	}

                // the user wanted to quit
        	if (move == null) { 
        		break;
            }
               
            // one of the players has entered an occupied position
            if ( m_gameBoard.hasPawn(move) ) {
                m_view.printBoard(m_gameBoard);
                m_view.showOccupiedMoveMessage();
            	continue;
            }
            
            // the specified location is free - now we can place the pawn
            boolean isLegalMove = false;
            if (isWhiteTurn) {
            	isLegalMove = m_gameBoard.PlaceWhitePawn(move);
            } else {
            	isLegalMove = m_gameBoard.PlaceBlackPawn(move);
            }

            // Move exceeds board limits
            if (!isLegalMove)
            {
                m_view.printBoard(m_gameBoard);
                m_view.showIllegalMoveMessage();
                continue;
            }

            isWhiteTurn = !isWhiteTurn;
            
            m_view.printBoard(m_gameBoard);
        }

        // Game is over, but no victory
        if (!getVictoryAchieved())
        {
            // User didnt ask to quit
            if (move != null)
            {
                m_view.showNutralGameOver();
            }
        }
        else
        {
            int winnerIndex = isWhiteTurn ? BLACK_PLAYER_INDEX : WHITE_PLAYER_INDEX;
            m_view.showVictoryGameOver(String.format(m_players[winnerIndex].getFullPlayerTitle()), move);
        }
        
        m_view.showGoodbye();
    }
}
