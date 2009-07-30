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
        boolean white_turn = true;

        m_view.printBoard(m_gameBoard);
        
        // Play the game until its over (quit, full board, or winning)
        while (!isGameOver()) {

               
        	// TODO : Check if player/computer, send player type+color to the method
        	// Get the next move
        	
        	if (white_turn) {
        		// TODO: get rid of the 0,1 magic numbers
        		move = m_players[0].makeMove();
        	} else {
        		move = m_players[1].makeMove();
        	}
               
        	if (move == null) { // the user wanted to quit
        		break;
            }
               
            if ( m_gameBoard.hasPawn(move) ) {
            	// one of the players has entered an occupied position 
            	m_view.showRepeatMoveMessage();
            	continue;
            }	   
            
            // the specified location is free - now we can place the pawn
            if (white_turn) {
            	m_gameBoard.PlaceWhitePawn(move);
            } else {
            	m_gameBoard.PlaceBlackPawn(move);
            }
            white_turn = !white_turn;
            
            m_view.printBoard(m_gameBoard);
        }

        
        m_view.showGoodbye();
    }
 
}
