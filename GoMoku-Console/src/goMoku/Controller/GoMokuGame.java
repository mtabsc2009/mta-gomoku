package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;
import goMoku.Model.PawnType;

import goMoku.View.GoMokuConsoleView;
import java.awt.Point;

public abstract class GoMokuGame {

    // Constants
	protected static final int WHITE_PLAYER_INDEX = 0;
	protected static final int BLACK_PLAYER_INDEX = 1;
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
     * TODO: FIX / COMMENT
     * we split the initialization into two parts.
     * first, the constructor is called, and then, after we've read the board size, and created the board,
     * the initGame function is called. 
     * FIXME: (don't agree) that's a mass (?). re-factor it or explain it better
     */
    public boolean initGame() {
    	
    	int boardSize = m_view.getBoardSize(MIN_BOARD_SIZE,MAX_BOARD_SIZE);
        if ( boardSize <=0 ) {	/* getBoardSize will return <= 0 in case of an error */ 
            return false;
        }
    	
        m_gameBoard = new GameBoard(boardSize);

        switch (m_gameType) {
        case ComputerVSComputer: 
        	m_players[WHITE_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
        	
        	m_players[BLACK_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	
        	m_view.setPlayersTitle(COMPUTER_TITLE, COMPUTER_TITLE);
        	break;
        	
        case ComputerVSUser:     
    		m_players[WHITE_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
        	
    		m_players[BLACK_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	
    		m_view.setPlayersTitle(USER_TITLE, COMPUTER_TITLE);
        	break;
        	
        case UserVSComputer:     
    		m_players[WHITE_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
    		
    		m_players[BLACK_PLAYER_INDEX] = new ComputerPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE,
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
    		
    		m_view.setPlayersTitle(COMPUTER_TITLE, USER_TITLE);
        	break;
        
        case UserVSUser:         
    		m_players[WHITE_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.WHITE_PLAYER_TITLE,
                        GoMokuConsoleView.WHITE_PLAYER_MARK);
    	
    		m_players[BLACK_PLAYER_INDEX] = new HumanPlayer(
                        m_gameBoard, 
                        m_view,
                        GoMokuConsoleView.BLACK_PLAYER_TITLE, 
                        GoMokuConsoleView.BLACK_PLAYER_MARK);
        	
    		m_view.setPlayersTitle(USER_TITLE, USER_TITLE);
    		break;
        }
        
        return true;
    }
    
    /**
     *  
     * @return true if [at least one of] the game-over conditions are met:
     * 			 <li> no more free cells on the board
     * 			 <li> one of the player achieved victory
     * 			<br>
     * 			otherwise returns false
     * 
     */
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

    
    /** 
     * Checks if a cell holds one of the (four) winning conditions. 
     * <br>
     * Cell holds a winning condition iff it begins a row (in one of 4 directions) of 
     * WINNING_STRIKE_LENGTH pawns of the as type.
     * <br>
     * The winning row is a row at one of the following directions:
     * <ul>
     * 	<li> horizontal (left-to-right) 
     * 	<li> vertical (upside-down)
     *	<li> main diagonal
     *	<li> secondary diagonal 
     * </ul>
     * 
     * @param location of the first cell in the row
     * @param boardSize size of board
     * @param pawnType the expected type of the row
     * @return	true if the cell at the specified location begins a winning row
     * 			<br>
     * 			false otherwise
     */
    private boolean isWinningCell(Point location, int boardSize, PawnType pawnType) {

    	 Point lineDirection		= new Point(1,0);
    	 Point colDirection 		= new Point(0,1);
    	 Point diagDirection 		= new Point(1,1);
    	 Point backDiagDirection	= new Point(-1,1);
    	 
    	 if (!m_gameBoard.hasPawn(location)) {
    		 return false;
    	 }
    	 
    	 return (hasWinningRow(location, lineDirection, boardSize, pawnType) ||
    			 hasWinningRow(location, colDirection, boardSize, pawnType)  ||
    			 hasWinningRow(location, diagDirection, boardSize, pawnType) ||
    			 hasWinningRow(location, backDiagDirection, boardSize, pawnType));
    }
    
    
    /**	TODO
     * Checks if a cell begins a winning row
     * 
     * @param location is the location of the first cell in the row
     * @param direction defines the type of row
     * @param boardSize boardSize size of board
     * @param pawnType pawnType the expected type of the row
     * @return true if the cell begins a winning row
     * @see isWinningCell method
     */ 
    private boolean hasWinningRow(Point location, Point direction, int boardSize, PawnType pawnType) {
    	
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
    
    protected IGoMokuView m_view;
    protected GoMokuGameType m_gameType;
    protected GameBoard m_gameBoard;
    protected Player[] m_players;

    public boolean getVictoryAchieved() {
        return m_victoryAchieved;
    }
    

}
