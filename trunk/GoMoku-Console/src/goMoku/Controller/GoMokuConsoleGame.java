package goMoku.Controller;

import goMoku.View.IGoMokuView;
import java.awt.Point;


public class GoMokuConsoleGame extends GoMokuGame {

    public GoMokuConsoleGame(GoMokuGameType gameType, IGoMokuView view) throws UserAbortException{
        super(gameType, view);
    }


    public void Start() throws UserAbortException   {

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
            if (!isLegalMove) {
                m_view.printBoard(m_gameBoard);
                m_view.showIllegalMoveMessage();
                continue;
            }

            isWhiteTurn = !isWhiteTurn;
            
            m_view.printBoard(m_gameBoard);
            
        } /* end of game loop */

        
        if (getVictoryAchieved()) {
        	/*
        	 * after winning move move, the isWhiteTurn was updated
        	 * so the winner is the player who made the previous turn.
        	 */
        	boolean whitePlayerWon = !isWhiteTurn;
            m_view.showVictoryGameOver(whitePlayerWon, move);
            
        }
        else {
        	// Game is over, but no victory
        	m_view.showNutralGameOver();        	
        }
        
    } /* end of flow() */    
}
