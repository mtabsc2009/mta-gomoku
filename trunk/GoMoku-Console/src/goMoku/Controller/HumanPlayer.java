package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

import java.awt.Point;

/**
 * HumanPlayer implementation to the Player abstract class
 */
public class HumanPlayer extends Player {

	public HumanPlayer (GameBoard board, IGoMokuView view, String name, char mark) {
		super(board,view,name, mark);
	}
	
	/**
	 * Reads the move using the view object.
	 * 
	 * @return Move coordinates entered by the player  
	 */
	public Point makeMove() {
		return m_view.readMove(m_name, m_mark);
	}
	
} 
