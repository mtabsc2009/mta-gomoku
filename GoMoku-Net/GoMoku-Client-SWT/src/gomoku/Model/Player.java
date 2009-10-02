package gomoku.Model;

import gomoku.Model.GameBoard;
import java.awt.Point;
import java.io.Serializable;

/**
 * Player is an abstract class. 
 * The derived class is required to implement the makeMove method. 
 */
public abstract class Player implements Serializable{
	
	protected GameBoard m_board;
	/** player's name */
	protected String m_name;

	Player(GameBoard board, String name) {
		m_board = board;
		m_name = name;
	}
	
	/**
	 * 
	 * @return a move received from the player.
	 * @throws UserAbortException any input operations done by the view 
	 * 		 	object might throw this exception.
	 */
	public abstract Point makeMove();

    public String getName()
    {
        return m_name;
    }

}
