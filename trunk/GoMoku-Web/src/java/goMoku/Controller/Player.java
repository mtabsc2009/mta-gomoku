package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;
import java.awt.Point;

/**
 * Player is an abstract class. 
 * The derived class is required to implement the makeMove method. 
 */
public abstract class Player {
	
	protected GameBoard m_board;
	protected IGoMokuView m_view;
	/** player's name */
	protected String m_name;
	/** player's mark on the game board */
	protected char m_mark;

	Player(GameBoard board, IGoMokuView view, String name, char mark) {
		m_board = board;
		m_view = view;
		m_name = name;
		m_mark = mark;
	}
	
	/**
	 * 
	 * @return a move received from the player.
	 * @throws UserAbortException any input operations done by the view 
	 * 		 	object might throw this exception.
	 */
	abstract Point makeMove() throws UserAbortException;

	/**
	 *	@returns The players full title (contains players name and mark).  
	 */
    public String getFullPlayerTitle() {
    	return String.format("%s (%c)", m_name, m_mark);
    }

    public String getName()
    {
        return m_name;
    }

}
