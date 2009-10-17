

package gomoku.Model;

import java.io.Serializable;


/**
 * The game board of the GoMoku Game
 */
public class GameBoard implements Serializable{

    // Constants
    public static final char BOARD_START_COLUMN = 'A';
    public static final char BOARD_LAST_COLUMN = 'Z';

    // Members
    /** the size of the square board */
    private int m_boardSize;				
    /** the maximum number of pawns that can be placed on the board */
    private int m_maxPawnCount;				
    /** stores the pawns that are placed on the board */
    Pawn []  m_gameBoard;	

    // Public methods
    
    /**
     * GameBoard constructor
	 *
     * @param boardSize, the size of the board
     */
    public GameBoard(int boardSize) {
    	m_boardSize = boardSize;
        /* max number of elements on a square board */
        m_maxPawnCount = m_boardSize * m_boardSize;	
        m_gameBoard = new Pawn[m_maxPawnCount];

        int i;
        for (i = 0 ; i < m_gameBoard.length ; ++i)
            m_gameBoard[i] = null;
        
    }

    /**
     *
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceBlackPawn(Point pawnLocation) {
        return placePawn(new Pawn(getPawnsCount() +1, pawnLocation, PawnType.Black));
        
    }

    /**
     * 
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceWhitePawn(Point pawnLocation) {
        return placePawn(new Pawn(getPawnsCount() +1, pawnLocation, PawnType.White));
    }

    /**
     * Tests for pawn's existence
	 *
     * @param line the line of the pawn (one-based)
     * @param column the column of the pawn (one-based)
     * @return true if there is a pawn at the location. false otherwise. 
     */
    public boolean hasPawn(int line, int column) {
    	return hasPawn(new Point(column,line));
    }

    /**
     * Tests for pawn's existence
 	 *
     * @param location the location on the board (one-based coordinates)
     * @return true if there is a pawn at the location. false otherwise. 
     */
    public boolean hasPawn(Point location)    {
    	if (findPawn(location) != null) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Retrieves a pawn's type
	 *
     * @param location the location on the board (one-based coordinates)
     * @return PawnType if there is a pawn at the location. otherwise returns null.
     */
    public PawnType getPawnType(Point location) {
    	Pawn pawn = findPawn(location);
    	if (pawn == null) {
    		return null;
    	}

    	return pawn.getType();
    }
    
    /**
     * Retrieves a pawn's type
	 *
	 * @param line the line of the pawn (one-based)
     * @param column the column of the pawn (one-based)
     * @return PawnType if there is a pawn at the location. otherwise returns null.
     */
    public PawnType getPawnType(int line, int col) {
    	return getPawnType(new Point(col,line));
    }
    
    /**
     * @return number of pawns currently on the board
     */
    public int getPawnsCount() {
        int i, count = 0;
        for (i = 0 ; i < m_gameBoard.length ; ++i) {
            if (m_gameBoard[i] != null) {
                count++;
            }
        }
            
        return count;
    }

    /**
     * @return the size of the board
     */
    public int getSize() {
        return m_boardSize;
    }
    
	/**
     * @return max number of pawns that can be placed on the board
     */
    public int getPawnsMaxCount() {
		return m_maxPawnCount;
	}

    
    // Private/Protected methods
    
    /**
     * Finds a pawn at a specified location
	 *
	 * @param location the location on the board (one-based coordinates)
     * @return Pawn object if there is a pawn at the location. otherwise returns null.
     */
    protected Pawn findPawn(Point location) {
        int i;
        
        for (i = 0 ; i < m_gameBoard.length ; ++i) {
            if (m_gameBoard[i] != null) {
                if (m_gameBoard[i].getLocation().x == location.x &&
                        m_gameBoard[i].getLocation().y == location.y ) {
                    return m_gameBoard[i];
                }
            }
        }
    	
        return null;
    }

    /**
     * Adds a pawn to the pawn-list 
	 *
	 * @param a pawn object
     * @return true if pawn was successfully added. false otherwise. 
     */
    private boolean placePawn(Pawn pawn)    {
    	/* validate location is in range [1-size],[1-size] */
        if ((pawn.getLocation().x <= 0 || pawn.getLocation().x > getSize()) ||
                (pawn.getLocation().y <= 0 || pawn.getLocation().y > getSize())) {
            return false;
        } 
        
        /* check location is not occupied */
        if ( hasPawn(pawn.getLocation()) ) {
            return false;
        }
        
        /* check that the board is not full */ 
        if ( getPawnsCount() >= getPawnsMaxCount()) {
        	return false;
        }
        
        m_gameBoard[getPawnsCount()] = pawn;
        
        return true;
    }

}
