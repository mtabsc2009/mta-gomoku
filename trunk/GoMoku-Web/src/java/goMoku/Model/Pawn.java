package goMoku.Model;

import java.awt.Point;
import goMoku.Model.PawnType;

/**
 * The simple pawn of the game
 */
public class Pawn {

    private int m_sequence;
    private PawnType m_type;   
    private Point m_point;

    public Pawn(int sequence, Point point, PawnType type) {
        this.m_sequence = sequence;
        this.m_point = point;
        this.m_type = type;
    }
    
    /**
     * @return the sequence when the pawn was put on the board
     */
    public int getSequence() {
        return m_sequence;
    }

    /**
     * @return the location of the pawn on the board
     */
    public Point getLocation() {
        return m_point;
    }
    
    /**
     * @return the sequence when the pawn was put on the board
     */
    public PawnType getType() {
        return m_type;
    }
}
