package goMoku.Controller;

import goMoku.Model.GameBoard;
//import goMoku.Model.Pawn;
///import goMoku.Model.WhitePawn;
///import goMoku.Model.BlackPawn;
import goMoku.View.IGoMokuView;
import goMoku.Model.PawnType;

import java.awt.Point;

public abstract class GoMokuGame {

    // Constants
    public static final int MIN_BOARD_SIZE = 15;
    public static final int MAX_BOARD_SIZE = 19;
    public static final String COMPUTER_TITLE = "Computer";
    public static final String USER_TITLE = "Human";


    public GoMokuGame(GoMokuGameType gameType, IGoMokuView view) {
        m_view = view;
        m_gameType = gameType;
        m_players = new Player[2];
       
    }

    /*
     * we split the initialization into two parts.
     * first, the constructor is called, and then, after we've read the board size, and created the board,
     * the initGame function is called. 
     * FIXME: that's a mass (?). re-factor it or explain it better
     */
    public boolean initGame() {
    	
    	int boardSize = m_view.getBoardSize(MIN_BOARD_SIZE,MAX_BOARD_SIZE);
        if ( boardSize <=0 ) {	// getBoardSize will return <= 0 in case of error 
            return false;
        }
    	
        m_gameBoard = new GameBoard(boardSize);

        switch (m_gameType) {
        case ComputerVSComputer: 
        	m_players[0] = new ComputerPlayer(m_gameBoard, m_view, COMPUTER_TITLE); 
        	m_players[1] = new ComputerPlayer(m_gameBoard, m_view, COMPUTER_TITLE);
        	break;
        case ComputerVSUser:     
    		m_players[0] = new ComputerPlayer(m_gameBoard, m_view, COMPUTER_TITLE); 
        	m_players[1] = new HumanPlayer(m_gameBoard, m_view, USER_TITLE);
        	break;
        case UserVSComputer:     
    		m_players[0] = new HumanPlayer(m_gameBoard, m_view, USER_TITLE); 
    		m_players[1] = new ComputerPlayer(m_gameBoard, m_view, USER_TITLE);
        	break;
        case UserVSUser:         
    		m_players[0] = new HumanPlayer(m_gameBoard, m_view, USER_TITLE); 
    		m_players[1] = new HumanPlayer(m_gameBoard, m_view, USER_TITLE);
        	break;
        }
        
        return true;
    }
    
    
    public boolean isGameOver() {
    	
    	int boardSize = m_gameBoard.getSize();
    	if (m_gameBoard.getpawnsCount() >= boardSize*boardSize) {
    		return true;
    	}
    	
    	int lineIndex, colIndex;
    	
    	for ( lineIndex = 1 ; lineIndex <= boardSize - 5 ; ++lineIndex) {
    		for ( colIndex = 1 ; colIndex <= boardSize - 5 ; ++colIndex) {
    			Point cell = new Point(colIndex, lineIndex);
    			if (m_gameBoard.hasPawn(cell)) {
    				if (isWinningCell(cell, boardSize, m_gameBoard.getPawnType(cell))) {
    					return true;
    				}
    			}
    		}
    	}
    	
    	return false;
    }
    
   
    /// TODO: comment this func 
    private boolean hasFiveRow(Point location, Point direction, int boardSize, PawnType pawnType) {
    	
    	 int i;
    	 boolean foundRow = true;
    	 
    	 for (i = 0 ; i < 5 ; ++i) {
    		 if (m_gameBoard.getPawnType(location.y + i*direction.y,location.x + i*direction.x) != pawnType) {
    			foundRow = false;
    			break;
    		 }
    	 }
    			
    	 return foundRow;
    }
    
    private boolean isWinningCell(Point location, int boardSize, PawnType pawnType) {
    	
    	 Point lineDirection = new Point(1,0);
    	 Point colDirection = new Point(0,1);
    	 Point diagDirection = new Point(1,1);
    	 
    	 if (m_gameBoard.hasPawn(location) == false) {
    		 return false;
    	 }
    	 
    	 return (hasFiveRow(location, lineDirection, boardSize, pawnType) ||
    			 hasFiveRow(location, colDirection, boardSize, pawnType) ||
    			 hasFiveRow(location, diagDirection, boardSize, pawnType) );
    			
    }
    
    
    
    protected IGoMokuView m_view;
    protected GoMokuGameType m_gameType;
    protected GameBoard m_gameBoard;
    protected Player[] m_players;
    

}
