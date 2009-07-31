package goMoku.Controller;

import goMoku.Model.GameBoard;
//import goMoku.Model.Pawn;
///import goMoku.Model.WhitePawn;
///import goMoku.Model.BlackPawn;
import goMoku.View.IGoMokuView;
import goMoku.Model.PawnType;

import goMoku.View.GoMokuConsoleView;
import java.awt.Point;

public abstract class GoMokuGame {

    // Constants
    protected static final int BLACK_PLAYER_INDEX = 1;
    protected static final int WHITE_PLAYER_INDEX = 0;
    public static final int MIN_BOARD_SIZE = 15;
    public static final int MAX_BOARD_SIZE = 19;
    public static final String COMPUTER_TITLE = "Computer";
    public static final String USER_TITLE = "Human";
    protected static final int WINNING_STRIKE_LENGTH = 5;

    private boolean m_victoryAchieved;
    protected boolean isWhiteTurn;

    public GoMokuGame(GoMokuGameType gameType, IGoMokuView view) {
        m_view = view;
        m_gameType = gameType;
        m_players = new Player[2];
        m_victoryAchieved = false;
        isWhiteTurn = true;
    }

    /*
     * we split the initialization into two parts.
     * first, the constructor is called, and then, after we've read the board size, and created the board,
     * the initGame function is called. 
     * FIXME: (don't agree) that's a mass (?). re-factor it or explain it better
     */
    public boolean initGame() {
    	
    	int boardSize = m_view.getBoardSize(MIN_BOARD_SIZE,MAX_BOARD_SIZE);
        if ( boardSize <=0 ) {	// getBoardSize will return <= 0 in case of an error 
            return false;
        }
    	
        m_gameBoard = new GameBoard(boardSize);

        switch (m_gameType) {
        case ComputerVSComputer: 
        	m_players[WHITE_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
        	m_players[BLACK_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	break;
        case ComputerVSUser:     
    		m_players[WHITE_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
        	m_players[BLACK_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	break;
        case UserVSComputer:     
    		m_players[WHITE_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
    		m_players[BLACK_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	break;
        case UserVSUser:         
    		m_players[WHITE_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
    		m_players[BLACK_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE, 
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	break;
        }
        
        return true;
    }
    
    
    public boolean isGameOver() {
    	
    	int boardSize = m_gameBoard.getSize();
    	if (m_gameBoard.getPawnsCount() >= m_gameBoard.getPawnsMaxCount()) {
    		return true;
    	}
    	
    	int lineIndex, colIndex;
    	
    	for ( lineIndex = 1 ; lineIndex <= boardSize; ++lineIndex) {
    		for ( colIndex = 1 ; colIndex <= boardSize; ++colIndex) {
    			Point cell = new Point(colIndex, lineIndex);
    			if (m_gameBoard.hasPawn(cell)) {
    				if (isWinningCell(cell, boardSize, m_gameBoard.getPawnType(cell))) {
                                        m_victoryAchieved = true;
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
    	 
    	 for (i = 0 ; i < WINNING_STRIKE_LENGTH ; ++i) {
    		 if (m_gameBoard.getPawnType(location.y + i*direction.y, location.x + i*direction.x) != pawnType) {
    			foundRow = false;
    			break;
    		 }
    	 }
    			
    	 return foundRow;
    }
    
    private boolean isWinningCell(Point location, int boardSize, PawnType pawnType) {

    	 Point lineDirection		= new Point(1,0);
    	 Point colDirection 		= new Point(0,1);
    	 Point diagDirection 		= new Point(1,1);
    	 Point backDiagDirection	= new Point(-1,1);
    	 
    	 if (m_gameBoard.hasPawn(location) == false) {
    		 return false;
    	 }
    	 
    	 return (hasFiveRow(location, lineDirection, boardSize, pawnType) ||
    			 hasFiveRow(location, colDirection, boardSize, pawnType)  ||
    			 hasFiveRow(location, diagDirection, boardSize, pawnType) ||
    			 hasFiveRow(location, backDiagDirection, boardSize, pawnType));
    			
    }
    
    
    
    protected IGoMokuView m_view;
    protected GoMokuGameType m_gameType;
    protected GameBoard m_gameBoard;
    protected Player[] m_players;

    public boolean getVictoryAchieved() {
        return m_victoryAchieved;
    }
    

}
