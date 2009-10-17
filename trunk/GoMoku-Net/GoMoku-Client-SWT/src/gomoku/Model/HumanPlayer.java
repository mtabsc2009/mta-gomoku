package gomoku.Model;

import java.io.Serializable;


/**
 * HumanPlayer implementation to the Player abstract class
 */
public class HumanPlayer extends Player implements Serializable{

	public HumanPlayer (GameBoard board, String name) {
		super(board,name);
	}
	
	/**
	 * Reads the move using the view object.
	 * 
	 * @return Move coordinates entered by the player  
	 */
	public Point makeMove() {
		//return m_view.readMove(m_name, m_mark);
            return new Point(0,0);  // TODO: remove this 
	}
	
} 
