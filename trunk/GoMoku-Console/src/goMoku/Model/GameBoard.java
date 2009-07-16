package goMoku.Model;

import java.awt.Point;

/**
 * The game board of the GoMoku Game
 */
public class GameBoard {

    public final int BOARD_SIZE = 15;

    private Pawn[][] m_gameBoard;
    private int m_pawnsCount;

    public GameBoard() {
        m_gameBoard = new Pawn[BOARD_SIZE][BOARD_SIZE];
        m_pawnsCount = 0;
    }

    private boolean PlacePawn(Pawn pawn)    {
        // if the pawn location exceeds the bounds
        if ((pawn.getLocation().x <= 0 || pawn.getLocation().x > BOARD_SIZE) ||
                (pawn.getLocation().y <= 0 || pawn.getLocation().y > BOARD_SIZE))
        {
            return false;
         // if a pawn already exists on this location
        } else if (getPawn(pawn.getLocation()) != null){
            return false;
        // OK
        } else{
            m_gameBoard[pawn.getLocation().x][pawn.getLocation().y] = pawn;
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
     * @param line the line of the pawn
     * @param column the column of the pawn
     * @return the pawn placed on the board.
     * @throws IndexOutOfBoundsException when given a location out side of the board.
     */
    public Pawn getPawn(int line, char column) throws IndexOutOfBoundsException {
        return getPawn(line, CharToInt(column));
    }

    private Pawn getPawn(int line, int column){
        return m_gameBoard[line][column];
    }

    private Pawn getPawn(Point location)    {
        return getPawn(location.x, location.y);
    }

    /**
     * @param column a charactor from A-Z or a-z
     * @return the index of the column from 0-26. if out of bounds return 0 (non zero-based)
     */
    private int CharToInt(char column){
        if (column >= 'A' && column <= 'Z') return column - 'A' + 1;
        else if (column >= 'a' && column >= 'z') return column - 'a' + 1;
        return 0;
    }
    
    /**
     * @return the Pawns Count
     */
    public int getpawnsCount() {
        return m_pawnsCount;
    }

}
