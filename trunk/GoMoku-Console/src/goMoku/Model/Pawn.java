package goMoku.Model;

import java.awt.Point;

/**
 * The simple pawn of the game
 */
public abstract class  Pawn {

    private int m_sequence;
    private Point m_point;

    public Pawn(int sequence, Point point) {
        this.m_sequence = sequence;
        this.m_point = point;
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

}
