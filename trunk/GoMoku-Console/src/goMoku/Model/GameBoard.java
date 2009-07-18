package goMoku.Model;

import java.awt.Point;

/**
 * The game board of the GoMoku Game
 */
public class GameBoard {

    // Constants
    public static final char BOARD_START_COLUMN = 'A';
    private static final char BOARD_LAST_COLUMN = 'Z';

    // Members
    private int m_boardSize;

    private Pawn[][] m_gameBoard;
    private int m_pawnsCount;

    public GameBoard(int boardSize) {
        m_boardSize = boardSize;
        m_gameBoard = new Pawn[m_boardSize][m_boardSize];
        m_pawnsCount = 0;
    }

    private boolean PlacePawn(Pawn pawn)    {
        // if the pawn location exceeds the bounds
        if ((pawn.getLocation().x <= 0 || pawn.getLocation().x > getSize()) ||
                (pawn.getLocation().y <= 0 || pawn.getLocation().y > getSize()))
        {
            return false;
         // if a pawn already exists on this location
        } else if (getPawn(pawn.getLocation()) != null){
            return false;
        // OK
        } else{
            m_gameBoard[pawn.getLocation().x - 1][pawn.getLocation().y - 1] = pawn;
            m_pawnsCount++;
            
            return true;
        }
    }

    /**
     *
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceBlackPawn(Point pawnLocation) {
        return PlacePawn(new BlackPawn(getpawnsCount() +1, pawnLocation));
    }

    /**
     * 
     * @param pawnLocation the location on the board to put the pawn
     * @return true on success, false if out of bounds or pawn already exists
     */
    public boolean PlaceWhitePawn(Point pawnLocation) {
        return PlacePawn(new WhitePawn(getpawnsCount() +1, pawnLocation));
    }

    /**
     *
     * @param line the line of the pawn (non zero-based)
     * @param column the column of the pawn (Starting with BOARD_START_COLUMN)
     * @return the pawn placed on the board. null if placement is empty.
     * @throws IndexOutOfBoundsException when given a location out side of the board.
     */
    public Pawn getPawn(int line, char column) throws IndexOutOfBoundsException {
        return getPawn(line, CharToInt(column));
    }

    /**
     *
     * @param line the line of the pawn (non zero-based)
     * @param column the column of the pawn (non zero-based)
     * @return the pawn placed on the board. null if placement is empty.
     * @throws IndexOutOfBoundsException when given a location out side of the board.
     */
    public Pawn getPawn(int line, int column){
        return m_gameBoard[line - 1][column - 1];
    }

    private Pawn getPawn(Point location)    {
        return getPawn(location.x, location.y);
    }

    /**
     * @param column a charactor from A-Z or a-z
     * @return the index of the column from 0-26. if out of bounds return 0 (non zero-based)
     */
    private int CharToInt(char column){
        if (Character.toUpperCase(column) >= BOARD_START_COLUMN &&
            Character.toUpperCase(column) <= BOARD_LAST_COLUMN) return Character.toUpperCase(column) - BOARD_START_COLUMN + 1;
        return 0;
    }
    
    /**
     * @return the Pawns Count
     */
    public int getpawnsCount() {
        return m_pawnsCount;
    }

    /**
     * @return the size of the board
     */
    public int getSize() {
        return m_boardSize;
    }

}
