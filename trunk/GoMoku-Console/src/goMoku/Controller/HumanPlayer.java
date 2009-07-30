package goMoku.Controller;

import goMoku.Model.GameBoard;
import goMoku.View.IGoMokuView;

import java.awt.Point;

public class HumanPlayer extends Player {

	public HumanPlayer (GameBoard board, IGoMokuView view, String name, char mark) {
		super(board,view,name, mark);
	}
	public Point makeMove() {
		return m_view.readMove(m_name, m_mark);
	}
	
} 
