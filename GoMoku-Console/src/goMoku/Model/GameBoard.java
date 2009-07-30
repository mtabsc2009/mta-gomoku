package goMoku.Model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * The game board of the GoMoku Game
 */
public class GameBoard {

    // Constants
    public static final char BOARD_START_COLUMN = 'A';
    public static final char BOARD_LAST_COLUMN = 'Z';

    // Members
    private int m_boardSize;

    //private Pawn[][] m_gameBoard;
    private LinkedList<Pawn> m_gameBoard;

    public GameBoard(int boardSize) {
        m_boardSize = boardSize;
        ///m_gameBoard = new Pawn[m_boardSize][m_boardSize];
        m_gameBoard = new LinkedList<Pawn>();
               
    }

    private boolean PlacePawn(Pawn pawn)    {
        // validate location
        if ((pawn.getLocation().x <= 0 || pawn.getLocation().x > getSize()) ||
                (pawn.getLocation().y <= 0 || pawn.getLocation().y > getSize()))
        {
            return false;
         
        } 
        
        // check location is not occupied
        if ( hasPawn(pawn.getLocation()) ) {
            return false;
        }
        
        // check the board is not full. 
        // this condition is enforced at this level as well. 
        if ( getSize() >= m_boardSize*m_boardSize) {
        	return false;
        }
        
        System.out.println("[DEBUG] Added to list: "+pawn.getLocation().x + " " +pawn.getLocation().y );
        m_gameBoard.add(pawn);
        
        System.out.println("[DEBUG] list size: "+ getpawnsCount());
        ListIterator<Pawn> itr = m_gameBoard.listIterator();
    	
    	while (itr.hasNext()) {
    		Pawn p = itr.next();
    		System.out.println("[DEBUG] "+ p.getLocation().x+" "+p.getLocation().y);
    	}
            
        return true;
        
    }

    /**
     *
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceBlackPawn(Point pawnLocation) {
        return PlacePawn(new Pawn(getpawnsCount() +1, pawnLocation, PawnType.Black));
    }

    /**
     * 
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceWhitePawn(Point pawnLocation) {
        return PlacePawn(new Pawn(getpawnsCount() +1, pawnLocation, PawnType.White));
    }

    /**
     *
     * @param line the line of the pawn (non zero-based)
     * @param column the column of the pawn (Starting with BOARD_START_COLUMN)
     * @return true if a pawn is placed on the board. false if placement is empty.
     */
    public boolean hasPawn(int line, int column) {
    	return hasPawn(new Point(column,line));
    }

    /**
    *
    * @param location the location on the board
    * @return true is a pawn is placed on the board. false if placement is empty.
    */
    public boolean hasPawn(Point location)    {
    	if (findPawn(location) != null) {
    		return true;
    	}
    	return false;
    }
    
    public PawnType getPawnType(Point location) {
    	Pawn pawn = findPawn(location);
    	if (pawn == null) {
    		return null;
    	}
    	return pawn.getType();
    }
    
    public PawnType getPawnType(int line, int col) {
    	return getPawnType(new Point(col,line));
    }
    

    /**
     * TODO: add documentation
     */
    protected Pawn findPawn(Point location) {
    	ListIterator<Pawn> itr = m_gameBoard.listIterator();
    	
    	while (itr.hasNext()) {
    		Pawn p = itr.next();
    		if (p.getLocation().x == location.x && p.getLocation().y == location.y) {
    			return p;
    		}
    	}
    	
        return null;
    }
    /**
     * @return the Pawns Count
     */
    public int getpawnsCount() {
        return m_gameBoard.size();
    }

    /**
     * @return the size of the board
     */
    public int getSize() {
        return m_boardSize;
    }

}
